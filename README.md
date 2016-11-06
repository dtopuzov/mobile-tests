# Mobile Tests with Appium

## About 
Sample project showing how to automate mobile apps with Appium

## Requirements 

[JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[Appium](http://appium.io/) 1.6.0 or above

```
npm install -g appium
```  

## Test Configurations

Tests can be executed against devices, emulators or simulators.
In order to execute same tests agains different devices it is obvius you need some configs.
Test configurations are available at ```src\test\resources\config``` and content looks like this:
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

   Run all tests
   ```
   gradlew clean test
   ```
   
   Run in pacakge
   ```
   gradlew clean test --tests Tests.* -Dconfig=selendroid.emu.default.api19
   ```
   
   Run in class all classes in Tests package starting with Selendroid
   ```
   gradlew clean test --tests Tests.Selendroid* -Dconfig=selendroid.emu.default.api19
   ```
   
## TODOs

### Research checkstyle and findbugs

Notes:
Add https://docs.gradle.org/current/userguide/checkstyle_plugin.html
```
apply plugin: 'java'
apply plugin: 'checkstyle'
apply plugin: 'findbugs'
apply plugin: 'build-dashboard'
```
