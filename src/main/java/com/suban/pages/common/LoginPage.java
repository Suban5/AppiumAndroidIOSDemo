package com.suban.pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {


    @AndroidFindBy(xpath = "(//android.widget.TextView[@text=\"Login\"])[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Login\"]")
    private WebElement loginHeader;

    @AndroidFindBy(accessibility = "username")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@name=\"username\"]")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "password")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"password\"]")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "loginBtn")
    @iOSXCUITFindBy(accessibility = "loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(id = "android:id/message")
    @iOSXCUITFindBy(accessibility = "Invalid login credentials, please try again")
    private WebElement errorMessage;

    @AndroidFindBy(id = "android:id/button1")
    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement clickOkButtonInPopup;

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isLoginHeaderPresent() {
        return loginHeader.isDisplayed();
    }

    public void login(String username, String password) {
        usernameField.click();
        usernameField.clear();

        usernameField.sendKeys(username);

        passwordField.click();
         passwordField.clear();

        passwordField.sendKeys(password);
        loginButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public void clickOkButton() {
        clickOkButtonInPopup.click();
    }


}