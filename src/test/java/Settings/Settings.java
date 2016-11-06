package Settings;

import java.io.InputStream;
import java.util.Properties;

public class Settings {

    public int defaultTimeout;
    public String platform;
    public String platformVersion;
    public String deviceName;
    public String testApp;
    public String appiumLogLevel;

    private Properties properties;

    private Properties readPropertiesFile(String configFile) throws Exception {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/" + configFile + ".properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            throw new Exception("Failed to read properties from " + configFile);
        }
    }

    public void initSettings() throws Exception {
        defaultTimeout = 60;
        platform = properties.getProperty("Android", null);
        platformVersion = "4.4";
        deviceName = properties.getProperty("deviceName", null);
        testApp = properties.getProperty("testapp", null);
        appiumLogLevel = "warn";
    }

    public Settings() throws Exception {
        String config = System.getProperty("config");
        if (config != null) {
            properties = readPropertiesFile(config);
            initSettings();
        } else {
            throw new Exception("Config file not specified.");
        }
    }
}
