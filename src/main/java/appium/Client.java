package appium;

import enums.DeviceType;
import enums.PlatformType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import settings.Settings;

import java.io.File;
import java.net.MalformedURLException;

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
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, settings.defaultTimeout);

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

        log.info("Starting appium client...");
        driver = new AndroidDriver<>(Server.service.getUrl(), capabilities);
        log.info("Appium client up and running!");

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
