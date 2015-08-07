package edu.psu.ist402.projectfittness;

/**
 * Stores data for a user
 */
public class User {
    String name;
    String birthdate;
    float height;
    float weight;

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

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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

}
