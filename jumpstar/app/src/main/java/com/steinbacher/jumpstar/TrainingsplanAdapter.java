package com.steinbacher.jumpstar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.core.User;
import com.steinbacher.jumpstar.view.TrainingsPlanView;

import java.util.List;

/**
 * Created by stge on 17.09.18.
 */

public class TrainingsplanAdapter extends ArrayAdapter {
    public TrainingsplanAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrainingsPlan trainingsPlan = (TrainingsPlan) getItem(position);

        if (convertView == null) {
            convertView = new TrainingsPlanView(getContext());
        }

        TrainingsPlanView view = (TrainingsPlanView) convertView;
        view.setTrainingsPlan(trainingsPlan);

        return view;
    }
}
