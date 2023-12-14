/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject.classes;

/**
 *
 * @author conradkadel
 */
public class SingleAtpRankingResponse {
    
    private int points;
    
    private int ranking;
    
    private int tournaments;
    
    private SingleTeamResponse team; 

    public int getPoints() {
        return points;
    }

    public int getRanking() {
        return ranking;
    }

    public int getTournaments() {
        return tournaments;
    }

    public SingleTeamResponse getTeam() {
        return team;
    }
    

    
}
