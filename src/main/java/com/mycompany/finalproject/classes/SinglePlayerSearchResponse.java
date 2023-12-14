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
public class SinglePlayerSearchResponse {
    
    
    // Main response class for a player Search
    
    private String name;
    private int sport_id;
    private String photo;
    private String logo;
    private String position;
    private int age;
    private double height;
    @SerializedName("flag")
    private String nationality;
    @SerializedName("market_value")
    private int value;
    
    private Sport[] sport;
    
    public int getSport_id() {
        return sport_id;
    }
     
    public String getLogo() {
        return logo;
    } 
     
    public Sport[] getSport() {
        return sport;
    }
     
    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPosition() {
        return position;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public String getNationality() {
        return nationality;
    }

    public int getValue() {
        return value;
    }
    
    public String returnInformation(){
        return "Name : " + this.getName() + "\n" +
                "Age : " + this.getAge() + "\n" +
                "Nationality : " + this.getNationality()+ "\n" +
                "Height :" + this.getHeight() +
                "Value : " + this.getValue()+ "\n" +
                "Position : " + this.getPosition() ;
                    
    }
    
}
