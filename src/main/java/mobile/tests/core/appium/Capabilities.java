package mobile.tests.core.appium;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.enums.DeviceType;
import mobile.tests.core.enums.PlatformType;
import mobile.tests.core.settings.Settings;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

/**
 * Desired capabilities for Appium sessions.
 */
public class Capabilities {

    private Settings settings;

    public Capabilities(Settings settings) {
        this.settings = settings;
    }

    public DesiredCapabilities initCapabilities() {
        DesiredCapabilities capabilities = this.initCommonCapabilities(this.settings);
        if (this.settings.testAppType == ApplicationType.Web) {
            capabilities = this.addWebCapabilities(capabilities, this.settings);
        } else {
            capabilities = this.addAppCapabilities(capabilities, this.settings);
        }
        return capabilities;
    }

    // Common capabilities for all types of apps
    private DesiredCapabilities initCommonCapabilities(Settings settings) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Set device name
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, this.settings.deviceName);

        // Set Android specific common capabilities
        if (this.settings.platform == PlatformType.Android) {

            // Set automation technology based on Android version
            if (this.settings.platformVersion < 4.2) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.SELENDROID);
            } else if (this.settings.platformVersion > 4.3) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
            } else {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
            }

            // Timeout for Android device to be available
            capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, 120);

            // Emulator specific capabilities
            if (this.settings.deviceType == DeviceType.Emulator) {
                capabilities.setCapability(AndroidMobileCapabilityType.AVD, this.settings.deviceName);
                capabilities.setCapability(AndroidMobileCapabilityType.AVD_ARGS, this.settings.android.emulatorOptions);
            }
        }

        // Set iOS specific common capabilities
        if (this.settings.platform == PlatformType.iOS) {

            // Set automation technology based on iOS version
            if (this.settings.platformVersion < 10.0) {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
            } else {
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            }

            // Timeout for iOS device to be available
            capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, this.settings.deviceBootTimeout * 1000);
            capabilities.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, true);
        }

        // Device specific capabilities
        if ((this.settings.deviceType == DeviceType.Android) || (this.settings.deviceType == DeviceType.iOS)) {
            capabilities.setCapability(MobileCapabilityType.UDID, this.settings.deviceId);
        }

        // Increase session timeout in debug mode to allow more debugging time
        if (this.settings.debug) {
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, this.settings.defaultTimeout * 10);
        } else {
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, this.settings.defaultTimeout);
        }

        return capabilities;
    }

    // Add web specific capabilities
    private DesiredCapabilities addWebCapabilities(DesiredCapabilities capabilities, Settings settings) {
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, this.settings.web.browser);
        if (this.settings.platform == PlatformType.Android) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, this.settings.app.packageId);
        }
        return capabilities;
    }

    // Add apps specific capabilities
    private DesiredCapabilities addAppCapabilities(DesiredCapabilities capabilities, Settings settings) {
        File appDir = new File("testapps");
        File app = new File(appDir, this.settings.app.testApp);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        if (this.settings.platform == PlatformType.Android) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, this.settings.app.packageId);
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY,
                    this.settings.app.defaultActivity);
        }

        return capabilities;
    }
}
