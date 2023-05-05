package ma.boumlyk.onboarding.tools.security.checkers.application;

import android.content.Context;

public class ApplicationDetector {

    public static boolean isValidApplicationId(Context context) {
        return true;
    }

    public static String getCurrentApplicationId(Context context) {
        return context.getPackageName();
    }

}
