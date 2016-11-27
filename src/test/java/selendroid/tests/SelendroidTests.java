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

    @Test
    public void checkBox() {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapCheckBox();
        Assert.assertEquals(seleniumDemo.isChecked(), false, "Checkbox is still checked.");
        seleniumDemo.tapCheckBox();
        Assert.assertEquals(seleniumDemo.isChecked(), true, "Checkbox is unchecked.");
    }

    @Test
    public void dismissPopup() throws IOException {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapDisplayPopupWindow();
        seleniumDemo.logCurrentScreen("Before popup dismiss");
        seleniumDemo.dissmissPopupDialog();
        seleniumDemo.logCurrentScreen("After popup dismiss");
    }

    @Test
    public void verifyToastMessages() throws IOException {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapDisplayToast();
        seleniumDemo.logCurrentScreen("Toast");
    }

    @Test
    public void progressDialog() {
        HomePage seleniumDemo = new HomePage(this.client.driver);
        seleniumDemo.tapShowProgressBar();
        ProgressDialog dialog = new ProgressDialog(this.client.driver);
        dialog.waitUntilDisappear();
    }
}
