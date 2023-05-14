package com.example.elethangapplication.cat;

import com.google.gson.annotations.SerializedName;

public class Cat {
    private int id;
    @SerializedName("name")
    private String catName;
    private String description;

    public Cat(int id, String catName, String description) {
        this.catName = catName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
