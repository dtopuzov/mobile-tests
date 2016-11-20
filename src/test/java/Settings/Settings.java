package Settings;

import Enums.DeviceType;
import Enums.OSType;
import Enums.PlatformType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

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
    public String packageId;
    public int defaultTimeout;
    public String appiumLogLevel;
    public AndroidSettings android;
    public iOSSettings ios;

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
            log.fatal("Failed to read " + configFile);
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
            this.log.fatal("Unknown DeviceType.");
            throw new Exception("Unknown DeviceType.");
        }
    }

    private PlatformType getPlatformType() throws Exception {
        String platformType = properties.getProperty("platform", "generic").toLowerCase();
        if (platformType.contains("android")) {
            return PlatformType.Andorid;
        } else if (platformType.contains("ios")) {
            return PlatformType.iOS;
        } else {
            this.log.fatal("Unknown PlatformType.");
            throw new Exception("Unknown PlatformType.");
        }
    }

    private String getPackageId() {
        String packageId = null;
        if (this.platform == PlatformType.Andorid) {
            packageId = "io.selendroid.testapp";
            // TODO: Do not use hard-coded packageId
        } else if (this.platform == PlatformType.iOS) {
            // TODO: Implement it
        }
        return packageId;
    }

    private String getDefaultActivity() {
        if (this.platform == PlatformType.Andorid) {
            String defaultActivity = "io.selendroid.testapp.HomeScreenActivity";
            // TODO: Do not use hard-coded defaultActivity
            return defaultActivity;
        } else {
            return null; // Default activity is applicable only for Android Apps
        }
    }

    private void initSettings() throws Exception {
        this.os = getOSType();
        this.platform = getPlatformType();
        this.platformVersion = Double.parseDouble(properties.getProperty("platformVersion", null));
        this.deviceName = properties.getProperty("deviceName", null);
        this.deviceType = getDeviceType();
        this.deviceId = properties.getProperty("deviceId", null);
        this.testApp = properties.getProperty("testapp", null);
        this.packageId = getPackageId();
        this.defaultTimeout = Integer.parseInt(properties.getProperty("defaultTimeout", "30"));
        this.appiumLogLevel = properties.getProperty("appiumLogLevel", "warn");
    }

    private void initPlatformSpecificSettings() {
        if (this.platform == PlatformType.Andorid) {
            this.android = this.initAndroidSettings();
        } else if (this.platform == PlatformType.iOS) {
            this.ios = this.initiOSSettings();
        }
    }

    private iOSSettings initiOSSettings() {
        this.ios = new iOSSettings();

        // Set acceptAlerts
        this.ios.acceptAlerts = this.propertyToBoolean("acceptAlerts", false);
        this.log.info("(iOS Only) Auto Accept Alerts: " + this.ios.acceptAlerts);

        return this.ios;
    }

    private AndroidSettings initAndroidSettings() {
        this.android = new AndroidSettings();

        // Set defaultActivity
        this.android.defaultActivity = this.getDefaultActivity();
        this.log.info("(Android Only) Default Activity: " + this.android.defaultActivity);

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
        log.info("Init settings...");
        String config = System.getProperty("config");
        if (config != null) {
            log.debug("Configuration: " + config);
            properties = readPropertiesFile(config);
            initSettings();
            initPlatformSpecificSettings();
        } else {
            log.fatal("Config file not specified.");
            throw new Exception("Config file not specified.");
        }
    }
}
