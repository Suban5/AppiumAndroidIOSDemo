package com.suban.utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static AppiumDriver driver; // You'll need to set this driver instance

    public static void setDriver(AppiumDriver driverInstance) {
        driver = driverInstance;
    }

    public static String captureScreenshot(String screenshotName) throws Exception {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" +
                screenshotName + "_" + timeStamp + ".png";

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(screenshotPath);
        FileUtils.copyFile(source, destination);

        return screenshotPath;
    }
}