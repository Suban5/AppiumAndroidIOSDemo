package com.suban.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListenerUtils implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
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

        ReportUtils.createTest(fullTestName);
        ReportUtils.logInfo("Test started: " + fullTestName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportUtils.logTestResult(result);
        ReportUtils.logPass("Test completed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ReportUtils.logTestResult(result);

        // Add screenshot for failed tests
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshot(result.getMethod().getMethodName());
            ReportUtils.addScreenshot(screenshotPath, "Failure Screenshot");
            ReportUtils.logFail("Screenshot captured for failure");
        } catch (Exception e) {
            ReportUtils.logFail("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportUtils.logTestResult(result);
        ReportUtils.logInfo("Test was skipped due to: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the report when the test suite finishes
        ReportUtils.flushReports();
    }

    // Optional: Implement these methods if needed
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Handle tests that failed but are within success percentage
    }
}