package com.suban.android;

import com.suban.base.BaseTest;
import com.suban.pages.common.HomePage;
import com.suban.pages.common.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.Test;


public class AndroidTests extends BaseTest {
    @Test
    public void testLogin() {
        try {
            HomePage homePage = new HomePage((AndroidDriver) driver);

            homePage.ClickLoginScreen();


        LoginPage loginPage = new LoginPage((AndroidDriver) driver);
        loginPage.login("testuser", "password123");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}