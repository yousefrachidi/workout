package ma.boumlyk.onboarding.tools.security.checkers.debug;

import android.content.Context;
import android.os.Debug;

public class DebuggerDetector {


    public static boolean isEnabled(Context context) {

        return Debug.isDebuggerConnected();

    }

}
