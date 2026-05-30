#!/usr/bin/env bash
set -euo pipefail

export ANDROID_SDK_ROOT="$HOME/Library/Android/sdk"
export PATH="$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/emulator:$PATH"

cd /Users/gp/HealifyApp
APK="app/build/outputs/apk/debug/app-debug.apk"
AVD_NAME="Medium_Phone_API_36.1"
LOGFILE="/tmp/avd_start.log"

if [ ! -f "$APK" ]; then
  echo "APK not found: $APK"
  exit 1
fi

if adb devices | grep -q "emulator"; then
  echo "Emulator already connected."
else
  echo "Starting emulator $AVD_NAME..."
  nohup emulator -avd "$AVD_NAME" -gpu auto -no-audio > "$LOGFILE" 2>&1 &
  EMU_PID=$!
  echo "Emulator process PID=$EMU_PID"

  SECONDS_WAIT=300
  until adb shell getprop sys.boot_completed 2>/dev/null | grep -q '1'; do
    sleep 5
    SECONDS_WAIT=$((SECONDS_WAIT-5))
    if [ "$SECONDS_WAIT" -le 0 ]; then
      echo "Emulator boot timed out. See $LOGFILE"
      exit 1
    fi
  done
fi

echo "Emulator boot complete. Installing APK..."
adb install -r "$APK"
adb shell monkey -p com.example.healifyapp -c android.intent.category.LAUNCHER 1

echo "App installed and launched on emulator."
