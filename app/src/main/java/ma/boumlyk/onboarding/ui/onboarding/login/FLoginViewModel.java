package ma.boumlyk.onboarding.ui.onboarding.login;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.home.FirstF;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FLoginViewModel extends BaseViewModel {

    @Inject
    FileManager fileManager;

    @Inject
    public FLoginViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

    }

    public void onCreateAccount() {


    }

    public void onNeedSupport() {
        fragment.postValue(new FSupport());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FirstF());
    }
}