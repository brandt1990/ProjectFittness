package edu.psu.ist402.projectfittness;

/**
 * Exercise Progress Model
 * Stores data for a single exercise progress entry
 */

public class ExerciseProgress {

    private String start_datetime;
    private String end_datetime;
    private int exercise_id;
    private int sets;

    public ExerciseProgress(String startDateTime, String endDateTime, int exercise_id, int sets) {
        this.setStart_datetime(startDateTime);
        this.setEnd_datetime(endDateTime);
        this.exercise_id = exercise_id;
        this.sets = sets;
    }


    public ExerciseProgress() {
        this.setStart_datetime("");
        this.setEnd_datetime("");
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


    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }
}
