package com.alphatica.genotick.genotick;

import java.lang.management.ManagementFactory;

public class Tools {
    public static String getPidString() {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        if (pid.contains("@")) {
            return pid.substring(0, pid.indexOf('@'));
        } else {
            return pid;
        }
    }
}
