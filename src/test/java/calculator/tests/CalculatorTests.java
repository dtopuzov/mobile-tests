package calculator.tests;

import calculator.enums.OperationType;
import calculator.pages.HomePage;
import mobile.tests.core.base.test.BaseTest;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Smoke tests for Calculator test app.
 */
public class CalculatorTests extends BaseTest {

    // This method will provide data for calculator tests
    @DataProvider(name = "calcDataProvider")
    public Object[][] createData() {
        return new Object[][]{
                // OperationType, firstDigit, secondDigit, expectedResult
                {OperationType.PLUS, 1, 1, "2"},
                {OperationType.PLUS, 1, 2, "3"},
                {OperationType.PLUS, 1, 3, "4"},
                {OperationType.PLUS, 1, 4, "5"}
        };
    }

    @Test(dataProvider = "calcDataProvider")
    public void calcOperations(String operation, int firstDigit, int secondDigit, String expectedResult) {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(operation, firstDigit, secondDigit);
        Assert.assertEquals(expectedResult, calc.getResult());
    }

    @Test
    public void rotate() {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(OperationType.PLUS, 3, 3);
        this.app.rotate(ScreenOrientation.LANDSCAPE);
        Assert.assertEquals("6", calc.getResult());
        this.app.rotate(ScreenOrientation.PORTRAIT);
        Assert.assertEquals("6", calc.getResult());
    }

    @Test
    public void runInBackground() {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(OperationType.PLUS, 3, 4);
        this.app.runInBackground(10);
        Assert.assertEquals("7", calc.getResult());
    }
}
