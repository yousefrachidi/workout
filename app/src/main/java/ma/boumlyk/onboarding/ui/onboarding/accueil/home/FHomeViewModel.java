package ma.boumlyk.onboarding.ui.onboarding.accueil.home;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.login.FLogin;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FHomeViewModel extends BaseViewModel {



    @Inject
    public FHomeViewModel() {
    }

    public void initiateViewModel(BaseActivity requireActivity ) {
        super.initiateViewModel(requireActivity);


    }

    public void onCreateAccount() {


    }

    public void onNeedSupport() {
        fragment.postValue(new FSupport());
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