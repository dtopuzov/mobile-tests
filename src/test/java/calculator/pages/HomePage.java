package calculator.pages;

import calculator.enums.OperationType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import mobile.tests.core.base.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

/**
 * Home page of Selendoid test app.
 */
public class HomePage extends BasePage {

    @FindBy(className = "android.widget.EditText")
    private MobileElement resultElement;

    public HomePage(AppiumDriver driver) {
        super(driver);
        this.loaded();
    }

    private HomePage loaded() {
        Assert.assertTrue(this.resultElement.isDisplayed());
        this.log.info("Calculator loaded.");
        return this;
    }

    public HomePage performOperation(String operation, int firstDigit, int secondDigit) {
        this.tapButton(String.valueOf(firstDigit));
        this.tapButton(operation);
        this.tapButton(String.valueOf(secondDigit));
        this.tapButton(OperationType.EQUAL);
        return this;
    }

    public String getResult() {
        String result = this.resultElement.getAttribute("text").trim();
        this.log.info("Result is " + result);
        return result;
    }

    private HomePage tapButton(String buttonText) {
        String xpath = "//android.widget.Button[@text='" + buttonText + "']";
        MobileElement digitButton = (MobileElement) this.driver.findElement(By.xpath(xpath));
        digitButton.tap(1, 250);
        this.log.info("Tap " + buttonText);
        return this;
    }
}
