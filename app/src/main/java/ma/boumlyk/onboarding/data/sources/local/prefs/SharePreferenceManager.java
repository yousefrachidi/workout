package ma.boumlyk.onboarding.data.sources.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceManager {

    public static final String _PREFERENCES_NAME = "ma.boumlyk.onboarding";

    static SharePreferenceManager instance;
    SharedPreferences sharedPreferences;

    public SharePreferenceManager(Context context) {
        sharedPreferences=  context.getSharedPreferences(_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharePreferenceManager getInstance(Context context){
        if (instance==null){
            instance =new SharePreferenceManager(context);
        }
        return instance;
    }
    public String getInstallationID(String default_value) {
        return getString("INSTALLATION_ID", default_value);
    }
    public void setInstallationID(String value) {
        putString("INSTALLATION_ID", value);
    }


    public void setDefaultUserName(String value) {
        putString("DEFAULT_USERNAME", value);
    }
    public String getDefaultUserName(String default_value) {
        return getString("DEFAULT_USERNAME", default_value);
    }

    private void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }
    private String getString(String key, String default_value) {
        return sharedPreferences.getString(key, default_value);
    }
}
