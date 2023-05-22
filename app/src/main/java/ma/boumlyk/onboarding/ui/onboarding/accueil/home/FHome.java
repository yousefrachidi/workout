package ma.boumlyk.onboarding.ui.onboarding.accueil.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FFinishBinding;
import ma.boumlyk.onboarding.databinding.FHomeBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class FHome extends BaseFragment {

    FHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_home, container, false);
        viewModel = new ViewModelProvider(this).get(FHomeViewModel.class);
        binding.setViewModel((FHomeViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        ((FHomeViewModel) viewModel).initiateViewModel((BaseActivity) requireActivity() );
        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {
                switch (action) {
                     case BaseActivity.ACTION_FINISH_LOADING:
                        onFinishLoading();
                        break;
                    case BaseActivity.ACTION_START_LOADING:
                        onStartLoading();
                        break;
                }
            }
        });



    }

    private void initiateView() {
        binding.layoutButton.operationRecycle.setAdapter(((FHomeViewModel) viewModel).getAdapter(requireActivity()) );
    }



    private void onStartLoading() {
       // UITools.setOnLoadingState(true, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }

    private void onFinishLoading() {
        //UITools.setOnLoadingState(false, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }
}