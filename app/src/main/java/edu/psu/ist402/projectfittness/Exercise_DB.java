package edu.psu.ist402.projectfittness;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Marc
 *
 *
 * Uses Android SQLiteAssetHelper (http://github.com/jgilfelt/android-sqlite-asset-helper)
 *
 *
 * Dates are in Unix time
 *
 *
 * PUBLIC METHODS:
 *
 * updateUser(String name, int birthdate, float height, float weight)
 *
 * getUserInfo()
 *      RETURNS User OBJECT
 *
 * addExerciseProgress(int datetime, int id, int sets, int reps, int length)
 *
 * getExerciseProgressList()
 *      RETURNS List<ExerciseProgress> OBJECT
 */




public class Exercise_DB extends SQLiteAssetHelper {
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
    private static final String COLUMN_BIRTHDATE = "BIRTHDATE";
    private static final String COLUMN_HEIGHT = "HEIGHT";
    private static final String COLUMN_WEIGHT = "WEIGHT";
    // Progress
    private static final String COLUMN_DATETIME = "DATETIME";
    private static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    private static final String COLUMN_SETS = "SETS";
    private static final String COLUMN_REPS = "REPS";
    private static final String COLUMN_LENGTH = "LENGTH";
    // Exercise_List
    private static final String EXERCISE_ID = "EXERCISE_ID";
    private static final String EXERCISE_NAME = "EXERCISE_NAME";
    private static final String TARGETED_MUSCLE = "TARGETED_MUSCLE";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String IMAGE_REF = "IMAGE_REF";
    private static final String SUGGESTED_PATTERN = "SUGGESTED_PATTERN";






    public Exercise_DB (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }






    //User DAO


    // Update User
    public void updateUser(String name, int birthdate, float height, float weight) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Delete existing data
        String sql = "DELETE FROM " + TABLE_USER;
        db.execSQL(sql);

        //Add user
        sql = "INSERT INTO " + TABLE_USER
                + " (" + COLUMN_NAME + ", " + COLUMN_BIRTHDATE + ", " + COLUMN_HEIGHT + ", " + COLUMN_WEIGHT
                + ") VALUES('" + name + "', " + birthdate + ", " + height + ", " + weight + ")";
        db.execSQL(sql);

        db.close();
    }


    // Get UserInfo
    // Returns User object
    public User getUserInfo() {
        User user = new User(getUserName(),getUserBirthdate(),getUserHeight(),getUserWeight());
        return user;
    }


    // Get UserName
    private String getUserName() {
        String name =  "";

        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            } while (cursor.moveToNext());
        }
        db.close();

        return name;
    }


    // Get UserBirthdate
    private int getUserBirthdate() {
        int birthdate = 0;

        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                birthdate = cursor.getInt(cursor.getColumnIndex(COLUMN_BIRTHDATE));
            } while (cursor.moveToNext());
        }
        db.close();

        return birthdate;
    }


    // Get UserHeight
    private float getUserHeight() {
        float height = 0;

        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                height = cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT));
            } while (cursor.moveToNext());
        }
        db.close();

        return height;
    }


    // Get UserWeight
    private float getUserWeight() {
        float weight = 0;

        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                weight = cursor.getInt(cursor.getColumnIndex(COLUMN_WEIGHT));
            } while (cursor.moveToNext());
        }
        db.close();

        return weight;
    }






    // Progress DAO


    // Add ExerciseEntry
    public void addExerciseProgress(int datetime, int id, int sets, int reps, int length) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Add entry
        String sql = "INSERT INTO " + TABLE_PROGRESS
                + " (" + COLUMN_DATETIME + ", " + COLUMN_EXERCISE_ID + ", " + COLUMN_SETS + ", " + COLUMN_REPS + ", " + COLUMN_LENGTH
                + ") VALUES(" + datetime + ", " + id + ", " + sets + ", " + reps + ", " + length + ")";
        db.execSQL(sql);

        db.close();
    }


    // Get ExerciseProgressList
    // Returns a list of ExerciseProgress objects
    public List<ExerciseProgress> getExerciseProgressList() {
        List<ExerciseProgress> ExerciseProgressList = new ArrayList<ExerciseProgress>();

        String selectQuery = "SELECT * FROM " + TABLE_PROGRESS + " ORDER BY " + COLUMN_DATETIME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ExerciseProgress entry = new ExerciseProgress(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_DATETIME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EXERCISE_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_SETS)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_REPS)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_LENGTH))
                        );
                ExerciseProgressList.add(entry);
            } while (cursor.moveToNext());
        }

        return ExerciseProgressList;
    }




    // Exercise_List DAO
    // TODO

}