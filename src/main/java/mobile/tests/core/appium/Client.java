package mobile.tests.core.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.enums.DeviceType;
import mobile.tests.core.enums.PlatformType;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Appium client.
 * TODO(dtopuzov): Add better docs
 */
public class Client {

    public static AndroidDriver driver;

    private static Logger log = LogManager.getLogger(Client.class.getName());

    public static void startAppiumClient(Settings settings) throws MalformedURLException {
        File appDir = new File("testapps");
        File app = new File(appDir, settings.testApp);
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Common capabilities
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, settings.deviceName);

        // Native and Hybrid apps capabilities
        if (settings.testAppType != ApplicationType.Web) {
            capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        }

        // Android specific capabilities
        if (settings.platform == PlatformType.Andorid) {

            // Set automation technology based on Android version
            if (settings.platformVersion < 4.2) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.SELENDROID);
            } else if (settings.platformVersion > 4.3) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
            } else {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
            }

            // Wait until app is up and running
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, settings.packageId);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, settings.android.defaultActivity);

            // Timeout for Android device to be available
            capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, 120);
        }

        // Emulator specific capabilities
        if (settings.deviceType == DeviceType.Emulator) {
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, settings.deviceName);
        }

        // Device specific capabilities
        if ((settings.deviceType == DeviceType.Android) || (settings.deviceType == DeviceType.iOS)) {
            capabilities.setCapability(MobileCapabilityType.UDID, settings.deviceId);
        }

        // Handle debug scenario
        // TODO(dtopuzov): Move isDebug to settings
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().indexOf("jdwp") >= 0;
        if (isDebug) {
            log.info("Debug mode detected. Increase timeout...");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, settings.defaultTimeout * 10);
        } else {
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, settings.defaultTimeout);
        }

        log.info("Starting appium client...");
        driver = new AndroidDriver<>(Server.service.getUrl(), capabilities);
        log.info("Appium client up and running!");
        driver.manage().timeouts().implicitlyWait(settings.defaultTimeout, TimeUnit.SECONDS);

        // Switch to WEBVIEW context if Hybrid App is detected
        if (settings.testAppType == ApplicationType.Hybrid) {
            // Switch to WEBVIEW context
            Set<String> contextNames = driver.getContextHandles();
            for (String contextName : contextNames) {
                if (contextName.contains("WEBVIEW")) {
                    log.info("Hybrid app detected. Switch context to " + contextName);
                    driver.context(contextName);
                    log.info("Context switched.");
                }
            }
        }
        // Notes: If default local server is running, please use:
        // driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    public static void stopAppiumClient() {
        if (driver != null) {
            log.info("Quit appium client.");
            driver.quit();
        }
    }
}
