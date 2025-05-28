package com.suban.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReportUtils.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReports() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "Test-Report-" + timeStamp + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/" + reportName);

        sparkReporter.config().setDocumentTitle("Appium Automation Report");
        sparkReporter.config().setReportName("Mobile Test Results");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        logger.info("Extent report initialized: {}", reportName);
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        logger.info("Test created in report: {}", testName);
    }

    public static void log(Status status, String message) {
        test.get().log(status, message);
    }

    public static void logPass(String message) {
        test.get().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public static void logFail(String message) {
        test.get().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
    }

    public static void logInfo(String message) {
        test.get().info(MarkupHelper.createLabel(message, ExtentColor.BLUE));
    }

    public static void addScreenshot(String screenshotPath, String title) {
        try {
            test.get().addScreenCaptureFromPath(screenshotPath, title);
            logger.info("Screenshot added to report: {} - {}", title, screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to add screenshot to report: {}", e.getMessage(), e);
        }
    }

    public static void flushReports() {
        extent.flush();
        logger.info("Extent report flushed and saved.");
    }

    public static void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            logPass("Test Passed");
            logger.info("Test passed: {}", result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logFail("Test Failed: " + result.getThrowable());
            logger.error("Test failed: {} - {}", result.getMethod().getMethodName(), result.getThrowable());
        } else {
            log(Status.SKIP, "Test Skipped");
            logger.warn("Test skipped: {}", result.getMethod().getMethodName());
        }
    }
}
