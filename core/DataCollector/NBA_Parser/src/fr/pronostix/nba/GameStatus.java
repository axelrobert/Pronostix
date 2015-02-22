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
public enum GameStatus {
    
    FINAL("Final"), 
    FINAL_OT("Final/OT"),
    POSTPONED("Postponed"),
    TO_BE_PLAYED("To be played");
   
    private final String value;

    GameStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
    
}
