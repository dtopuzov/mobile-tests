package tabview.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Navigation bar component.
 */
public class NavigationBar extends BasePage {

    public NavigationBar(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"navigation-container\"]/a[1]")
    public WebElement homeButton;

    @FindBy(xpath = "//*[@id=\"navigation-container\"]/a[2]")
    public WebElement settingsButton;

    @FindBy(xpath = "//*[@id=\"navigation-container\"]/a[3]")
    public WebElement contactsButton;

    public HomePage navigateToHome() {
        this.homeButton.click();
        log.info("Navigate to home page.");
        return (HomePage) this;
    }

    public SettingsPage navigateToSettings() {
        this.settingsButton.click();
        log.info("Navigate to settings page.");
        return (SettingsPage) this;
    }

    public ContactsPage navigateToContacts() {
        this.contactsButton.click();
        log.info("Navigate to contacts page.");
        return (ContactsPage) this;
    }
}
