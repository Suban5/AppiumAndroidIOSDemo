package com.suban.pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "Echo Box")
    @iOSXCUITFindBy(accessibility = "Echo Box")
    private WebElement echoBox;

    @AndroidFindBy(accessibility = "Login Screen")
    @iOSXCUITFindBy(accessibility = "Login Screen")
    private WebElement loginScreen;

    public void ClickLoginScreen() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(loginScreen)).click();
    }
}
