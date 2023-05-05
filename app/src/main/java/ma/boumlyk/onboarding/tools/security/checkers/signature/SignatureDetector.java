package ma.boumlyk.onboarding.tools.security.checkers.signature;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;

import ma.boumlyk.onboarding.tools.security.tools.HashManager;
import timber.log.Timber;

public class SignatureDetector {

    public static boolean isValidSignature(Context context) {
        return  true;

    }

    public static String getTempSignature(Context context, long timeStamp) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(getCurrentSignature(context));
            sb.append(context.getPackageName());
            sb.append(timeStamp);
            Timber.tag("getAPIKey").d("StringBuilder ::: %s", sb.toString());
            return HashManager.toHex(MessageDigest.getInstance("SHA-256").digest(sb.toString().getBytes())).toLowerCase();
        } catch (Exception e) {
        }
        return "";
    }

    public static String getCurrentSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo.signatures.length > 0) {
                Signature signature = packageInfo.signatures[0];
                Timber.tag("App").i("SHA-256-Signature : %s", HashManager.toHex(MessageDigest.getInstance("SHA-256").digest(signature.toByteArray())).toLowerCase());
                return HashManager.toHex(MessageDigest.getInstance("SHA-256").digest(signature.toByteArray())).toLowerCase();
            }
        } catch (Exception e) {
        }
        return "";
    }

}
