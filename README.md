# Dittany

Dittany is a 2D RTS/shooter prototype. The source lives under the `de.cirrus.dittany` package; the entry point is `de.cirrus.dittany.Dittany`.

It is plain Java (AWT/Swing) with no external dependencies and no build tool — the repository is an Eclipse-style project (`src` and `res` as source folders, output to `bin`) that also opens directly in VS Code or IntelliJ.

## Building and running

In VS Code with the Extension Pack for Java installed, open the folder and press F5 (the "Launch Dittany" configuration).

From the command line:

```powershell
# PowerShell
javac -d bin (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp "bin;res" de.cirrus.dittany.Dittany
```

```bash
# bash
javac -d bin $(find src -name '*.java')
java -cp "bin:res" de.cirrus.dittany.Dittany
```

`res` must be on the runtime classpath: sprites and the level map are loaded as classpath resources.
