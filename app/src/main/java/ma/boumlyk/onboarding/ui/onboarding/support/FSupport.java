package ma.boumlyk.onboarding.ui.onboarding.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FLoginBinding;
import ma.boumlyk.onboarding.databinding.FSupportBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class FSupport extends BaseFragment {


    FSupportBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_support, container, false);
        viewModel = new ViewModelProvider(this).get(FSupportViewModel.class);
        binding.setViewModel((FSupportViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        viewModel.initiateViewModel((BaseActivity) requireActivity());
        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {

            }
        });
        slideDown(binding.relativeLayout2);
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
}