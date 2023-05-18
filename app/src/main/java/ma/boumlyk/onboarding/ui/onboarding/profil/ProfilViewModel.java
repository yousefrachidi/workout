package ma.boumlyk.onboarding.ui.onboarding.profil;

import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.home.FirstF;
import ma.boumlyk.onboarding.ui.onboarding.registerPhone.FRegisterPhone;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class ProfilViewModel extends BaseViewModel {

    String[] cityList = {"Casa, casa City", "Rabat", "789 Pine St, Anyvillage USA"};


    @Inject
    public ProfilViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

    }

    public void onCreateAccount() {
        fragment.postValue(new FRegisterPhone());
    }

    public void onNeedSupport() {
        fragment.postValue(new FSupport());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FirstF());
    }

    public ArrayAdapter<String> getAdapter(FragmentActivity requireActivity) {
           ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity, android.R.layout.simple_dropdown_item_1line, cityList);
        return adapter;
    }
}