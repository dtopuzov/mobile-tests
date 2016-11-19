package Appium;

import Settings.Settings;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Server {

    public static AppiumDriverLocalService service;

    private static Logger log = LogManager.getLogger(Server.class.getName());
    private static String appiumPath = System.getenv("APPDATA") + "\\npm\\node_modules\\appium\\build\\lib\\main.js";
    private static String logPath = System.getProperty("user.dir") + "\\build\\test-results\\log\\appium-server.log";
    private static File appiumExecutable = new File(appiumPath);
    private static File logFile = new File(logPath);

    public static void startAppiumServer(Settings settings) {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withAppiumJS(appiumExecutable)
                .withStartUpTimeOut(180, TimeUnit.SECONDS)
                .withLogFile(logFile)
                .withArgument(GeneralServerFlag.LOG_LEVEL, settings.appiumLogLevel);

        log.info("Starting appium server...");
        service = AppiumDriverLocalService.buildService(serviceBuilder);
        service.start();
        log.info("Appium server up and running!");

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("Client server is not started!");
        }
    }

    public static void stopAppiumServer() {
        if (service != null) {
            log.info("Quit appium server.");
            service.stop();
        }
    }
}
