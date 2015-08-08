package edu.psu.ist402.projectfittness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> ls = new ArrayList<>();
        ls.add("1");
        ls.add("2");
        ls.add("3");
        ls.add("4");

        ListView lv;


        for (String l : ls) {
            Log.d("Zunair", l);
        }


        if (!userExists()) {
            Intent intent = new Intent(getApplicationContext(), UserEntryActivity.class);
            startActivity(intent);
        } else {
            //Start user summary activity or update main activity layout to user summary
            Intent myIntent = new Intent(getApplicationContext(), UserSummaryActivity.class);
            startActivity(myIntent);
        }


    }

    // Check if a user exists
    private boolean userExists() {
        Exercise_DB db = new Exercise_DB(this);
        User user = db.getUserInfo();
        db.close();
        if (user.getName() != null && user.getName() != "") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
