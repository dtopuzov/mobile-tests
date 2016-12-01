package calculator.tests;

import calculator.enums.OperationType;
import calculator.pages.HomePage;
import mobile.tests.core.base.test.BaseTest;
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
                {OperationType.MINUS, 3, 2, "1"},
                {OperationType.MULTIPLY, 2, 2, "4"},
                {OperationType.DIVIDE, 9, 3, "3"}
        };
    }

    @Test(dataProvider = "calcDataProvider")
    public void calcTest(String operation, int firstDigit, int secondDigit, String expectedResult) {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(operation, firstDigit, secondDigit);
        Assert.assertEquals(expectedResult, calc.getResult());
    }
}
