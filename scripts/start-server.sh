#!/bin/sh

set -e

DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

cd $DIR/../spigot
java -Xms512M -Xmx1024M -XX:MaxPermSize=128M -jar spigot.jar
