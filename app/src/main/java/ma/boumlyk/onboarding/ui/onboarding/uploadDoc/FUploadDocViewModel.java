package ma.boumlyk.onboarding.ui.onboarding.uploadDoc;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.finish.FFinish;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FUploadDocViewModel extends BaseViewModel {

    String[] addresses = {"123 Main St, Anytown USA", "456 Oak St, Anycity USA", "789 Pine St, Anyvillage USA"};

    boolean isPhoneNumberValid = false;
    boolean isPhoneNumberEmpty = true;

    EditText editPhoneNumber;


    @Inject
    public FUploadDocViewModel() {
    }

    public void initiateViewModel(BaseActivity requireActivity, EditText editPhoneNumber) {
        super.initiateViewModel(requireActivity);
        this.editPhoneNumber=editPhoneNumber;
    }

    @Override
    public void onNextPressed() {
        super.onNextPressed();
        fragment.postValue(new FFinish());
    }

    public void onNeedSupport() {
        fragment.postValue(new FSupport());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FRegisterInfo());
    }


}