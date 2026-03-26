#!/usr/bin/env bash
set -euo pipefail

# Generate full offline stories for debug too
./gradlew :tools:generator:run assembleDebug

APK=app/build/outputs/apk/debug/app-debug.apk
if [ -f "$APK" ]; then
  echo "Debug APK built at $APK"
else
  echo "Check Gradle output for errors" >&2
fi
