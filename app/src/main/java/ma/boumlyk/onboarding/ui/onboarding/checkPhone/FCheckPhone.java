package ma.boumlyk.onboarding.ui.onboarding.checkPhone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FCheckPhoneBinding;
import ma.boumlyk.onboarding.databinding.FRegisterPhoneBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class FCheckPhone extends BaseFragment {


    FCheckPhoneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_check_phone, container, false);
        viewModel = new ViewModelProvider(this).get(FCheckPhoneViewModel.class);
        binding.setViewModel((FCheckPhoneViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        ((FCheckPhoneViewModel) viewModel).initiateViewModel((BaseActivity) requireActivity(),binding.editPhoneNumber);
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

    }

    public void slideDown(View view) {
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(1500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
      //  view.setVisibility(View.VISIBLE);
    }

    protected void onPhoneTyping() {
        binding.editPhoneNumber.setTextColor(ContextCompat.getColor(requireContext(), R.color.field_text2));
    }

    protected void onPhoneNotValid() {
        binding.editPhoneNumber.setTextColor(ContextCompat.getColor(requireActivity(), R.color.errorTextViewColor));
    }


    private void onStartLoading() {
       // UITools.setOnLoadingState(true, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }

    private void onFinishLoading() {
        //UITools.setOnLoadingState(false, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }
}