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
        MatchParser parser = new MatchParser(allSports);

//        Summary_Checker summary_checker = new Summary_Checker(allSports);
//
//        summary_checker.parse();

//        ResultParser resultParser = new ResultParser(allSports);
//
//        resultParser.parsing();
//        resultParser.writeToFile();

//        FinalCheck finalCheck = new FinalCheck(allSports);
//
//        finalCheck.checkResults();

//
        parser.parsing();
////
////
        Hashtable<String, ArrayList<Match>> matches = parser.getMatchesTable();
//
//
        Bet bet = new Bet(matches);

        bet.calculate();



    }
}
