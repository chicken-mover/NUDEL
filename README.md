# NUDEL
New Uruk Derived Extension Library

This is a repository for all plugins used by the 
[New Uruk](http://uruk.d3mok.net) Minecraft server.

Plugins are built to target [Spigot](http://spigotmc.org).

## Setting up your development environment

Assuming you are doing this on Linux, the following should work to get started:
```bash
$ git clone git@github.com:chicken-mover/NUDEL.git
$ cd NUDEL
$ bash scripts/bootstrap-spigot.sh
```

It will take some time for the Spigot build to complete, but once it is done
you will have a new directory under `NUDEL/spigot` containing all of the
built files.

You can then start the local server with:
```bash
$ bash scripts/start-server.sh
```

For further information on the setup/build process, be sure to check out the
[relevant page on the Spigot wiki](http://www.spigotmc.org/wiki/spigot-installation/)

## Testing plugin builds

The Spigot [blank plugin tutorial](http://www.spigotmc.org/wiki/creating-a-blank-spigot-plugin-in-eclipse/)
explains how to create and build plugins in Eclipse. These files should go 
under `NUDEL/plugins/<PluginName>`. `net.d3mok.uruk.<PluginName>` is the 
preferred namespacing scheme.

Builds of JARs can be output to `NUDEL/spigot/plugins/` for testing with the
server built in the previous section.

Testing your plugin builds on the live server will get your access permanently
revoked.

## License

This project is public domain under the terms of the 
[Unlicense](http://unlicense.org/), namely:

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.
