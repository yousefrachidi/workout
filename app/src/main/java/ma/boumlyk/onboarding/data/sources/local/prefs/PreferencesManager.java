package ma.boumlyk.onboarding.data.sources.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;


import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ma.boumlyk.onboarding.models.tools.Language;


@Singleton
public class PreferencesManager {

    public static final String SKY_ID_SHARED_PREFERENCES_NAME = "ma.boumlyk.onboarding";

    @ApplicationContext
    Context applicationContext;
    @Inject
    public PreferencesManager() {
    }
    public SharedPreferences getSharedPreferences(@ApplicationContext Context applicationContext) {
        return applicationContext.getSharedPreferences(SKY_ID_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public String getInstallationID(String default_value) {
        return getString("INSTALLATION_ID", default_value);
    }
    public String getDefaultUserName(String default_value) {
        return getString("DEFAULT_USERNAME", default_value);
    }

    public void setInstallationID(String value) {
        putString("INSTALLATION_ID", value);
    }
  public void setDefaultUserName(String value) {
        putString("DEFAULT_USERNAME", value);
    }

    public Language getLanguage(Language default_value) {
        return Language.getLanguage(getString("LANGUAGE", default_value.getIso3()));
    }

    public void setLanguage(Language value) {
        putString("LANGUAGE", value.getIso3());
    }


    ////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// Getters & Setters /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    private void putString(String key, String value) {
        getSharedPreferences(applicationContext).edit().putString(key, value).apply();
    }

    private void putInt(String key, int value) {
        getSharedPreferences(applicationContext).edit().putInt(key, value).apply();
    }

    private void putBoolean(String key, boolean value) {
        getSharedPreferences(applicationContext).edit().putBoolean(key, value).apply();
    }

    private String getString(String key, String default_value) {
        return getSharedPreferences(applicationContext).getString(key, default_value);
    }

    private int getInt(String key, int default_value) {
        return getSharedPreferences(applicationContext).getInt(key, default_value);
    }

    private boolean getBoolean(String key, boolean default_value) {
        return getSharedPreferences(applicationContext).getBoolean(key, default_value);
    }


}
