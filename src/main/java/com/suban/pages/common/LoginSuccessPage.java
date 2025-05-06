package com.suban.pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginSuccessPage extends BasePage{
    public LoginSuccessPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Secret Area\"]")
    @iOSXCUITFindBy(id = "Secret Area")
    private WebElement secretArea;

    @AndroidFindBy(accessibility = "Logout")
    @iOSXCUITFindBy(accessibility = "Logout")
    private WebElement logoutButton;

    public boolean isSecretAreaPresent() {
        return secretArea.isDisplayed();
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

}
