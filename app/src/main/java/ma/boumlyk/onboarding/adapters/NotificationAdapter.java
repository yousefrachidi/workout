package ma.boumlyk.onboarding.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.boumlyk.onboarding.R;
import timber.log.Timber;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    List<String> iamNotifications;
    MutableLiveData<String> selectedNotif;


    public NotificationAdapter(@NonNull Context context /*,MutableLiveData<IAMNotification> selectedNotif*/ ) {
        setHasStableIds(true);
        this.selectedNotif = selectedNotif;
        iamNotifications = new ArrayList<>();
        this.context = context;
        Timber.tag("NotificationAdapter").d("NotificationAdapter: size:%s", iamNotifications.size());
    }

//    @SuppressLint("NotifyDataSetChanged")
//    public void feedNotifications(List<IAMNotification> IAMNotifications) {
//        iamNotifications.clear();
//        iamNotifications.addAll(IAMNotifications);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_white_progress_bar, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
//        IAMNotification iamNotification = iamNotifications.get(position);
//        if ((iamNotification.getNotificationStatus() !=null) && (iamNotification.getNotificationStatus().equals(IAMNotification.STATUS_SEEN))) {
//            changeStatus(viewHolder);
//        }
//        viewHolder.itemView.setOnClickListener(view -> {
//            selectedNotif.postValue(iamNotifications.get(position));
//            changeStatus(viewHolder);
//      });
//        viewHolder.date.setText(Utils.getDateTimeFromDate(iamNotification.getNotificationDateTime()));
//        viewHolder.txt_title.setText(iamNotification.getNotificationTitle());
//        viewHolder.body.setText(iamNotification.getNotificationBody());

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeStatus(ViewHolder viewHolder){
        viewHolder.txt_title.setTextColor(ContextCompat.getColor(context, R.color.blackColor));
        viewHolder.txt_title.setCompoundDrawablesRelative(context.getDrawable(R.drawable.ic_notif_off), null, null, null);
        viewHolder.txt_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notif_off, 0, 0, 0);

    }

    @Override
    public int getItemCount() {
        return iamNotifications.size();
    }

    public interface ItemClickListener {
        void OnItemClick(int item);
    }

//    public IAMNotification getItem(int position) {
//        if (iamNotifications != null && iamNotifications.size() > position) {
//            return iamNotifications.get(position);
//        }
//        return null;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView date;
        private  TextView body;
        private  TextView txt_title;

        public ViewHolder(View v) {
            super(v);

//            date = v.findViewById(R.id.date_notif);
//            body = v.findViewById(R.id.body_notif);
//            txt_title = v.findViewById(R.id.txt_title);

        }

        public TextView getTextView() {
            return date;
        }
    }


}
