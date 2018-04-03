package georg.steinbacher.community_jump_trainer;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Configuration;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.util.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mTextMessage;

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

        // is this the first time the app is started ?
        // if yes -> open the Setup
        if(!SharedPreferencesManager.getBoolean(this,
                SetupActivity.SETUP_COMPLETED_KEY,
                false)) {
            Intent setupActivity = new Intent(this, SetupActivity.class);
            this.startActivity(setupActivity);
        }

        Log.i(TAG, "onCreate: "  + Configuration.getInstance().getReachHeight());

        setContentView(R.layout.activity_main);

        //Bottom Navigation
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Receicler
        RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        List<Exercise> exerciseList = new ArrayList<>();
        /*
        exerciseList.add(new Exercise("Excercise1", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise2", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise3", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise4", "This is a long description bla as1 asdsdaf"));
        exerciseList.add(new Exercise("Excercise5", "This is a long description bla as1 asdsdaf"));
        */

        RVAdapter adapter = new RVAdapter(exerciseList);
        rv.setAdapter(adapter);
    }

}
