/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.data.collector;

import fr.pronostix.nba.Game;
import fr.pronostix.nba.GameStatus;
import fr.pronostix.nba.Journey;
import fr.pronostix.nba.NBACalendar;
import fr.pronostix.nba.Team;
import fr.pronostix.nba.utils.DateUtils;
import fr.pronostix.nba.utils.NBATeams;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author François_2
 */
public class NBACalendarCollector {
    
    public static final int NBA_NUMBER_OF_GAMES_PER_TEAM = 82;

    private static final String ESPN_BASE_URI = 
            "http://scores.espn.go.com/nba/scoreboard?date=";

    //Constantes : dates de la saison, devront être changé à chaque saison
    public static final String REGULAR_SEASON_FIRST_DAY = "20141027";
    public static final String REGULAR_SEASON_LAST_DAY = "20150415";
    public static final String[] NO_GAMES_DAY = new String[]{
        "20141127",
        "20141224",
        "20150213",
        "20150214",
        "20150215",
        "20150216",
        "20150217",
        "20150218"};
    
    private List<String> getEspnDateCode() {
        List<String> nbaGamesDate = new ArrayList<>();
        String startingDate = REGULAR_SEASON_FIRST_DAY;
        do {
            startingDate = DateUtils.getNextDate(startingDate);
            if (DateUtils.checkDateOk(NO_GAMES_DAY, startingDate)) {
                nbaGamesDate.add(startingDate);
            }
        } while (!(startingDate).equals(REGULAR_SEASON_LAST_DAY));
        return nbaGamesDate;
    }

    private List<Element> extractGameHeadersFromJourney(Journey j) {
        Document doc = null;
        Elements el;

        boolean downloadSucceeded = false;
        while (!downloadSucceeded) {
            try {
                doc = Jsoup.connect(j.getUri()).get();
                downloadSucceeded = true;
            } catch (IOException ex) {
                System.out.println("\t[DOWNLOADING FAILED... : " +
                        j.getUri() + ". RESTARTING...]");
            }
        }
        el = doc.getElementsByClass("mod-content");
        List<Element> games = new ArrayList<>();
        for (Element e : el) {
            if (e.child(0).hasClass("game-header")) {
                games.add(e);
            }
        }
        return games;
    }

    private Game extractGameInformation(Element gameElement, String gameDate,
            HashMap<String, Team> nbaTeams) {
        String hour = gameElement.getElementsByClass("game-status").first().
                text();

        Element teamAwayEl = gameElement.getElementsByClass("away").first();
        String teamAway = teamAwayEl.getElementsByClass("team-name").text();
        Element teamHomeEl = gameElement.getElementsByClass("home").first();
        String teamHome = teamHomeEl.getElementsByClass("team-name").text();

        Team home = nbaTeams.get(teamHome);
        Team away = nbaTeams.get(teamAway);

        Game game = null;
        GameStatus status;

        if (hour.contains("Final")) {
            boolean overTime = gameElement.getElementsByClass("game-status").
                    first().text().contains("OT");
            int qtNumbers;
            if (overTime) {
                qtNumbers = 5;
                status = GameStatus.FINAL_OT;
            } else {
                qtNumbers = 4;
                status = GameStatus.FINAL;
            }
            Element teamAwayScoresEl = teamAwayEl.getElementsByClass("score").
                    first();
            int[] awayQuartersScores = new int[qtNumbers];
            int finalTeamAwayScore;
            for (int i = 0; i < qtNumbers; i++) {
                awayQuartersScores[i] = Integer.parseInt(teamAwayScoresEl.
                        child(i).text());
            }
            finalTeamAwayScore = Integer.parseInt(teamAwayScoresEl.child(5).
                    text());

            Element teamHomeScoresEl = teamHomeEl.getElementsByClass("score").
                    first();
            int[] homeQuartersScores = new int[qtNumbers];
            int finalTeamHomeScore;
            for (int i = 0; i < qtNumbers; i++) {
                homeQuartersScores[i] = Integer.parseInt(teamHomeScoresEl.
                        child(i).text());
            }
            finalTeamHomeScore = Integer.parseInt(teamHomeScoresEl.child(5).
                    text());

            game = new Game(away, home, finalTeamAwayScore, finalTeamHomeScore,
                    awayQuartersScores, homeQuartersScores, status,
                    gameDate);
        } else {
            //The game will be played
            if (!hour.contains("Postponed")) {
                if (DateUtils.checkIfGameBelongsToDate(gameDate, hour)) {
                    game = new Game(away, home, gameDate, 
                            DateUtils.convertETHourToUTC1(hour));
                } else {
                    game = new Game(away, home, DateUtils.getNextDate(gameDate), 
                            DateUtils.convertETHourToUTC1(hour));
                }
            } else {
                //The game is postponed
                game = new Game(away, home, gameDate);
            }
        }
        return game;
    }

    private TreeMap<String, Journey> extractESPNData(HashMap<String, Team> nbaTeams) {
        List<String> nbaGamesDates = getEspnDateCode();
        TreeMap<String, Journey> journeys = new TreeMap<>();

        //Preprare the uris to be used
        for (String date : nbaGamesDates) {
            String uri = ESPN_BASE_URI + date;
            journeys.put(date, new Journey(date, uri));
        }
        int id = 1;
        for (Entry<String, Journey> j : journeys.entrySet()) {
            //Download and extract html elements from uri
            System.out.println("EXTRACTING JOURNEY : " + j.getKey() + "... (" 
                    + id + "/" + journeys.size() +")");
            List<Element> games = extractGameHeadersFromJourney(j.getValue());
            //Extract games information
            for (Element game : games) {
                Game g = extractGameInformation(game, j.getKey(), nbaTeams);
                //Add the game to each nba team calendar
                g.getHome().addGame(j.getKey(), g);
                g.getAway().addGame(j.getKey(), g);
                //add the game to the journey
                j.getValue().addGame(g);
            }
            id ++;
        }
        return journeys;
    }

    private void printGameInfo(Game game) {
        int spaces = 4;
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        if (game.isOvertimeNeeded()) {
            System.out.println(" Q1 - Q2 - Q3 - Q4 - OT / F");
        } else {
            System.out.println(" Q1 - Q2 - Q3 - Q4 / F");
        }
        System.out.print(game.getAway().getNickname() + " ");
        for (int i = 0; i < game.getQtAwayScores().length; i++) {
            System.out.print(game.getQtAwayScores()[i]);
            if (i < game.getQtAwayScores().length - 1) {
                System.out.print(" - ");
            } else {
                System.out.print(" / ");
            }
        }
        System.out.println(game.getAwayScore());
        System.out.print(game.getHome().getNickname() + " ");
        for (int i = 0; i < game.getQtHomeScores().length; i++) {
            System.out.print(game.getQtHomeScores()[i]);
            if (i < game.getQtHomeScores().length - 1) {
                System.out.print(" - ");
            } else {
                System.out.print(" / ");
            }
        }
        System.out.println(game.getHomeScore());
    }

    public NBACalendar getNBACalendar() {

        HashMap<String, Team> nbaTeams = NBATeams.teams;
        //Check si le fichier existe;
        //Extraire les données du fichier
        //OU
        //Faire le parsage des donnés depuis le site, construire le fichier 
        //et le sauver
        //retourner le calendrier
        System.out.println("DOWNLOADING CALENDAR FROM "
                + "ESPN SITE...\n");
        TreeMap<String, Journey> jouneys = extractESPNData(nbaTeams);
        System.out.println("CALENDAR READY.\n");
        return new NBACalendar(jouneys, nbaTeams);
    }

}
