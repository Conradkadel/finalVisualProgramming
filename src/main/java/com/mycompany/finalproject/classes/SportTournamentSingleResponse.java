/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject.classes;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author conradkadel
 */
public class SportTournamentSingleResponse {
    
    // Main Receiver class for a match 
    
    private String name;
    
    @SerializedName("home_team")
    private SingleTeamResponse homeTeam;
   
    @SerializedName("away_team")
    private SingleTeamResponse awayTeam;
    
    @SerializedName("home_score")
    private Score homeScore;
     
    @SerializedName("away_score")
    private Score awayScore;
    
    @SerializedName("time_details")
    private Time time;
    
    private Season season;
    
    private String status;
    
    @SerializedName("main_odds")
    private MatchOdds matchOdds;

    public String getName() {
        return name;
    }

    public SingleTeamResponse getHomeTeam() {
        return homeTeam;
    }

    public SingleTeamResponse getAwayTeam() {
        return awayTeam;
    }

    public Score getHomeScore() {
        return homeScore;
    }

    public Score getAwayScore() {
        return awayScore;
    }

    public Time getTime() {
        return time;
    }

    public Season getSeason() {
        return season;
    }

    public String getStatus() {
        return status;
    }

    public MatchOdds getMatchOdds() {
        return matchOdds;
    }
    
     
         
    
}
