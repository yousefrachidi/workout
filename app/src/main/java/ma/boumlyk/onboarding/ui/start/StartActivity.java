package ma.boumlyk.onboarding.ui.start;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.ui.BaseActivity;
import timber.log.Timber;


@AndroidEntryPoint
public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        viewModel = new ViewModelProvider(this).get(StartActivityViewModel.class);
        viewModel.initiateViewModel(this);
        Timber.tag("StartActivity").d("StartActivity :: Start");

    }
}