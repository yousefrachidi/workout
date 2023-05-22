package ma.boumlyk.onboarding.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.models.Operation;
import ma.boumlyk.onboarding.tools.Utils;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.OperationViewHolder> {
    private Context context;
    private List<Operation> operationList;

    public OperationAdapter(Context context, List<Operation> operationList) {
        this.context = context;
        this.operationList = operationList;
    }

    @NonNull
    @Override
    public OperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operation, parent, false);
        return new OperationAdapter.OperationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationViewHolder holder, int position) {
        Operation operation = operationList.get(position);
        Log.d("priceTextView", "onBindViewHolder://////// ");
        Log.d("priceTextView", "onBindViewHolder: "+Utils.getMonthFromMills(operation.getDate()));
        holder.monthText.setText(Utils.getMonthFromMills(operation.getDate()));
        holder.timeText.setText(Utils.getTimeFromMills(operation.getDate()));
        holder.priceTextView.setText(String.valueOf(operation.getPrice()) + " DH");

        Log.d("priceTextView", "onBindViewHolder: ");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void feedData(List<Operation> operationList){
        this.operationList = operationList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return operationList.size();
    }

    public static class OperationViewHolder extends RecyclerView.ViewHolder {
        TextView monthText;
        TextView timeText;
        TextView priceTextView;

        public OperationViewHolder(@NonNull View itemView) {
            super(itemView);
            monthText = itemView.findViewById(R.id.monthText);
            timeText = itemView.findViewById(R.id.timeText);
            priceTextView = itemView.findViewById(R.id.sommeText);
        }
    }
}

