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
 public class CustomListModelMatches extends AbstractListModel<String>{
     
     // Creates the CustomListModel of the Sports display
     
    private ArrayList<Sport> data = new ArrayList<Sport>();

    public CustomListModelMatches(){
        super();
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(int index) {
        return data.get(index).getSportName();
    }
    
     public Sport getFullElementAt(int index) {
        return data.get(index); 
    }

     
    public void addElement(Sport s){
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
