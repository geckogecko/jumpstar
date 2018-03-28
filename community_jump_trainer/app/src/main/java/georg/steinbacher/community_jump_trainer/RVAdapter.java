package georg.steinbacher.community_jump_trainer;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Exercise;

/**
 * Created by georg on 15.03.18.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ExerciseViewHolder>{
    private static final String TAG = "RVAdapter";

    private List<Exercise> mExerciseList;

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtName;
        TextView txtDescription;
        ImageView imgPhoto;

        ExerciseViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            txtName = (TextView)itemView.findViewById(R.id.person_name);
            txtDescription = (TextView)itemView.findViewById(R.id.person_age);
            imgPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

    RVAdapter(List<Exercise> exerciseList) {
        mExerciseList = exerciseList;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ExerciseViewHolder pvh = new ExerciseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        holder.txtName.setText(mExerciseList.get(position).getName());
        holder.txtDescription.setText("bla");
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

}
