package edu.psu.ist402.projectfittness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        //todo convert cm to ft
        userHeight.setText(String.valueOf(user.getHeight()) + " cm");
        userWeight.setText(String.valueOf(user.getWeight()) + " lbs.");
        userAge.setText(String.valueOf(user.getAge()) + " years old");

        //Load exercise history
        TableLayout tLayout = (TableLayout) findViewById(R.id.tLayout);
        TextView tv1 = (TextView) findViewById(R.id.exerciseNameTemplate);
        TextView tv2 = (TextView) findViewById(R.id.exerciseSetsTemplate);
        TextView tv3 = (TextView) findViewById(R.id.exerciseDateTimeTemplate);

        TableRow temp = (TableRow) findViewById(R.id.exerciseRowTemplate);
        ViewGroup.LayoutParams params = temp.getLayoutParams(); // get row params


        List<ExerciseProgress> exerciseProgress = db.getExerciseProgressList();

        for (ExerciseProgress eP : exerciseProgress) {
            // Create row
            TableRow tr = new TableRow(tLayout.getContext());
            tr.setLayoutParams(params); // Set rows params as set in XML
            eP.getEnd_datetime();

            //Add Column 1 - Exercise
            TextView textV = new TextView(tr.getContext());
            //textV.setLayoutParams(tv1.getLayoutParams());
            textV.setText(String.valueOf(db.getInfoForID(eP.getExercise_id()).getExercise_name()));
            textV.setVisibility(View.VISIBLE);
            textV.setWidth(dpToPixels(140));
            tr.addView(textV, 0, tv1.getLayoutParams());

            //Add Column 2 - Sets completed
            textV = new TextView(tr.getContext());
            textV.setLayoutParams(tv2.getLayoutParams());
            textV.setText(String.valueOf(eP.getSets()));
            textV.setVisibility(View.VISIBLE);
            textV.setWidth(dpToPixels(80));
            textV.setPadding(dpToPixels(30),0,0,0);
            tr.addView(textV, 1);


            //Add Column 3 - Date time ended
            textV = new TextView(tr.getContext());
            textV.setLayoutParams(tv3.getLayoutParams());
            textV.setText(String.valueOf(getDateTime(Long.parseLong(eP.getEnd_datetime()), "MM/dd/yyyy HH:mm")));
            textV.setVisibility(View.VISIBLE);
            tr.addView(textV, 2);
            textV.setWidth(dpToPixels(10));
            tLayout.addView(tr);
        }
    }


    // Convert dp to pixels
    private int dpToPixels(int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return (int) px;
    }


    private String getDateTime(long ms, String format) {
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(ms);
        //return new SimpleDateFormat(format).format(calendar.getTime());
        return new SimpleDateFormat(format).format(new Date(ms));
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
