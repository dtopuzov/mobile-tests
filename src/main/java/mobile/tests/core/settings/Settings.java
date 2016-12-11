package mobile.tests.core.settings;

import io.appium.java_client.remote.MobileBrowserType;
import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.enums.DeviceType;
import mobile.tests.core.enums.OSType;
import mobile.tests.core.enums.PlatformType;
import mobile.tests.core.utils.android.Aapt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.getProperty;

/**
 * Settings used in test runs.
 */
public class Settings {
    public static OSType os;
    public static PlatformType platform;
    public static double platformVersion;
    public static String deviceName;
    public static DeviceType deviceType;
    public static String deviceId;
    public static ApplicationType testAppType;
    public static boolean restartApp;
    public static int defaultTimeout;
    public static int deviceBootTimeout;
    public static String appiumLogLevel;
    public static AppSettings app;
    public static WebSettings web;
    public static AndroidSettings android;
    public static IOSSettings ios;
    public static String logFilesPath = getProperty("user.dir") + "\\build\\test-results\\log";
    public static String screenshotFilesPath = getProperty("user.dir") + "\\build\\test-results\\screenshots";
    public static boolean debug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().indexOf("jdwp") >= 0;


    private static Properties properties;
    private static Aapt aapt = new Aapt();
    private static Logger log = LogManager.getLogger(Settings.class.getName());

    public Settings() throws Exception {
        log.info("Init settings...");
        String config = getProperty("config");
        if (config != null) {
            log.debug("Configuration: " + config);
            properties = readPropertiesFile(config);
            initSettings();
            initApplicationTypeSpecificSettings();
            initPlatformSpecificSettings();
        } else {
            log.fatal("Config file not specified.");
            throw new Exception("Config file not specified.");
        }
    }

    private static Properties readPropertiesFile(String configFile) throws Exception {
        try {
            String path = "config/" + configFile + ".properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            log.fatal("Failed to read " + configFile);
            throw new Exception("Failed to read properties from " + configFile);
        }
    }

    private static OSType getOSType() throws Exception {
        String operationSystem = getProperty("os.name", "generic").toLowerCase();
        if ((operationSystem.contains("mac")) || (operationSystem.contains("darwin"))) {
            return OSType.MacOS;
        } else if (operationSystem.contains("win")) {
            return OSType.Windows;
        } else if (operationSystem.contains("nux")) {
            return OSType.Linux;
        } else {
            log.fatal("Unknown OS.");
            throw new Exception("Unknown OS.");
        }
    }

    private static DeviceType getDeviceType() throws Exception {
        String deviceType = properties.getProperty("deviceType", "generic").toLowerCase();
        if (deviceType.contains("android")) {
            return DeviceType.Android;
        } else if (deviceType.contains("ios")) {
            return DeviceType.iOS;
        } else if (deviceType.contains("emu")) {
            return DeviceType.Emulator;
        } else if (deviceType.contains("sim")) {
            return DeviceType.Simulator;
        } else {
            log.fatal("Unknown DeviceType.");
            throw new Exception("Unknown DeviceType.");
        }
    }

    private static PlatformType getPlatformType() throws Exception {
        String platformType = properties.getProperty("platform", "generic").toLowerCase();
        if (platformType.contains("android")) {
            return PlatformType.Android;
        } else if (platformType.contains("ios")) {
            return PlatformType.iOS;
        } else {
            log.fatal("Unknown PlatformType.");
            throw new Exception("Unknown PlatformType.");
        }
    }

    private static String getBrowserType() throws Exception {
        String platformType = properties.getProperty("browser", "browser").toLowerCase();
        if (platformType.contains("browser")) {
            return MobileBrowserType.BROWSER;
        } else if (platformType.contains("chrome")) {
            return MobileBrowserType.CHROME;
        } else if (platformType.contains("chromium")) {
            return MobileBrowserType.CHROMIUM;
        } else if (platformType.contains("safari")) {
            return MobileBrowserType.SAFARI;
        } else {
            log.fatal("Unknown PlatformType.");
            throw new Exception("Unknown PlatformType.");
        }
    }

    private static ApplicationType getTestAppType() throws Exception {
        String testAppType = properties.getProperty("testAppType", "Native").toLowerCase();
        if (testAppType.contains("native")) {
            return ApplicationType.Native;
        } else if (testAppType.contains("hybrid")) {
            return ApplicationType.Hybrid;
        } else if (testAppType.contains("web")) {
            return ApplicationType.Web;
        } else {
            log.fatal("Invalid Application Type: " + testAppType);
            throw new Exception("Invalid Application Type: " + testAppType);
        }
    }

    private static String getPackageId() {
        String packageId = properties.getProperty("packageId", null);
        if (packageId == null) {
            if (platform == PlatformType.Android) {
                packageId = aapt.getPackage();
            } else if (platform == PlatformType.iOS) {
                // TODO(dtopuzov): Implement it
            }
        }
        return packageId;
    }

