package ma.boumlyk.onboarding.tools.firebase;


import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ma.boumlyk.onboarding.models.cookie.Cookies;

@Singleton
public class IAMNotificationManager {

    Cookies cookies;
    String notificationToken = null;

    @Inject
    public IAMNotificationManager(@ApplicationContext Context applicationContext, Cookies cookies) {
        this.cookies = cookies;
    }

    public void requestToken() {
        /*
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notificationToken = task.getResult();
                    }
                });
         */
        notificationToken = "";
    }

    public String getNotificationToken() {
        return notificationToken;
    }


    public Intent getPendingIntent(Context context/*, IAMNotification notification*/) {
        Intent intent = null;
//        cookies.setRootingStep(notification.getNotificationReference().getTargetStep());
//          intent = new Intent(context, notification.getNotificationReference().getTargetActivity());
//        intent.putExtra("notification", GsonProvider.getInstance().toJson(notification));
        return intent;
    }


}
