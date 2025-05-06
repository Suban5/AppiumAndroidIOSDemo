package com.suban.base;

import org.openqa.selenium.TimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class DeviceManager {
    // Timeout constants
    private static final long BOOT_TIMEOUT_SECONDS = 120;
    private static final long SHUTDOWN_TIMEOUT_SECONDS = 30;
    private static final long POLL_INTERVAL_MS = 1000;

    private static boolean isEmulatorReady() throws IOException {
        // Check both boot complete and package manager ready
        return checkAdbProperty("sys.boot_completed", "1") &&
                checkAdbProperty("init.svc.bootanim", "stopped");
    }

    private static boolean checkAdbProperty(String property, String expectedValue) throws IOException {
        Process process = new ProcessBuilder("adb", "shell", "getprop", property)
                .redirectErrorStream(true)
                .start();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String value = reader.readLine();
            return expectedValue.equals(value != null ? value.trim() : null);
        }
    }

    // Android
    public static void startAndroidEmulator(String avdName) throws IOException, InterruptedException {
        //emulator -list-avds
        //emulator -avd <AVD_NAME>
        //Runtime.getRuntime().exec(new String[]{"emulator", "-avd", avdName, "-no-snapshot-load"});
        //Thread.sleep(30000);
        ProcessBuilder pb = new ProcessBuilder("emulator", "-avd", avdName, "-no-snapshot-load");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        long startTime = System.currentTimeMillis();
        long timeoutMillis = BOOT_TIMEOUT_SECONDS * 1000;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            if (isEmulatorReady()) {
                System.out.println("Emulator is ready");
                return;
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }

        throw new TimeoutException("Emulator did not become ready within " + BOOT_TIMEOUT_SECONDS + " seconds");

    }

    public static void stopAndroidEmulator() throws IOException {
        Runtime.getRuntime().exec(new String[]{"adb", "emu", "kill"});
    }

    // iOS
    public static void startIOSSimulator(String udid) throws IOException, InterruptedException {
//        Runtime.getRuntime().exec(new String[]{"xcrun", "simctl", "boot", udid});
//        Thread.sleep(30000);
// First check if already booted
        if (isSimulatorBooted(udid)) {
            System.out.println("Simulator " + udid + " is already booted");
            return;
        }

        // Boot the simulator
        Process bootProcess = new ProcessBuilder("xcrun", "simctl", "boot", udid).start();
        if (bootProcess.waitFor() != 0) {
            throw new IOException("Failed to boot simulator " + udid);
        }

        // Wait for boot completion
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(BOOT_TIMEOUT_SECONDS)) {
            if (isSimulatorBooted(udid)) {
                System.out.println("Simulator " + udid + " successfully booted");
                return;
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }

        throw new TimeoutException("Simulator " + udid + " did not boot within " +
                BOOT_TIMEOUT_SECONDS + " seconds");
    }

    public static void stopIOSSimulator(String udid) throws IOException, InterruptedException {
      // Runtime.getRuntime().exec(new String[]{"xcrun", "simctl", "shutdown", "all"});
        // Check if already shutdown
        if (!isSimulatorBooted(udid)) {
            System.out.println("Simulator " + udid + " is already shutdown");
            return;
        }

        // Shutdown the simulator
        Process shutdownProcess = new ProcessBuilder("xcrun", "simctl", "shutdown", udid).start();
        if (shutdownProcess.waitFor() != 0) {
            throw new IOException("Failed to shutdown simulator " + udid);
        }

        // Wait for shutdown completion
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < TimeUnit.SECONDS.toMillis(SHUTDOWN_TIMEOUT_SECONDS)) {
            if (!isSimulatorBooted(udid)) {
                System.out.println("Simulator " + udid + " successfully shutdown");
                return;
            }
            Thread.sleep(POLL_INTERVAL_MS);
        }

        throw new TimeoutException("Simulator " + udid + " did not shutdown within " +
                SHUTDOWN_TIMEOUT_SECONDS + " seconds");
    }
    /**
     * Checks if simulator is booted and ready
     */
    private static boolean isSimulatorBooted(String udid) throws IOException {
        // Check boot status
        Process listProcess = new ProcessBuilder("xcrun", "simctl", "list", "devices").start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(listProcess.getInputStream()))) {
            String line;
            boolean foundDevice = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(udid)) {
                    foundDevice = true;
                    // Check if it's in booted state
                    if (line.contains("(Booted)")) {
                        return true;
                    }
                }
            }

            if (!foundDevice) {
                throw new IOException("Simulator with UDID " + udid + " not found");
            }
        }

        // Additional check for services being ready
        return isSimulatorReady(udid);
    }

    /**
     * Checks if simulator services are ready (more thorough than just boot status)
     */
    private static boolean isSimulatorReady(String udid) throws IOException {
        try {
            // Check if SpringBoard is running (indicates UI is ready)
            Process process = new ProcessBuilder("xcrun", "simctl", "spawn", udid,
                    "launchctl", "print", "system").start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                return reader.lines().anyMatch(line -> line.contains("com.apple.SpringBoard"));
            }
        } catch (IOException e) {
            return false;
        }
    }

}