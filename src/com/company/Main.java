package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<String> allSports = new ArrayList<>();
        allSports.add("futbal");
        allSports.add("hokej");
        allSports.add("basketbal");
        allSports.add("hadzana");
        allSports.add("americky-futbal");
//
//        MatchParser parser = new MatchParser(allSports);

//        Summary_Checker summary_checker = new Summary_Checker(allSports);
//
//        summary_checker.parse();

        ResultParser resultParser = new ResultParser(allSports);

        resultParser.parsing();
        System.out.println("hash");
        resultParser.writeToFile();

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

//
//        parser.parsing();
//
//
//        Hashtable<String, ArrayList<Match>> matches = parser.getMatchesTable();
//
//
//        Bet bet = new Bet(matches);
//
//        bet.calculate();



    }
}
