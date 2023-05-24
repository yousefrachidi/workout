package ma.boumlyk.onboarding.ui.onboarding.registerPhone;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.hbb20.CountryCodePicker;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.checkPhone.FCheckPhone;
import ma.boumlyk.onboarding.ui.onboarding.home.FirstF;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FRegisterPhoneViewModel extends BaseViewModel {

    boolean isPhoneNumberValid = false;
    boolean isPhoneNumberEmpty = true;

    EditText editPhoneNumber;

    public MutableLiveData<String> phoneNumberCountryCode = new MutableLiveData<>();
    public MutableLiveData<Boolean> isActiveBTN = new MutableLiveData<>(false);

    @Inject
    public FRegisterPhoneViewModel() {
    }

    public void initiateViewModel(BaseActivity requireActivity, EditText editPhoneNumber, CountryCodePicker countryCodePicker) {
        super.initiateViewModel(requireActivity);
        this.editPhoneNumber=editPhoneNumber;

        countryCodePicker.setOnCountryChangeListener(() -> phoneNumberCountryCode.postValue(countryCodePicker.getSelectedCountryCodeWithPlus()));
        countryCodePicker.setPhoneNumberValidityChangeListener(isValidNumber -> {
            isPhoneNumberValid = isValidNumber;

            if (isValidNumber) {
                isActiveBTN.postValue( true);
            }else {
                isActiveBTN.postValue(false);
            }
        });

    }

    @Override
    public void onNextPressed() {
        super.onNextPressed();
        fragment.postValue(new FCheckPhone());
    }

    public void onNumberKeyboardClick(View v) {

    }

    public void onPhoneTextChanged(CharSequence s, int start, int before, int count) {
        isPhoneNumberEmpty = s.toString().isEmpty();
        actions.postValue(Collections.singletonList(BaseActivity.ACTION_ON_PHONE_TYPING));
    }


    public EditText.OnFocusChangeListener getOnPhoneFocusChanged() {
        return (v, hasFocus) -> {
            if ((!hasFocus) && (!isPhoneNumberEmpty)) {
                if (!isPhoneNumberValid)
                    actions.postValue(Collections.singletonList(BaseActivity.ACTION_PHONE_NOT_VALID));

                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        };
    }

    // Method to hide the soft keyboard
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onNeedSupport() {
        fragment.postValue(new FSupport());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FRegisterInfo());
    }


}