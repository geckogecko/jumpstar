package com.steinbacher.jumpstar;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.steinbacher.jumpstar.core.Equipment;
import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.db.PlanWriter;
import com.steinbacher.jumpstar.db.VerticalHeightWriter;
import com.steinbacher.jumpstar.util.Factory;
import com.steinbacher.jumpstar.view.ExerciseOverviewLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stge on 24.09.18.
 */

public class ExerciseOverviewFragment extends Fragment {
    private static final String TAG = "ExerciseOverviewFragmen";
    private View mView;
    private FloatingActionButton mCreateNewPlanButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_exercise_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        mCreateNewPlanButton = view.findViewById(R.id.create_new_trainingsplan);
        mCreateNewPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPlanDialog();
            }
        });

        ExercisePageAdapter pageAdapter = new ExercisePageAdapter(getFragmentManager());
        ViewPager viewPager = mView.findViewById(R.id.pager);
        viewPager.setAdapter(pageAdapter);
    }

    public class ExercisePageAdapter extends FragmentStatePagerAdapter {

        public ExercisePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ExercisePageFragment fragment = new ExercisePageFragment();
            switch (position) {
                case 0: fragment.init(Exercise.Category.WARMUP); break;
                case 1: fragment.init(Exercise.Category.STRENGTH); break;
                case 2: fragment.init(Exercise.Category.PLYOMETRIC); break;
                case 3: fragment.init(Exercise.Category.STRETCH); break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.detail_exercises_warmup).replace(":", "");
                case 1: return getString(R.string.detail_exercises_strength).replace(":", "");
                case 2: return getString(R.string.detail_exercises_plyometric).replace(":", "");
                case 3: return getString(R.string.detail_exercises_stretch).replace(":", "");
                default: return "";
            }
        }
    }

    private void createPlanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogTheme));
        builder.setTitle(getContext().getString(R.string.create_new_plan_input_hint));

        final LinearLayoutCompat layoutCompat = new LinearLayoutCompat(getContext());
        builder.setView(layoutCompat);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AppCompatEditText textView = layoutCompat.findViewById(R.id.edit_text);
                final String inputString = textView.getText().toString();
                if(inputString.isEmpty()) {
                    dialog.cancel();
                } else {
                    createNewPlan(inputString);
                }
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        View view = dialog.getLayoutInflater().inflate(R.layout.alertdialog_edittext, layoutCompat);
        final AppCompatEditText input = view.findViewById(R.id.edit_text);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        dialog.show();
    }

    private void createNewPlan(String planName) {
        Log.i(TAG, "createNewPlan: ");
        PlanWriter writer = new PlanWriter(getContext());
        writer.add(planName, new ArrayList<Exercise>());
    }
}
