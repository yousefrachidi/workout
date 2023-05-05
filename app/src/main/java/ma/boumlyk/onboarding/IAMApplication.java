package ma.boumlyk.onboarding;

import android.app.Application;

import androidx.databinding.library.baseAdapters.BuildConfig;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class IAMApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //TrustKit.initializeWithNetworkSecurityConfiguration(getApplicationContext());
        if (BuildConfig.DEBUG) {
            //Timber.plant(new Timber.DebugTree());
            //HttpsTrustManager.allowAllSSL();
        }
    }
}