package google.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.support.FindBy;

/**
 * Home page of www.google.com.
 */
public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(className = "android.widget.CheckBox")
    public MobileElement searchBox;

    @FindBy(className = "android.widget.CheckBox")
    public MobileElement searchButton;

    public HomePage search(String searchTerm) {
        this.searchBox.sendKeys(searchTerm);
        this.searchButton.tap(1, 500);
        return this;
    }
}
