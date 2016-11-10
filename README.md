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
In order to execute same tests against different devices it is obvious you need some configs.
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
   gradlew clean test -Dconfig=selendroid.emu.default.api19
   ```
   
   Run tests in package
   ```
   gradlew clean test --tests Tests.Selendroid.* -Dconfig=selendroid.emu.default.api19
   ```

## Implementation details

### Testing Framework

Tests are based on popular [TestNG](http://testng.org/doc/index.html) framework.

Resources:
[TestNG Documentation](http://testng.org/doc/documentation-main.html)
[TestNG Tutorial](https://www.tutorialspoint.com/testng/testng_parameterized_test.htm)

TestNG samples:
[Hello-World Tests](https://github.com/dtopuzov/Demos/tree/master/src/test/java/tests/demo_02_testng_hello_world)
[Data-Driven Tests](https://github.com/dtopuzov/Demos/tree/master/src/test/java/tests/demo_03_testng_data_driven)

### Logging

Logging is done via [log4j 2.x](http://logging.apache.org/log4j/2.x/).


## TODOs

### Checkstyle and findbugs

Notes:
Add https://docs.gradle.org/current/userguide/checkstyle_plugin.html
```
apply plugin: 'java'
apply plugin: 'checkstyle'
apply plugin: 'findbugs'
apply plugin: 'build-dashboard'
```
