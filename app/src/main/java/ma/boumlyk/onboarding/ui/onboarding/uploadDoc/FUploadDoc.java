package ma.boumlyk.onboarding.ui.onboarding.uploadDoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FRegisterPhoneBinding;
import ma.boumlyk.onboarding.databinding.FUploadDocBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;

public class FUploadDoc extends BaseFragment {


    FUploadDocBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_upload_doc, container, false);
        viewModel = new ViewModelProvider(this).get(FUploadDocViewModel.class);
        binding.setViewModel((FUploadDocViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        ((FUploadDocViewModel) viewModel).initiateViewModel((BaseActivity) requireActivity() );
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
//        slideDown(binding.relativeLayout2);
        viewModel.animateViewFromBottomToTop(binding.relativeLayout2);

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




    private void onStartLoading() {
       // UITools.setOnLoadingState(true, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }

    private void onFinishLoading() {
        //UITools.setOnLoadingState(false, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }
}