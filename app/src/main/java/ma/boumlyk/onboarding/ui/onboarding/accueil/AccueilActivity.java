package ma.boumlyk.onboarding.ui.onboarding.accueil;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.ActivityAccueilBinding;
import ma.boumlyk.onboarding.databinding.ActivityOnbordingBinding;
import ma.boumlyk.onboarding.tools.security.tools.Utils;
import ma.boumlyk.onboarding.ui.BaseActivity;

@AndroidEntryPoint
public class AccueilActivity extends BaseActivity {

    private ActivityAccueilBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_accueil);
        viewModel = new ViewModelProvider(this).get(AccueilViewModel.class);
        binding.setLifecycleOwner(this);
        initiateView(savedInstanceState);
        initiateObservers();

    }

    private void initiateView(Bundle savedInstanceState) {
    }

    private void initiateObservers() {
        viewModel.initiateViewModel(this );
        viewModel.actions.observe(this, actions -> {
            for (String action : actions) {
                switch (action) {
                    case ACTION_START_LOADING:
                        onStartLoading();
                        break;
                    case ACTION_FINISH_LOADING:
                        onFinishLoading();
                        break;
                }
            }
        });
    }

    private void onStartLoading() {
        if (progressDialog == null)
            progressDialog = Utils.createProgressDialog(this);
        progressDialog.show();
    }

    private void onFinishLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}