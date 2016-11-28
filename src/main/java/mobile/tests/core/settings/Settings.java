package mobile.tests.core.settings;

import mobile.tests.core.enums.ApplicationType;
import mobile.tests.core.enums.DeviceType;
import mobile.tests.core.enums.OSType;
import mobile.tests.core.enums.PlatformType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Settings used in test runs.
 * <p>
 * Settings can be defined in test/resources/config/<name-of-config>.properties
 * <p>
 * Description:
 * platform - PlatformType enum value (Android or iOS).
 * platformVersion - Version of the platform on device under test.
 * deviceName - Name of mobile device.
 * In case of Emulators - framework will automatically start emulator with specified name.
 * deviceType - DeviceType enum value (Android, iOS, Emulator or Simulator)
 * TODO(dtopuzov): Describe all the mobile.tests.core.settings
 */
public class Settings {

    /* Sample config file
        platform=Android
        platformVersion=4.3
        deviceName=Sony M4
        deviceType=Android
        deviceId=YT910LNE9U
        testapp=selendroid-test-app-0.11.0.apk
        defaultTimeout=30
        appiumLogLevel=warn
    */

    public OSType os;
    public PlatformType platform;
    public double platformVersion;
    public String deviceName;
    public DeviceType deviceType;
    public String deviceId;
    public String testApp;
    public ApplicationType testAppType;
    public String packageId;
    public int defaultTimeout;
    public String appiumLogLevel;
    public AndroidSettings android;
    public IOSSettings ios;
    public String logFilesPath = System.getProperty("user.dir") + "\\build\\test-results\\log\\";
    public String screenshotFilesPath = System.getProperty("user.dir") + "\\build\\test-results\\screenshots\\";
    private Properties properties;
    private Logger log = LogManager.getLogger(Settings.class.getName());

    private Properties readPropertiesFile(String configFile) throws Exception {
        try {
            String path = "config/" + configFile + ".properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            this.log.fatal("Failed to read " + configFile);
            throw new Exception("Failed to read properties from " + configFile);
        }
    }

    private OSType getOSType() throws Exception {
        String operationSystem = System.getProperty("os.name", "generic").toLowerCase();
        if ((operationSystem.contains("mac")) || (operationSystem.contains("darwin"))) {
            return OSType.MacOS;
        } else if (operationSystem.contains("win")) {
            return OSType.Windows;
        } else if (operationSystem.contains("nux")) {
            return OSType.Linux;
        } else {
            this.log.fatal("Unknown OS.");
            throw new Exception("Unknown OS.");
        }
    }

    private DeviceType getDeviceType() throws Exception {
        String deviceType = this.properties.getProperty("deviceType", "generic").toLowerCase();
        if (deviceType.contains("android")) {
            return DeviceType.Android;
        } else if (deviceType.contains("ios")) {
            return DeviceType.iOS;
        } else if (deviceType.contains("emu")) {
            return DeviceType.Emulator;
        } else if (deviceType.contains("sim")) {
            return DeviceType.Simulator;
        } else {
            this.log.fatal("Unknown DeviceType.");
            throw new Exception("Unknown DeviceType.");
        }
    }

    private PlatformType getPlatformType() throws Exception {
        String platformType = this.properties.getProperty("platform", "generic").toLowerCase();
        if (platformType.contains("android")) {
            return PlatformType.Android;
        } else if (platformType.contains("ios")) {
            return PlatformType.iOS;
        } else {
            this.log.fatal("Unknown PlatformType.");
            throw new Exception("Unknown PlatformType.");
        }
    }

    private ApplicationType getTestAppType() {
        String testAppType = this.properties.getProperty("testAppType", "Native").toLowerCase();
        if (testAppType.contains("native")) {
            return ApplicationType.Native;
        } else if (testAppType.contains("hybrid")) {
            return ApplicationType.Hybrid;
        } else if (testAppType.contains("web")) {
            return ApplicationType.Web;
        } else {
            return null;
        }
    }


