package edu.psu.ist402.projectfittness;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UserEntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userentry);

        addDatePickerOn((EditText)findViewById(R.id.editText_DOB));
    }

    private void addDatePickerOn(final EditText txtObj)
    {
        String myFormat = "MMMM dd, yyyy"; //In which you need put here
        final SimpleDateFormat df = new SimpleDateFormat(myFormat, Locale.US);

        txtObj.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment datePickerFragment = new DatePickerFragment() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            Calendar c = Calendar.getInstance();
                            c.set(year, month, day);
                            txtObj.setText(df.format(c.getTime()));
                            //nextField.requestFocus(); //moves the focus to something else after dialog is closed
                        }
                    };
                    datePickerFragment.show(UserEntryActivity.this.getFragmentManager(), "datePicker");
                }
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year-21, month, day);
            dpd.getDatePicker().setMinDate(dpd.getDatePicker().getMinDate());
            dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //blah
        }
    }
}
