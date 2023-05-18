package ma.boumlyk.onboarding.ui.onboarding.profil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FProfilBinding;
import ma.boumlyk.onboarding.databinding.FRegisterInfoBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class FProfil extends BaseFragment {


    FProfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_profil, container, false);
        viewModel = new ViewModelProvider(this).get(ProfilViewModel.class);
        binding.setViewModel((ProfilViewModel) viewModel);
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
//        slideDown(binding.relativeLayout2);
        viewModel.animateViewFromBottomToTop(binding.relativeLayout2);

    }

    private void initiateView() {


    }


}