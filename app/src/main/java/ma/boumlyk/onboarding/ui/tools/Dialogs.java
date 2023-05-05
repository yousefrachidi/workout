package ma.boumlyk.onboarding.ui.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.List;

import ma.boumlyk.onboarding.R;


public class Dialogs {

    public static void openDialogToRequestLocationSettingsEnabling(Activity activity, List<String> permissions, int REQUEST_CODE) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_simple, null);
        TextView title = mView.findViewById(R.id.title);
        TextView confirmButton = mView.findViewById(R.id.confirmButton);
        ImageView cancelButton = mView.findViewById(R.id.cancelButton);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        confirmButton.setText(activity.getResources().getString(android.R.string.ok));
        title.setText(activity.getResources().getString(R.string.permission_required));
        cancelButton.setOnClickListener(view -> dialog.dismiss());
        confirmButton.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(activity,
                    permissions.toArray(new String[0]),
                    REQUEST_CODE);
            dialog.dismiss();
        });
        dialog.show();
    }
    public static void enableGps(Activity activity) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_simple, null);
        TextView title = mView.findViewById(R.id.title);
        TextView confirmButton = mView.findViewById(R.id.confirmButton);
        ImageView cancelButton = mView.findViewById(R.id.cancelButton);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        confirmButton.setText(activity.getResources().getString(android.R.string.ok));
        title.setText(activity.getResources().getString(R.string.gps));
        cancelButton.setOnClickListener(view -> dialog.dismiss());
        confirmButton.setOnClickListener(view -> {
            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            dialog.dismiss();
        });
        dialog.show();
    }
}
