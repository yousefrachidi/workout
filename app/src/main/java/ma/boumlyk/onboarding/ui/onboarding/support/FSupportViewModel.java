package ma.boumlyk.onboarding.ui.onboarding.support;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.login.FLogin;

@HiltViewModel
public class FSupportViewModel extends BaseViewModel {

    @Inject
    FileManager fileManager;

    @Inject
    public FSupportViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

    }

    public void onCall() {


    }

    public void onSendMail() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FLogin());
    }
}