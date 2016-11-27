package selendroid.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Home page of Selendoid test app.
 */
public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver) {
        super(driver);
        Assert.assertTrue(this.checkBox.isDisplayed());
        Assert.assertTrue(this.displayPopupWindowButton.isDisplayed());
        this.log.info("HomePage loaded.");
    }

    @FindBy(className = "android.widget.CheckBox")
    @CacheLookup
    private MobileElement checkBox;

    @FindBy(xpath = "//android.widget.Button[@text='Display Popup Window']")
    @CacheLookup
    private MobileElement displayPopupWindowButton;

    public HomePage tapCheckBox() {
        this.checkBox.tap(1, 500);
        this.log.info("Tap on checkBox");
        return this;
    }

    public boolean isChecked() {
        String isChecked = this.checkBox.getAttribute("checked");
        this.log.info("Checkbox is " + isChecked);
        return Boolean.valueOf(isChecked);
    }

    public HomePage tapDisplayPopupWindow() {
        this.displayPopupWindowButton.tap(1, 500);
        this.log.info("Tap on Display Popup Window");
        return this;
    }

    public void dissmissPopupDialog() {
        // Can not locate popup window because of https://code.google.com/p/android/issues/detail?id=93268
        // TODO(dtopuzov): Implement better workaround (if possible)
        Dimension size = this.driver.manage().window().getSize();
        int x = ((Double) (size.getWidth() * 0.5)).intValue();
        int y = ((Double) (size.getHeight() * 0.575)).intValue();
        this.driver.tap(1, x, y, 500);
        this.log.info("Tap ok to dismiss popup window");
    }
}
