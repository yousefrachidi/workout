package ma.boumlyk.onboarding.ui.tools.uiHTMLViewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;

@HiltViewModel
public class ActivityHTMLViewerForSignatureViewModel extends BaseViewModel {

    @Inject
    public ActivityHTMLViewerForSignatureViewModel() {
    }

    public void initiateViewModel(BaseActivity baseActivity) {
        super.initiateViewModel(baseActivity);
        actions.postValue(Collections.singletonList(ActivityHTMLViewerForSignature.ACTION_START_LOADING_DIALOG));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onBack(View view) {
        ((Activity) view.getContext()).finish();
        onBackPressed();
    }


    @SuppressLint("CheckResult")
    public void onNextPressed(View view) {
        super.onNextPressed();
    }


}
