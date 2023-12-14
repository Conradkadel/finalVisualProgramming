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
public class SaveData {
    
    // Response Class for File
    
    private ArrayList<SingleTeamResponse> teamFavList = new ArrayList<>();
    private ArrayList<SinglePlayerSearchResponse> playerFavList = new ArrayList<>();
    
    public SaveData(ArrayList<SingleTeamResponse> tL,ArrayList<SinglePlayerSearchResponse> pL){
        teamFavList = tL;
        playerFavList = pL;
    }

    public ArrayList<SingleTeamResponse> getTeamFavList() {
        return teamFavList;
    }

    public ArrayList<SinglePlayerSearchResponse> getPlayerFavList() {
        return playerFavList;
    }
    
    
}
