package edu.psu.ist402.projectfittness;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Marc on 8/6/2015.
 * Uses Android SQLiteAssetHelper (http://github.com/jgilfelt/android-sqlite-asset-helper)
 * Dates are in Unix time
 */
public class MyDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "exercise.db";

    // Change this value when database changes
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_USER = "User";
    private static final String TABLE_PROGRESS = "Progress";
    private static final String TABLE_EXERCISE_LIST = "Exercise_List";

    // Column Names
    // User
    private static final String COLUMN_NAME = "NAME";
    private static final int COLUMN_BIRTHDATE = "BIRTHDATE";
    private static final float COLUMN_HEIGHT = "HEIGHT";
    private static final float COLUMN_WEIGHT = "WEIGHT";
    // Progress
    private static final int COLUMN_DATE = "DATE";
    private static final int COLUMN_TIME = "TIME";
    private static final int COLUMN_EXERCISE_ID = "EXERCISE_ID";
    private static final int COLUMN_SETS = "SETS";
    private static final int COLUMN_REPS = "REPS";
    private static final int COLUMN_LENGTH = "LENGTH";
    // Exercise_List
    private static final int EXERCISE_ID = "EXERCISE_ID";
    private static final String EXERCISE_NAME = "EXERCISE_NAME";
    private static final String TARGETED_MUSCLE = "TARGETED_MUSCLE";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String IMAGE_REF = "IMAGE_REF";
    private static final String SUGGESTED_PATTERN = "SUGGESTED_PATTERN";




    public MyDatabase (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    //User DAO
    //TODO

    // Update User
    public void updateUser(String Name, Date Birthdate, float height, float weight) {

    }

    // Get UserName
    public String getUserName() {

    }

    // Get UserBirthdate
    public String getUserBirthdate() {

    }

    // Get UserHeight
    public float getUserHeight() {

    }

    // Get UserWeight
    public float getUserWeight() {

    }




    // Progress DAO
    // TODO




    // Exercise_List DAO
    // TODO

}
