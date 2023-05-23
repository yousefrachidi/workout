package ma.boumlyk.onboarding.ui.onboarding.registerInfo;

import static ma.boumlyk.onboarding.ui.BaseActivity.ACTION_ON_USERNAME_TYPING;

import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.models.customer.Customer;
import ma.boumlyk.onboarding.models.tools.Message;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.home.FirstF;
import ma.boumlyk.onboarding.ui.onboarding.registerPhone.FRegisterPhone;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FRegisterInfoViewModel extends BaseViewModel {

    String[] cityList = {"Casa, casa City", "Rabat", "789 Pine St, Anyvillage USA"};




    public MutableLiveData<Boolean> isActive = new MutableLiveData<>(false);

    @Inject
    public FRegisterInfoViewModel() {
        customer = new Customer();
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

    }

    public void onUsernameTextChanged(CharSequence s, int start, int before, int count) {
        isActive.postValue(s.length() > 0);
    }

    public void onUsernameTextChangedFullName(CharSequence s, int start, int before, int count) {
        isActive.postValue(s.length() > 0);
    }

    public void onUsernameTextChangedAddress(CharSequence s, int start, int before, int count) {
        isActive.postValue(s.length() > 0);
    }
    public void onUsernameTextChangedCity(CharSequence s, int start, int before, int count) {
        isActive.postValue(s.length() > 0);
    }
    public void onUsernameTextChangedEmail(CharSequence s, int start, int before, int count) {
        isActive.postValue(s.length() > 0);
    }


    public void onCreateAccount() {
        if (Boolean.TRUE.equals(isActive.getValue())) {
            fragment.postValue(new FRegisterPhone());
        }else {
          //  message.postValue(new Message(R.string.veulliez_saiasir_,Message.INFO_TYPE));
        }
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