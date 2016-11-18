package Tests.Selendroid.Tests;

import Appium.Client;
import Base.BaseTest;
import Tests.Selendroid.Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SelendroidTests extends BaseTest {

    @Test
    public void checkBox() {
        HomePage seleniumDemo = new HomePage(Client.driver);
        this.log.info("Home page loaded");
        seleniumDemo.checkBox.tap(1, 500);
        this.log.info("Tap on checkBox");
        boolean isCecked = Boolean.valueOf(seleniumDemo.checkBox.getAttribute("checked"));
        Assert.assertEquals(isCecked, false, "Checkbox is still checked.");

        seleniumDemo.checkBox.tap(1, 500);
        isCecked = Boolean.valueOf(seleniumDemo.checkBox.getAttribute("checked"));
        Assert.assertEquals(isCecked, true, "Checkbox is unchecked.");
    }
}
