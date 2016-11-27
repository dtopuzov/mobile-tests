package tabview.tests;

import mobile.tests.core.base.test.BaseTest;
import org.testng.annotations.Test;
import tabview.pages.ContactsPage;
import tabview.pages.HomePage;
import tabview.pages.SettingsPage;

/**
 * Smoke tests for TabView test app.
 */
public class TabViewTests extends BaseTest {

    @Test
    public void navigation() {
        HomePage home = new HomePage(this.client.driver);
        SettingsPage settings = home.navigateToSettings();
        ContactsPage contact = settings.navigateToContacts();
        home = contact.navigateToHome();
    }
}
