package edu.psu.ist402.projectfittness;

/**
 *  Stores data for a single exercise
 */

public class ExerciseInfo {
    int exercise_id;
    String exercise_name;
    String targeted_muscle;
    String difficulty;
    String image_ref;
    String suggested_pattern;


    public ExerciseInfo(int exercise_id, String exercise_name, String targeted_muscle, String difficulty, String image_ref, String suggested_pattern) {
        this.exercise_id = exercise_id;
        this.exercise_name = exercise_name;
        this.targeted_muscle = targeted_muscle;
        this.difficulty = difficulty;
        this.image_ref = image_ref;
        this.suggested_pattern = suggested_pattern;
    }

    public ExerciseInfo() {
        this.exercise_id = 0;
        this.exercise_name = "";
        this.targeted_muscle = "";
        this.difficulty = "";
        this.image_ref = "";
        this.suggested_pattern = "";
    }


    public String getTargeted_muscle() {
        return targeted_muscle;
    }

    public void setTargeted_muscle(String targeted_muscle) {
        this.targeted_muscle = targeted_muscle;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getImage_ref() {
        return image_ref;
    }

    public void setImage_ref(String image_ref) {
        this.image_ref = image_ref;
    }

    public String getSuggested_pattern() {
        return suggested_pattern;
    }

    public void setSuggested_pattern(String suggested_pattern) {
        this.suggested_pattern = suggested_pattern;
    }


}
