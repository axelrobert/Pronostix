/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.nba.utils;

import fr.pronostix.nba.Team;
import java.util.HashMap;

/**
 *
 * @author François_2
 */
public class NBATeams {
    
    public static final HashMap<String, Team> teams = new HashMap<>();
    
    static {
        teams.put("Hawks", new Team("Atlanta", "Hawks", "ATL", null));
        teams.put("Celtics", new Team("Boston", "Celtics", "BOS", null));
        teams.put("Nets", new Team("Brooklyn", "Nets", "BKN", null));
        teams.put("Hornets", new Team("Charlotte", "Hornets", "CHA", null));
        teams.put("Bulls", new Team("Chicago", "Bulls", "CHI", null));
        teams.put("Cavaliers", new Team("Cleveland", "Cavaliers", "CLE", null));
        teams.put("Mavericks", new Team("Dallas", "Mavericks", "DAL", null));
        teams.put("Nuggets", new Team("Denver", "Nuggets", "DEN", null));
        teams.put("Pistons", new Team("Detroit", "Pistons", "DET", null));
        teams.put("Warriors", new Team("Golden State", "Warriors", "GSW", null));
        teams.put("Rockets", new Team("Houston", "Rockets", "HOU", null));
        teams.put("Pacers", new Team("Indiana", "Pacers", "IND", null));
        teams.put("Clippers", new Team("LA", "Clippers", "LAC", null));
        teams.put("Lakers", new Team("LA", "Lakers", "LAL", null));
        teams.put("Grizzlies", new Team("Memphis", "Grizzlies", "MEM", null));
        teams.put("Heat", new Team("Miami", "Heat", "MIA", null));
        teams.put("Bucks", new Team("Milwaukee", "Bucks", "MIL", null));
        teams.put("Timberwolves", new Team("Minnesota", "Timberwolves", "MIN", null));
        teams.put("Pelicans", new Team("New Orleans", "Pelicans", "NOP", null));
        teams.put("Knicks", new Team("New York", "Knicks", "NYK", null));
        teams.put("Thunder", new Team("Oklahoma City", "Thunder", "OKC", null));
        teams.put("Magic", new Team("Orlando", "Magic", "ORL", null));
        teams.put("76ers", new Team("Philadelphia", "Sixers", "PHI", null));
        teams.put("Suns", new Team("Phoenix", "Suns", "PHX", null));
        teams.put("Trail Blazers", new Team("Portland", "Blazers", "POR", null));
        teams.put("Kings", new Team("Sacramento", "Kings", "SAC", null));
        teams.put("Spurs", new Team("San Antonio", "Spurs", "SAS", null));
        teams.put("Raptors", new Team("Toronto", "Raptors", "TOR", null));
        teams.put("Jazz", new Team("Utah", "Jazz", "UTA", null));
        teams.put("Wizards", new Team("Washington", "Wizards", "WAS", null));
    }
    
}
