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

    @FindBy(className = "//android.widget.Button[@text=CLR]")
    private MobileElement clearButton;

    public HomePage(AppiumDriver driver) {
        super(driver);
        this.loaded();
    }

    private HomePage loaded() {
        Assert.assertTrue(this.resultElement.isDisplayed());
        Assert.assertTrue(this.clearButton.isDisplayed());
        this.log.info("Calculator loaded.");
        return this;
    }

    public HomePage performOperation(OperationType operation, int firstDigit, int secondDigit) {
        this.clearButton.tap(1, 500);
        this.log.info("Clear old results");
        this.tapDigit(String.valueOf(firstDigit));
        this.tapOperations(operation);
        this.tapDigit(String.valueOf(secondDigit));
        this.tapOperations(OperationType.Equals);
        return this;
    }

    public String getResult() {
        String result = this.resultElement.getText();
        this.log.info("Result is " + result);
        return result;
    }

    private HomePage tapDigit(String buttonText) {
        String xpath = "//android.widget.Button[@text=" + buttonText + "]";
        MobileElement digitButton = (MobileElement) this.driver.findElement(By.xpath(xpath));
        digitButton.tap(1, 500);
        this.log.info("Tap " + buttonText);
        return this;
    }

    private HomePage tapOperations(OperationType operation) {
        String operationString = operation.name().toLowerCase();
        MobileElement opButton = (MobileElement) this.driver.findElement(By.id(operationString));
        opButton.tap(1, 500);
        this.log.info("Tap " + operationString);
        return this;
    }
}