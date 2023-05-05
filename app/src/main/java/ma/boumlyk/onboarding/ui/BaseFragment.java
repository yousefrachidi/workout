package ma.boumlyk.onboarding.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected BaseViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (viewModel != null)
            viewModel.onPermissionsResult((BaseActivity) requireActivity(), requestCode, permissions, grantResults);
    }

    public void onMotherActivityResult(int requestCode, int resultCode, Intent data) {
        if (viewModel != null)
            viewModel.onMotherActivityResult(requestCode, resultCode, data);
    }

    public void requestAllPermissions(AppCompatActivity activity) {
        if (viewModel != null)
            viewModel.requestAllPermissions(activity);
    }

    public void requestLocationPermissions(AppCompatActivity activity) {
        if (viewModel != null)
            viewModel.requestLocationPermissions(activity);
    }

    public void requestPhonePermissions(AppCompatActivity activity) {
        if (viewModel != null)
            viewModel.requestPhonePermissions(activity);
    }

    public void requestStorage(AppCompatActivity activity) {
        if (viewModel != null)
            viewModel.requestStoragePermissions(activity);
    }


    public void onBackPressed() {
        if (viewModel != null)
            viewModel.onBackPressed();
    }

}
