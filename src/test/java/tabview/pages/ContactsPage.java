package tabview.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Home page of Selendoid test app.
 */
public class ContactsPage extends NavigationBar {

    public ContactsPage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"contactsViewScreen\"]/div[1]/div[2]/div")
    public WebElement contactsList;
}
