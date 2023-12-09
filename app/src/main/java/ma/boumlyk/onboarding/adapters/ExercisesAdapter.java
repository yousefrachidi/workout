package ma.boumlyk.onboarding.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.models.Exercise;
import ma.boumlyk.onboarding.tools.Utils;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {
    private Context context;
    private List<Exercise> exerciseList;

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(View view, Exercise exercise);

        void onItemLongClick(View view, int position);
    }

    public ExercisesAdapter(Context context, List<Exercise> exerciseList,OnItemClickListener mListener) {
        this.context = context;
        this.exerciseList = exerciseList;
        this.mListener =mListener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ExercisesAdapter.ExerciseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.imgView.setImageResource(exercise.getImg());
        holder.nameText.setText(exercise.getName());

        holder.itemView.setOnClickListener(view -> mListener.onItemClick(holder.itemView , exercise));
        holder.nameText.setOnClickListener(view -> mListener.onItemClick(holder.itemView , exercise));
    }

    @SuppressLint("NotifyDataSetChanged")
    public void feedData(List<Exercise> exerciseList){
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView nameText;


        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgExe);
            nameText = itemView.findViewById(R.id.nameText);
        }
    }
}

