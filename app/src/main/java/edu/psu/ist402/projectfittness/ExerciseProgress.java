package edu.psu.ist402.projectfittness;

/**
 * Created by Marc
 * Stores data for a single exercise progress entry
 */

public class ExerciseProgress {
    int datetime;
    int exercise_id;
    int sets;
    int reps;
    int length;


    public ExerciseProgress(int datetime, int exercise_id, int sets, int reps, int length) {
        this.datetime = datetime;
        this.exercise_id = exercise_id;
        this.sets = sets;
        this.reps = reps;
        this.length = length;
    }


    public ExerciseProgress() {
        this.datetime = 0;
        this.exercise_id = 0;
        this.sets = 0;
        this.reps = 0;
        this.length = 0;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }


}
