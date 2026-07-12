# Dittany

Dittany is a 2D RTS/shooter prototype written in Java using AWT/Swing. The entry point is `de.cirrus.dittany.Dittany`.

The project uses the Gradle wrapper, so no system-wide Gradle installation is required. Application code and assets follow the standard Gradle directory layout:

- `src/main/java` contains Java sources.
- `src/main/resources` contains sprites and level data placed on the runtime classpath.

## Building and running

On Windows:

```console
.\gradlew.bat build
.\gradlew.bat run
```

On Linux or macOS:

```bash
./gradlew build
./gradlew run
```

## Windows package

Create a self-contained Windows application image with:

```console
.\gradlew.bat --no-daemon packageExe
```

The result is written to `build/jpackage/Dittany`. Ship that entire directory. It includes `Dittany.exe`, the game, its resources, and a Java runtime, so players do not need to install Java separately.

The project officially targets Java 26. Building and packaging require a full JDK 26 containing `jpackage`. Players do not need a JDK because the packaged application includes its own runtime. WiX is not required because the output is a portable application directory rather than an installer.
