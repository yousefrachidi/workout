package ma.boumlyk.onboarding.tools.updateManager;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.app.Activity;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;

public class UpdateManager {
    public static final int MY_REQUEST_CODE =300 ;
    AppUpdateManager appUpdateManager;
    Activity context;

    public UpdateManager(Activity context) {
        appUpdateManager = AppUpdateManagerFactory.create(context);
        this.context=context;
    }

    public void updateMe(){
// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {

                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            IMMEDIATE,
                            // The current activity making the update request.
                            (Activity) context,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(e -> {
            e.printStackTrace();
        });
        /// end check for update
    }

    public void doOnresume(){
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            Log.e("TAG", "onResume: ============================"+(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE));
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            (Activity) context,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).addOnFailureListener(e ->
                        Log.e("TAG", "onResume:============================ "+e.getMessage() ));
    }
}
