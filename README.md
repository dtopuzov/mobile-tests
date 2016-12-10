# Mobile Tests with Appium

## About 

Sample project showing how to automate mobile apps with Appium.

Technologies used:
- JDK
- Gradle
- Android commandline tools
- Appium
- Sikuli
- TestNG
- log4j

## Requirements 

### [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### [ANDROID SDK](https://developer.android.com/studio/index.html#downloads)

Commandline tools are enough.

Setup steps:

1. Download Android SDK zip and extract it

2. Set ANDROID_HOME variable (in my case: `C:\Tools\android-sdk`)

3. (Optional) Add `%ANDRID_HOME%\tools` and `%ANDRID_HOME%\platform-tools` to PATH

4. Update Android SDK

5. Install [Intel HAXM](https://software.intel.com/en-us/android/articles/intel-hardware-accelerated-execution-manager) in order to use accelerated emulators

6. Download emulator images and create emulators

```
#!/bin/bash

echo y | $ANDROID_HOME/tools/android update sdk --filter platform-tools --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter tools --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter build-tools-24.0.2 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-23 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-22 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-21 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter android-19 --all --no-ui

echo y | $ANDROID_HOME/tools/android update sdk --filter extra-intel-Hardware_Accelerated_Execution_Manager --all --no-ui
sudo $ANDROID_HOME/extras/intel/Hardware_Accelerated_Execution_Manager/silent_install.sh

echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-23 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-22 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-21 --all --no-ui
echo y | $ANDROID_HOME/tools/android update sdk --filter sys-img-x86-android-19 --all --no-ui

echo no | $ANDROID_HOME/tools/android create avd -n Emulator-Api19-Default -t android-19 --abi default/x86 -c 12M -f
echo no | $ANDROID_HOME/tools/android create avd -n Emulator-Api21-Default -t android-21 --abi default/x86 -c 12M -f
echo no | $ANDROID_HOME/tools/android create avd -n Emulator-Api22-Default -t android-22 --abi default/x86 -c 12M -f
echo no | $ANDROID_HOME/tools/android create avd -n Emulator-Api23-Default -t android-23 --abi default/x86 -c 12M -f
```

### [Appium](http://appium.io/) 1.6.0 or above

```
npm install -g appium
```  

## Test Configurations

Tests can be executed against devices, emulators or simulators.

In order to execute same tests against different devices it is obvious you need some configs.
Test configurations are available at `src\test\resources\config` and content looks like this:

```
platform=Android
platformVersion=4.4
deviceName=Emulator-Api19-Default
deviceType=Emulator
testapp=selendroid-test-app-0.11.0.apk
defaultTimeout=30
appiumLogLevel=warn
```

TODO: Add more details for test configs

## Run Tests

   Run tests in package
   ```
   gradlew clean test --tests calculator.tests* -Dconfig=calculator.emu.default.api19
   ```

## Check for code style and errors

[Checkstyle](https://docs.gradle.org/current/userguide/checkstyle_plugin.html) is used to check code style.

Rules are listed in `config/checkstyle/checkstyle.xml`

`build` and `test` tasks dependes on `check`, so checks will be executed each time when you build or run tests.

Task depencies are defined in `build.gradle`
```
build.dependsOn(check)
test.dependsOn(check)
check.dependsOn.remove(test)
```

## Additional Resources

### Mobile Web Testing

[Appium Docs](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/mobile-web.md)

[Chrome Driver Docs](https://sites.google.com/a/chromium.org/chromedriver/getting-started/getting-started---android)