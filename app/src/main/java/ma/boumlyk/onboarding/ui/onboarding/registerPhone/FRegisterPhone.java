package ma.boumlyk.onboarding.ui.onboarding.registerPhone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FRegisterPhoneBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;
import ma.boumlyk.onboarding.ui.tools.UITools;

public class FRegisterPhone extends BaseFragment {

    FRegisterPhoneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_register_phone, container, false);
        viewModel = new ViewModelProvider(this).get(FRegisterPhoneViewModel.class);
        binding.setViewModel((FRegisterPhoneViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        ((FRegisterPhoneViewModel) viewModel).initiateViewModel((BaseActivity) requireActivity(), binding.editPhoneNumber,  binding.ccpNumbre);
        ((FRegisterPhoneViewModel) viewModel).isActiveBTN.observe(requireActivity(),aBoolean -> {
            if (aBoolean  ) {
                enableBTN();
            }else {
                disableBTN();
            }
        });
        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {
                switch (action) {
                    case BaseActivity.ACTION_PHONE_NOT_VALID:
                        onPhoneNotValid();
                        break;
                    case BaseActivity.ACTION_ON_PHONE_TYPING:
                        onPhoneTyping();
                        break;

                    case BaseActivity.ACTION_FINISH_LOADING:
                        onFinishLoading();
                        break;
                    case BaseActivity.ACTION_START_LOADING:
                        onStartLoading();
                        break;
                }
            }
        });
//        slideDown(binding.relativeLayout2);
        viewModel.animateViewFromBottomToTop(binding.relativeLayout2);

    }

    private void initiateView() {
        disableBTN();
        binding.editPhoneNumber.setCursorVisible(false);
        binding.editPhoneNumber.setFocusableInTouchMode(false);
        binding.editPhoneNumber.setFocusable(false);

        binding.ccpNumbre.registerCarrierNumberEditText(binding.editPhoneNumber);

        binding.keyword.setListener(new NumberKeyboardListener() {
            @Override
            public void onNumberClicked(int i) {
                binding.editPhoneNumber.setText(binding.editPhoneNumber.getText() +""+ i);
            }

            @Override
            public void onLeftAuxButtonClicked() {
            }

            @Override
            public void onRightAuxButtonClicked() {

                String text =  binding.editPhoneNumber.getText().toString();
                if (text.length() > 0) {
                    String newText = text.substring(0, text.length() - 1);
                    binding.editPhoneNumber.setText(newText);
                    binding.editPhoneNumber.setSelection(newText.length());
                }

            }
        });

    }


    protected void onPhoneTyping() {
        binding.editPhoneNumber.setTextColor(ContextCompat.getColor(requireContext(), R.color.field_text2));
    }

    protected void onPhoneNotValid() {
        disableBTN();
        binding.editPhoneNumber.setTextColor(ContextCompat.getColor(requireActivity(), R.color.errorTextViewColor));
    }

    private void disableBTN() {
        binding.btnNext.setEnabled(false);
        binding.btnNext.setTextColor(requireActivity().getColor(R.color.colorBtn));
        binding.btnNext.setBackground(requireActivity().getDrawable(R.drawable.border_btn_disable));
    }
    private void enableBTN() {
        binding.btnNext.setEnabled(true);
        binding.btnNext.setBackground(requireActivity().getDrawable(R.drawable.border_btn_secondly));
        binding.btnNext.setTextColor(requireActivity().getColor(R.color.white));
    }


    private void onStartLoading() {
       // UITools.setOnLoadingState(true, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }

    private void onFinishLoading() {
        //UITools.setOnLoadingState(false, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }
}