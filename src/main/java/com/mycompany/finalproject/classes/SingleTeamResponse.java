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
public class SingleTeamResponse {
    
    // Response class for Team Information
     
    private int id;
    
    private int sports_id;

    public int getSports_id() {
        return sports_id;
    }
    
    @SerializedName("name_full")
    private String name;
    
    private String logo;
    
    private String country;
   
    
    private Venue venue;
    
    private Manager manager;

    public Venue getVenue() {
        return venue;
    }
    
    public Manager getManager() {
        return manager;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getCountry() {
        return country;
    }
    
  
            
    
}
