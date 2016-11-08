package Settings;

import java.io.File;
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
            String path = "config/" + configFile + ".properties";
            System.out.println(path);
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            System.out.println(getClass().getClassLoader().getResource(path).getPath());
            Properties properties = new Properties();
            properties.load(inputStream);
            System.out.println("count: " + properties.size());
            System.out.println(properties.getProperty("deviceName", " "));
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
        appiumLogLevel = properties.getProperty("appiumLogLevel", "warn");
        System.out.println("platform: " + platform);
    }

    public Settings() throws Exception {
        System.out.println("Settings init...");
        String config = System.getProperty("config");
        if (config != null) {
            System.out.println("Read settings from " + config);
            properties = readPropertiesFile(config);
            initSettings();
        } else {
            throw new Exception("Config file not specified.");
        }
    }
}
