package edu.psu.ist402.projectfittness;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
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

        //db.addExercise("Leg raises", "20,3,10");

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
    Dialog settingsDialog;
    ExerciseInfo selectedExerciseInfo;

    // "Begin/End Workout" button handler
    public void onClickBeginEndWorkout(View view) {
        Button btnBeginEndWorkout = (Button) findViewById(R.id.btnBeginEndWorkout);
        if (btnBeginEndWorkout.getText().toString().contains("Begin")) {
            btnBeginEndWorkout.setText("End Workout");

            Speak("Alright. let's do this!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //get which exercise is selected
            Spinner spinnerExercises = (Spinner) findViewById(R.id.spinnerExercises);
            String selectedExercise = spinnerExercises.getSelectedItem().toString();
            selectedExerciseInfo = getSelectedExerciseInfoByName(selectedExercise);

            //brief user about the exercise
            String[] exercisePattern = selectedExerciseInfo.getSuggested_pattern().split(",");
            selectedExerciseInfo.setSetLength(exercisePattern[0]);
            selectedExerciseInfo.setSetCount(exercisePattern[1]);
            selectedExerciseInfo.setRepCount(exercisePattern[2]);
            String exerciseName = selectedExerciseInfo.getExercise_name();

            Speak("For, " + exerciseName + " exercise. You have to do " + selectedExerciseInfo.getRepCount() + " reps. " +
                    selectedExerciseInfo.getSetCount() + " times. each set in " + selectedExerciseInfo.getSetLength() + " seconds.");
            Speak("Click the exercise image when you are ready!");

            showExerciseInfo();


        } else if (btnBeginEndWorkout.getText().toString().contains("End")) {
            btnBeginEndWorkout.setText("Begin Workout");
            // TODO timer stop, store data?
            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
        }
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // open a dialog box with exercise image
    private void showExerciseInfo() {
        settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View imgDialog = getLayoutInflater().inflate(R.layout.image_layout, null);

        ImageView exImage = (ImageView) imgDialog.findViewById(R.id.exerciseImage);
        ((TextView) imgDialog.findViewById(R.id.tvSets)).setText(selectedExerciseInfo.getSetCount());
        ((TextView) imgDialog.findViewById(R.id.tvReps)).setText(selectedExerciseInfo.getRepCount());
        exImage.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });
        exImage.setImageResource(this.getResources().
                getIdentifier("drawable/" + selectedExerciseInfo.getImage_ref(), null, this.getPackageName()));
        settingsDialog.setContentView(imgDialog);
        settingsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //Log.d("",dialog.toString());
                startWorkout();
                // TODO timer start
            }
        });
        settingsDialog.show();
    }

    public static void startWorkout() {


    }

    private ExerciseInfo getSelectedExerciseInfoByName(String exerciseName) {

        Exercise_DB db = new Exercise_DB(this);
        List<ExerciseInfo> exercises = db.getExerciseNameList();
        ExerciseInfo exerciseInfo = null;

        for (int i = 0; i < exercises.size(); i++) {
            exerciseInfo = exercises.get(i);
            if (exerciseName.toLowerCase().equals(exerciseInfo.getExercise_name().toLowerCase())) {
                break;
            }
        }

        return exerciseInfo;
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

    public void dismissListener(View view) {
    }
}
