package tabview.pages;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.page.BasePage;
import mobile.tests.core.base.uimap.UIMapItem;
import mobile.tests.core.utils.image.UIRectangle;
import mobile.tests.core.base.uimap.UIMap;

/**
 * Navigation bar component.
 */
public class NavigationBar extends BasePage {

    private UIMap map;

    public NavigationBar(AppiumDriver driver) {
        super(driver);
        this.map = new UIMap();
    }

    public HomePage navigateToHome() {
        UIMapItem button = this.map.getItem("homeButton");
        this.gestures.tap(button);
        return new HomePage(driver);
    }

    public SettingsPage navigateToSettings() {
        UIMapItem button = this.map.getItem("settingsButton");
        this.gestures.tap(button);
        return new SettingsPage(driver);
    }

    public ContactsPage navigateToContacts() {
        UIMapItem button = this.map.getItem("contactsButton");
        this.gestures.tap(button);
        return new ContactsPage(driver);
    }
}
