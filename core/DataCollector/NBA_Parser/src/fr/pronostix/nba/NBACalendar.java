/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.nba;

import static fr.pronostix.data.collector.NBACalendarCollector.NBA_NUMBER_OF_GAMES_PER_TEAM;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author François_2
 */
public class NBACalendar {
    
    public static final String NBA_CALENDAR_FILE = 
            "./nba-regular_season-2015.xml";
    
    private TreeMap<String, Journey> journeys;
    private HashMap<String, Team> teams;
    
    public NBACalendar(TreeMap<String, Journey> journeys, 
            HashMap<String, Team> teams) {
        this.journeys = journeys;
        this.teams = teams;
        
    }

    public TreeMap<String, Journey> getJourneys() {
        return journeys;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }
    
    public boolean dataSanityChecked() {
        for (Map.Entry<String, Team> entry : teams.entrySet()) {
            Team team = entry.getValue();
            if (team.getNumberOfGamesPlayed() != NBA_NUMBER_OF_GAMES_PER_TEAM) {
                return false;
            }
        }
        return true;
    }
    
}
