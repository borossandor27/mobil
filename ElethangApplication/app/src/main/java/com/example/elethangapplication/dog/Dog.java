package com.example.elethangapplication.dog;

import com.google.gson.annotations.SerializedName;

public class Dog {
    private int id;
    @SerializedName("name")
    private String dogName;
    @SerializedName("description")
    private String dogDescription;

    public Dog( String dogName, String dogDescription) {
        this.dogName = dogName;
        this.dogDescription = dogDescription;
    }

    public int getId() {
        return id;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogDescription() {
        return dogDescription;
    }

    public void setDogDescription(String dogDescription) {
        this.dogDescription = dogDescription;
    }
}
