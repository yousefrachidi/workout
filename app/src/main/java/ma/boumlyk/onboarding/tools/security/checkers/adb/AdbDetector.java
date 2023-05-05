package ma.boumlyk.onboarding.tools.security.checkers.adb;

import android.content.Context;
import android.provider.Settings;


public class AdbDetector {

    public static boolean isEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1;
    }


}
