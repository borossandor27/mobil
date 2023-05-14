package com.example.elethangapplication.program;

public class Program {
    private int id;
    private String type;
    private String date;
    private String time;

    public Program(int id, String type, String date, String time) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
