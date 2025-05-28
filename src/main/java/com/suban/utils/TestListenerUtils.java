package com.suban.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListenerUtils implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListenerUtils.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test suite started: {}", context.getName());
        // Initialize reports when test suite starts
        ReportUtils.initReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in the report when a test method starts
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String fullTestName = testDescription != null && !testDescription.isEmpty() ?
                testName + " - " + testDescription : testName;

        logger.info("Test started: {}", fullTestName);
        ReportUtils.createTest(fullTestName);
        ReportUtils.logInfo("Test started: " + fullTestName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: {}", result.getMethod().getMethodName());
        ReportUtils.logTestResult(result);
        ReportUtils.logPass("Test completed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: {}", result.getMethod().getMethodName());
        ReportUtils.logTestResult(result);

        // Add screenshot for failed tests
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshot(result.getMethod().getMethodName());
            logger.info("Screenshot captured for failure: {}", screenshotPath);
            ReportUtils.addScreenshot(screenshotPath, "Failure Screenshot");
            ReportUtils.logFail("Screenshot captured for failure");
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            ReportUtils.logFail("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: {} Reason: {}", result.getMethod().getMethodName(), result.getThrowable());
        ReportUtils.logTestResult(result);
        ReportUtils.logInfo("Test was skipped due to: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite finished: {}", context.getName());
        // Flush the report when the test suite finishes
        ReportUtils.flushReports();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("Test failed but within success percentage: {}", result.getMethod().getMethodName());
        // Handle tests that failed but are within success percentage
    }
}

