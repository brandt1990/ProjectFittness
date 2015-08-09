package edu.psu.ist402.projectfittness;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * User Model
 * Stores data for a user
 */
public class User {
    private String name;
    private String birthdate;
    private float height;
    private float weight;

    public User(String name, String birthdate, float height, float weight) {
        this.name = name;
        this.birthdate = birthdate;
        this.height = height;
        this.weight = weight;
    }

    public User() {
        this.name = "";
        this.birthdate = "";
        this.height = 0;
        this.weight = 0;
    }

    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Returns formatted date of birth.
     * Can also be used to get only year, month or day
     */
    public String getBirthdate(String format) {
        String retVal = null;
        try {
            final SimpleDateFormat df = new SimpleDateFormat(format, Locale.US);
            if (this.birthdate != "") {
                String[] dt = this.birthdate.split(",");
                Calendar c = Calendar.getInstance();
                c.set(Integer.parseInt(dt[0]), Integer.parseInt(dt[1]), Integer.parseInt(dt[2]));

                retVal = df.format(c.getTime());
                c.clear();
                c = null;
            }
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }
        return retVal;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setBirthdate(Calendar birthDate) {

        this.birthdate =
                birthDate.get(Calendar.YEAR) + "," +
                        birthDate.get(Calendar.MONTH) + "," +
                        birthDate.get(Calendar.DAY_OF_MONTH);
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Returns calculated age using date of birth.
     */
    public int getAge() {
        int age = 0;
        try {
            String dob = getBirthdate("yyyyMMdd");
            if (dob != null && dob != "") {
                Integer year = Integer.parseInt(dob.substring(0, 4));
                Integer month = Integer.parseInt(dob.substring(4, 6)) - 1; // -1 is for handling calendar month starting at 0
                Integer day = Integer.parseInt(dob.substring(4, 8));

                Calendar c = Calendar.getInstance();
                age = c.get(Calendar.YEAR) - year;
                if (c.get(Calendar.MONTH) < month ||
                        (c.get(Calendar.MONTH) == month && c.get(Calendar.DAY_OF_MONTH) < day)) {
                    age--;
                }
            }
        } catch (Exception e) {
            age = -1;
        }
        return age;
    }

}
