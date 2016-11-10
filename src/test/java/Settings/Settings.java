package Settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private Logger log = LogManager.getRootLogger();

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

    public void initSettings() throws Exception {
        defaultTimeout = 60;
        platform = properties.getProperty("Android", null);
        platformVersion = "4.4";
        deviceName = properties.getProperty("deviceName", null);
        testApp = properties.getProperty("testapp", null);
        appiumLogLevel = properties.getProperty("appiumLogLevel", "warn");
    }

    public Settings() throws Exception {
        log.info("Init settings...");
        String config = System.getProperty("config");
        if (config != null) {
            log.debug("Configuration: " + config);
            properties = readPropertiesFile(config);
            initSettings();
        } else {
            log.fatal("Config file not specified.");
            throw new Exception("Config file not specified.");
        }
    }
}
