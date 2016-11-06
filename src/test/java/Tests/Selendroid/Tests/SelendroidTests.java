package Tests.Selendroid.Tests;

import Appium.Client;
import BaseTest.BaseTest;
import Tests.Selendroid.Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelendroidTests extends BaseTest {

    SelendroidTests() throws Exception {
    }

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
