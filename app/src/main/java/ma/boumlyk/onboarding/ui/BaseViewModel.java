package ma.boumlyk.onboarding.ui;

import static android.app.Activity.RESULT_OK;
import static ma.boumlyk.onboarding.tools.updateManager.UpdateManager.MY_REQUEST_CODE;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.data.CookiesRepository;
import ma.boumlyk.onboarding.data.sources.remote.interceptors.ErrorHandler;
import ma.boumlyk.onboarding.data.sources.remote.interceptors.NetworkException;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.models.tools.Message;
import ma.boumlyk.onboarding.tools.message.Messenger;
import ma.boumlyk.onboarding.tools.permissions.PermissionManager;
import ma.boumlyk.onboarding.tools.updateManager.UpdateManager;
import timber.log.Timber;

@HiltViewModel
public class BaseViewModel extends ViewModel implements Observable {

    public static final String TAG = "Tag";

    @Inject
    public Messenger messenger;
    @Inject
    public Cookies cookies;
    @Inject
    public PermissionManager permissionManager;
    @Inject
    public CookiesRepository cookiesRepository;

    public MutableLiveData<List<String>> actions;

    public MutableLiveData<Message> message;
    public MutableLiveData<Fragment> fragment;

    UpdateManager updateManager;

    public MutableLiveData<Pair<Intent, Class<?>>> intentClass;


    // il faut cree base dialog
    public MutableLiveData<String> tempDialogLabel ;
    public MutableLiveData<String> tempDialogValue ;



    @Inject
    public BaseViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {


        try {
            updateManager = new UpdateManager(activity);
            updateManager.updateMe();
        } catch (Exception e) {
            Log.d("TAG", "onCreate: " + e.getMessage());
        }

        actions = new MutableLiveData<>();
        message = new MutableLiveData<>();
        fragment = new MutableLiveData<>();
        intentClass = new MutableLiveData<>();
        ///il faut cree baseDialog
        tempDialogLabel = new MutableLiveData<String>("");
        tempDialogValue = new MutableLiveData<String>("");


        message.observe(activity, msg -> messenger.communicateMessage(msg));

        intentClass.observe(activity, value -> {
            activity.startActivity(value.first.setClass(activity, value.second));
        });

        fragment.observe(activity, fragment -> {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment, fragment.getTag())
                    .addToBackStack(null)
                    .commit();
        });



    }

    //il faut cree baseDialog
    public void onConfirmEdit(View view) {
    }

    public void onDismissEditDialog( ) {
    }

    /////

    public void onNextPressed() {
    }

    public void onBackPressed() {
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
    }

    public void onPermissionsResult(BaseActivity requireActivity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    public void onMotherActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                try {
                    if (updateManager != null)
                        updateManager.updateMe();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void requestAllPermissions(AppCompatActivity activity) {
        permissionManager.requestAllPermissions(activity);
    }

    public void requestStoragePermissions(AppCompatActivity activity) {
        permissionManager.requestStoragePermissions(activity);
    }

    public void requestLocationPermissions(AppCompatActivity activity) {
        permissionManager.requestLocationPermissions(activity);
    }

    public void requestPhonePermissions(AppCompatActivity activity) {
        permissionManager.requestPhonePermissions(activity);
    }

    public void handleError(Throwable throwable) {
         Timber.tag(ErrorHandler.class.getSimpleName()).e(throwable);
        Timber.tag(ErrorHandler.class.getSimpleName()).d("UNEXPECTED_ERROR ... %s", throwable.getMessage());
        Log.d(TAG, "handleError:->>> "+ throwable.getMessage());
        actions.postValue(Arrays.asList(BaseActivity.ACTION_FINISH_LOADING, BaseActivity.ACTION_FINISH_LOADING_DIALOG));
        if (!(throwable instanceof NetworkException))
            new Handler(Looper.getMainLooper()).post(() -> message.postValue(new Message(R.string.error_handler_divers, Message.WARNING_TYPE)));
    }

    public void logOut() {

    }

    public void onActivityResume(){
        try {
            if (updateManager != null)
                updateManager.doOnresume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
