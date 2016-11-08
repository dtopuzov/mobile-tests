package BaseTest;

import Appium.Client;
import Appium.Server;
import Settings.Settings;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    public static Settings settings;


    public BaseTest() throws Exception {
        System.out.println("BaseTest init...");
        settings = new Settings();
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        Server.startAppiumServer(settings);
        Client.startAppiumClient(settings);
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
