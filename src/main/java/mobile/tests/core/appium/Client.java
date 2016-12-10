package mobile.tests.core.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.enums.PlatformType;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Appium client.
 */
public class Client {

    public AppiumDriver<?> driver;

    private AppiumDriverLocalService server;
    private Settings settings;
    private Logger log = LogManager.getLogger(Client.class.getName());

    public Client(AppiumDriverLocalService service, Settings settings) {
        this.server = service;
        this.settings = settings;
    }

    public void startAppiumClient() throws Exception {

        // Init Appium client capabilities
        Capabilities cap = new Capabilities(this.settings);
        DesiredCapabilities capabilities = cap.initCapabilities();

        // Start Appium client
        this.log.info("Starting appium client...");
        if (this.settings.platform == PlatformType.Android) {
            this.driver = new AndroidDriver<>(this.server.getUrl(), capabilities);
        } else if (this.settings.platform == PlatformType.iOS) {
            this.driver = new IOSDriver<>(this.server.getUrl(), capabilities);
        } else {
            throw new Exception("Unknown platform type: " + this.settings.platform);
        }
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
    }

    public void stopAppiumClient() {
        if (this.driver != null) {
            this.log.info("Quit appium client.");
            this.driver.quit();
        }
    }
}
