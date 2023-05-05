package ma.boumlyk.onboarding.utils;


public class Logger {

    public static void d(String tag, String msg) {
        System.out.println("DEBUG: " + tag + ": " + msg);
    }

    public static void i(String tag, String msg) {
        System.out.println("INFO: " + tag + ": " + msg);
    }

    public static void w(String tag, String msg) {
        System.out.println("WARN: " + tag + ": " + msg);
    }

    public static void e(String tag, String msg) {
        System.out.println("ERROR: " + tag + ": " + msg);
    }

}