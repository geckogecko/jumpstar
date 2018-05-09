package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.github.pwittchen.swipe.library.rx2.SwipeListener;
import com.stephentuso.welcome.WelcomeHelper;

import georg.steinbacher.community_jump_trainer.db.VerticalHeightWriter;
import georg.steinbacher.community_jump_trainer.util.JSONHolder;
import georg.steinbacher.community_jump_trainer.util.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity implements SwipeListener{
    private static final String TAG = "MainActivity";

    private WelcomeHelper mSetupHelper;
    private Context mContext;
    private Swipe mSwipe;
    private BottomNavigationView mNavigation;

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
                    mPrevItem = item;
                    return true;
                case R.id.navigation_trainingsPlanChooser:
                    TrainingsPlanSelectionFragment fragmentTrai = new TrainingsPlanSelectionFragment();
                    if(mPrevItem.getItemId() == R.id.navigation_home) {
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else if(mPrevItem.getItemId() == R.id.navigation_settings){
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                    fragmentTransaction.replace(R.id.main_content, fragmentTrai).commit();
                    mPrevItem = item;
                    return true;
                case R.id.navigation_settings:
                    PreferenceFragment fragmentPref = new PreferenceFragment();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                    fragmentTransaction.replace(R.id.main_content, fragmentPref).commit();
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
        Log.i(TAG, "onCreate: " + getIntent().getStringExtra("test"));


        // DEVELOPMENT TODO remove when not needed
        //SharedPreferencesManager.clear(mContext);
        /*
        SharedPreferencesManager.clear(mContext);
        VerticalHeightWriter writer2 = new VerticalHeightWriter(mContext);
        writer2.drop();
        */

        int[] ids = {1,2};
        Configuration.set(mContext, Configuration.CURRENT_TRAININGSPLANS_ID_KEY, ids);

        VerticalHeightWriter writer = new VerticalHeightWriter(getApplicationContext());
        writer.add(System.currentTimeMillis(), 30);
        writer.add(System.currentTimeMillis() + 10000, 32);
        writer.add(System.currentTimeMillis() + 20000, 34);
        writer.add(System.currentTimeMillis() + 30000, 35);

        //END DEVELOPMENT

        // is this the first time the app is started ?
        // if yes -> open the Setup
        if(!Configuration.isSet(mContext, Configuration.SETUP_COMPLETED_KEY)) {
            mSetupHelper = new WelcomeHelper(this, SetupActivity.class);
            mSetupHelper.forceShow();
        } else {
            initMain();
        }
    }

    private void initMain() {
        setContentView(R.layout.activity_main);

        //set swipe left right listener
        mSwipe = new Swipe();
        mSwipe.setListener(this);

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
        holder.loadExercises(mContext);
        holder.loadTrainingsPlans(mContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            Configuration.set(mContext, Configuration.SETUP_COMPLETED_KEY, true);
            initMain();
        }
    }

    public void changeContent(int id) {
        mNavigation.setSelectedItemId(id);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        mSwipe.dispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onSwipingLeft(MotionEvent event) {

    }

    @Override
    public boolean onSwipedLeft(MotionEvent event) {
        if(mNavigation.getSelectedItemId() == R.id.navigation_home) {
            mNavigation.setSelectedItemId(R.id.navigation_trainingsPlanChooser);
        } else if(mNavigation.getSelectedItemId() == R.id.navigation_trainingsPlanChooser) {
            mNavigation.setSelectedItemId(R.id.navigation_settings);
        }
        return false;
    }

    @Override
    public void onSwipingRight(MotionEvent event) {

    }

    @Override
    public boolean onSwipedRight(MotionEvent event) {
        if(mNavigation.getSelectedItemId() == R.id.navigation_settings) {
            mNavigation.setSelectedItemId(R.id.navigation_trainingsPlanChooser);
        } else if(mNavigation.getSelectedItemId() == R.id.navigation_trainingsPlanChooser) {
            mNavigation.setSelectedItemId(R.id.navigation_home);
        }
        return false;
    }

    @Override
    public void onSwipingUp(MotionEvent event) {

    }

    @Override
    public boolean onSwipedUp(MotionEvent event) {
        return false;
    }

    @Override
    public void onSwipingDown(MotionEvent event) {

    }

    @Override
    public boolean onSwipedDown(MotionEvent event) {
        return false;
    }
}
