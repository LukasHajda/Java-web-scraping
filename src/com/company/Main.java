package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> allSports = new ArrayList<>();
        allSports.add("futbal");
        allSports.add("hokej");
        allSports.add("basketbal");
        allSports.add("hadzana");
        allSports.add("americky-futbal");

        MatchParser parser = new MatchParser(allSports);

//        ResultParser resultParser = new ResultParser(allSports);
//
//        resultParser.parsing();

//        ArrayList<Result> results = resultParser.getAllResults();
//
//        System.out.println(results.size());
//
//        BufferedReader currentFile = null;
//        try {
//            currentFile = new BufferedReader(new FileReader(new File("Bets/ff.txt")));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        String line;
//
//        if (currentFile == null) return;
//
//        int count = 0;
//        int winCount = 0;
//        try {
//
//            while ((line = currentFile.readLine()) != null && line.length() > 0) {
//                //System.out.println(line);
//                count++;
//                String[] parsed = line.split(";");
//                int id = Integer.parseInt(parsed[0]);
//
//                String betstr = parsed[3].split(" ")[2];
//
//                int bet = Integer.parseInt(betstr);
//                //System.out.println("ID: " + id);
//
//                for(Result result : results) {
//                    if (result.getId() == id) {
//                        if (bet == 3) {
//                            if ((Integer.parseInt(result.getRes()[0]) == 1 || Integer.parseInt(result.getRes()[0]) == 2) ||
//                                    (Integer.parseInt(result.getRes()[1]) == 1 || Integer.parseInt(result.getRes()[1]) == 2) ||
//                                    (Integer.parseInt(result.getRes()[2]) == 1 || Integer.parseInt(result.getRes()[2]) == 2)) {
//                                System.out.println("win id: " + id);
//                                winCount++;
//                                break;
//                            }
//                        }
//                    }
//                }
//
//            }
//
//
//
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        System.out.println("Count wins: " + winCount);
//        System.out.println("pocet podanych: " + count);


        parser.parsing();


        Hashtable<String, ArrayList<Match>> matches = parser.getMatchesTable();


        System.out.println("IDEEEEEEEm");

//        for (Map.Entry<String, ArrayList<Match>> entry : matches.entrySet()) {
//            int count = 0;
//            //System.out.println("SPORT: " + entry.getKey());
//
//            count += entry.getValue().size();
//
//            for(Match match : entry.getValue()) {
//                System.out.println(match.toString());
//            }
//
//            System.out.println("Korekcia " + entry.getKey() + " Pocet: " + count);
//
//            System.out.println("***********************************************************");
//        }


        Bet bet = new Bet(matches);

        bet.calculate();



    }
}
