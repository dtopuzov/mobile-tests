package tabview.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.support.FindBy;

/**
 * Home page of Selendoid test app.
 */
public class SettingsPage extends NavigationBar {

    public SettingsPage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@text='Settings']")
    public MobileElement settingsContent;
}
