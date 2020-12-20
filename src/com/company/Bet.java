package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Bet {

    private Hashtable<String, ArrayList<Match>> matches;
    private int totalBet = 0;

    private BufferedWriter currentFile;

    public Bet(Hashtable<String, ArrayList<Match>> matches) {
        this.matches = matches;
    }


    private void writeToFile(int betType, Match match, double value) {

        try {
            this.currentFile.write(match.getId() + ";B" + betType + ";" + value + "\n");
        } catch (IOException e) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }

    }

    private void writeToFile(int betType, Match match, double value1, double value2) {

        try {
            this.currentFile.write(match.getId() + ";B" + betType + ";" + value1 + ";" + value2 + "\n");
        } catch (IOException e) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }

    }


    public void calculate() {

        double profit1 = 0;
        double profit2 = 0;

        for (Map.Entry<String, ArrayList<Match>> entry : this.matches.entrySet()) {

            String sportType = entry.getKey();

            try {
                this.currentFile = new BufferedWriter(new FileWriter(new File("Bets/" + sportType + "Bet.txt"), false));
            }catch (Exception ex) {
                System.out.println("Something went wrong with writing");
                System.exit(1);
            }

            for (Match match : entry.getValue()) {

                Team team1 = match.getTeam1();
                Team team2 = match.getTeam2();

                if (team1.getRate() >= 2.00 && team2.getRate() >= 2.00) {
                    this.totalBet += 4;

                    match.setBet(3);

                    double value1 = Math.round((team1.getRate() * 2) * 100.0) / 100.0;
                    double value2 = Math.round((team2.getRate() * 2) * 100.0) / 100.0;

                    this.writeToFile( 3, match, value1, value2);

                    profit1 += value1;
                    profit2 += value2;

                }else {

                    double diff = Math.abs(team1.getRate() - team1.getRate());

                    if (diff < 2.00) continue;

                    this.totalBet += 2;


                    if (team1.getRate() > team2.getRate()) {

                        match.setBet(2);

                        double v2 = Math.round((team2.getRate() * 2) * 100.0) / 100.0;

                        this.writeToFile( 2, match, v2);

                        profit1 += v2;
                        profit2 += v2;
                    } else {

                        match.setBet(1);

                        double v1 = Math.round((team1.getRate() * 2) * 100.0) / 100.0;

                        this.writeToFile( 1, match, v1);

                        profit1 += v1;
                        profit2 += v1;
                    }
                }


            }

            try {
                this.currentFile.close();
            } catch (IOException e) {
                System.out.println("Something went wrong with writing");
                System.exit(1);
            }
        }

        try {
            this.currentFile = new BufferedWriter(new FileWriter(new File("summary.txt"), false));

            this.currentFile.write("Celkovo vsadene: " + this.totalBet + "\n");


            double sum1 = Math.round((profit1 - this.totalBet) * 100.0) / 100.0;
            double sum2 = Math.round((profit2 - this.totalBet) * 100.0) / 100.0;

            this.currentFile.write("konecny vyhra: " + sum1 + " alebo " + sum2);

            this.currentFile.close();

        }catch (Exception ex) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }

    }
}