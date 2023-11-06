/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject.classes;

/**
 *
 * @author conradkadel
 */
public class Match {
    
    // This Class is used to create Matches between 2 individual Team Classes
    // under development..
    
    private TeamBase teamOne;
    private TeamBase teamTwo;
    
    public Match(TeamBase one, TeamBase two){
        teamOne = one;
        teamTwo = two; 
    }
    
    public String returnMatchInfo(){
        return teamOne.getTeamName() + "||" + teamTwo.getTeamName();
    }
}
