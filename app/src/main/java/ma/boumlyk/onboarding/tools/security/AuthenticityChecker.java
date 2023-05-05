package ma.boumlyk.onboarding.tools.security;

import android.content.Context;

public class AuthenticityChecker {

    public static boolean isSafeEnvironment(Context context) {

//        try {
//            boolean isNotSafe = DebuggerDetector.isEnabled(context)
//                      || (!Configs.getCurrentSignature(context).equals(Configs.getSignature(context)))
//                   //  || (!Configs.getCurrentApplicationId(context).equals(Configs.getApplicationId(context)))
//                     || HookDetector.isHookDetected(context)
//                    || RootedDetector.isJailBroken(context);
//            return !isNotSafe;
//         } catch (Exception e) {
//            try {
//                boolean isNotSafe = DebuggerDetector.isEnabled(context)
//                        || (!Configs.getCurrentSignature(context).equals(Configs.getSignature(context)))
//                        //  || (!Configs.getCurrentApplicationId(context).equals(Configs.getApplicationId(context)))
//                        || HookDetector.isHookDetected(context)
//                          || RootedDetector.isJailBroken(context);
//                return !isNotSafe;
//             } catch (Exception e1) {
//                return true;
//             }
//       }
        return true;

    }


}
