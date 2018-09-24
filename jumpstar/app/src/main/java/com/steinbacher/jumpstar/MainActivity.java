package com.steinbacher.jumpstar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;


import com.kobakei.ratethisapp.RateThisApp;
import com.steinbacher.jumpstar.util.JSONHolder;

import org.solovyev.android.checkout.Billing;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Context mContext;
    private BottomNavigationView mNavigation;
    private Fragment mCurrentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        private MenuItem mPrevItem;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment fragmentHome = new HomeFragment();
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.main_content, fragmentHome).commit();
                    mCurrentFragment = fragmentHome;
                    mPrevItem = item;
                    return true;
                case R.id.navigation_trainingsPlanChooser:
                    TrainingsPlanSelectionFragment fragmentTrai = new TrainingsPlanSelectionFragment();
                    if(mPrevItem.getItemId() == R.id.navigation_home) {
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else if(mPrevItem.getItemId() == R.id.navigation_exercises || mPrevItem.getItemId() == R.id.navigation_settings){
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                    fragmentTransaction.replace(R.id.main_content, fragmentTrai).commit();
                    mCurrentFragment = fragmentTrai;
                    mPrevItem = item;
                    return true;
                case R.id.navigation_exercises:
                    ExerciseOverviewFragment fragmentExercise = new ExerciseOverviewFragment();
                    if(mPrevItem.getItemId() == R.id.navigation_home || mPrevItem.getItemId() == R.id.navigation_trainingsPlanChooser) {
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else if(mPrevItem.getItemId() == R.id.navigation_settings){
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                    fragmentTransaction.replace(R.id.main_content, fragmentExercise).commit();
                    mCurrentFragment = fragmentExercise;
                    mPrevItem = item;
                    return true;
                case R.id.navigation_settings:
                    PreferenceFragment fragmentPref = new PreferenceFragment();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.main_content, fragmentPref).commit();
                    mCurrentFragment = fragmentPref;
                    mPrevItem = item;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        //set the rate app dialog
        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this);

        //set the local to us if needed
        if(!Configuration.isSet(this, Configuration.UNIT_LOCAL_KEY)) {
            Locale current = getResources().getConfiguration().locale;
            if(current.getCountry().equals(Locale.US.getCountry())) {
                Configuration.set(this, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.IMPERIAL.name());
            }
        }

        // DEVELOPMENT TODO remove when not needed
        //SharedPreferencesManager.clear(mContext);
        /*
        SharedPreferencesManager.clear(mContext);
        VerticalHeightWriter writer2 = new VerticalHeightWriter(mContext);
        writer2.drop();


        int[] ids = {1,2};
        Configuration.set(mContext, Configuration.CURRENT_TRAININGSPLANS_ID_KEY, ids);
        Configuration.set(mContext, Configuration.SHOW_VERTICAL_PROGRESS, true);
        */
        //END DEVELOPMENT

        initMain();
    }

    private void initMain() {
        setContentView(R.layout.activity_main);

        //Bottom Navigation
        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.navigation_home);

        //Set the content
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, homeFragment)
                .commit();

        //Load all the json data
        JSONHolder holder = JSONHolder.getInstance();
        if(!holder.loadAll(mContext)) {
            //unable to load our json data
            finish();
        }
    }

    public void changeContent(int id, boolean animation) {
        if(animation) {
            mNavigation.setSelectedItemId(id);
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (id) {
                case R.id.navigation_home:
                    HomeFragment fragmentHome = new HomeFragment();
                    fragmentTransaction.replace(R.id.main_content, fragmentHome).commit();
                    mCurrentFragment = fragmentHome;
                    break;
                case R.id.navigation_trainingsPlanChooser:
                    TrainingsPlanSelectionFragment fragmentTrai = new TrainingsPlanSelectionFragment();
                    fragmentTransaction.replace(R.id.main_content, fragmentTrai).commit();
                    mCurrentFragment = fragmentTrai;
                    break;
                case R.id.navigation_exercises:
                    ExerciseOverviewFragment fragmentExercise = new ExerciseOverviewFragment();
                    fragmentTransaction.replace(R.id.main_content, fragmentExercise).commit();
                    mCurrentFragment = fragmentExercise;
                    break;
                case R.id.navigation_settings:
                    PreferenceFragment fragmentPref = new PreferenceFragment();
                    fragmentTransaction.replace(R.id.main_content, fragmentPref).commit();
                    mCurrentFragment = fragmentPref;
                    break;
            }
        }
    }
}
