package mobile.tests.core.base.app;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.page.BasePage;
import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;

/**
 * This class represent application under test.
 */
public class App {

    private AppiumDriver driver;
    private Settings settings;
    private Logger log = LogManager.getLogger(BasePage.class.getName());

    public App(Settings settings, AppiumDriver driver) {
        this.driver = driver;
        this.settings = settings;
    }

    public void restart() {
        String app;
        if (this.settings.testAppType == ApplicationType.Web) {
            app = this.settings.web.browser;
        } else {
            app = this.settings.app.testAppName;
        }
        this.log.info("Restarting " + app);
        this.driver.resetApp();
        this.log.info(app + " restarted.");
    }

    public void runInBackground(int seconds) {
        this.log.info("Run " + this.settings.app.testAppName
                + " in background for " + String.valueOf(seconds) + "seconds.");
        this.driver.runAppInBackground(seconds);
        this.log.info(this.settings.app.testAppName + " is back on top.");
    }

    public void rotate(ScreenOrientation orientation) {
        this.log.info("Changing screen orientation to " + orientation);
        this.driver.rotate(orientation);
        Assert.assertEquals(this.getScreenOrientation(), orientation, "Failed to change orientation.");
    }

    public ScreenOrientation getScreenOrientation() {
        ScreenOrientation orientation = this.driver.getOrientation();
        this.log.info("Current screen orientation is " + orientation);
        return orientation;
    }
}