    private static String getTestAppName() {
        String testAppName = properties.getProperty("testAppName", null);
        if (testAppName == null) {
            if (platform == PlatformType.Android) {
                testAppName = aapt.getApplicationLabel();
                // Hack that might help in some cases
                if (testAppName == null) {
                    testAppName = app.testApp.replace(".apk", "");
                }
            } else if (platform == PlatformType.iOS) {
                // TODO(dtopuzov): Implement it
            }
        }
        return testAppName;
    }

    private static String getDefaultActivity() {
        String defaultActivity = properties.getProperty("defaultActivity", null);
        if (defaultActivity == null) {
            if (platform == PlatformType.Android) {
                defaultActivity = aapt.getLaunchableActivity();
                return defaultActivity;
            } else {
                // Default activity is applicable only for Android Apps
                return null;
            }
        }
        return defaultActivity;
    }

    private static void initSettings() throws Exception {
        os = getOSType();
        platform = getPlatformType();
        platformVersion = Double.parseDouble(properties.getProperty("platformVersion", null));
        deviceName = properties.getProperty("deviceName", null);
        deviceType = getDeviceType();
        deviceId = properties.getProperty("deviceId", null);
        testAppType = getTestAppType();
        restartApp = Boolean.parseBoolean(properties.getProperty("restartApp", "true"));
        defaultTimeout = Integer.parseInt(properties.getProperty("defaultTimeout", "30"));
        deviceBootTimeout = Integer.parseInt(properties.getProperty("deviceBootTimeout", "180"));
        appiumLogLevel = properties.getProperty("appiumLogLevel", "warn");
        log.info("[Host] Host OS: " + os);
        log.info("[Mobile Device] Mobile OS: " + platform);
        log.info("[Mobile Device] Mobile OS Version: " + platformVersion);
        log.info("[Mobile Device] Mobile Device Name: " + deviceName);
        log.info("[Mobile Device] Mobile Device Type: " + deviceType);
        log.info("[Mobile Device] Mobile Device Id: " + deviceId);
        log.info("[Appium] Restart TestApp Between Tests: " + restartApp);
        log.info("[Appium] Appium Default Timeout: " + defaultTimeout);
        log.info("[Appium] Device Boot Timeout: " + deviceBootTimeout);
        log.info("[Appium] Appium Server Log Level: " + appiumLogLevel);
        log.info("[Logs] Log files location: " + logFilesPath);
        log.info("[Logs] Screenshots location: " + screenshotFilesPath);
        log.info("[Other] Debug mode: " + debug);
        log.info("[TestApp] TestApp Type: " + testAppType);
    }

    private static void initApplicationTypeSpecificSettings() throws Exception {
        if (testAppType == ApplicationType.Web) {
            app = null;
            web = initWebSettings();
        } else {
            web = null;
            app = initAppSettings();
        }
    }

    private static void initPlatformSpecificSettings() {
        if (platform == PlatformType.Android) {
            android = initAndroidSettings();
        } else if (platform == PlatformType.iOS) {
            ios = initIOSSettings();
        }
    }

    private static AppSettings initAppSettings() {
        app = new AppSettings();

        app.testApp = properties.getProperty("testApp", null);
        log.info("[TestApp] TestApp File: " + app.testApp);

        app.testAppName = getTestAppName();
        log.info("[TestApp] TestApp Name: " + app.testAppName);

        app.packageId = getPackageId();
        log.info("[TestApp] TestApp PackageId: " + app.packageId);

        // Set defaultActivity
        if (platform == PlatformType.Android) {
            app.defaultActivity = getDefaultActivity();
            log.info("[TestApp] Default Activity: " + app.defaultActivity);
        }

        return app;
    }

    private static WebSettings initWebSettings() throws Exception {
        app = new AppSettings();
        web = new WebSettings();

        web.browser = getBrowserType();
        log.info("[Web] Browser: " + web.browser);

        if (platform == PlatformType.Android) {
            // Set packageId of browser
            app.packageId = "com.android." + web.browser.toLowerCase();
            log.info("[Web] Browser PackageId: " + app.packageId);
        }

        web.baseURL = properties.getProperty("baseURL", null);
        log.info("[Web] Base URL: " + web.baseURL);

        return web;
    }

    private static IOSSettings initIOSSettings() {
        ios = new IOSSettings();

        // Set acceptAlerts
        ios.acceptAlerts = propertyToBoolean("acceptAlerts", false);
        log.info("[iOS Only] Auto Accept Alerts: " + ios.acceptAlerts);

        return ios;
    }

    private static AndroidSettings initAndroidSettings() {
        android = new AndroidSettings();

        // Set emulatorOptions
        android.emulatorOptions = properties.getProperty("defaultActivity", "");
        log.info("[Android Only] Emulator Startup Options: " + android.emulatorOptions);

        return android;
    }

    private static Boolean propertyToBoolean(String str, boolean defaultValue) {
        String value = properties.getProperty(str);
        if (value == null) {
            return defaultValue;
        }
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            return null;
        }
    }
}
