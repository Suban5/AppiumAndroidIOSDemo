package com.suban.base;

import com.suban.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected AppiumDriver driver;

    @BeforeSuite
    public void globalSetup() throws Exception {
        String platform = ConfigReader.getProperty("platform");
        if (platform.equalsIgnoreCase("android")) {
            DeviceManager.startAndroidEmulator(ConfigReader.getProperty("android.avd.name"));
        } else {
            DeviceManager.startIOSSimulator(ConfigReader.getProperty("ios.simulator.udid"));
        }
        AppiumServer.startServer();
    }

    @BeforeMethod
    public void setup() throws Exception {
        driver = DriverManager.getDriver(ConfigReader.getProperty("platform"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterSuite
    public void globalTearDown() throws Exception {
        AppiumServer.stopServer();
        if (ConfigReader.getProperty("platform").equalsIgnoreCase("android")) {
            DeviceManager.stopAndroidEmulator();
        } else {
            DeviceManager.stopIOSSimulator(ConfigReader.getProperty("ios.simulator.udid"));
        }
    }
}
