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
public class TeamBase {
    
    // This Class is used for creating any kind of Sports Team like in football or soccer
    // has a lot of different fields and information it stores
    // not every team has the same kind of fields
    
    
    
    private String teamName;
    
    private int teamSizeWhole;
    
    private int teamSizePlaying;
    
    private int teamCreationYear;
    
    private ArrayList<String> titles;
    
    public TeamBase(String name){
        teamName = name;
    }
    
    
    public String getTeamName() {
        return teamName;
    }

    public int getTeamSizeWole() {
        return teamSizeWhole;
    }

    public int getTeamSizePlaying() {
        return teamSizePlaying;
    }

    public int getTeamCreationYear() {
        return teamCreationYear;
    }

    
    
    
}