    private String getPackageId() {
        String packageId = this.properties.getProperty("packageId", null);
        if (packageId == null) {
            if (this.platform == PlatformType.Android) {
                packageId = "io.selendroid.testapp";
                // TODO(dtopuzov): Do not use hard-coded packageId
            } else if (this.platform == PlatformType.iOS) {
                // TODO(dtopuzov): Implement it
            }
        }
        return packageId;
    }

    private String getDefaultActivity() {
        String defaultActivity = this.properties.getProperty("defaultActivity", null);
        if (defaultActivity == null) {
            if (this.platform == PlatformType.Android) {
                defaultActivity = "io.selendroid.testapp.HomeScreenActivity";
                // TODO(dtopuzov): Do not use hard-coded defaultActivity
                return defaultActivity;
            } else {
                return null; // Default activity is applicable only for Android Apps
            }
        }
        return defaultActivity;
    }

    private void initSettings() throws Exception {
        this.os = this.getOSType();
        this.platform = this.getPlatformType();
        this.platformVersion = Double.parseDouble(this.properties.getProperty("platformVersion", null));
        this.deviceName = this.properties.getProperty("deviceName", null);
        this.deviceType = this.getDeviceType();
        this.deviceId = this.properties.getProperty("deviceId", null);
        this.testApp = this.properties.getProperty("testApp", null);
        this.testAppType = this.getTestAppType();
        this.packageId = this.getPackageId();
        this.defaultTimeout = Integer.parseInt(this.properties.getProperty("defaultTimeout", "30"));
        this.appiumLogLevel = this.properties.getProperty("appiumLogLevel", "warn");

        this.log.info("Host OS: " + this.ios);
        this.log.info("Mobile OS: " + this.platform);
        this.log.info("Mobile OS Version: " + this.platformVersion);
        this.log.info("Mobile Device Name: " + this.deviceName);
        this.log.info("Mobile Device Type: " + this.deviceType);
        this.log.info("Mobile Device Id: " + this.deviceId);
        this.log.info("TestApp: " + this.testApp);
        this.log.info("TestApp Type: " + this.testAppType);
        this.log.info("TestApp PackageId: " + this.packageId);
        this.log.info("Appium Default Timeout: " + this.defaultTimeout);
        this.log.info("Appium Server Log Level: " + this.appiumLogLevel);
        this.log.info("Log files location: " + this.logFilesPath);
        this.log.info("Screenshots location: " + this.screenshotFilesPath);
    }

    private void initPlatformSpecificSettings() {
        if (this.platform == PlatformType.Android) {
            this.android = this.initAndroidSettings();
        } else if (this.platform == PlatformType.iOS) {
            this.ios = this.initIOSSettings();
        }
    }

    private IOSSettings initIOSSettings() {
        this.ios = new IOSSettings();

        // Set acceptAlerts
        this.ios.acceptAlerts = this.propertyToBoolean("acceptAlerts", false);
        this.log.info("[iOS Only] Auto Accept Alerts: " + this.ios.acceptAlerts);

        return this.ios;
    }

    private AndroidSettings initAndroidSettings() {
        this.android = new AndroidSettings();

        // Set defaultActivity
        this.android.defaultActivity = this.getDefaultActivity();
        this.log.info("[Android Only] Default Activity: " + this.android.defaultActivity);

        return this.android;
    }

    private Boolean propertyToBoolean(String str, boolean defaultValue) {
        String value = this.properties.getProperty(str);
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

    public Settings() throws Exception {
        this.log.info("Init mobile.tests.core.settings...");
        String config = System.getProperty("config");
        if (config != null) {
            this.log.debug("Configuration: " + config);
            this.properties = this.readPropertiesFile(config);
            this.initSettings();
            this.initPlatformSpecificSettings();
        } else {
            this.log.fatal("Config file not specified.");
            throw new Exception("Config file not specified.");
        }
    }
}
