package ma.boumlyk.onboarding.ui.onboarding.finish;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.login.FLogin;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FFinishViewModel extends BaseViewModel {



    @Inject
    public FFinishViewModel() {
    }

    public void initiateViewModel(BaseActivity requireActivity ) {
        super.initiateViewModel(requireActivity);


    }

    public void onCreateAccount() {


    }



    @Override
    public void onNextPressed() {
        super.onNextPressed();
        fragment.postValue(new FLogin());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FRegisterInfo());
    }


}