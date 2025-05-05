package com.suban.base;

import com.suban.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.File;
import java.net.URL;

public class DriverManager {
    public static AppiumDriver getDriver(String platform) throws Exception {
        return platform.equalsIgnoreCase("android") ?
                createAndroidDriver() : createIOSDriver();
    }

    private static AndroidDriver createAndroidDriver() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator") // or your specific device name
                .setApp(new File(ConfigReader.getProperty("android.app.path")).getAbsolutePath())
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAppWaitActivity("*")
                .setAvd(ConfigReader.getProperty("android.avd.name"));
        return new AndroidDriver(new URL(ConfigReader.getProperty("appium.url")), options);
    }

    private static IOSDriver createIOSDriver() throws Exception {


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


