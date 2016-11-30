package selendroid.tests;

import mobile.tests.core.base.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import selendroid.pages.HomePage;
import selendroid.pages.ProgressDialog;

import java.io.IOException;

/**
 * Smoke tests for Selendoid test app.
 */
public class SelendroidTests extends BaseTest {

    @Test(enabled = false)
    public void checkBox() {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapCheckBox();
        Assert.assertEquals(seleniumDemo.isChecked(), false, "Checkbox is still checked.");
        seleniumDemo.tapCheckBox();
        Assert.assertEquals(seleniumDemo.isChecked(), true, "Checkbox is unchecked.");
    }

    @Test(enabled = false)
    public void dismissPopup() throws IOException {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapDisplayPopupWindow();
        seleniumDemo.logScreen("Before popup dismiss");
        seleniumDemo.dissmissPopupDialog();
        seleniumDemo.logScreen("After popup dismiss");
    }

    @Test(enabled = false)
    public void verifyToastMessages() throws IOException {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapDisplayToast();
        seleniumDemo.logScreen("Toast");
    }

    @Test
    public void progressDialog() {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        ProgressDialog dialog = seleniumDemo.tapShowProgressBar();
        dialog.waitUntilDisappear();
    }
}
