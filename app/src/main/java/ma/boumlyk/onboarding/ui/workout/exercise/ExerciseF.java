package ma.boumlyk.onboarding.ui.onboarding.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FragmentExerciseBinding;
import ma.boumlyk.onboarding.databinding.FragmentFirstBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class ExerciseF extends BaseFragment {


    FragmentExerciseBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false);
        viewModel = new ViewModelProvider(this).get(ExerciseFViewModel.class);
        binding.setViewModel((ExerciseFViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        viewModel.initiateViewModel((BaseActivity) requireActivity());
        ((ExerciseFViewModel)viewModel).selectedExercise.observe(requireActivity(), exercise -> {

        });
        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {

            }
        });
    }

    private void initiateView() {
        binding.exerciseRecycle.setHasFixedSize(true);
        binding.exerciseRecycle.setAdapter(((ExerciseFViewModel) viewModel).getAdapter(requireActivity()) );
    }
}