package base.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.PageFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Base page.
 * TODO(dtopuzov): Add better docs
 */
public class BasePage {

    public AppiumDriver driver;
    public Logger log = LogManager.getLogger(BasePage.class.getName());

    private BufferedImage getScreen() {
        try {
            File screen = this.driver.getScreenshotAs(OutputType.FILE);
            return ImageIO.read(screen);
        } catch (Exception e) {
            this.log.error("Failed to take screenshot!");
            return null;
        }
    }

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitForImage(String imageName, double similarity) {
    }

    public void tapOnImage(String imageName, double similarity) {
    }
}
