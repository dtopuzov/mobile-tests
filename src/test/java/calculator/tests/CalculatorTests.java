package calculator.tests;

import calculator.enums.OperationType;
import calculator.pages.HomePage;
import mobile.tests.core.base.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Smoke tests for Calculator test app.
 */
public class CalculatorTests extends BaseTest {

    @Test
    public void plus() {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(OperationType.Plus, 1, 1);
        Assert.assertEquals("2", calc.getResult());
    }

    @Test
    public void minus() {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(OperationType.Minus, 2, 1);
        Assert.assertEquals("1", calc.getResult());
    }

    @Test
    public void multiply() {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(OperationType.Multiply, 2, 2);
        Assert.assertEquals("4", calc.getResult());
    }

    @Test
    public void sum() {
        HomePage calc = new HomePage(this.client.driver);
        calc.performOperation(OperationType.Divide, 9, 3);
        Assert.assertEquals("3", calc.getResult());
    }
}
