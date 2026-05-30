#!/usr/bin/env bash
set -euo pipefail

cd /Users/gp/HealifyApp
mkdir -p "$HOME/jdk"

JDK_ARCHIVE="$HOME/jdk/temurin17.tar.gz"
ARCH="$(uname -m)"
if [[ "$ARCH" == "arm64" ]]; then
  JDK_URL="https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.19%2B10/OpenJDK17U-jdk_aarch64_mac_hotspot_17.0.19_10.tar.gz"
else
  JDK_URL="https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.19%2B10/OpenJDK17U-jdk_x64_mac_hotspot_17.0.19_10.tar.gz"
fi

echo "Downloading JDK for architecture: $ARCH"
curl -fL -o "$JDK_ARCHIVE" "$JDK_URL"
cd "$HOME/jdk"
tar xzf "$JDK_ARCHIVE"
rm -f "$JDK_ARCHIVE"
rm -f current
ln -s "$(ls -d jdk-17* | head -n1)/Contents/Home" current
export JAVA_HOME="$HOME/jdk/current"

"$JAVA_HOME/bin/java" -version

cd /Users/gp/HealifyApp
./gradlew build
