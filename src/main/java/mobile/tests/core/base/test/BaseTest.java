package mobile.tests.core.base.test;

import mobile.tests.core.appium.Client;
import mobile.tests.core.appium.Server;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/**
 * Base test.
 * TODO(dtopuzov): Add better docs
 */
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
        if (this.failedToInitSettings != null) {
            this.log.fatal("Failed to init mobile.tests.core.settings.");
            throw new Exception(this.failedToInitSettings);
        }
    }

    public BaseTest() {
        this.initSettings();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {
        this.verifySettings();

        Server.startAppiumServer(this.settings);
        Client.startAppiumClient(this.settings);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        String testName = method.getName();
        this.log.info("Start: " + testName);
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) {
        int status = result.getStatus();
        if (status == ITestResult.FAILURE) {
            try {
                this.log.debug(Client.driver.getPageSource());
            } catch (Exception e) {
                this.log.error("Failed to get page source.");
            }
        }
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        Client.stopAppiumClient();
        Server.stopAppiumServer();
    }
}
