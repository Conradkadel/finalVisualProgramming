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
    
    private String name;
    private int sport_id;
    private String photo;
    private String position;
    private int age;
    private double height;
    @SerializedName("flag")
    private String nationality;
    @SerializedName("market_value")
    private int value;
    
     public int getSport_id() {
        return sport_id;
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
    
}
