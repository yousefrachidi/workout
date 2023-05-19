package ma.boumlyk.onboarding.ui.onboarding.accueil;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.accueil.home.FHome;
import ma.boumlyk.onboarding.ui.onboarding.home.FirstF;

@HiltViewModel
public class AccueilViewModel extends BaseViewModel {

    @Inject
    FileManager fileManager;

    @Inject
    public AccueilViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

       fragment.postValue(new FHome());
    }

}