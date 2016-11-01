package Tests;

import Appium.Client;
import BaseTest.BaseTest;
import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelendroidAppTests extends BaseTest {

    @Test
    public void checkBox() {
        HomePage seleniumDemo = new HomePage(Client.driver);
        seleniumDemo.checkBox.tap(1, 500);
        boolean isCecked = Boolean.valueOf(seleniumDemo.checkBox.getAttribute("checked"));
        Assert.assertEquals(isCecked, false, "Checkbox is still checked.");

        seleniumDemo.checkBox.tap(1, 500);
        isCecked = Boolean.valueOf(seleniumDemo.checkBox.getAttribute("checked"));
        Assert.assertEquals(isCecked, true, "Checkbox is unchecked.");
    }
}
