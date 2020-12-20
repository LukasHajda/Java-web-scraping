package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResultParser {

    //"//div[@class='ui-box ui-box--no-padding']"
    //$x("//table[@class='table events-table show-info-numbers'] | //table[@class='table events-table show-info-numbers tablesorter tablesorter-default']")
    private final By findAllResultBlocks_xPath = new By.ByXPath("//table[@class='table events-table show-info-numbers'] | //table[@class='table events-table show-info-numbers tablesorter tablesorter-default']");
    private final By findAllTrTags = new By.ByXPath(".//tr[not(@style='display: none')]");
    private final By findTbody = new By.ByXPath("tbody");
    private final By findTdTag_tagName = new By.ByTagName("td");
    private final By findId = new By.ByClassName("event-info-number");
    private ArrayList<Result> allResults = new ArrayList<>();

    private  String date;

    private ArrayList<String> allSports;
    private WebDriver driver;

    public ResultParser(ArrayList<String> allSports) {
        this.allSports = allSports;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.date = dtf.format(LocalDateTime.now().minusDays(1));

        System.out.println(this.date);
    }

    public void parsing() {

//        int matches = 0;
//        int draw = 0;
//
//        for(String sport : this.allSports) {
//
//
//            int day_count = 1;
//
//            while (day_count++ != 120) {
//
//                String link = "https://www.ifortuna.sk/stavkovanie/vysledky/" + sport  + "/" + this.date;
//
//                this.driver = new FirefoxDriver();
//
//                this.driver.get(link);
//
//                this.waitForLoad();
//
//                this.initializeWebsite();
//
//                List<WebElement> allTables = driver.findElements(findAllResultBlocks_xPath);
//
//                ArrayList<WebElement> arrTables = new ArrayList<>(allTables);
//
//                for(WebElement table : arrTables) {
//
//                    WebElement tBody = table.findElement(findTbody);
//
//                    List<WebElement> allTrTags = tBody.findElements(findAllTrTags);
//                    ArrayList<WebElement> arrTrTags = new ArrayList<>(allTrTags);
//
//                    for(WebElement tr : arrTrTags) {
//
//                        matches++;
//
//                        List<WebElement> allTdTags = tr.findElements(findTdTag_tagName);
//                        ArrayList<WebElement> arrTdTags = new ArrayList<>(allTdTags);
//
//                        WebElement idTag = arrTdTags.get(0).findElement(findId);
//
//                        int id = Integer.parseInt(idTag.getText());
//
//                        String[] results = arrTdTags.get(1).getText().split(",");
//
//
//                        if (results.length < 3) continue;
//
//                        if (results[0].equals("0") || results[1].equals("0") || results[2].equals("0")) draw++;
//
//
////                        Result result = new Result(id, results);
////
////                        this.allResults.add(result);
//
//
//                    }
//
//                }
//
//                this.driver.close();
//
//                this.date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now().minusDays(day_count));
//
//            }


            String link = "https://www.ifortuna.sk/stavkovanie/vysledky/" + sport  + "/" + this.date;

            this.driver.get(link);

            this.waitForLoad();

            this.initializeWebsite();

            List<WebElement> allTables = driver.findElements(findAllResultBlocks_xPath);

            ArrayList<WebElement> arrTables = new ArrayList<>(allTables);

            for(WebElement table : arrTables) {

                WebElement tBody = table.findElement(findTbody);

                List<WebElement> allTrTags = tBody.findElements(findAllTrTags);
                ArrayList<WebElement> arrTrTags = new ArrayList<>(allTrTags);

                for(WebElement tr : arrTrTags) {

                    List<WebElement> allTdTags = tr.findElements(findTdTag_tagName);
                    ArrayList<WebElement> arrTdTags = new ArrayList<>(allTdTags);

                    WebElement idTag = arrTdTags.get(0).findElement(findId);

                    int id = Integer.parseInt(idTag.getText());

                    String[] results = arrTdTags.get(1).getText().split(",");


                    if (results.length < 3) continue;


                    Result result = new Result(id, results);

                    this.allResults.add(result);


                }

            }

            this.driver.close();

        }

        //this.printResults();

    }


    private void printResults() {
        for(Result result : this.allResults) {
            System.out.println(result.toString());
        }

        System.out.println(this.allResults.size());
    }

    public ArrayList<Result> getAllResults() {
        return this.allResults;
    }

    private void waitForLoad() {

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initializeWebsite() {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;


        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        int actualPosition = Integer.parseInt(js.executeScript("return window.pageYOffset;").toString());
        int previousPosition = 0;

        this.waitForLoad();

        while ( actualPosition != previousPosition) {

            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            previousPosition = actualPosition;

            actualPosition = Integer.parseInt(js.executeScript("return window.pageYOffset;").toString());

            this.waitForLoad();

        }

        // Now we know there are no more sports matches for particular sport.
    }
}