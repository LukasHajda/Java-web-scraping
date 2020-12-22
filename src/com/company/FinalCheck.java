package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinalCheck {

    private BufferedReader currentBetFile;
    private BufferedReader currentResultFile;

    private ArrayList<String> allSports;

    private ArrayList<String> betLines = new ArrayList<>();
    private ArrayList<String> resultLines = new ArrayList<>();

    public FinalCheck(ArrayList<String> sports) {
        this.allSports = sports;
    }


    private void check(String sport) throws IOException {

        BufferedWriter betRewrite = null;
        try {
            betRewrite = new BufferedWriter(new FileWriter(new File("Bets/" + sport + "Bet.txt"), false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (betRewrite == null) return;


        System.out.println("SPORT: " + sport);
        for (String bet : this.betLines) {

            String[] parsedBet = bet.split(";");
            String idB = parsedBet[0];
            String typeB = parsedBet[1];

            for (String result : this.resultLines) {

                String[] parsedRes = result.split(";");
                String idR = parsedRes[0];

                if (idB.equals(idR)) {
                    Pattern r = Pattern.compile("(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*");

                    // Now create matcher object.
                    Matcher m = r.matcher(parsedRes[1]);

                    if (m.find( )) {
                        if (typeB.equals("B1")) {
                            if (m.group(1).equals("1") || m.group(2).equals("1") || m.group(3).equals("1")) {
                                betRewrite.write(bet + ";V1" + "\n");
                            }
                        } else if (typeB.equals("B2")) {
                            if (m.group(1).equals("2") || m.group(2).equals("2") || m.group(3).equals("2")) {
                                betRewrite.write(bet + ";V2" + "\n");
                            }
                        }else {
                            if (m.group(1).equals("2") || m.group(2).equals("2") || m.group(3).equals("2")) {
                                betRewrite.write(bet + ";V2" + "\n");
                            }

                            if (m.group(1).equals("1") || m.group(2).equals("1") || m.group(3).equals("1")) {
                                betRewrite.write(bet + ";V1" + "\n");
                            }
                        }
                    }
                }



            }
        }

        try {
            betRewrite.close();
        } catch (IOException e) {
            System.out.println("Something went wrong with writing");
            System.exit(1);
        }
    }


    public void checkResults() throws IOException {

        for(String sport : this.allSports) {

            try {
                this.currentBetFile = new BufferedReader(new FileReader(new File("Bets/" + sport + "Bet.txt")));
                this.currentResultFile = new BufferedReader(new FileReader(new File("Result/" + sport + "Res.txt")));
            }catch (Exception ex) {
                System.out.println("Something went wrong with writing");
                System.exit(1);
            }

            // Load all records from Bet
            String line;
            while ((line = this.currentBetFile.readLine()) != null && line.length() > 0) {
                this.betLines.add(line);
            }

            this.currentBetFile.close();

            // Load all record from Result
            while ((line = this.currentResultFile.readLine()) != null && line.length() > 0) {
                this.resultLines.add(line);
            }

            this.currentResultFile.close();

            this.check(sport);

            this.resultLines.clear();
            this.betLines.clear();

        }

    }
}
