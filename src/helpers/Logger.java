package helpers;

import java.util.Date;

public class Logger {
    public static void Log(String message) {
        System.out.println("[" + new Date().toString() + "][Visual Sorting] " + message);
    }
}
