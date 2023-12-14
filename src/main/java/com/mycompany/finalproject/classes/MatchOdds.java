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
public class MatchOdds {
    
    @SerializedName("outcome_1")
    public Odd homeOdds;
    @SerializedName("outcome_2")
    public Odd awayOdds;
    
}
