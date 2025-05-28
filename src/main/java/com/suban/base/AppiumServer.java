package com.suban.base;

import com.suban.config.ConfigReader;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class AppiumServer {
    private static AppiumDriverLocalService service;
    private static final int port= Integer.parseInt(ConfigReader.getProperty("appium.port"));
    private static final Logger logger = LoggerFactory.getLogger(AppiumServer.class);

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
                    logger.info("Appium server started on: {}", service.getUrl());
                } else {
                    logger.error("Failed to start Appium server");
                }
            } catch (Exception e) {
                logger.error("Error starting Appium server: {}", e.getMessage(), e);
                //logger.debug("Exception details: ", e);
            }
        } else {
            logger.warn("Appium server is already running on port {}", port);
        }
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            logger.info("Appium server stopped");
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
