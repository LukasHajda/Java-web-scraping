package com.company;

import java.io.*;
import java.util.ArrayList;

public class Summary_Checker {


    private ArrayList<String> sports;
    public Summary_Checker(ArrayList<String> sports) {
        this.sports = sports;
    }


    public void parse() throws IOException {

        int draw = 0;
        int win = 0;
        int doubleWin = 0;
        int classicWin = 0;
        int lose = 0;
        int count = 0;
        int noContest = 0;
        double profit = 0.0;

        for(String sport : this.sports) {

            System.out.println(sport);

            BufferedReader currentFile = null;
            try {
                currentFile = new BufferedReader(new FileReader(new File("Bets/" + sport + "Bet.txt")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (currentFile == null) continue;

            String line;

            while ((line = currentFile.readLine()) != null && line.length() > 0) {
                String[] parsed = line.split(";");

                count++;

                // obycajny zapas no contest
                if (parsed.length == 3) {
                    noContest++;
                    //profit += 2.0;
                }

                if (parsed.length == 4) {
                    // double bet no contest
                    if (parsed[1].equals("B3")) {
                        noContest++;
                        //profit += 4.0;
                    }

                    // obyc kontrola
                    if (parsed[1].equals("B1") || parsed[1].equals("B2")) {
                        if (parsed[3].equals("P")) {
                            lose++;
                            profit -= 2.0;
                        }else if (parsed[3].equals("R")) {
                            draw++;
                            profit -= 2.0;
                        }else {
                            double v = Double.parseDouble(parsed[2]);
                            profit += (v - 2.0);
                            win++;
                            classicWin++;
                        }
                    }
                }

                double v;
                if (parsed.length == 5) {
                    if (parsed[4].equals("R")) {
                        draw++;
                        profit -= 1.0;
                    }else if (parsed[4].equals("V1")) {
                        v = Double.parseDouble(parsed[2]);

                        profit += (v - 1.0);
                        win++;
                        doubleWin++;
                    }else {
                        v = Double.parseDouble(parsed[3]);

                        profit += (v - 1.0);
                        win++;
                        doubleWin++;
                    }
                }

            }
        }


        System.out.println("Profit: " + profit);
        System.out.println("draws: " + draw);
        System.out.println("wins: " + win);
        System.out.println("lost: " + lose);
        System.out.println("No contest: " + noContest);
        System.out.println("Double bet: " + doubleWin);
        System.out.println("Classic: " + classicWin);
        System.out.println("All: " + count);


    }



}
