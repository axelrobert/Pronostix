/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.nba;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author François_2
 */
public class Journey {
    
    private String date;
    private String uri;
    private List<Game> games;

    public Journey(String date, String uri, List<Game> games) {
        this.date = date;
        this.games = games;
        this.uri = uri;
    }

    public Journey(String date) {
        this.date = date;
        this.games = new ArrayList<>();
    }
    
    public Journey(String date, String uri) {
        this.date = date;
        this.uri = uri;
        this.games = new ArrayList<>();
    }
    
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void addGame(Game game) {
        games.add(game);
    }
    
    
    
}
