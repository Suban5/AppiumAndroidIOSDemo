package com.suban.base;

import com.suban.config.ConfigReader;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;

public class AppiumServer {
    private static AppiumDriverLocalService service;
    private static int port= Integer.parseInt(ConfigReader.getProperty("appium.port"));

    public static void startServer() {
        if (!isServerRunning(port)) {
            // Set up the Appium service builder
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")
                    .usingPort(port)
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                    .withArgument(GeneralServerFlag.RELAXED_SECURITY);

            // Start the server with more error handling
            try {
                service = AppiumDriverLocalService.buildService(builder);
                service.start();

                if (service.isRunning()) {
                    System.out.println("Appium server started on: " + service.getUrl());
                } else {
                    System.out.println("Failed to start Appium server");
                }
            } catch (Exception e) {
                System.err.println("Error starting Appium server: " + e.getMessage());
                //e.printStackTrace();
            }
        } else {
            System.out.println("Appium server is already running on port 4723");
        }
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("Appium server stopped");
        }
    }

    private static boolean isServerRunning(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true; // Port is occupied (server is running)
        }
    }

}
