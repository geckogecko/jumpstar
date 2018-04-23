package georg.steinbacher.community_jump_trainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.stephentuso.welcome.WelcomeHelper;

import java.sql.Time;

import georg.steinbacher.community_jump_trainer.db.VerticalHeightWriter;
import georg.steinbacher.community_jump_trainer.util.JSONHolder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private WelcomeHelper mSetupHelper;
    private Context mContext;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment fragmentHome = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentHome)
                            .commit();
                    return true;
                case R.id.navigation_trainingsPlanChooser:
                    TrainingsPlanSelectionFragment fragmentTrai = new TrainingsPlanSelectionFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentTrai)
                            .commit();
                    return true;
                case R.id.navigation_settings:
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

        // DEVELOPMENT TODO remove when not needed
        Configuration.set(mContext, Configuration.CURREN_TRAININGSPLAN_ID_KEY, 1);

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
        }

        setContentView(R.layout.activity_main);

        //Bottom Navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Set the content
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, homeFragment)
                .commit();

        //Load all the json data
        JSONHolder holder = JSONHolder.getInstance();
        holder.loadExercises(mContext);
        holder.loadTrainingsPlans(mContext);



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
            Configuration.set(mContext, Configuration.SETUP_COMPLETED_KEY, true);
        }
    }
}
