<p align="center">
    <a href="#footnote"><img src="src/main/resources/META-INF/pluginIcon.svg" width="250" height="250" alt="Askama plugin logo"><sup>*</sup></a>
</p>

<h1 align="center">
  Askama Template Support<br />
  <small>for IntelliJ-based IDEs</small>
</h1>

<p align="center">
Adds (currently very basic) support for <a href="https://github.com/djc/askama">Askama</a> template files to IntelliJ platform IDEs.
</p>

<br />

## Installation
You can find this plugin on the [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/16591-askama-template-support).

### Manual Installation
1. Download the .zip file from the [Releases section](https://github.com/SamJakob/intellij-askama-template-plugin/releases/).
2. Open Settings -> Plugins
3. Select the options icon (cog) to the right of the 'Marketplace' and 'Installed' tabs at the top of the settings pane.
4. Select 'Install Plugin from Disk...' from the menu.
5. Choose the previously downloaded .zip file.

## Notes

By default, this plugin recognizes `.askama` and `*.askama.html` files as Askama template files,
however you can change this in File Associations.

I originally planned to add support for most, if not all, Askama keywords and
Rust 'interpolation', however have been struggling to find the time to do so, so
if anyone's interested, please feel free to open an issue or pull request.

<hr />

<a name="footnote">*: unofficial logo!</a>
