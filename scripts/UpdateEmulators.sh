#!/bin/bash

echo y | $ANDROID_HOME/tools/android update sdk --filter extra-intel-Hardware_Accelerated_Execution_Manager --all --no-ui
sudo $ANDROID_HOME/extras/intel/Hardware_Accelerated_Execution_Manager/silent_install.sh

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-24 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-24 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-arm64-v8a-android-24 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-23 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-google_apis-23 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-23 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-22 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-google_apis-22 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-22 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-21 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-google_apis-21 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-21 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-19 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-19 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-google_apis-19 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-18 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-18 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-google_apis-18 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-17 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-armeabi-v7a-android-17 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-google_apis-17 --all --no-ui
