package edu.psu.ist402.projectfittness;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        workoutSetsTimeLeft = (TextView) findViewById(R.id.workoutSetsTimeLeft);
        this.bundle = savedInstanceState;
        ((EditText) findViewById(R.id.workoutDate)).setText(
                new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));

//        ((Button) findViewById(R.id.btnBeginEndWorkout)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Button b = (Button) v;
//                if (b.getText().equals("stop")) {
//                    timerHandler.removeCallbacks(timerRunnable);
//                    b.setText("start");
//                } else {
//                    startTime = System.currentTimeMillis();
//                    timerHandler.postDelayed(timerRunnable, 0);
//                    b.setText("stop");
//                }
//            }
//        });

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

        final Spinner spinnerExercises = (Spinner) findViewById(R.id.spinnerExercises);
        spinnerExercises.setAdapter(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinnerExercises.setAdapter(adapter);
        spinnerExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedExercise = parent.getItemAtPosition(position).toString();
                //store selected exercise
                selectedExerciseInfo = getSelectedExerciseInfoByName(selectedExercise);
                String[] exercisePattern = selectedExerciseInfo.getSuggested_pattern().split(",");
                selectedExerciseInfo.setSetLength(exercisePattern[0]);
                selectedExerciseInfo.setSetCount(exercisePattern[1]);
                selectedExerciseInfo.setRepCount(exercisePattern[2]);

                //Update view
                ((TextView) findViewById(R.id.tvSets)).setText(selectedExerciseInfo.getSetCount());
                ((TextView) findViewById(R.id.tvReps)).setText(selectedExerciseInfo.getRepCount());
                ((TextView) findViewById(R.id.tvLen)).setText(selectedExerciseInfo.getSetLength());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    boolean speakOptionOn = true;
    Dialog settingsDialog;
    ExerciseInfo selectedExerciseInfo;
    ExerciseProgress currentExerciseProgress;

    // "Begin/End Workout" button handler
    public void onClickBeginEndWorkout(View view) {
        Button btnBeginEndWorkout = (Button) findViewById(R.id.btnBeginEndWorkout);
        if (btnBeginEndWorkout.getText().toString().contains("Begin")) {
            currentExerciseProgress = new ExerciseProgress();
            btnBeginEndWorkout.setText("End Workout");

            Speak("Alright. let's do this!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            //get which exercise is selected
//            Spinner spinnerExercises = (Spinner) findViewById(R.id.spinnerExercises);
//            String selectedExercise = spinnerExercises.getSelectedItem().toString();
//            selectedExerciseInfo = getSelectedExerciseInfoByName(selectedExercise);

            //brief user about the exercise
            String exerciseName = selectedExerciseInfo.getExercise_name();

            Speak("For, " + exerciseName + " workout. You have to do " + selectedExerciseInfo.getRepCount() + " reps. " +
                    selectedExerciseInfo.getSetCount() + " times. each set in " + selectedExerciseInfo.getSetLength() + " seconds.");
            Speak("Click the exercise image when you are ready!");

            showExerciseInfo();


        } else if (btnBeginEndWorkout.getText().toString().contains("End")) {
            btnBeginEndWorkout.setText("Begin Workout");

            endWorkout(true);



            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
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

            }
        });
        settingsDialog.show();
    }

    private static TextView workoutSetsTimeLeft;
    long startTime = 0;
    private boolean booWaiting;

    Handler timerHandler;
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            boolean exit = false;

            String waiting = "";

            //workoutSetsTimeLeft.setText(String.format("%d:%02d", minutes, seconds));
            //Time left to complete a set
            Integer timeLeft = seconds * -1;
            int currentSec = ((seconds * -1) + 1);

            if (timeLeft == 0) {
                int t = Integer.parseInt(selectedExerciseInfo.getSetCount()) - 1;
                currentExerciseProgress.setSets(currentExerciseProgress.getSets() + 1);

                if (t == -1) {

                    exit = true;
                } else {
                    if (!booWaiting) {
                        selectedExerciseInfo.setSetCount(String.valueOf(t));
                        startTime = System.currentTimeMillis() + (60 * 1000);
                        if (t == 1) {
                            Speak("Great. now wait another 60 seconds.");
                            Speak("Only " + t + " more set to go.");
                        } else {
                            Speak("OK. now wait 60 seconds.");
                            if (t != 0) {
                                Speak(t + " more sets to go.");
                            } else {
                                Speak("Great! on to next exercise.");
                                Speak("You can wait another 60 seconds if you like.");
                            }
                        }
                        booWaiting = true;
                    } else {
                        startTime = System.currentTimeMillis() + ((Integer.parseInt(selectedExerciseInfo.getSetLength())) * 1000);
                        Speak("Start the next set now.");
                        booWaiting = false;
                    }
                }
            }

            updateTime(currentSec);

            if (exit | endWorkoutForce) {
                sleepFor(1);
                workoutSetsTimeLeft.setVisibility(View.GONE);
                if (!endWorkoutForce) {
                    endWorkout(false);
                }
                return; //exits timer thread
            }
            timerHandler.postDelayed(this, 1000);
        }
    };

    void sleepFor(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    String waiting = "";

    void updateTime(int currentSec) {
        if (currentSec == 1) {
            workoutSetsTimeLeft.setText(waiting + String.valueOf(currentSec) + " second");
        } else {
            workoutSetsTimeLeft.setText(waiting + String.valueOf(currentSec) + " seconds");
        }
        if (booWaiting) {
            waiting = "Wait ";
        } else {
            waiting = "";
        }
    }

    public void startWorkout() {

        ((Button) findViewById(R.id.btnAddWorkout)).setEnabled(false);
        startTime = System.currentTimeMillis() + (Integer.parseInt(selectedExerciseInfo.getSetLength()) * 1000);
        timerHandler = new Handler();
        timerHandler.postDelayed(timerRunnable, 0);
        workoutSetsTimeLeft.setVisibility(View.VISIBLE);
        //Calendar now = Calendar.getInstance();
        Date dt = new Date();
        currentExerciseProgress.setEnd_datetime(String.valueOf(dt.getTime()));
        ((EditText) findViewById(R.id.workoutStartTime)).setText(
                new SimpleDateFormat("HH:mm:ss").format(dt));

    }

    boolean endWorkoutForce = false;

    public void endWorkout(boolean forceEnd) {

        ((Button) findViewById(R.id.btnAddWorkout)).setEnabled(true);
        endWorkoutForce = forceEnd;

        Date dt = new Date();
        currentExerciseProgress.setStart_datetime(String.valueOf(dt.getTime()));
        ((EditText) findViewById(R.id.workoutEndTime)).setText(
                new SimpleDateFormat("HH:mm:ss").format(dt));

        if (endWorkoutForce) {
            timerHandler.removeCallbacks(timerRunnable);
            workoutSetsTimeLeft.setVisibility(View.GONE);
        }

        // TODO updateLog/store data
        Exercise_DB db = new Exercise_DB(this);

        currentExerciseProgress.setExercise_id(selectedExerciseInfo.getExercise_id());

        db.addExerciseProgress(
                currentExerciseProgress.getStart_datetime(),
                currentExerciseProgress.getEnd_datetime(),
                currentExerciseProgress.getSets(),
                currentExerciseProgress.getExercise_id());
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

    Integer speechID = 0;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void SpeakNew(String speech) {
        CharSequence cs = speech;
        speechID = speechID + 1;
        tts.speak(cs, TextToSpeech.QUEUE_ADD, this.bundle, String.valueOf(speechID));
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
