package ma.boumlyk.onboarding.ui.start;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.util.Pair;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.tools.firebase.IAMNotificationManager;
import ma.boumlyk.onboarding.tools.security.Encryptor;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.OnboardingActivity;

@HiltViewModel
public class StartActivityViewModel extends BaseViewModel {

    @Inject
    FileManager fileManager;
    @Inject
    Encryptor encryptor;
    @Inject
    IAMNotificationManager notificationManager;
    Bundle extras;

    @Inject
    public StartActivityViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);
        onCreate(activity);
    }

    public void onCreate(BaseActivity activity) {
        start(activity);
    }

    private void start(BaseActivity activity) {
        notificationManager.requestToken();
        new android.os.Handler().postDelayed(() -> {
            intentClass.postValue(new Pair<>(new Intent(), OnboardingActivity.class));
           // resource.init( activity  );
            activity.finish();
        }, 1000);
    }
}
