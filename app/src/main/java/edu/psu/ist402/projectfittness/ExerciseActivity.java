package edu.psu.ist402.projectfittness;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.Locale;


public class ExerciseActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {

    private String[] arraySpinner;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        addExerciseList();
        this.bundle = savedInstanceState;
    }


    // Populate exercise spinner
    private void addExerciseList() {
        Exercise_DB db = new Exercise_DB(this);

        List<ExerciseInfo> exercises = db.getExerciseNameList();
        this.arraySpinner = new String[exercises.size()];
        for (int i = 0; i < exercises.size(); i++) {
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


    boolean speakOptionOn = true;

    // "Begin/End Workout" button handler
    public void onClickBeginEndWorkout(View view) {
        Button btnBeginEndWorkout = (Button) findViewById(R.id.btnBeginEndWorkout);
        if (btnBeginEndWorkout.getText().toString().contains("Begin")) {
            btnBeginEndWorkout.setText("End Workout");

            Speak("Alright. let's do this!");
            // TODO timer start

        } else if (btnBeginEndWorkout.getText().toString().contains("End")) {
            btnBeginEndWorkout.setText("Begin Workout");
            // TODO timer stop, store data?
            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
        }
    }

    private int MY_DATA_CHECK_CODE = 1;
    private TextToSpeech tts;


    // "Add Previous Workout" button handler
    public void onClickExerciseInfo(View view) {
        // Enable date to be edited
        EditText workoutDate = (EditText) findViewById(R.id.workoutDate);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void SpeakNew(String speech) {
        CharSequence cs = speech;
        tts.speak(cs, TextToSpeech.QUEUE_ADD, this.bundle, "0");
    }

    void Speak(String speech) {
        if (speakOptionOn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                SpeakNew(speech);
            } else {
                tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
            }
        }
    }

    @Override
    public void onInit(int status) {
        tts.setLanguage(Locale.US);


    }

    @Override
    protected void onStart() {
        super.onStart();
        tts = new TextToSpeech(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tts.shutdown();
    }
}
