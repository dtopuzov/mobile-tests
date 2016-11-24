package google.tests;

import google.pages.HomePage;
import mobile.tests.core.appium.Client;
import mobile.tests.core.base.test.BaseTest;
import org.testng.annotations.Test;

/**
 * Smoke tests for Selendoid test app.
 */
public class GoogleSearchTests extends BaseTest {

    @Test
    public void searchTest() {
        HomePage home = new HomePage(Client.driver);
        home.search("Appium");
    }
}
