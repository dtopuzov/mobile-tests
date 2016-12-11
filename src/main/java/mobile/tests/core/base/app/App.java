package mobile.tests.core.base.app;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.page.BasePage;
import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This class represent application under test.
 */
public class App {

    private AppiumDriver driver;
    private Logger log = LogManager.getLogger(BasePage.class.getName());

    public App(AppiumDriver driver) {
        this.driver = driver;
    }

    public void restart() {
        if (Settings.testAppType == ApplicationType.Web) {
            String browser = Settings.web.browser;
            String url = Settings.web.baseURL;
            this.driver.get(url);
            this.log.info(browser + " restarted at " + url);
        } else {
            String app = Settings.app.testAppName;
            this.driver.resetApp();
            this.log.info(app + " restarted.");
        }
    }

    public void runInBackground(int seconds) {
        this.log.info("Run " + Settings.app.testAppName
                + " in background for " + String.valueOf(seconds) + "seconds.");
        this.driver.runAppInBackground(seconds);
        this.log.info(Settings.app.testAppName + " is back on top.");
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

    public BufferedImage getScreen() {
        try {
            File screen = this.driver.getScreenshotAs(OutputType.FILE);
            return ImageIO.read(screen);
        } catch (Exception e) {
            this.log.error("Failed to take screenshot!");
            return null;
        }
    }
}
