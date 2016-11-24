package tabview.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

/**
 * Navigation bar component.
 */
public class NavigationBar extends BasePage {

    public NavigationBar(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "navigation-container")
    public MobileElement navigationBar;

    private BasePage natigateTo(String pageName) {
        MobileElement item = this.navigationBar.findElement(By.xpath("//[@text='" + pageName + "']"));
        item.tap(1, 500);
        log.info("Navigate to " + pageName);
        return this;
    }

    public HomePage navigateToHome() {
        return (HomePage) this.natigateTo("Home View");
    }

    public SettingsPage navigateToSettings() {
        return (SettingsPage) this.natigateTo("Settings");
    }

    public ContactsPage navigateToContacts() {
        return (ContactsPage) this.natigateTo("Contacts");
    }
}
