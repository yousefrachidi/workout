package ma.boumlyk.onboarding.ui.onboarding.accueil.profil;

import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.accueil.home.FHome;
import ma.boumlyk.onboarding.ui.onboarding.home.FirstF;
import ma.boumlyk.onboarding.ui.onboarding.registerPhone.FRegisterPhone;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class ProfilViewModel extends BaseViewModel {

    @Inject
    public ProfilViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

    }


    public void onNeedSupport() {
        fragment.postValue(new FSupport());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FHome());
    }


}