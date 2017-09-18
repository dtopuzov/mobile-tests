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

2. Set ANDROID_HOME variable (in my case: `~/tools/android-sdk`)

3. Update Android SDK, Install Intel HAXM, Download emulator images and create emulators

```
#!/bin/bash

echo y |  $ANDROID_HOME/tools/bin/sdkmanager "tools"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "build-tools;25.0.3"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "build-tools;23.0.1"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "platforms;android-25"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "platforms;android-23"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "platforms;android-19"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "emulator"

echo y |  $ANDROID_HOME/tools/bin/sdkmanager "extras;intel;Hardware_Accelerated_Execution_Manager"
sudo $ANDROID_HOME/extras/intel/Hardware_Accelerated_Execution_Manager/silent_install.sh

echo y |  $ANDROID_HOME/tools/bin/sdkmanager "system-images;android-25;google_apis;x86"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "system-images;android-23;default;x86"
echo y |  $ANDROID_HOME/tools/bin/sdkmanager "system-images;android-19;default;x86"

echo no | $ANDROID_HOME/tools/bin/avdmanager create avd -n Emulator-Api19-Default -k "system-images;android-19;default;x86" -b default/x86 -c 12M -f
echo no | $ANDROID_HOME/tools/bin/avdmanager create avd -n Emulator-Api23-Default -k "system-images;android-23;default;x86" -b default/x86 -c 12M -f
echo no | $ANDROID_HOME/tools/bin/avdmanager create avd -n Emulator-Api25-Google -k "system-images;android-25;google_apis;x86" -b google_apis/x86 -c 12M -f

# Note: Do not run last line if you don't understand it!
# Append "hw.gpu.enabled=yes", "hw.lcd.density=240" and "skin.name=480x800" to config.ini file of each emulator
find ~/.android/avd -type f -name 'config.ini' -exec bash -c 'echo $0 && echo "hw.lcd.density=240" | tee -a $0 && echo "skin.name=480x800" | tee -a $0 && echo "hw.gpu.enabled=yes"  | tee -a $0 && echo "hw.keyboard=no" | tee -a $0 && cat $0' {} \;
```

### [Appium](http://appium.io/)

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
