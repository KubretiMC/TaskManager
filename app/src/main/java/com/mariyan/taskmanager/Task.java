package com.mariyan.taskmanager;

import java.util.ArrayList;

public class Task {
    private Integer id;
    private String name;
    private String date;
    private String notes;

    public static ArrayList<Task> list = new ArrayList<>();

    public Task(int id, String name, String date, String notes) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes () {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

