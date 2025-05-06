package com.suban.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;

public class AppiumServer {
    private static AppiumDriverLocalService service;

    public static void startServer() {
        if (!isServerRunning(4723)) {
            // Set up the Appium service builder
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                   // .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
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
                e.printStackTrace();
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

    public static boolean isRunning() {
        return service != null && service.isRunning();
    }
}
