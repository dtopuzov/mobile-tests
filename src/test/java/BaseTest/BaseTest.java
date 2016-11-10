package BaseTest;

import Appium.Client;
import Appium.Server;
import Settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {

    public Settings settings;
    public Logger log = LogManager.getLogger(BaseTest.class.getName());

    private Exception failedToInitSettings = null;

    private void initSettings() {
        try {
            this.settings = new Settings();
        } catch (Exception e) {
            this.failedToInitSettings = e;
        }
    }

    private void verifySettings() throws Exception {
        if (failedToInitSettings != null) {
            this.log.fatal("Failed to init settings.");
            throw new Exception(failedToInitSettings);
        }
    }

    public BaseTest() {
        initSettings();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {
        verifySettings();

        Server.startAppiumServer(settings);
        Client.startAppiumClient(settings);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        String testName = method.getName();
        this.log.info("Start: " + testName);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        Client.stopAppiumClient();
        Server.stopAppiumServer();
    }
}
