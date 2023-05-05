package ma.boumlyk.onboarding.ui.onboarding;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;

@HiltViewModel
public class OnboardingActivityViewModel extends BaseViewModel {

    @Inject
    FileManager fileManager;

    @Inject
    public OnboardingActivityViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

        boolean isException=activity.getIntent().getBooleanExtra("UNCAUGHT_EXCEPTION",false);
    }

}