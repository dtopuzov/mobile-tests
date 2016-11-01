package Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HelloWorldTests {

    @Test
    public void twoIntsAreEqual() {
        Assert.assertEquals(2, 2, "2 and 2 are not equal");
    }

    @Test
    public void intAnsStringAreEqual() {
        Assert.assertEquals(2, "2", "2 and 2 are not equal");
    }
}
