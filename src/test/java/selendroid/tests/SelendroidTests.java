package selendroid.tests;

import mobile.tests.core.appium.Client;
import mobile.tests.core.base.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import selendroid.pages.HomePage;

import java.io.IOException;

/**
 * Smoke tests for Selendoid test app.
 */
public class SelendroidTests extends BaseTest {

    @Test
    public void checkBox() {
        HomePage seleniumDemo = new HomePage(Client.driver);
        seleniumDemo.tapCheckBox();
        Assert.assertEquals(seleniumDemo.isChecked(), false, "Checkbox is still checked.");
        seleniumDemo.tapCheckBox();
        Assert.assertEquals(seleniumDemo.isChecked(), true, "Checkbox is unchecked.");
    }

    @Test
    public void dismissPopup() throws IOException {
        HomePage seleniumDemo = new HomePage(Client.driver);
        seleniumDemo.tapDisplayPopupWindow();
        seleniumDemo.logCurrentScreen("Before popup dismiss.");
        seleniumDemo.dissmissPopupDialog();
        seleniumDemo.logCurrentScreen("After popup dismiss.");
    }
}
