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
        this.loaded();
    }

    @FindBy(className = "android.widget.CheckBox")
    @CacheLookup
    private MobileElement checkBox;

    @FindBy(xpath = "//android.widget.Button[@text='Display Popup Window']")
    @CacheLookup
    private MobileElement displayPopupWindowButton;

    @FindBy(xpath = "//android.widget.Button[@text='Displays a Toast']")
    @CacheLookup
    private MobileElement displayToastButton;

    @FindBy(xpath = "//*[@text='Hello selendroid toast!']")
    @CacheLookup
    private MobileElement toastMessage;

    @FindBy(xpath = "//android.widget.Button[@text='Show Progress Bar for a while']")
    @CacheLookup
    private MobileElement showProgressBarButton;

    public HomePage tapCheckBox() {
        this.checkBox.tap(1, 500);
        this.log.info("Tap on checkBox");
        return this;
    }

    public HomePage loaded() {
        Assert.assertTrue(this.checkBox.isDisplayed());
        Assert.assertTrue(this.displayPopupWindowButton.isDisplayed());
        this.log.info("HomePage loaded.");
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

    public HomePage tapDisplayToast() {
        this.displayToastButton.tap(1, 500);
        this.log.info("Tap on Display a Toast");
        return this;
    }

    public ProgressDialog tapShowProgressBar() {
        this.showProgressBarButton.tap(1, 500);
        this.log.info("Tap on Show Progress Bar");
        return new ProgressDialog(this.driver);
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

    public void verifyToastMessageDisplayed() {
        Assert.assertTrue(this.toastMessage.isDisplayed());
    }
}
