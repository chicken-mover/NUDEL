#!/bin/bash

#
# --rev to specify a revision number
# 
#

set -e

DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

cd $DIR
mkdir spigot
cd spigot

echo "Rebuilding Spigot with BuildTools..."

bt_latest() {
    wget -O BuildTools.jar "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
}

if [ ! -f BuildTools.jar ]; then
    echo "BuildTools.jar not found, will re-download"
    bt_latest
else
    read -p "Download the latest version of BuildTools? [y/N]" LATEST
    if [ $LATEST == "y" ]; then
        bt_latest
    fi
fi

# Wiki says to do this.
git config --global --unset core.autocrlf

echo "Running BuildTools.jar with the following arguments: $@"
java -jar BuildTools.jar $@
