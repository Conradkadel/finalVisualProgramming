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
    @SerializedName("sport_id")
    public int id;
    
    public String name;
    
    public String logo;
    
    @SerializedName("start_date")
    public String startDate;
    
    @SerializedName("end_date")
    public String endDate;
    
    @SerializedName("home_team")
    public TeamResponse homeTeam;
    
    @SerializedName("away_team")
    public TeamResponse awayTeam; 
    
    @SerializedName("status_more")
    public String status;
    
     
         
    
}
