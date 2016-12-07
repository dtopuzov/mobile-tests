# Mobile Tests with Appium

## About 

Sample project showing how to automate mobile apps with Appium.

Technologies used:
- JDK
- Gradle
- Appium
- Sikuli
- TestNG
- log4j

## Requirements 

[JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[Appium](http://appium.io/) 1.6.0 or above

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
   gradlew clean test --tests selendroid.tests* -Dconfig=selendroid.emu.default.api19
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

Logging is done via [log4j 2.x](https://logging.apache.org/log4j/2.x/manual/configuration.html).
[Tutorial](http://www.journaldev.com/7128/log4j2-example-tutorial-configuration-levels-appenders)
