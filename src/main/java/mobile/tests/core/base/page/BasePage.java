package mobile.tests.core.base.page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobile.tests.core.base.find.Find;
import mobile.tests.core.base.find.FindByImage;
import mobile.tests.core.base.gestures.Gestures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.PageFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * TODO(dtopuzov): Add better docs.
 */
public class BasePage {

    public Find finder;
    public FindByImage findByImage;
    public Gestures gestures;
    public AppiumDriver driver;
    public Logger log = LogManager.getLogger(BasePage.class.getName());

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.findByImage = new FindByImage(this.driver);
        this.finder = new Find(this.driver);
        this.gestures = new Gestures(this.driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void navigateBack() {
        this.driver.navigate().back();
        this.log.info("Navigate back.");
    }

    public void logScreen(String imageName) throws IOException {
        BufferedImage bufferedImage = this.getScreen();
        File outputFile = new File(imageName + ".png");
        ImageIO.write(bufferedImage, "png", outputFile);
    }

    private BufferedImage getScreen() {
        try {
            File screen = this.driver.getScreenshotAs(OutputType.FILE);
            return ImageIO.read(screen);
        } catch (Exception e) {
            this.log.error("Failed to take screenshot!");
            return null;
        }
    }
}
