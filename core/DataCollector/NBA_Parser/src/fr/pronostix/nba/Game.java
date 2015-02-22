/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.nba;

/**
 *
 * @author François_2
 */
public class Game {

    private final Team away;
    private final Team home;

    private int[] qtHomeScores;
    private int[] qtAwayScores;
    private int home_score;
    private int away_score;

    private GameStatus status;

    private final String date;
    private final String hour;

    // Constructor for a game finished
    public Game(Team away, Team home, int away_score, int home_score,
            int[] qtAwayScores, int[] qtHomeScores, GameStatus status,
            String date) {
        this.away = away;
        this.home = home;
        this.home_score = home_score;
        this.away_score = away_score;
        this.qtHomeScores = qtHomeScores;
        this.qtAwayScores = qtAwayScores;
        this.date = date;
        this.status = status;
        this.hour = null;
    }

    //Constructor for a game ready to be played
    public Game(Team away, Team home, String date, String hour) {
        this.away = away;
        this.home = home;
        this.home_score = 0;
        this.away_score = 0;
        this.date = date;
        this.status = GameStatus.TO_BE_PLAYED;
        this.hour = hour;
    }

    //Constructor for a game postponed
    public Game(Team away, Team home, String date) {
        this.away = away;
        this.home = home;
        this.home_score = 0;
        this.away_score = 0;
        this.date = date;
        this.status = GameStatus.POSTPONED;
        this.hour = null;
    }

    public Team getAway() {
        return away;
    }

    public Team getHome() {
        return home;
    }

    public int getHomeScore() {
        return home_score;
    }

    public void setHomeScore(int home_score) {
        this.home_score = home_score;
    }

    public int getAwayScore() {
        return away_score;
    }

    public void setAwayScore(int away_score) {
        this.away_score = away_score;
    }

    public String getDate() {
        return date;
    }

    public boolean isOvertimeNeeded() {
        return(status == GameStatus.FINAL_OT);
    }

    public int[] getQtHomeScores() {
        return qtHomeScores;
    }

    public void setQtHomeScores(int[] qtHomeScores) {
        this.qtHomeScores = qtHomeScores;
    }

    public int[] getQtAwayScores() {
        return qtAwayScores;
    }

    public void setQtAwayScores(int[] qtAwayScores) {
        this.qtAwayScores = qtAwayScores;
    }

    public String getHour() {
        return hour;
    }

    public boolean isPostponed() {
        return(status == GameStatus.POSTPONED);
    }
    
    public GameStatus getGameStatus() {
        return status;
    }
    
    public void setGameStatus(GameStatus status) {
        this.status = status;
    }
    

    @Override
    public String toString() {
        return (date + ": \n" + away + " vs " + home + "\n" + away_score + " - " + home_score + "\n");
    }

    public Team getWinner() {
        if (home_score == 0) {
            return null;
        }
        if (home_score > away_score) {
            return home;
        } else {
            return away;
        }
    }

    public boolean hasBeenPlayed() {
        return(status != GameStatus.TO_BE_PLAYED && status != GameStatus.POSTPONED);
    }
}
