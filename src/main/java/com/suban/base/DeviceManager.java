package com.suban.base;

import java.io.IOException;

public class DeviceManager {
    // Android
    public static void startAndroidEmulator(String avdName) throws IOException, InterruptedException {
        //Runtime.getRuntime().exec(new String[]{"emulator", "-avd", avdName, "-no-snapshot-load"});
        //Thread.sleep(15000);
    }

    public static void stopAndroidEmulator() throws IOException {
       // Runtime.getRuntime().exec(new String[]{"adb", "emu", "kill"});
    }

    // iOS
    public static void startIOSSimulator(String udid) throws IOException, InterruptedException {
//        Runtime.getRuntime().exec(new String[]{"xcrun", "simctl", "boot", udid});
//        Thread.sleep(10000);
    }

    public static void stopIOSSimulator() throws IOException {
//        Runtime.getRuntime().exec(new String[]{"xcrun", "simctl", "shutdown", "all"});
    }
}