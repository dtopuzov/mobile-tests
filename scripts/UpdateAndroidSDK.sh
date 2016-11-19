#!/bin/bash

echo y | $ANDROID_HOME/tools/android update sdk --filter platform-tools --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter tools --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter build-tools-24.0.2 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter build-tools-23.0.3 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-24 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-23 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-22 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-21 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-19 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-18 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-17 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter extra-android-m2repository --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter extra-google-m2repository --all --no-ui