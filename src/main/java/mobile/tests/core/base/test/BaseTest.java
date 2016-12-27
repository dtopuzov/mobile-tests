package mobile.tests.core.base.test;

import mobile.tests.core.appium.Client;
import mobile.tests.core.appium.Server;
import mobile.tests.core.base.app.App;
import mobile.tests.core.base.context.TestContext;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

/**
 * Base test.
 * TODO(dtopuzov): Add better docs
 */
public class BaseTest {

    public Settings settings;
    public App app;
    public Logger log = LogManager.getLogger(BaseTest.class.getName());
    public TestContext context;

    private Client client;
    private Server server;
    private Exception failedToInitSettings = null;
    private boolean isFirstTest = true;

    private void initSettings() {
        try {
            this.settings = new Settings();
        } catch (Exception e) {
            this.failedToInitSettings = e;
        }
    }

    private void initAppium() {
        this.server = new Server(this.settings);
        this.server.startAppiumServer();
        this.client = new Client(this.server.service, this.settings);
    }

    private void verifySettings() throws Exception {
        if (this.failedToInitSettings != null) {
            this.log.fatal("Failed to init mobile.tests.core.settings.");
            throw new Exception(this.failedToInitSettings);
        }
    }

    public BaseTest() {
        this.initSettings();
        this.initAppium();
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeClass() throws Exception {
        this.verifySettings();
        this.client.startAppiumClient();
        this.app = new App(this.settings, this.client.driver);
        this.context = new TestContext(this.settings, this.client.driver, this.app);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        String testName = method.getName();
        this.log.info("Start: " + testName);
        if ((this.settings.restartApp == true) && (!this.isFirstTest)) {
            this.app.restart();
        }
        this.isFirstTest = false;
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) {
        int status = result.getStatus();
        if (status == ITestResult.FAILURE) {
            try {
                this.log.debug(this.client.driver.getPageSource());
            } catch (Exception e) {
                this.log.error("Failed to get page source.");
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterClass() {
        this.client.stopAppiumClient();
        this.server.stopAppiumServer();
    }
}
