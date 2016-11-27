package mobile.tests.core.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import mobile.tests.core.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Appium server.
 * TODO(dtopuzov): Add better docs
 */
public class Server {

    public AppiumDriverLocalService service;

    private Settings settings;
    private Logger log = LogManager.getLogger(Server.class.getName());
    private String appiumPath = System.getenv("APPDATA") + "\\npm\\node_modules\\appium\\build\\lib\\main.js";
    private String logPath = System.getProperty("user.dir") + "\\build\\test-results\\log\\appium-server.log";
    private File appiumExecutable = new File(this.appiumPath);
    private File logFile = new File(this.logPath);

    public Server(Settings settings) {
        this.settings = settings;
    }

    public void startAppiumServer() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withAppiumJS(this.appiumExecutable)
                .withStartUpTimeOut(180, TimeUnit.SECONDS)
                .withLogFile(this.logFile)
                .withArgument(GeneralServerFlag.LOG_LEVEL, this.settings.appiumLogLevel);

        this.log.info("Starting appium server...");
        this.service = AppiumDriverLocalService.buildService(serviceBuilder);
        this.service.start();
        this.log.info("Appium server up and running!");

        if (this.service == null || !this.service.isRunning()) {
            throw new RuntimeException("Client server is not started!");
        }
    }

    public void stopAppiumServer() {
        if (this.service != null) {
            this.log.info("Quit appium server.");
            this.service.stop();
        }
    }
}
