package com.suban;

import com.suban.base.BaseTest;
import com.suban.utils.TestListenerUtils;
import com.suban.pages.common.HomePage;
import com.suban.pages.common.LoginPage;
import com.suban.pages.common.LoginSuccessPage;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListenerUtils.class)
public class LoginTest extends BaseTest {
    @Test
    public void testLogin() {
        try {
            HomePage homePage = new HomePage((AppiumDriver) driver);

            homePage.ClickLoginScreen();


            LoginPage loginPage = new LoginPage((AppiumDriver) driver);
            Assert.assertTrue(loginPage.isLoginHeaderPresent());

            loginPage.login("foo", "bar");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
            Assert.assertEquals(loginPage.getErrorMessage(), "Invalid login credentials, please try again");
            loginPage.clickOkButton();

           // loginPage = new LoginPage((AppiumDriver) driver);
            loginPage.login("alice", "mypassword");

            LoginSuccessPage loginSuccessPage = new LoginSuccessPage((AppiumDriver) driver);
            Assert.assertTrue(loginSuccessPage.isSecretAreaPresent());

            loginSuccessPage.clickLogoutButton();

            Assert.assertTrue(loginPage.isLoginHeaderPresent());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}