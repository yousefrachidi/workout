package ma.boumlyk.onboarding.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.ActivityMainBinding;
import ma.boumlyk.onboarding.tools.security.tools.Utils;
import ma.boumlyk.onboarding.ui.BaseActivity;

public class MainActivity extends BaseActivity  {
    private ActivityMainBinding binding;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setLifecycleOwner(this);
        initiateView(savedInstanceState);
        initiateObservers();
    }

    private void initiateView(Bundle savedInstanceState) {
        setSupportActionBar(binding.toolbar);
    }

    private void initiateObservers() {
        ((MainActivityViewModel) viewModel).initiateViewModel(this, getIntent());
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