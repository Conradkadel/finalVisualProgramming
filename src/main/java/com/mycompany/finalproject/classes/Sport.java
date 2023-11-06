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
public class Sport {
    
    // This Class is used to safe an individual Sport and all its information
    
    
    private String sportName;
   
    private ArrayList<Tournament> currentTournament;
    
    public Sport(String name, ArrayList<Tournament> turnamentList){
        
        sportName = name;
        currentTournament = turnamentList;
    }

    public String getSportName() {
        return sportName;
    }
    
    public void addToTournamentList(Tournament t){
        currentTournament.add(t);
    }
    
   

    public ArrayList getCurrentTournament() {
        return currentTournament;
    }
    
}
