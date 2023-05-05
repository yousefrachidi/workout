package ma.boumlyk.onboarding.tools.security.checkers.mock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

public class MockDetector {


    @SuppressLint("LongLogTag")
    public static boolean isMockLocationOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "0".equals(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION));
        } else {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            if (packages != null) {
                for (ApplicationInfo applicationInfo : packages) {
                    try {
                        PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                                PackageManager.GET_PERMISSIONS);

                        // Get Permissions
                        String[] requestedPermissions = packageInfo.requestedPermissions;

                        if (requestedPermissions != null) {
                            for (int i = 0; i < requestedPermissions.length; i++) {
                                if (requestedPermissions[i]
                                        .equals("android.permission.ACCESS_MOCK_LOCATION")
                                        && !applicationInfo.packageName.equals(context.getPackageName())) {
                                    return true;
                                }
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("Mock location check error", e.getMessage());
                    }
                }
            }

            return false;
        }
    }

}
