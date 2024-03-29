package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MatchParser {

    private final By findAllSectionTags_xPath = new By.ByXPath("//div[@id='sport-events-list-content']/section[*]");
    private final By findAllTrTrTags_xPath = new By.ByXPath(".//tr[not(@style='display: none') and not(@class='running-live')]");
    private final By findAllInfoNumber = new By.ByXPath(".//span[@class='event-info-number']");
    private final By findAllTdTags = new By.ByXPath(".//td[*]");
    private final By findTheadTag_tagName = new By.ByTagName("thead");
    private final By findBodyTag_tagName = new By.ByTagName("tbody");
    private final By findTrTag_tagName = new By.ByTagName("tr");
    private final By findThTag_tagName = new By.ByTagName("th");
    private final By findSpanTag_tagName = new By.ByTagName("span");
    private final By findCheckBox = new By.ById("checkbox_show_info");



    private final ArrayList<String> allLinks;
    private final String date;

    private  WebDriver driver;
    private ArrayList<Match> matches = new ArrayList<>();
    private Hashtable<String, Integer>  matchesTableDates = new Hashtable<>();
    private Hashtable<String, ArrayList<Match>> matchesTable = new Hashtable<>();

    // How many matches
    private int amount = 0;

    public MatchParser(ArrayList<String> allSports) {
        this.allLinks = allSports;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.date = dtf.format(LocalDateTime.now().plusDays(1));
    }

    private void sortMatches() {
        this.matches.sort(Comparator.comparing(Match::getDate));
    }

    /**
     * Main logic.
     * Parsing matches from given driver.
     */

    public void parsing() {

        for(String sport : this.allLinks) {

            this.driver = new FirefoxDriver();


            String link = "https://www.ifortuna.sk/stavkovanie/" + sport + "?date=" + this.date;


            this.driver.get(link);

            String url = this.driver.getCurrentUrl();

            System.out.println("current link: " + url);
            System.out.println("drive: " + link);

            if (!url.equals(link)) {
                System.out.println("nezhoda");
                this.driver.close();
                continue;
            }

            this.waitForLoad();

            WebElement checkBox = this.driver.findElement(findCheckBox);

            checkBox.click();

            this.initializeWebsite();


            List<WebElement> allSections = driver.findElements(findAllSectionTags_xPath);

            ArrayList<WebElement> arrSections = new ArrayList<>(allSections);


            for (WebElement section : arrSections) {

                WebElement thead = section.findElement(findTheadTag_tagName);

                WebElement trTag = thead.findElement(findTrTag_tagName);

                List<WebElement> allThTags = trTag.findElements(findThTag_tagName);

                ArrayList<WebElement> arrTh = new ArrayList<>(allThTags);

                if (arrTh.size() < 4) continue;

                if (!arrTh.get(1).getText().equals("1") ||
                        !arrTh.get(2).getText().equals("0") ||
                        !arrTh.get(3).getText().equals("2")) {
                    continue;
                }

                WebElement tbody = section.findElement(findBodyTag_tagName);

                List<WebElement> allTrTags = tbody.findElements(findAllTrTrTags_xPath);

                ArrayList<WebElement> arrTr = new ArrayList<>(allTrTags);


                for (WebElement tr : arrTr) {
                    List<WebElement> allTd = tr.findElements(findAllTdTags);

                    ArrayList<WebElement> arrTd = new ArrayList<>(allTd);

                    WebElement firstTd = arrTd.get(0);


                    String spanInfoNumber = firstTd.findElement(findAllInfoNumber).getText();

                    String[] teams = firstTd.findElement(findSpanTag_tagName).getText().split("-");

                    double firstTeamRate = Double.parseDouble(arrTd.get(1).getText());

                    // draw
                    double drawRate = Double.parseDouble(arrTd.get(2).getText());

                    double secondTeamsRate = Double.parseDouble(arrTd.get(3).getText());

                    Team team1 = new Team(teams[0], firstTeamRate);
                    Team team2 = new Team(teams[1], secondTeamsRate);

                    String dateStr = arrTd.get( arrTd.size() - 1).getText();

                    LocalDateTime date = this.parseDate(dateStr);

                    int infoNumber = Integer.parseInt(spanInfoNumber);

                    Match match = new Match(team1, team2, date, sport, infoNumber, drawRate);

                    this.addToHashTableMatches(match);

                    this.matches.add(match);

                    this.amount++;

                }

            }
            this.driver.close();
        }

        this.sortMatches();

        this.sortHashTable();
    }

    private void sortHashTable() {
        for(Map.Entry<String, ArrayList<Match>> entry : this.matchesTable.entrySet()) {
            entry.getValue().sort(Comparator.comparing(Match::getDate));
        }
    }

    private void addToHashTableMatches(Match match) {

        if ( this.matchesTable.get(match.getSportType()) == null) {
            this.matchesTable.put(match.getSportType(), new ArrayList<>());

            ArrayList<Match> arr = this.matchesTable.get(match.getSportType());

            arr.add(match);

        }else {
            ArrayList<Match> arr = this.matchesTable.get(match.getSportType());

            arr.add(match);
        }
    }

    public Hashtable<String, ArrayList<Match>> getMatchesTable() {
        return this.matchesTable;
    }

    private void printHashTable() {
        for (Map.Entry<String, ArrayList<Match>> entry : this.matchesTable.entrySet()) {
            int count = 0;
            //System.out.println("SPORT: " + entry.getKey());

            count += entry.getValue().size();

            for(Match match : entry.getValue()) {
                System.out.println(match.toString());
            }

            System.out.println("Korekcia " + entry.getKey() + " Pocet: " + count);

            System.out.println("***********************************************************");
        }

    }

    private LocalDateTime parseDate(String dateStr) {

        LocalDateTime cal;

        String[] DateAndTime = dateStr.replace(" ", "").split("\\.");
        int year = LocalDate.now().getYear();
        int day = Integer.parseInt(DateAndTime[0]);

        int month = Integer.parseInt(DateAndTime[1]);
        int hours = Integer.parseInt(DateAndTime[2].split(":")[0]);
        int minutes = Integer.parseInt(DateAndTime[2].split(":")[1]);


        this.addToHashTable("" + day + "." + month);


        cal = LocalDateTime.of(year, month, day, hours, minutes);

        return cal;

    }

    private void addToHashTable(String giveKey) {

        this.matchesTableDates.merge(giveKey, 1, Integer::sum);
    }

    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    private void printAllMatches() {
        for (Match match : this.matches) {
            //if (match.getSportType().equals("hadzana")) System.out.println(match.toString());
            System.out.println(match.toString());
        }
        System.out.println("Sum: " + this.matches.size());
    }

    /**
     * After every scroll program has to wait for 6 seconds. Just in case of error.
     */

    private void waitForLoad() {

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Scroll all the way down
     */

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
