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

    private int doubleBet = 0;
    private int classicBet = 0;

    private BufferedWriter currentFile;

    public Bet(Hashtable<String, ArrayList<Match>> matches) {
        this.matches = matches;

        System.out.println("VELKOST: " + this.matches.size());
    }


    private void writeToFile(int betType, Match match, double value1, double value2) {

        try {
            this.currentFile.write(match.toString() + ";" + match.getId() + ";B" + betType + ";" + value1 + ";" + value2 + "\n");
        } catch (IOException e) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }

    }

    private void writeToFile(int betType, Match match, double value1) {

        try {
            this.currentFile.write(match.toString() + ";" + match.getId() + ";B" + betType + ";" + value1 + "\n");
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
                double drawRate = match.getDrawRate();


                if (team1.getRate() < 2) {
                    this.totalBet += 2;
                    this.classicBet++;

                    double value1 = Math.round((team1.getRate() * 2.0) * 100.0) / 100.0;

                    profit1 += value1;

                    this.writeToFile( 1, match, value1);

                } else if (team2.getRate() < 2) {
                    this.totalBet += 2;
                    this.classicBet++;

                    double value2 = Math.round((team2.getRate() * 2.0) * 100.0) / 100.0;

                    profit2 += value2;

                    this.writeToFile( 2, match, value2);

                } else {
                    double min1;
                    double min2;
                    this.doubleBet++;
                    this.totalBet += 1;

                    if (team1.getRate() < team2.getRate()) {
                        min1 = team1.getRate();

                        if (team2.getRate() < drawRate) {

                            min2 = team2.getRate();

                            double value1 = Math.round((min1 * 0.5) * 100.0) / 100.0;
                            double value2 = Math.round((min2 * 0.5) * 100.0) / 100.0;

                            profit1 += value1;
                            profit2 += value2;


                            this.writeToFile(3, match, value1, value2);

                        } else {

                            min2 = drawRate;

                            double value1 = Math.round((min1 * 0.5) * 100.0) / 100.0;
                            double value2 = Math.round((min2 * 0.5) * 100.0) / 100.0;

                            profit1 += value1;
                            profit2 += value2;

                            this.writeToFile(4, match, value1, value2);
                        }

                    } else {
                        min1 = team2.getRate();

                        if (team1.getRate() < drawRate) {
                            min2 = team1.getRate();

                            double value1 = Math.round((min1 * 0.5) * 100.0) / 100.0;
                            double value2 = Math.round((min2 * 0.5) * 100.0) / 100.0;

                            profit1 += value1;
                            profit2 += value2;

                            this.writeToFile(3, match, value1, value2);
                        } else {

                            min2 = drawRate;

                            double value1 = Math.round((min1 * 0.5) * 100.0) / 100.0;
                            double value2 = Math.round((min2 * 0.5) * 100.0) / 100.0;

                            profit1 += value1;
                            profit2 += value2;

                            this.writeToFile(5, match, value1, value2);
                        }
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

        System.out.println("PR1: " + profit1);
        System.out.println("PR2: " + profit2);

        try {
            this.currentFile = new BufferedWriter(new FileWriter(new File("summary.txt"), false));

            this.currentFile.write("Celkovo vsadene: " + this.totalBet + "\n");


            double sum1 = Math.round((profit1 - this.totalBet) * 100.0) / 100.0;
            double sum2 = Math.round((profit2 - this.totalBet) * 100.0) / 100.0;

            this.currentFile.write("konecny vyhra: " + sum1 + " alebo " + sum2 + "\n");

            this.currentFile.write("Dvojity bet: " + this.doubleBet + "\n");
            this.currentFile.write("Klasicky bet: " + this.classicBet + "\n");

            this.currentFile.close();

        }catch (Exception ex) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }

    }
}
