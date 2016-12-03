package mobile.tests.core.base.app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobile.tests.core.base.page.BasePage;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.PageFactory;

/**
 * This class represent application under test
 */
public class App {

    private AppiumDriver driver;
    private Logger log = LogManager.getLogger(BasePage.class.getName());

    public App(Settings settings, AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void restart() {
        this.driver.resetApp();
    }

    public void runInBackground(int seconds) {
        this.driver.runAppInBackground(seconds);
    }

    public void rotate(ScreenOrientation orientation) {
        this.driver.rotate(orientation);
    }

    public ScreenOrientation getScreenOrientation() {
        return this.driver.getOrientation();
    }
}
