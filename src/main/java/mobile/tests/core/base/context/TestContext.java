package mobile.tests.core.base.context;

import io.appium.java_client.AppiumDriver;
import mobile.tests.core.base.app.App;
import mobile.tests.core.settings.Settings;

/**
 * Test context.
 */
public class TestContext {

    public Settings settings;
    public AppiumDriver driver;
    public App app;

    public TestContext(Settings settings, AppiumDriver driver, App app) {
        this.settings = settings;
        this.driver = driver;
        this.app = app;
    }
}
