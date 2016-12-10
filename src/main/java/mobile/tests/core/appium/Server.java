package mobile.tests.core.appium;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import mobile.tests.core.enums.OSType;
import mobile.tests.core.settings.Settings;
import mobile.tests.core.utils.os.Process;
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

    public Server(Settings settings) {
        this.settings = settings;
    }

    // Get appium executable path
    private File getAppiumExecutable() {
        String appiumPath = "";
        if (this.settings.os == OSType.Windows) {
            appiumPath = System.getenv("APPDATA") + "\\npm\\node_modules\\appium\\build\\lib\\main.js";
        } else {
            appiumPath = Process.runProcess("which appium").trim();
        }
        this.log.info("[Appium Server] Path: " + appiumPath);
        return new File(appiumPath);
    }

    // Set location where appium log files will be writen
    private File setServerLogPath() {
        String logPath = this.settings.logFilesPath + File.separator + "appium-server.log";
        this.log.info("[Appium Server] Log: " + logPath);
        return new File(logPath);
    }

    public void startAppiumServer() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withAppiumJS(this.getAppiumExecutable())
                .withStartUpTimeOut(180, TimeUnit.SECONDS)
                .withLogFile(this.setServerLogPath())
                .withArgument(GeneralServerFlag.LOG_LEVEL, this.settings.appiumLogLevel);

        this.log.info("Starting appium server...");
        this.service = AppiumDriverLocalService.buildService(serviceBuilder);
        this.service.start();
        this.log.info("Appium server up and running!");

        if (this.service == null || !this.service.isRunning()) {
            throw new RuntimeException("Appium server is not started!");
        }
    }

    public void stopAppiumServer() {
        if (this.service != null) {
            this.log.info("Quit appium server.");
            this.service.stop();
        }
    }
}
