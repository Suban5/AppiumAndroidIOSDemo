package com.suban.ios;

import com.suban.base.BaseTest;
import com.suban.pages.common.HomePage;
import com.suban.pages.common.LoginPage;
import io.appium.java_client.ios.IOSDriver;
import org.testng.annotations.Test;

public class IOSTests extends BaseTest {
    @Test
    public void testLogin() {
        try {
            HomePage homePage = new HomePage((IOSDriver) driver);

            homePage.ClickLoginScreen();


            LoginPage loginPage = new LoginPage((IOSDriver) driver);
            loginPage.login("testuser", "password123");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

