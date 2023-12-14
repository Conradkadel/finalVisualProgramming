/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject.classes;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author conradkadel
 */
 public class CustomListModelTournament extends AbstractListModel<String>{
     
     // Creates the CustomListModel for a tournament 
     
    private ArrayList<Match> data = new ArrayList<Match>();

    public CustomListModelTournament(){
        super();
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(int index) {
        return data.get(index).getName();
    }
    
     public Match getFullElementAt(int index) {
        return data.get(index);
    }
     
     public ArrayList<Match> getData(){
         return data;
     }
     
    public void addElement(Match s){
        data.add(s);
        int t = data.size()-1;
        fireContentsChanged(this, t, t);
    }

    public void removeElement(int index){
        if(data.size() > 0){
            data.remove(index);
            fireContentsChanged(this, index, data.size()-1);                
        }
    }
    


  }
