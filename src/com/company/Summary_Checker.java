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
        double profit = 0.0;
        int noContestDoubleBet = 0;
        int noContestClassicBet = 0;
        int drawWin = 0;

        for(String sport : this.sports) {

            System.out.println(sport);

            BufferedReader currentFile = null;
            try {
                currentFile = new BufferedReader(new FileReader(new File("Bets/" + sport + "Bet.txt")));
            } catch (FileNotFoundException e) {
                System.out.println("dsdsd");
                e.printStackTrace();
            }

            if (currentFile == null) {
                System.out.println("dsdsdsdsdsdsdsds");
                continue;
            }

            String line;

            while ((line = currentFile.readLine()) != null && line.length() > 0) {
                String[] parsed = line.split(";");

                System.out.println("som tu");

                count++;

                // obycajny zapas no contest
                if (parsed.length == 4) {
                    noContestClassicBet++;
                    profit += 2.0;
                }

                if (parsed.length == 5) {
                    // double bet no contest
                    if (parsed[2].equals("B3")) {
                        noContestDoubleBet++;
                        profit += 1.0;
                    }

                    // obyc kontrola
                    if (parsed[2].equals("B1") || parsed[2].equals("B2")) {
                        if (parsed[4].equals("P")) {
                            lose++;
                            profit -= 2.0;
                        }else if (parsed[4].equals("R")) {
                            draw++;
                            profit -= 2.0;
                        }else {
                            double v = Double.parseDouble(parsed[3]);
                            profit += (v - 2.0);
                            win++;
                            classicWin++;
                        }
                    }
                }

                double v;
                if (parsed.length == 6) {
                    if (parsed[2].equals("B3")) {
                        if (parsed[5].equals("R")) {
                            draw++;
                            profit -= 1.0;
                        } else if (parsed[5].equals("V1")) {
                            v = Double.parseDouble(parsed[3]);
//
                            profit += (v - 1.0);
                            win++;
                            doubleWin++;
                        } else {
                            v = Double.parseDouble(parsed[4]);
//
                            profit += (v - 1.0);
                            win++;
                            doubleWin++;
                        }
                    }
                } else if (parsed[2].equals("B4")) {
                    if (parsed[5].equals("V1")) {
                        v = Double.parseDouble(parsed[3]);
//
                        profit += (v - 1.0);
                        win++;
                        doubleWin++;
                    } else if (parsed[5].equals("VR")) {
                        v = Double.parseDouble(parsed[4]);
//
                        profit += (v - 1.0);
                        win++;
                        drawWin++;
                    } else {
                        lose++;
                        profit -= 1.0;
                    }
                } else if (parsed[2].equals("B5")){
                    if (parsed[5].equals("V2")) {
                        v = Double.parseDouble(parsed[3]);
//
                        profit += (v - 1.0);
                        win++;
                        doubleWin++;
                    } else if (parsed[5].equals("VR")) {
                        v = Double.parseDouble(parsed[4]);
//
                        profit += (v - 1.0);
                        win++;
                        drawWin++;
                    } else {
                        lose++;
                        profit -= 1.0;
                    }

                }

            }
        }


        BufferedWriter resultFile;

        System.out.println("konec");

        try {
            resultFile = new BufferedWriter(new FileWriter(new File("result.txt"), false));

            resultFile.write("No contest double bet: " + noContestDoubleBet + "\n");
            resultFile.write("No contest classic bet: " + noContestClassicBet + "\n");
            resultFile.write("No contest double bet payback: " + (noContestDoubleBet) + "\n");
            resultFile.write("No contest classic bet payback: " + (noContestClassicBet * 2) + "\n");
            resultFile.write("***********************\n");
            resultFile.write("All checked matches: " + count + "\n");
            resultFile.write("Win: " + win + "\n");
            resultFile.write("Lost: " + lose + "\n");
            resultFile.write("Draw win: " + drawWin + "\n");
            resultFile.write("Double bet win: " + doubleWin + "\n");
            resultFile.write("classic bet win: " + classicWin + "\n");
            resultFile.write("Profit: " + profit + "\n");


            resultFile.close();

        }catch (Exception ex) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }

    }

}
