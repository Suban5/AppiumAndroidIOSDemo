package com.suban.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportUtils {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

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
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
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
        test.get().addScreenCaptureFromPath(screenshotPath, title);
    }

    public static void flushReports() {
        extent.flush();
    }

    public static void logTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            logPass("Test Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logFail("Test Failed: " + result.getThrowable());
        } else {
            log(Status.SKIP, "Test Skipped");
        }
    }
}
