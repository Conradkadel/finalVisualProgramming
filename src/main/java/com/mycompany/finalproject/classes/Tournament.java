/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject.classes;

import java.util.ArrayList;

/**
 *
 * @author conradkadel
 */
public class Tournament {
    
    // This Class is used to create Tournaments of differnt Matches and stores them in a list
    // under development
    // need to find better way to store these matches to display them correctly
    
    private String tournamentName;
    
    private int tournamentSize;
    
    private ArrayList<Match> matches;
  
    public Tournament(String name, int size, ArrayList<Match> matches){
        this.tournamentName = name;
        this.tournamentSize = size;
        this.matches = matches;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }
}
