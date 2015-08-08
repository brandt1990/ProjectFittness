package edu.psu.ist402.projectfittness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_summary);

        // Display user details
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userAge = (TextView) findViewById(R.id.userAge);
        TextView userHeight = (TextView) findViewById(R.id.userHeight);
        TextView userWeight = (TextView) findViewById(R.id.userWeight);
        Exercise_DB db = new Exercise_DB(this);
        User user = db.getUserInfo();
        userName.setText(user.getName());
        userAge.setText(user.getBirthdate());

        //todo convert cm to ft
        userHeight.setText(String.valueOf(user.getHeight()) + " cm");
        userWeight.setText(String.valueOf(user.getWeight()) + " lbs.");

        userAge.setText(String.valueOf(user.getAge()) + " years old");
    }


    // Start music app when Play Music is clicked
    public void onClickPlayMusic(View view) {
        try {
            Intent myIntent = new Intent("android.intent.action.MUSIC_PLAYER");
            startActivity(myIntent);
        } catch(Exception e) {
            // Show error toast if something goes wrong
            Toast.makeText(getApplicationContext(), "Unable to open music player",
                    Toast.LENGTH_LONG).show();
        }
    }


    // Open exercise progress activity when ClickExerciseEntry is clicked
    public void onClickExerciseEntry(View view) {
        Intent myIntent = new Intent(getApplicationContext(), ExerciseActivity.class);
        startActivity(myIntent);
    }


    // Open exercise info activity when ExerciseInfo is clicked
    // TODO
    public void onClickExerciseInfo(View view) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_summary) {
            /*
            // Open User Entry activity
            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
            */
            return true;

        }
        if (id == R.id.action_settings) {
            // Open User Entry activity
            Intent myIntent = new Intent(getApplicationContext(), UserEntryActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_help) {
            // Open Help activity
            Intent myIntent = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_about) {
            // Open About activity
            Intent myIntent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(myIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
