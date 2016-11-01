package BaseTest;

import Appium.Client;
import Appium.Server;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        Server.startAppiumServer();
        Client.startAppiumClient();
    }

    @BeforeTest
    public void beforeTest() {
    }

    @AfterTest
    public void afterTest() {
    }

    @AfterClass
    public static void afterClass() {
        Client.stopAppiumClient();
        Server.stopAppiumServer();
    }
}
