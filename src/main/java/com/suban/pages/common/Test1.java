package com.suban.pages.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class Test1 extends BasePage {
    public Test1(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "navBar")
    @iOSXCUITFindBy(accessibility = "navBar")
    private WebElement navBar;

    @AndroidFindBy(id = "btnBack")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    public boolean isNavBarDisplayed() {
        return navBar.isDisplayed();
    }

    public void clickBack() {
        backButton.click();
    }
}
