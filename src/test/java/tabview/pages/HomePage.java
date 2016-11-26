package tabview.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Home page.
 */
public class HomePage extends NavigationBar {

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"homeViewScreen\"]/div[1]/div[2]/h1")
    public WebElement homePageContent;
}
