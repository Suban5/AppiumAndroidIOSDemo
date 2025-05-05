package com.suban.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.IOException;
import java.net.ServerSocket;

public class AppiumServer {
    private static AppiumDriverLocalService service;

    public static void startServer() {
//        if (!isServerRunning(4723)) {
//            service = new AppiumServiceBuilder()
//                    .withIPAddress("127.0.0.1")
//                    .usingPort(4723)
//                    .build();
//            service.start();
//            System.out.println("Appium server started on: " + service.getUrl());
//        } else {
//            System.out.println("Appium server is already running on port 4723");
//        }
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
           // service.stop();
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
