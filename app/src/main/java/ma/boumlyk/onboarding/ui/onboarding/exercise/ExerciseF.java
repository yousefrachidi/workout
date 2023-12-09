package ma.boumlyk.onboarding.ui.onboarding.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FragmentFirstBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.accueil.home.FHomeViewModel;

public class FirstF extends BaseFragment {


    FragmentFirstBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        viewModel = new ViewModelProvider(this).get(FirstFViewModel.class);
        binding.setViewModel((FirstFViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        viewModel.initiateViewModel((BaseActivity) requireActivity());
        ((FirstFViewModel)viewModel).selectedExercise.observe(requireActivity(),exercise -> {

        });
        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {

            }
        });
    }

    private void initiateView() {
        binding.exerciseRecycle.setHasFixedSize(true);
        binding.exerciseRecycle.setAdapter(((FirstFViewModel) viewModel).getAdapter(requireActivity()) );
    }
}