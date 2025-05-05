package com.suban.pages.common;

import com.suban.base.AppiumServer;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {


    public LoginPage(AppiumDriver driver) {
        super(driver);
    }
    @AndroidFindBy(accessibility = "username")
    @iOSXCUITFindBy(accessibility = "username")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "password")
    @iOSXCUITFindBy(accessibility = "password")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "loginBtn")
    @iOSXCUITFindBy(accessibility = "loginBtn")
    private WebElement loginButton;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"TheApp\"]")
    private WebElement backButton;

    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public void clickBackButton() {
        backButton.click();
    }
}