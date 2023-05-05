package ma.boumlyk.onboarding.ui.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;

@HiltViewModel
public class MainActivityViewModel extends BaseViewModel {

    private Drawable[] screenIcons;

    @Inject
    FileManager fileManager;

    @Inject
    public MainActivityViewModel() {
    }

    public void initiateViewModel(BaseActivity activity, Intent data) {
        super.initiateViewModel(activity);
        actions.postValue(Collections.singletonList(BaseActivity.ACTION_FINISH_LOADING));
    }

}