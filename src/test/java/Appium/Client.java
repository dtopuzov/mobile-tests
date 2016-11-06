package Appium;

import Settings.Settings;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;

public class Client {

    public static AndroidDriver driver;

    public static void startAppiumClient(Settings settings) throws MalformedURLException {
        File appDir = new File("testapps");
        File app = new File(appDir, settings.testApp);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, settings.deviceName);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, settings.defaultTimeout);
        capabilities.setCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, 120);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, "io.selendroid.testapp");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "io.selendroid.testapp.HomeScreenActivity");
        capabilities.setCapability(AndroidMobileCapabilityType.AVD, settings.deviceName);
        driver = new AndroidDriver<>(Server.service.getUrl(), capabilities);

        // Notes: If default local server is running, please use:
        //driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    public static void stopAppiumClient() {
        if (driver != null) {
            driver.quit();
        }
    }
}
