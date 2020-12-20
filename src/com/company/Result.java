package com.company;

import org.openqa.selenium.WebDriver;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Result {

    private int id;
    private String date;
    private String[] res;
    private String sportType;

    public Result(int id, String[] arr, String sportType) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.date = dtf.format(LocalDateTime.now());
        this.id = id;
        this.res = arr;
        this.sportType = sportType;
    }

    public String getSportType() {
        return this.sportType;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String[] getRes() {
        return res;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (String value : this.res) {
            st.append(value).append(", ");
        }

        return this.id + ";" + st;
    }
}
