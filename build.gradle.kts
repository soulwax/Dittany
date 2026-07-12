plugins {
    java
    application
}

group = "de.cirrus"
version = "1.0.1"

val javaVersion = 26

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

application {
    mainClass.set("de.cirrus.dittany.Dittany")
}

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(javaVersion)
}

tasks.withType<ProcessResources>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "de.cirrus.dittany.Dittany")
    }
}

val checkVersion = tasks.register("checkVersion") {
    group = "verification"
    description = "Fails if the version above disagrees with the newest CHANGELOG.md entry"

    val changelogText = providers.fileContents(layout.projectDirectory.file("CHANGELOG.md")).asText
    val declared = version.toString()

    doLast {
        val newest = Regex("""## \[(\d+\.\d+\.\d+)]""").find(changelogText.get())?.groupValues?.get(1)
            ?: throw GradleException("CHANGELOG.md has no released version entry like '## [1.2.3] - date'.")
        if (newest != declared) {
            throw GradleException(
                "Version mismatch: build.gradle.kts declares $declared " +
                    "but the newest CHANGELOG.md entry is $newest. Update both together."
            )
        }
    }
}

tasks.check {
    dependsOn(checkVersion)
}

tasks.register<Exec>("packageExe") {
    group = "distribution"
    description = "Builds a self-contained Windows application with a Dittany.exe launcher"
    dependsOn("installDist")

    val installDir = layout.buildDirectory.dir("install/${project.name}")
    // Keep runnable distributions outside build/ so Gradle can clean while the game is open.
    val outputDir = layout.projectDirectory.dir("dist")
    // jpackage always writes <dest>/<name>; the image is renamed to the versioned dir afterwards
    // so the launcher stays Dittany.exe.
    val rawImageDir = outputDir.dir(project.name)
    val appImageDir = outputDir.dir("${project.name}-${project.version}")
    val jpackage = File(
        System.getProperty("java.home"),
        if (System.getProperty("os.name").startsWith("Windows")) "bin/jpackage.exe" else "bin/jpackage"
    )

    inputs.dir(installDir)
    outputs.dir(appImageDir)

    doFirst {
        if (!jpackage.isFile) {
            throw GradleException(
                "jpackage was not found in the JDK running Gradle (${jpackage.absolutePath}). " +
                    "Run Gradle with a full JDK 26."
            )
        }
        outputDir.asFile.mkdirs()
        rawImageDir.asFile.deleteRecursively()
        appImageDir.asFile.deleteRecursively()
    }

    doLast {
        if (!rawImageDir.asFile.renameTo(appImageDir.asFile)) {
            throw GradleException("Could not rename ${rawImageDir.asFile} to ${appImageDir.asFile}.")
        }
    }

    executable(jpackage)
    args(
        "--type", "app-image",
        "--name", project.name,
        "--app-version", project.version.toString(),
        "--input", installDir.map { it.dir("lib") }.get().asFile,
        "--main-jar", "${project.name}-${project.version}.jar",
        "--main-class", application.mainClass.get(),
        "--dest", outputDir.asFile,
        "--java-options", "-Dfile.encoding=UTF-8"
    )

    // Run with -Pzip to also produce dist/Dittany-<version>.zip for sharing.
    if (providers.gradleProperty("zip").isPresent) {
        finalizedBy("packageZip")
    }
}

tasks.register<Zip>("packageZip") {
    group = "distribution"
    description = "Zips the packaged Windows application into dist/ for sharing"
    dependsOn("packageExe")

    from(layout.projectDirectory.dir("dist/${project.name}-${project.version}"))
    into("${project.name}-${project.version}")
    archiveFileName.set("${project.name}-${project.version}.zip")
    destinationDirectory.set(layout.projectDirectory.dir("dist"))
}
