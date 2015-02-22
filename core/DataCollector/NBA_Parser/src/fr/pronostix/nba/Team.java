/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.nba;

import com.sun.jndi.toolkit.url.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author François_2
 */
public class Team {

    private String city;
    private String name;
    private String nickname;
    private Uri logo;

    private TreeMap<String, Game> games;

    private int wins;
    private int loss;

    public Team(String city, String name, String nickname, Uri logo) {
        this.city = city;
        this.name = name;
        this.logo = logo;
        this.nickname = nickname;
        this.wins = 0;
        this.loss = 0;
        this.games = new TreeMap<String, Game>();
    }

    public void addGame(String date, Game game) {
        games.put(date, game);
    }

    @Override
    public String toString() {
        return nickname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Uri getLogo() {
        return logo;
    }

    public void setLogo(Uri logo) {
        this.logo = logo;
    }

    public TreeMap<String, Game> getGames() {
        return games;
    }

    public void setGames(TreeMap<String, Game> games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }
    
    public int getNumberOfGamesPlayed() {
        int numberOfGames = 0;
        for(Map.Entry<String, Game> entry : games.entrySet()) {
            Game g = entry.getValue();
            if (!g.isPostponed()) {
                numberOfGames++;
            }
        }
        return numberOfGames;
    }
    
    public int getNumberOfGamesPostponed() {
        int numberOfGames = 0;
        for(Map.Entry<String, Game> entry : games.entrySet()) {
            Game g = entry.getValue();
            if (g.isPostponed()) {
                numberOfGames++;
            }
        }
        return numberOfGames;
    }

    public void printCalendar() {
        for (Entry<String, Game> entry : games.entrySet()) {
            String date = entry.getKey();
            Game game = entry.getValue();

            System.out.println(date);
            if (game.getHome().nickname.equals(this.nickname)) {
                System.out.print(" vs " + game.getAway());
            } else {
                System.out.print(" @ " + game.getHome());
            }
            if (game.getWinner() != null) {
                if (game.getWinner().nickname.equals(this.nickname)) {
                    System.out.print(" : W " + game.getAwayScore() + " - " + game.getHomeScore());
                } else {
                    System.out.print(" : L " + game.getAwayScore() + " - " + game.getHomeScore());
                }
                if (game.isOvertimeNeeded()) {
                    System.out.print("ot");
                }
            }
            System.out.println("");
        }
    }
}
