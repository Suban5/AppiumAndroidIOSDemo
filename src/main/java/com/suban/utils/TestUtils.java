package com.suban.utils;

import com.suban.config.ConfigReader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class TestUtils {
    private static final int EXPLICIT_WAIT = ConfigReader.getIntProperty("explicit.wait");
    private static final Logger logger = LoggerFactory.getLogger(TestUtils.class);

    public static WebElement waitForElement(AppiumDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static List<WebElement> waitForElements(AppiumDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static void click(AppiumDriver driver, By locator) {
        waitForElement(driver, locator).click();
    }

    public static void sendKeys(AppiumDriver driver, By locator, String text) {
        WebElement element = waitForElement(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    public static boolean isElementPresent(AppiumDriver driver, By locator) {
        try {
            waitForElement(driver, locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static String takeScreenshot(AppiumDriver driver, String testName) {
        String screenshotDir = "test-output/screenshots/";
        new File(screenshotDir).mkdirs();

        String fileName = testName + "_" + System.currentTimeMillis() + ".png";
        String filePath = screenshotDir + fileName;

        try {
            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(filePath));
            logger.info("Screenshot taken: {}", filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to take screenshot: " + e.getMessage());
        }
    }

    public static void scrollToElement(AppiumDriver driver, String text) {
        String uiAutomator = "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))";
        driver.findElement(AppiumBy.androidUIAutomator(uiAutomator));
    }

    public static void hideKeyboard(AppiumDriver driver) {
        try {
            driver.executeScript("mobile: hideKeyboard");
            // driver.hideKeyboard();
        } catch (Exception e) {
            logger.debug("Keyboard was not visible or could not be hidden: {}", e.getMessage());
        }
    }
}
