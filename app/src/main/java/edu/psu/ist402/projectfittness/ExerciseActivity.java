package edu.psu.ist402.projectfittness;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;


public class ExerciseActivity extends ActionBarActivity {

    private String[] arraySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


        addExerciseList();

    }




    // Populate exercise spinner
    private void addExerciseList() {
        Exercise_DB db = new Exercise_DB(this);

        List<ExerciseInfo> exercises = db.getExerciseNameList();
        this.arraySpinner = new String[exercises.size()];
        for (int i=0; i<exercises.size(); i++) {
            ExerciseInfo exercise = exercises.get(i);
            arraySpinner[i] = exercise.getExercise_name();
        }

        db.close();

        Spinner spinnerExercises = (Spinner) findViewById(R.id.spinnerExercises);
        spinnerExercises.setAdapter(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinnerExercises.setAdapter(adapter);

    }



    // "Begin/End Workout" button handler
    public void onClickBeginEndWorkout(View view) {
        Button btnBeginEndWorkout = (Button) findViewById(R.id.btnBeginEndWorkout);
        if(btnBeginEndWorkout.getText().toString().contains("Begin")) {
            btnBeginEndWorkout.setText("End Workout");
            // TODO timer start
        } else if(btnBeginEndWorkout.getText().toString().contains("End")) {
            btnBeginEndWorkout.setText("Begin Workout");
            // TODO timer stop, store data?
            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
        }
    }



    // "Add Previous Workout" button handler
    public void onClickExerciseInfo(View view) {
        // Enable date to be edited
        EditText workoutDate = (EditText)findViewById(R.id.workoutDate);
        workoutDate.setEnabled(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
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
            // Open User Entry activity
            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
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
