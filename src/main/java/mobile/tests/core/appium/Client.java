package mobile.tests.core.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
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

    public AndroidDriver driver;

    private AppiumDriverLocalService server;
    private Settings settings;
    private Logger log = LogManager.getLogger(Client.class.getName());

    public Client(AppiumDriverLocalService service, Settings settings) {
        this.server = service;
        this.settings = settings;
    }

    public void startAppiumClient() throws MalformedURLException {
        File appDir = new File("testapps");
        File app = new File(appDir, this.settings.testApp);
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Common capabilities
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, this.settings.deviceName);

        // Native and Hybrid apps capabilities
        if (this.settings.testAppType != ApplicationType.Web) {
            capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        }

        // Android specific capabilities
        if (this.settings.platform == PlatformType.Andorid) {

            // Set automation technology based on Android version
            if (this.settings.platformVersion < 4.2) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.SELENDROID);
            } else if (this.settings.platformVersion > 4.3) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
            } else {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
            }

            // Wait until app is up and running
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, this.settings.packageId);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,
                    this.settings.android.defaultActivity);

            // Timeout for Android device to be available
            capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, 120);
        }

        // Emulator specific capabilities
        if (this.settings.deviceType == DeviceType.Emulator) {
            capabilities.setCapability(AndroidMobileCapabilityType.AVD, this.settings.deviceName);
        }

        // Device specific capabilities
        if ((this.settings.deviceType == DeviceType.Android) || (this.settings.deviceType == DeviceType.iOS)) {
            capabilities.setCapability(MobileCapabilityType.UDID, this.settings.deviceId);
        }

        // Handle debug scenario
        // TODO(dtopuzov): Move isDebug to settings
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                getInputArguments().toString().indexOf("jdwp") >= 0;
        if (isDebug) {
            this.log.info("Debug mode detected. Increase timeout...");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, this.settings.defaultTimeout * 10);
        } else {
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, this.settings.defaultTimeout);
        }

        this.log.info("Starting appium client...");
        this.driver = new AndroidDriver<>(this.server.getUrl(), capabilities);
        this.log.info("Appium client up and running!");
        this.driver.manage().timeouts().implicitlyWait(this.settings.defaultTimeout, TimeUnit.SECONDS);

        // Switch to WEBVIEW context if Hybrid App is detected
        if (this.settings.testAppType == ApplicationType.Hybrid) {
            // Switch to WEBVIEW context
            Set<String> contextNames = this.driver.getContextHandles();
            for (String contextName : contextNames) {
                if (contextName.contains("WEBVIEW")) {
                    this.log.info("Hybrid app detected. Switch context to " + contextName);
                    this.driver.context(contextName);
                    this.log.info("Context switched.");
                }
            }
        }
        // Notes: If default local server is running, please use:
        // driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    public void stopAppiumClient() {
        if (this.driver != null) {
            this.log.info("Quit appium client.");
            this.driver.quit();
        }
    }
}
