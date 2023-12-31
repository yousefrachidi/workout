package ma.boumlyk.onboarding.ui.onboarding.registerInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FRegisterInfoBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class FRegisterInfo extends BaseFragment {


    FRegisterInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_register_info, container, false);
        viewModel = new ViewModelProvider(this).get(FRegisterInfoViewModel.class);
        binding.setViewModel((FRegisterInfoViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        viewModel.initiateViewModel((BaseActivity) requireActivity());
        ((FRegisterInfoViewModel) viewModel).isActive.observe(requireActivity(),aBoolean -> {
            if (!aBoolean) {
                disableBTN();
            }else {
                enableBTN();
            }
        });

        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {

            }
        });
//        slideDown(binding.relativeLayout2);
        viewModel.animateViewFromBottomToTop(binding.relativeLayout2);

    }

    private void disableBTN() {
//        binding.btnNext.setBackgroundColor(requireActivity().getColor(R.color.gray_color));
        binding.btnNext.setTextColor(requireActivity().getColor(R.color.colorBtn));
        binding.btnNext.setBackground(requireActivity().getDrawable(R.drawable.border_btn_disable));
    }
    private void enableBTN() {
      //  binding.btnNext.setBackgroundColor(requireActivity().getColor(R.color.colorBtn));
        binding.btnNext.setBackground(requireActivity().getDrawable(R.drawable.border_btn_secondly));
        binding.btnNext.setTextColor(requireActivity().getColor(R.color.white));
    }

    private void initiateView() {

        binding.editCity.setAdapter(((FRegisterInfoViewModel) viewModel).getAdapter(requireActivity()));

    }

    public void slideDown(View view) {
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(1500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
      //  view.setVisibility(View.VISIBLE);
    }
}