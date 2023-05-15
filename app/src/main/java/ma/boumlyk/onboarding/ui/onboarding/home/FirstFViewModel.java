package ma.boumlyk.onboarding.ui.onboarding.home;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.login.FLogin;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;

@HiltViewModel
public class FirstFViewModel extends BaseViewModel {



    @Inject
    public FirstFViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

    }


    public void onCreateAccount() {
        fragment.postValue(new FRegisterInfo());
    }

    public void onSingIn() {
        fragment.postValue(new FLogin());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}