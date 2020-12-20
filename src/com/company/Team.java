package com.company;

import java.util.Collection;

public class Team {

    private String name;
    private double rate;

    public Team(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return this.name;
    }

    public double getRate() {
        return this.rate;
    }
}
