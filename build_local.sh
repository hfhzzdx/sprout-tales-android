#!/usr/bin/env bash
set -euo pipefail

# Local build helper (optional)
./gradlew :tools:generator:run assembleRelease

APK=app/build/outputs/apk/release/app-release-unsigned.apk
if [ -f "$APK" ]; then
  echo "APK built at $APK"
else
  echo "Check Gradle output for errors" >&2
fi
