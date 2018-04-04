package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeHelper;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Configuration;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.util.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private WelcomeHelper mSetupHelper;
    private Context mContext;
    private Configuration mConfig = Configuration.getInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        //SharedPreferencesManager.clear(mContext);

        initConfiguration(mContext);

        // is this the first time the app is started ?
        // if yes -> open the Setup
        if(!mConfig.isSetupCompleted()) {
            mSetupHelper = new WelcomeHelper(this, SetupActivity.class);
            mSetupHelper.forceShow();
        }

        setContentView(R.layout.activity_main);

        //Bottom Navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*
        //Receicler
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        List<Exercise> exerciseList = new ArrayList<>();

        exerciseList.add(new Exercise("Excercise1", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise2", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise3", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise4", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise5", "This is a long description bla as1 asdsdaf"));


        RVAdapter adapter = new RVAdapter(exerciseList);
        rv.setAdapter(adapter);
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == WelcomeHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
           SharedPreferencesManager.writeBoolean(this,
                   Configuration.SETUP_COMPLETED_KEY,
                   true);
        }
    }

    private void initConfiguration(Context context) {
        Configuration configuration = Configuration.getInstance();

        //SETUP COMPLETED
        boolean setupCompleted = SharedPreferencesManager.getBoolean(context, Configuration.SETUP_COMPLETED_KEY, false);
        if(setupCompleted) {
            configuration.setSetupCompleted(setupCompleted);
        }

        //REACHED HEIGHT
        double reachHeight = SharedPreferencesManager.getDouble(context, Configuration.REACHED_HEIGHT_KEY, -1);
        if(reachHeight != -1) {
            configuration.setReachHeight(reachHeight);
        }
    }
}
