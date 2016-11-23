package selendroid.pages;

import mobile.tests.core.base.page.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

/**
 * Home page of Selendoid test app.
 */
public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(className = "android.widget.CheckBox")
    public MobileElement checkBox;
}
