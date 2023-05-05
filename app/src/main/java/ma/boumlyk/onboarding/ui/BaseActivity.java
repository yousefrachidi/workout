package ma.boumlyk.onboarding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ma.boumlyk.onboarding.Configs;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.tools.updateManager.UpdateManager;
import ma.boumlyk.onboarding.ui.onboarding.OnboardingActivity;
import timber.log.Timber;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    //PROGRESS BTN
    public static final String ACTION_START_LOADING = "START_LOADING";
    public static final String ACTION_FINISH_LOADING = "FINISH_LOADING";

    //PROGRESS PAGE
    public static final String ACTION_START_LOADING_DIALOG = "START_LOADING_DIALOG";
    public static final String ACTION_FINISH_LOADING_DIALOG = "FINISH_LOADING_DIALOG";

    //
    public static final String ACTION_OPEN_SIGNATURE_DIALOG = "OPEN_SIGNATURE_DIALOG";
    public static final String ACTION_SHOW_DIALOG = "ACTION_SHOW_DIALOG";
    public static final String ACTION_DISMISS_DIALOG = "DISMISS_DIALOG";
    public static final String ACTION_CLOSE_MENU = "CLOSE_MENU";
    public static final String ACTION_OPEN_DIALOG_CONFIRMATION = "OPEN_DIALOG" ;
    public static final String ACTION_CHOOSE_DOC = "CHOOSE_DOC" ;


    ///EDITTEXT
    public static final String ACTION_ON_USERNAME_INVALID = "ON_USERNAME_INVALID";
    public static final String ACTION_ON_USERNAME_TYPING = "ON_USERNAME_TYPING";

    //PERMISSION
    public static final String ACTION_REQUEST_SKY_ID_PERMISSIONS = "REQUEST_SKY_ID_PERMISSIONS";
    public static final String ACTION_REQUEST_STORAGE_PERMISSIONS = "REQUEST_STORAGE_PERMISSIONS";

    protected BaseViewModel viewModel;
    private Handler disconnectHandler = new Handler(msg -> true);
    private Runnable disconnectCallback = this::logOut;
    private static int nbrOfLiveActivities = 0;

    @Inject
    public Cookies cookies;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nbrOfLiveActivities++;
        Timber.tag("BaseActivityLifecycle").d("onDestroy invoked %s, %s", this.getClass().getName(), nbrOfLiveActivities);
        Log.e("TAG", "BaseActivityLifecycle: onCreate invoked   "+ this.getClass().getName()+"   "+ nbrOfLiveActivities);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
          // WindowManager.LayoutParams.FLAG_SECURE);


    }

    @Override
    public void onBackPressed() {
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null)
            fragment.onBackPressed();
        if (viewModel != null)
            viewModel.onBackPressed();
    }

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, Configs.DISCONNECT_TIMEOUT);
    }
    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null)
            fragment.onPermissionsResult(requestCode, permissions, grantResults);
        if (viewModel != null)
            viewModel.onPermissionsResult(this, requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null)
            fragment.onMotherActivityResult(requestCode, resultCode, data);
        if (viewModel != null)
            viewModel.onMotherActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetDisconnectTimer();
        if (viewModel != null)
            viewModel.onActivityResume();
        Timber.tag("BaseActivityLifecycle").d("onResume invoked %s, %s", this.getClass().getName(), nbrOfLiveActivities);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopDisconnectTimer();
        Timber.tag("BaseActivityLifecycle").d("onPause invoked %s, %s", this.getClass().getName(), nbrOfLiveActivities);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nbrOfLiveActivities--;
        Timber.tag("BaseActivityLifecycle").d("onDestroy invoked %s, %s", this.getClass().getName(), nbrOfLiveActivities);

        Log.e("TAG", "BaseActivityLifecycle: onDestroy invoked   "+ this.getClass().getName()+"   "+ nbrOfLiveActivities);

        if(nbrOfLiveActivities==0){
            if (viewModel!=null){
                viewModel.logOut();
            }
        }
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    }

    private void logOut() {
        Log.e("TAG", "logout: BaseActivity");
        if (viewModel!=null)
            viewModel.logOut();
        cookies.setAccessToken(null);
//        cookies.initCustomerScope(getPackageName());
        Intent myIntent = new Intent(BaseActivity.this, OnboardingActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BaseActivity.this.startActivity(myIntent);
    }
}
