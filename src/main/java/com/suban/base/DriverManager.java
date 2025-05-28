package com.suban.base;

import com.suban.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    public static AppiumDriver getDriver(String platform) throws Exception {
        logger.info("Getting driver for platform: {}", platform);
        return platform.equalsIgnoreCase("android") ?
                createAndroidDriver() : createIOSDriver();
    }

    private static AndroidDriver createAndroidDriver() throws Exception {
        logger.info("Creating AndroidDriver instance");
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator") // or your specific device name
                .setApp(new File(ConfigReader.getProperty("android.app.path")).getAbsolutePath())
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAppWaitActivity("*")
                .setAvd(ConfigReader.getProperty("android.avd.name"))
                .setNoReset(true);
        return new AndroidDriver(new URL(ConfigReader.getProperty("appium.url")), options);
    }

    private static IOSDriver createIOSDriver() throws Exception {
        logger.info("Creating IOSDriver instance");
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(ConfigReader.getProperty("ios.simulator.name"))
                .setPlatformVersion(ConfigReader.getProperty("ios.platform.version"))
                .setUdid(ConfigReader.getProperty("ios.simulator.udid"))
                .setApp(System.getProperty("user.dir")+"/" +ConfigReader.getProperty("ios.app.path"))
                .setAutomationName("XCUITest")
                .setBundleId(ConfigReader.getProperty("ios.bundle.id"))
                .setNoReset(true)
                .setFullReset(false);
        return new IOSDriver(new URL(ConfigReader.getProperty("appium.url")), options);

    }
}

