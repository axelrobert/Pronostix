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
import fr.pronostix.nba.utils.NBATeams;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author François_2
 */
public class NBACalendarReader {

    private static String getKeyFromNickname(HashMap<String, Team> nbaTeams,
            String nickname) {
        for (Entry<String, Team> team : nbaTeams.entrySet()) {
            if (team.getValue().getNickname().equals(nickname)) {
                return team.getKey();
            }
        }
        return null;
    }

    public static boolean nbaCalendarFileExists() {
        File nbaCalendarFile = new File(NBACalendar.NBA_CALENDAR_FILE);
        return nbaCalendarFile.isFile();
    }

    public static NBACalendar readNBACalendar()
            throws ParserConfigurationException, SAXException, IOException {
        HashMap<String, Team> nbaTeams = NBATeams.teams;
        TreeMap<String, Journey> journeys = new TreeMap<>();

        File fXmlFile = new File(NBACalendar.NBA_CALENDAR_FILE);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        NodeList nJourneysList = doc.getElementsByTagName("journey");

        for (int i = 0; i < nJourneysList.getLength(); i++) {

            Node nJourney = nJourneysList.item(i);
            if (nJourney.getNodeType() == Node.ELEMENT_NODE) {
                String date;
                String uri;
                List<Game> games = new ArrayList<>();
                Element eJourney = (Element) nJourney;

                date = eJourney.getAttribute("americanDate");
                uri = eJourney.getAttribute("source");
                NodeList nGamesList = eJourney.getElementsByTagName("game");
                for (int j = 0; j < nGamesList.getLength(); j++) {
                    Game g;
                    Team home;
                    Team away;
                    GameStatus status = null;
                    String gameDate;
                    String hour;
                    Node nGame = nGamesList.item(j);
                    if (nGame.getNodeType() == Node.ELEMENT_NODE) {
                        Element eGame = (Element) nGame;
                        home = nbaTeams.get(getKeyFromNickname(nbaTeams, eGame.getAttribute("homeTeam")));
                        away = nbaTeams.get(getKeyFromNickname(nbaTeams, eGame.getAttribute("awayTeam")));
                        gameDate = eGame.getAttribute("frenchDate");
                        String sStatus = eGame.getAttribute("status");
                        switch (sStatus) {
                            case "To be played":
                                status = GameStatus.TO_BE_PLAYED;
                                break;
                            case "Final":
                                status = GameStatus.FINAL;
                                break;
                            case "Final/OT":
                                status = GameStatus.FINAL_OT;
                                break;
                            case "Postponed":
                                status = GameStatus.POSTPONED;
                                break;
                        }
                        if (status == GameStatus.TO_BE_PLAYED) {
                            hour = eGame.getAttribute("utcHour");
                            g = new Game(away, home, gameDate, hour);
                            games.add(g);
                            away.addGame(date, g);
                            home.addGame(date, g);
                        } else if (status == GameStatus.POSTPONED) {
                            g = new Game(away, home, gameDate);
                            games.add(g);
                            away.addGame(date, g);
                            home.addGame(date, g);
                        } else {
                            NodeList nScoreList = eGame.getElementsByTagName("score");
                            for (int l = 0; l < nScoreList.getLength(); l++) {
                                Node nScoreNode = nScoreList.item(l);
                                if (nScoreNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eScoreEl = (Element) nScoreNode;
                                    int awayScore = Integer.parseInt(eScoreEl.getAttribute("awayScore"));
                                    int homeScore = Integer.parseInt(eScoreEl.getAttribute("homeScore"));

                                    NodeList nQtsList = eScoreEl.getElementsByTagName("qt");
                                    int[] awayQtScores = new int[nQtsList.getLength()];
                                    int[] homeQtScores = new int[nQtsList.getLength()];
                                    for (int k = 0; k < nQtsList.getLength(); k++) {
                                        Node nQtNode = nQtsList.item(k);
                                        if (nQtNode.getNodeType() == Node.ELEMENT_NODE) {

                                            Element eQtEl = (Element) nQtNode;
                                            awayQtScores[k] = Integer.parseInt(eQtEl.getAttribute("awayScore"));
                                            homeQtScores[k] = Integer.parseInt(eQtEl.getAttribute("homeScore"));
                                        }

                                    }
                                    g = new Game(away, home, awayScore, homeScore, awayQtScores, homeQtScores, status, gameDate);
                                    games.add(g);
                                    away.addGame(date, g);
                                    home.addGame(date, g);
                                }
                            }

                        }
                    }
                }
                Journey journey = new Journey(date, uri, games);
                journeys.put(date, journey);
            }
        }
        return new NBACalendar(journeys, nbaTeams);
    }

}
