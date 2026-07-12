plugins {
    java
    application
}

group = "de.cirrus"
version = "1.0.0"

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

tasks.register<Exec>("packageExe") {
    group = "distribution"
    description = "Builds a self-contained Windows application with a Dittany.exe launcher"
    dependsOn("installDist")

    val installDir = layout.buildDirectory.dir("install/${project.name}")
    val outputDir = layout.buildDirectory.dir("jpackage")
    val appImageDir = outputDir.map { it.dir(project.name) }
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
        outputDir.get().asFile.mkdirs()
        appImageDir.get().asFile.deleteRecursively()
    }

    executable(jpackage)
    args(
        "--type", "app-image",
        "--name", project.name,
        "--app-version", project.version.toString(),
        "--input", installDir.map { it.dir("lib") }.get().asFile,
        "--main-jar", "${project.name}-${project.version}.jar",
        "--main-class", application.mainClass.get(),
        "--dest", outputDir.get().asFile,
        "--java-options", "-Dfile.encoding=UTF-8"
    )
}
