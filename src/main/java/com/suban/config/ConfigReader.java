package com.suban.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    static {
        loadConfig();
    }

    private static void loadConfig() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
            logger.info("Loaded config file: {}", CONFIG_PATH);
        } catch (IOException e) {
            logger.error("Failed to load config file: {}", CONFIG_PATH, e);
            throw new RuntimeException("Failed to load config file: " + CONFIG_PATH, e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.error("Property '{}' not found in config file", key);
            throw new RuntimeException("Property '" + key + "' not found in config file");
        }
        logger.debug("Property loaded: {}={}", key, value);
        return value;
    }

    public static String getPlatformProperty(String key) {
        String platform = getProperty("platform");
        String propFileName = platform + ".properties";
        Properties platformProps = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/resources/" + propFileName)) {
            platformProps.load(input);
            String value = platformProps.getProperty(key);
            logger.debug("Platform property loaded: {} from {} = {}", key, propFileName, value);
            return value;
        } catch (IOException e) {
            logger.error("Failed to load platform properties: {}", propFileName, e);
            throw new RuntimeException("Failed to load platform properties: " + propFileName, e);
        }
    }

    public static int getIntProperty(String key) {
        int value = Integer.parseInt(getProperty(key));
        logger.debug("Integer property loaded: {}={}", key, value);
        return value;
    }

    public static boolean getBooleanProperty(String key) {
        boolean value = Boolean.parseBoolean(getProperty(key));
        logger.debug("Boolean property loaded: {}={}", key, value);
        return value;
    }
}
