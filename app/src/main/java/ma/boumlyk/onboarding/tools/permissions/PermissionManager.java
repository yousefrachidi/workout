// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


package ma.boumlyk.onboarding.tools.permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.ui.tools.Dialogs;

@Singleton
public class PermissionManager {

    public static final int REQUEST_CODE = 101;
    public static final List<String> LOCATION_PERMISSIONS = new ArrayList<>(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));
    public static final List<String> STORAGE_PERMISSIONS = new ArrayList<>(Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE));
    public static final List<String> STORAGE_PERMISSIONS_11 = new ArrayList<>(Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE));
    public static final List<String> NETWORK_PERMISSIONS = new ArrayList<>(Arrays.asList(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE));
    public static final List<String> CAMERA_PERMISSIONS = new ArrayList<>(Collections.singletonList(Manifest.permission.CAMERA));
    public static final List<String> CALL_PHONE_PERMISSIONS = new ArrayList<>(Collections.singletonList(Manifest.permission.CALL_PHONE));


    private final String[] permissions = null;

    Context appContext;

    @Inject
    public PermissionManager(@ApplicationContext Context context) {
        this.appContext = context;
    }

    public void requestLocationPermissions(AppCompatActivity activity) {
        List<String> permissions = new ArrayList<>(LOCATION_PERMISSIONS);
        if (!checkPermissions(permissions))
            requestPermissions(activity, permissions);
    }

    public boolean checkPhonePermissions() {
        return checkPermissions(CALL_PHONE_PERMISSIONS);
    }

    public boolean checkLocationPermissions() {
        return checkPermissions(LOCATION_PERMISSIONS);
    }



    public boolean checkStoragePermission() {
        return checkPermissions(STORAGE_PERMISSIONS);
    }


    public void requestStoragePermissions(AppCompatActivity activity) {
        List<String> permissions = new ArrayList<>(STORAGE_PERMISSIONS);

        if (!checkPermissions(permissions))
            requestPermissions(activity, permissions);

    }

    public void requestAllPermissions(AppCompatActivity activity) {
        List<String> permissions = new ArrayList<>();
        permissions.addAll(LOCATION_PERMISSIONS);
        permissions.addAll(CAMERA_PERMISSIONS);
        permissions.addAll(STORAGE_PERMISSIONS);
        if (!checkPermissions(permissions))
            requestPermissions(activity, permissions);
        locationAccessEnabled(activity);
    }

    public void requestPhonePermissions(AppCompatActivity activity) {
        if (!checkPermissions(CALL_PHONE_PERMISSIONS))
            requestPermissions(activity, CALL_PHONE_PERMISSIONS);
    }


    public boolean checkPermissions(List<String> permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(appContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean checkManagerStorage(Context activity) {
        if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)){
            if (Environment.isExternalStorageManager()) {
                return true;
            } else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
                return false;
            }
        }else{
            return true;
        }
    }

    public void requestPermissions(AppCompatActivity activity, List<String> permissions) {
        boolean showDialog = false;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showDialog = true;
                break;
            }
        }
        if (showDialog) {
            Dialogs.openDialogToRequestLocationSettingsEnabling(activity, permissions, REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[0]), REQUEST_CODE);
        }
    }

    public boolean locationAccessEnabled(Activity activity) {
        if (checkPermissions(LOCATION_PERMISSIONS)){
            LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ignored) {
            }
            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ignored) {
            }
            return gps_enabled || network_enabled;
        }else{
            requestPermissions((AppCompatActivity)activity,LOCATION_PERMISSIONS);
            return false;
        }

    }

    public static class RationaleDialog extends DialogFragment {
        public static List<String> permissions;

        public static RationaleDialog newInstance(List<String> permissions) {
            RationaleDialog.permissions = permissions;
            return new RationaleDialog();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_required)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        ActivityCompat.requestPermissions(requireActivity(),
                                permissions.toArray(new String[0]),
                                REQUEST_CODE);
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }

        @Override
        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);
        }
    }

    public String[] getPermissions() {
        return permissions;
    }
}
