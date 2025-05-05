package com.suban.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";

    static {
        loadConfig();
    }

    private static void loadConfig() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + CONFIG_PATH, e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config file");
        }
        return value;
    }

    public static String getPlatformProperty(String key) {
        String platform = getProperty("platform");
        String propFileName = platform + ".properties";
        Properties platformProps = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/resources/" + propFileName)) {
            platformProps.load(input);
            return platformProps.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load platform properties: " + propFileName, e);
        }
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
}
