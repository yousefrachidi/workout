package ma.boumlyk.onboarding.ui.onboarding.home;

import android.view.View;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.adapters.ExercisesAdapter;
import ma.boumlyk.onboarding.adapters.OperationAdapter;
import ma.boumlyk.onboarding.models.Exercise;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.login.FLogin;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;

@HiltViewModel
public class FirstFViewModel extends BaseViewModel {

    ExercisesAdapter exercisesAdapter ;

    public MutableLiveData<Exercise> selectedExercise = new MutableLiveData<>(null);

    @Inject
    public FirstFViewModel() {
    }

    public void initiateViewModel(BaseActivity activity) {
        super.initiateViewModel(activity);

        exercisesAdapter.feedData(getData());

    }

    private List<Exercise> getData() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("","ABS", R.drawable.img_add_doc));
        exercises.add(new Exercise("","ABS", R.drawable.img_add_doc));
        exercises.add(new Exercise("","ABS", R.drawable.img_add_doc));
        exercises.add(new Exercise("","ABS", R.drawable.img_add_doc));
        exercises.add(new Exercise("","ABS", R.drawable.img_add_doc));
        exercises.add(new Exercise("","ABS", R.drawable.img_add_doc));
        return exercises;
    }


    public ExercisesAdapter getAdapter(FragmentActivity requireActivity) {
        exercisesAdapter = new ExercisesAdapter(requireActivity, new ArrayList<>(), new ExercisesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Exercise exercise) {
                selectedExercise.postValue(exercise);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return exercisesAdapter;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}