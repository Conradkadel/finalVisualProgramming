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
     
    private ArrayList<Tournament> data = new ArrayList<Tournament>();

    public CustomListModelTournament(){
        super();
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(int index) {
        return data.get(index).getTournamentName();
    }
    
     public Tournament getFullElementAt(int index) {
        return data.get(index);
    }
     
     public ArrayList<Tournament> getData(){
         return data;
     }
     
    public void addElement(Tournament s){
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
