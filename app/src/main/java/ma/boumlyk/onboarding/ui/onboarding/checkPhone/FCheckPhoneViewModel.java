package ma.boumlyk.onboarding.ui.onboarding.checkPhone;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.davidmiguel.numberkeyboard.NumberKeyboard;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FCheckPhoneViewModel extends BaseViewModel {


    boolean isPhoneNumberValid = false;
    boolean isPhoneNumberEmpty = true;




    @Inject
    public FCheckPhoneViewModel() {
    }

    public void initiateViewModel(BaseActivity requireActivity ) {
        super.initiateViewModel(requireActivity);


    }

    public void onCreateAccount() {


    }

    public void onNumberKeyboardClick(View view) {

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