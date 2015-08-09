package edu.psu.ist402.projectfittness;

/**
 * Exercise Progress Model
 * Stores data for a single exercise progress entry
 */

public class ExerciseProgress {

    private long start_datetime;
    private long end_datetime;
    private int exercise_id;
    private int sets;

    public ExerciseProgress(long startDateTime, long endDateTime, int exercise_id, int sets) {
        this.start_datetime = startDateTime;
        this.end_datetime = endDateTime;
        this.exercise_id = exercise_id;
        this.sets = sets;
    }


    public ExerciseProgress() {
        this.start_datetime = 0;
        this.end_datetime = 0;
        this.exercise_id = 0;
        this.sets = 0;
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

    public long getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(long start_datetime) {
        this.start_datetime = start_datetime;
    }

    public long getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(long end_datetime) {
        this.end_datetime = end_datetime;
    }
}
