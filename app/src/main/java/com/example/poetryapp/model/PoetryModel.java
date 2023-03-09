package com.example.poetryapp.model;

public class PoetryModel {
    int id;
    String poetry_data;
    String poet_name;
    String date_time;

    public PoetryModel(int id, String poetry_data, String poet_name, String date_time) {
        this.id = id;
        this.poetry_data = poetry_data;
        this.poet_name = poet_name;
        this.date_time = date_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoetry() {
        return poetry_data;
    }

    public void setPoetry(String poetry_data) {
        this.poetry_data = poetry_data;
    }

    public String getPoetname() {
        return poet_name;
    }

    public void setPoetname(String poet_name) {
        this.poet_name = poet_name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
