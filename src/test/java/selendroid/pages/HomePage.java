package selendroid.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.support.FindBy;

/**
 * Home page of Selendoid test app.
 */
public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver) {
        super(driver);
        this.checkBox.isDisplayed();
        this.log.info("HomePage loaded.");
    }

    @FindBy(className = "android.widget.CheckBox")
    private MobileElement checkBox;

    @FindBy(xpath = "//android.widget.Button[@text='Display Popup Window']")
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
        // TODO(dtopuzov): Implement workaround for https://code.google.com/p/android/issues/detail?id=93268
    }
}
