package com.company;

import java.time.LocalDateTime;


public class Match{

    private Team team1;
    private Team team2;
    private LocalDateTime date;
    private String sportType;
    private int id;
    // 1 -> bet for first team
    // 2 -> bet for second team
    // 3 -> bet for both team
    private int bet;

    public Match(Team team1, Team team2, LocalDateTime date, String sportType, int id) {
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.id = id;
        this.sportType = sportType;

    }

    @Override
    public String toString() {
        return this.team1.getName() + " (" + this.team1.getRate() + ") VS " + this.team2.getName() + " (" + this.team2.getRate() + ")";
    }

    public int getId() {
        return this.id;
    }

    public String getSportType() {
        return sportType;
    }

    public Team getTeam1() {
        return this.team1;
    }

    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Team getTeam2() {
        return this.team2;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
