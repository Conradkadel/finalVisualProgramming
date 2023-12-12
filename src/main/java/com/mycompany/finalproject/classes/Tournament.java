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
    
   
    private TeamResponse homeTeam;
   
    private TeamResponse awayTeam; 
    
    private String status;
    
  
    public Tournament(String name, int size,TeamResponse homeTeam,TeamResponse awayTeam, String s){
        this.tournamentName = name;
        this.tournamentSize = size;
        
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.status = s;
    
    }


    public String getTournamentName() {
        return tournamentName;
    }
    public String getInformation(){
        return this.tournamentName + " is currently " + this.status + "/n";
    }
    public TeamResponse getHomeTeam(){
        return homeTeam;
    }
    public TeamResponse getAwayTeam(){
        return awayTeam;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }
}
