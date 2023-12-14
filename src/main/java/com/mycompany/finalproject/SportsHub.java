/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template


// VISUAL PROGRAMMING FINAL
    
    This Porgramm is a Sports Score Viewer where there is a lot of information 
    about both live and old games or about specific people. It will load the 
    information needed from an online api. 

    API USED : https://rapidapi.com/tipsters/api/sportscore1/
    
    TODO :
        - GEt taps sorted
        - more functions
        
        
    DIARY:
    Start NOVEMBER 4 - 1h Tring some Design Ideas and playing with the UI
    
          NOVEMBER 5 - 3h Coding and setting up JList for the display of Sports
                        Different classes for the Lists and future API
                        Sorting the Design making it look simpler
                         
         NOVEMBER 10 - 1h Researching about possible API and Desgin changes 
                        to a TAB based Design

         NOVEMBER 21 - 1.5h Trying to implement a possible API with Classes 
                        for Matches and Turnmaents
         
         NOVEMBER 27 - 2h Changed my API and create new Classes for receiveing 
                        the data. 

         NOVEMBER 29 - 2h Display the information of the API on 2 JLIST that show
                        each Sport and then the current live game in that specific
                        Sport. 

         NOVEMBER 30 - 2h - Made it possible to load the logo of each of the games
                            2 PLayers from the URL and then Display it in 2 JPANLS

         DECEMBER 4 - 3 h - Create a new Panel including a JTable and a search bar
                            where players can be searched by their name from the 
                             API.

         Decembet 6 - 4h - Created a new Panel like the player Seach just for Teams
                           and a ATP List which gets the currents ATP ranking

         Decembet 9 - 5h - Put in a whole desing based around the Programm which goes
                            accross all current pages
         
         December 11 - 4h - Worked again on a lot of desing and display options 
                            and seeing what will look good

         Decembet 12 - 2.5h Added my last Page called Favourties where we can
                            create a list to safe all our data

         Decembet 13 - 4h Got my last feature implemented that we can save data to a
                        external file and whenever we have a program it will load the
                         existing data

         Decembet 14 7.5h - BUGG CHANGES AND WRAPPING UP // COMMENTING

           
          
//////////// STATRT OF MY PROGRAM
 */


//// IMPORTS AND PACKAGES //////////

package com.mycompany.finalproject;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mycompany.finalproject.classes.SingleSportResponse;
import com.mycompany.finalproject.classes.Sport;
import com.mycompany.finalproject.classes.SportResponse;
import com.mycompany.finalproject.classes.SportTournamentSearchResponse;
import com.mycompany.finalproject.classes.SportTournamentSingleResponse;
import com.mycompany.finalproject.classes.Match;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import com.mycompany.finalproject.classes.CustomListModelMatches;
import com.mycompany.finalproject.classes.CustomListModelSport;
import com.mycompany.finalproject.classes.PlayerSearchResponse;
import com.mycompany.finalproject.classes.SinglePlayerSearchResponse;
import com.mycompany.finalproject.classes.AtpRankingResponse;
import com.mycompany.finalproject.classes.SaveData;
import com.mycompany.finalproject.classes.SingleAtpRankingResponse;
import com.mycompany.finalproject.classes.SingleTeamResponse;
import com.mycompany.finalproject.classes.TeamResponse;
import com.mycompany.finalproject.classes.TeamResponseSingle;
import com.mycompany.finalproject.panels.MatchDisplay;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author conradkadel
 */
public class SportsHub extends javax.swing.JFrame {
    
    // FIELDS //
    
    // Here I am creating differnt kinds of List that we will use throught
    // the Code to move and safe Data
    
    private CustomListModelSport listSport = new CustomListModelSport();
    private CustomListModelMatches listMatches= new CustomListModelMatches();
  
    private SingleAtpRankingResponse[] listRankingTennis; 
    private SinglePlayerSearchResponse[] playerSearchList;
    private SingleTeamResponse[] teamSearchList;
    
    private ArrayList<SingleTeamResponse> teamFavList = new ArrayList<>();
    private ArrayList<SinglePlayerSearchResponse> playerFavList = new ArrayList<>();

    // gson for json
    private Gson gson = new Gson();
   
    /**
     * Creates new form MainProgram
     */
    
    /////////////////// Constructor Start ///////////
    
    public SportsHub() {
        initComponents();
        
  
        // First I will create all the SelectionListeners to my Tables
        // This is all done pretty much them same althought the only thing is 
        // what changes when value changes
        
        
        
       /// SELECTION LISTENER ATP TABLE API-https://docs.oracle.com/javase/8/docs/api/javax/swing/event/ListSelectionListener.html
       //-https://www.youtube.com/watch?v=5dK4dA39INk
       
        ListSelectionModel modelAtp = jtblAtp.getSelectionModel();
        modelAtp.addListSelectionListener(new ListSelectionListener() {
        
            @Override
            public void valueChanged(ListSelectionEvent e){
                // Check if slection is empty
                if(! modelAtp.isSelectionEmpty())
                {
                    // Get selection
                    int atpIndexPlayer = modelAtp.getMinSelectionIndex();
                    BufferedImage imagePlayer = null;
                    // Load the Image
                    try {
                        URL url= new URL(listRankingTennis[atpIndexPlayer].getTeam().getLogo());
                        imagePlayer = ImageIO.read(url);
                        Image newImagePlayer = imagePlayer.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH); // Scaler
                        jlblTennisPlayer.setIcon(new ImageIcon(newImagePlayer));
                    } catch (IOException l) {
                        JOptionPane.showMessageDialog(null,"Nothing FOund");
                    }
                    // Set other Panels to the specific values
                    
                    jpnlAtpTennis.setText(listRankingTennis[atpIndexPlayer].getTeam().getName());
                    jpnlAtpNationality.setText(listRankingTennis[atpIndexPlayer].getTeam().getCountry());
                    jpnlAtpTournaments.setText("Tournaments played: " + listRankingTennis[atpIndexPlayer].getTournaments());
                    jlablAtpRanking.setText(" #"+listRankingTennis[atpIndexPlayer].getRanking());
                    
                }
            }
        });
        
        // SELECTION LISTENER FAVTEAM
        
        ListSelectionModel modelFavTeams = jtblFavTeam.getSelectionModel();
        modelFavTeams.addListSelectionListener(new ListSelectionListener() {
        
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(! modelFavTeams.isSelectionEmpty())
                {
                    int favTeamIndex = modelFavTeams.getMinSelectionIndex();
                    BufferedImage imagePlayer = null;
                    try {
                        URL url= new URL(teamFavList.get(favTeamIndex).getLogo());
                        imagePlayer = ImageIO.read(url);
                        Image newImagePlayer = imagePlayer.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                        jlblFavIcon.setIcon(new ImageIcon(newImagePlayer));
                        
                    } catch (IOException l) {
                        JOptionPane.showMessageDialog(null,"Nothing FOund");
                    }
                    
                    jlblFavName.setText(teamFavList.get(favTeamIndex).getName());
                    jlblInfo1.setText(teamFavList.get(favTeamIndex).getCountry().toUpperCase());
                    if(teamFavList.get(favTeamIndex).getManager() != null){
                        jlblInfo2.setText("Managed by : " + teamFavList.get(favTeamIndex).getManager().name);
                    }
                    else{
                        jlblInfo2.setText("Not Available");
                    }
                    if(teamFavList.get(favTeamIndex).getVenue() != null){
                        jlblInfo3.setText(teamFavList.get(favTeamIndex).getVenue().stadium.name);
                    }
                    else{
                        jlblInfo3.setText("Not Available");
                    }
                    
                }
            }
        });
        
        // SELECTION LISTENER FAVPLAYER
        
        ListSelectionModel modelFavPlayers = jtblFavPlayer.getSelectionModel();
        modelFavPlayers.addListSelectionListener(new ListSelectionListener() {
        
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(! modelFavPlayers.isSelectionEmpty())
                {
                    int favPlayerIndex = modelFavPlayers.getMinSelectionIndex();
                    BufferedImage imagePlayer = null;
                    try {
                        URL url= new URL(playerFavList.get(favPlayerIndex).getPhoto());
                        imagePlayer = ImageIO.read(url);
                        Image newImagePlayer = imagePlayer.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                        jlblFavIcon.setIcon(new ImageIcon(newImagePlayer));
                    } catch (IOException l) {
                        JOptionPane.showMessageDialog(null,"Nothing Found");
                    }
                    jlblFavName.setText(playerFavList.get(favPlayerIndex).getName());
                    jlblInfo1.setText(playerFavList.get(favPlayerIndex).getNationality().toUpperCase());
                    if(playerFavList.get(favPlayerIndex).getPosition().length() > 0){
                        jlblInfo2.setText("Position : " + playerFavList.get(favPlayerIndex).getPosition());
                    }
                    else{
                        jlblInfo2.setText("Not Available");
                    }
                    if(playerFavList.get(favPlayerIndex).getAge() > 0){
                        jlblInfo3.setText("Age : " + playerFavList.get(favPlayerIndex).getAge());
                    }
                    else{
                        jlblInfo3.setText("Not Available");
                    }
                    
                }
            }
        });
        
        // SLECTION LISTENER 
        
        ListSelectionModel modelPlayer = jtblPlayer.getSelectionModel();
        modelPlayer.addListSelectionListener(new ListSelectionListener() {
      
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(! modelPlayer.isSelectionEmpty())
                {
                    int indexPlayer = modelPlayer.getMinSelectionIndex();
                    BufferedImage imagePlayer = null;
                    try {
                        URL url;
                        if(jCmbPlayerSearch.getSelectedIndex() == 1){
                        url= new URL(playerSearchList[indexPlayer].getLogo()); // This is because my API Strucuture
                        }else{
                        url= new URL(playerSearchList[indexPlayer].getPhoto()); // So i need to Seperate it
                        }
                        imagePlayer = ImageIO.read(url);
                        Image newImagePlayer = imagePlayer.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                        jlblPlayerPicSearch.setIcon(new ImageIcon(newImagePlayer));
                        
                    } catch (IOException l) {
                        JOptionPane.showMessageDialog(null,"Nothing Found");
                    }
                    catch(java.lang.ArrayIndexOutOfBoundsException i){
                        JOptionPane.showMessageDialog(null,"Nothing Found");
                    }
                    
                    // Set all the panels
                    
                    jpnlPsearchName.setText(playerSearchList[indexPlayer].getName());
                    jpnlPsearchNationality.setText(playerSearchList[indexPlayer].getNationality().toUpperCase());

                    if(playerSearchList[indexPlayer].getAge() > 0){
                        jpnlPsearchAge.setText("Age :" + playerSearchList[indexPlayer].getAge());
                    }
                    else{
                        jpnlPsearchAge.setText("Not Available");
                    }
                    if(playerSearchList[indexPlayer].getHeight() > 0){
                        jpnlPsearchHeight.setText("Height :"+ playerSearchList[indexPlayer].getHeight());
                    }
                    else{
                        jpnlPsearchHeight.setText("Not Available");
                    }
                    if(playerSearchList[indexPlayer].getValue() > 0){
                        jpnlPsearchValue.setText("Worth :"+playerSearchList[indexPlayer].getValue());
                    }
                    else{
                        jpnlPsearchValue.setText("Not Available");
                    }
                    
                }
            }
        });
        
        //SelectionLister for TeamTable
        
        ListSelectionModel modelSearchTeam = jtblTeam.getSelectionModel();
        modelSearchTeam.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(! modelSearchTeam.isSelectionEmpty())
                {
                    int indexPlayer = modelSearchTeam.getMinSelectionIndex();
                    BufferedImage imagePlayer = null;
                    SingleTeamResponse teamResponse = getFullTeamData(teamSearchList[indexPlayer].getId());
                    try {
                        URL url= new URL(teamResponse.getLogo());
                        imagePlayer = ImageIO.read(url);
                        Image newImagePlayer = imagePlayer.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                        jlblTeamPic.setIcon(new ImageIcon(newImagePlayer));
                       
                    } catch (IOException l) {
                        JOptionPane.showMessageDialog(null,"Nothing FOund");
                    }
                    // Again we set all the fields
                    
                    jpnlTeamName.setText(teamResponse.getName());
                    if(teamResponse.getCountry().length() > 0){
                        jpnlTeamCountry.setText(teamResponse.getCountry().toUpperCase());
                    }
                    else{
                        jpnlTeamCountry.setText("Not Available");
                    }
                    if(teamResponse.getManager() != null){
                        jpnlTeamManager.setText("Managed by: " + teamResponse.getManager().name);
                    }
                    else{
                        jpnlTeamManager.setText("Not Available");
                    }
                    if(teamResponse.getVenue() != null){
                        jpnlTeamVenue.setText("<HTML>Playing at: "+ teamResponse.getVenue().stadium.name + " with capacity of :" + teamResponse.getVenue().capacity + "</HTML>");
                    }
                    else{
                        jpnlTeamVenue.setText("Not Available");
                    }
                    
                }
            }
        });
        
        // RIght here I get the table models from both favorite tables and then
        // I reset them so they have nothing inside
        
        DefaultTableModel playerFav = (DefaultTableModel)jtblFavPlayer.getModel();
        playerFav.setRowCount(0);
        DefaultTableModel teamFav = (DefaultTableModel)jtblFavTeam.getModel();
        teamFav.setRowCount(0);
        
        // This will load the data from data.txt which is saved
        ReadFromFile();
        
       
        
        
        // THIS IS TO SAFE DATA FROM API
        
        /** ORIGINAL
        *
        * for(int i= 0 ;i < sports.getSportList().length;i++){
                  listSport.addElement(new Sport(sportsList[i].name,sportsList[i].SportsId));
              }
        */
        // Just adding Sport elements into the list
        
        listSport.addElement(new Sport("Football",1));
        listSport.addElement(new Sport("Tennis",2));
        listSport.addElement(new Sport("Basketball",3));
        listSport.addElement(new Sport("Ice Hockey",4));
        listSport.addElement(new Sport("Volleyball",5));
        listSport.addElement(new Sport("Handball",6));
       
        jlstSports.setModel(listSport);
        
        
    }
    //// END OF CONSTRUCTOR
    
    
    //// METHODS 
    
    private static void WriteToFile(String myData) {
        
        // This is created with help from : https://crunchify.com/java-saving-and-loading-data-from-a-file-simple-production-ready-utility-for-file-readwrite-operation/
        
        // Will write to the data.txt file whatever its passed
        
        File file = new File("/Users/conradkadel/NetBeansProjects/FinalProject/finalProject/src/main/java/com/mycompany/finalproject/data/data.txt");
        file.delete(); // I delet it on purpose for it to reset
        
        // if doenst exist so it recreates a new one
        if (!file.exists()) {  
            try {
                File directory = new File(file.getParent());
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                JOptionPane.showInputDialog("Nothing Found");
            }
        }
        
        try {
            // Creating a Writer and writing to the file
            FileWriter Writer;
            Writer = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferWriter = new BufferedWriter(Writer);
            bufferWriter.write(myData.toString());
            bufferWriter.close();
            
        } catch (IOException e) {
  
        }
    }
    
 
    public void ReadFromFile() {
        
        // Also inspired by Crunshify
        
        // This method will read from a file
        
        // load the file
        File crunchifyFile = new File("/Users/conradkadel/NetBeansProjects/FinalProject/finalProject/src/main/java/com/mycompany/finalproject/data/data.txt");
        InputStreamReader isReader;
        try {
            isReader = new InputStreamReader(new FileInputStream(crunchifyFile), StandardCharsets.UTF_8);
            JsonReader myReader = new JsonReader(isReader);
            SaveData savedData = gson.fromJson(myReader, SaveData.class); // Converts data into my receiving Class
            teamFavList = savedData.getTeamFavList(); // Setting the Favlist
            playerFavList = savedData.getPlayerFavList();
            
        } catch (Exception e) {
          JOptionPane.showInputDialog("Nothing Found");
        }
        
        // Puts the data into the Tables to display

        DefaultTableModel modelPlayer = (DefaultTableModel)jtblFavPlayer.getModel();
        for(SinglePlayerSearchResponse sr : playerFavList){
            modelPlayer.addRow(new Object[]{sr.getName(),getSport(sr.getSport_id()),sr.getNationality().toUpperCase(),sr.getAge()});
        }
        DefaultTableModel modelTeam = (DefaultTableModel)jtblFavTeam.getModel();
        for(SingleTeamResponse sr : teamFavList){
                modelTeam.addRow(new Object[]{sr.getName(),getSport(sr.getSports_id()),sr.getCountry().toUpperCase()});
        }
       
    }
    
    private void setAtpResponse(int atpIndex){
        
        // This method will request a pull from the Api with the information
        // of the WTA or ATP top 100 list
        // It will display the data in the ATP List Panel
        
        HttpResponse<String> response = null;
        HttpRequest request;
        
        // checking if to search for WTA or ATP
        if(atpIndex == 0){
            request = HttpRequest.newBuilder()
                    //Request to the API
		.uri(URI.create("https://sportscore1.p.rapidapi.com/tennis-rankings/atp?page=1"))
		.header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
		.header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
            try{
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            }
            catch(IOException e){
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SportsHub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(atpIndex == 1){
            request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/tennis-rankings/wta?page=1"))
		.header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
		.header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
            try{
                response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            }
            catch(IOException e){
                   
            } catch (InterruptedException ex) {
                Logger.getLogger(SportsHub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Converts the data that we get and put it in my response class and then
        // into the arrayList
        
        String myJsonData = response.body();
        Gson gson = new Gson();
        AtpRankingResponse respo = gson.fromJson(myJsonData, AtpRankingResponse.class);
        listRankingTennis = respo.getData();
    }
    
    private PlayerSearchResponse getPlayerSearchResponse(String name){
        
        // This method will get a player response based on a name we give it to search
        
        HttpRequest request = null;
        
        // Again here my Api is beeing dumb 
        // Need to check if selected Sport is Tennis and if yes i need to search
        // for tennis players with the team search which 
        
        int sportsIndex = jCmbPlayerSearch.getSelectedIndex() + 1;
        if(sportsIndex != 2)
        {
         request = HttpRequest.newBuilder()
                  .uri(URI.create("https://sportscore1.p.rapidapi.com/players/search?name=" + name + "&sport_id=" + sportsIndex))
                  .header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
                  .header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
                  .method("POST", HttpRequest.BodyPublishers.noBody())
                  .build();
        }
        else
        {
        request = HttpRequest.newBuilder()
                 .uri(URI.create("https://sportscore1.p.rapidapi.com/teams/search?name="+name+"&sport_id=" + sportsIndex))
                 .header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
                 .header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
                 .method("POST", HttpRequest.BodyPublishers.noBody())
                 .build();
        }
        
        HttpResponse<String> response = null;
        
        try{
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception IOException){
            JOptionPane.showInputDialog("Nothing Found");
        }
        
        // Again Converts it into the Response Class but returns it this time
        
        String myJsonData = response.body();
        Gson gson = new Gson();
        PlayerSearchResponse respo = gson.fromJson(myJsonData, PlayerSearchResponse.class);
            return respo;
    }
 
    private SportTournamentSearchResponse getTournamentData(int sportsID){
         // This Function is used to get all important information about all the Sports current live Events and returns a SportTournamentSearchResponse
         
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/sports/" + sportsID + "/events/live"))
		.header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
		.header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = null;
    try{
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
    catch(Exception IOException){
        JOptionPane.showInputDialog("Nothing Found");
    }
     String myJsonData = response.body(); 
     Gson gson = new Gson();
     SportTournamentSearchResponse respo = gson.fromJson(myJsonData, SportTournamentSearchResponse.class);
        return respo;
    }
    
     private TeamResponse getTeamSearchData(int sportsID, String teamName){
         // This Function is used to get all important information about a team
         // which is a search
         // It will return a TeamResponse
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/teams/search?sport_id=" + sportsID + "&name=" + teamName))
		.header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
		.header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
		.method("POST", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = null;
    try{
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
    catch(Exception IOException){
        JOptionPane.showInputDialog("Nothing Found");
    }
     String myJsonData = response.body(); 
     Gson gson = new Gson();
     TeamResponse respo = gson.fromJson(myJsonData, TeamResponse.class);
     return respo;
    }
     
    private SingleTeamResponse getFullTeamData(int sportsID){
         // This Function is used to get all important information about a specific
         // team which we already know so we can get more information
         
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/teams/" + sportsID))
		.header("X-RapidAPI-Key", "6e246dd021msh5bf872cbc5cd9ccp1fcc40jsna4e86aae8085")
		.header("X-RapidAPI-Host", "sportscore1.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response = null;
    try{
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
    catch(Exception IOException){
        JOptionPane.showInputDialog("Nothing Found");
    }
     String myJsonData = response.body(); 
      
     Gson gson = new Gson();
     TeamResponseSingle teamResponse = gson.fromJson(myJsonData, TeamResponseSingle.class);
    
     return teamResponse.getData();
    }
     
     
    // For Api use to convert the white soaces into "%20"
    // Done with a String Builder
    
    private String noWhiteSpace(String s){
        StringBuilder newString = new StringBuilder();
        for(int i = 0; i<s.length(); i++) {

            // access each character
            char a = s.charAt(i);
            if(Character.isWhitespace(a)){ // if white space then dont append
                newString.append("%20");
            }
            else{
                newString.append(a);
            }
        }
        return newString.toString();
    }
     
    
    // For our Main Page Matches List to update the list wheneber we change 
    // to a new Sport
    
    private CustomListModelMatches updateTournamentList(ArrayList list){
        // updates the TournmaentList
        listMatches = new CustomListModelMatches();
        for(var t : list){
            listMatches.addElement((Match)t);
        }
        return listMatches;
   
    }
    
    // This is used io the Constructor to build the 6 diff Sports my API
    private String getSport(int id){
        if(id == 1){
            return "Football";
        }
        if(id == 2){
            return "Tennis";
        }
        if(id == 3){
            return "Baskedball";
        }
        if(id == 4){
            return "IceHockey";
        }
        if(id == 5){
            return "Volleyball";
        }
        if(id == 6){
            return "Handball";
        }
        return "No Sport";
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblInfo = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jpnlIcon = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel15 = new javax.swing.JPanel();
        jPnlProgramm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlIcon = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlstSports = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jlblAway = new javax.swing.JLabel();
        jlblLiveStatus = new javax.swing.JLabel();
        jlblHome = new javax.swing.JLabel();
        jlblLiveInfo = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstTournaments = new javax.swing.JList<>();
        jbtnFindOutMore = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblPlayer = new javax.swing.JTable();
        jbtnFindPlayer = new javax.swing.JButton();
        jtfSearchBar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCmbPlayerSearch = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jlblPlayerPicSearch = new javax.swing.JLabel();
        jpnlPsearchName = new javax.swing.JLabel();
        jtbnFavPlayer = new javax.swing.JButton();
        jpnlPsearchHeight = new javax.swing.JLabel();
        jpnlPsearchNationality = new javax.swing.JLabel();
        jpnlPsearchValue = new javax.swing.JLabel();
        jpnlPsearchAge = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSbSearchTeam = new javax.swing.JTextField();
        jCobTeamSearch = new javax.swing.JComboBox<>();
        jbtnFindTeam = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtblTeam = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jlblTeamPic = new javax.swing.JLabel();
        jpnlTeamCountry = new javax.swing.JLabel();
        jpnlTeamManager = new javax.swing.JLabel();
        jpnlTeamVenue = new javax.swing.JLabel();
        jpnlTeamName = new javax.swing.JLabel();
        jbtnAddFavTeam = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtblFavTeam = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtblFavPlayer = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jlblFavIcon = new javax.swing.JLabel();
        jlblInfo3 = new javax.swing.JLabel();
        jlblFavName = new javax.swing.JLabel();
        jbtnRemovePlayer = new javax.swing.JButton();
        jlblInfo1 = new javax.swing.JLabel();
        jlblInfo2 = new javax.swing.JLabel();
        jbtnRemoveTeam = new javax.swing.JButton();
        jbtnSaveData = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jCmbAtp = new javax.swing.JComboBox<>();
        jbtnGetATP = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jtblAtp = new javax.swing.JTable();
        jlblTennisPlayer = new javax.swing.JLabel();
        jpnlAtpTennis = new javax.swing.JLabel();
        jpnlAtpTournaments = new javax.swing.JLabel();
        jpnlAtpNationality = new javax.swing.JLabel();
        jlablAtpRanking = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpnlIconLayout = new javax.swing.GroupLayout(jpnlIcon);
        jpnlIcon.setLayout(jpnlIconLayout);
        jpnlIconLayout.setHorizontalGroup(
            jpnlIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jpnlIconLayout.setVerticalGroup(
            jpnlIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sports HUB");
        setResizable(false);

        jPanel5.setBackground(new java.awt.Color(0, 153, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel5.setToolTipText("s\n");
        jPanel5.setFont(new java.awt.Font("Phosphate", 1, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Impact", 1, 60)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("SPORTS HUB");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(pnlIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setToolTipText("");
        jPanel4.setSize(new java.awt.Dimension(200, 200));

        jlstSports.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jlstSports.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jlstSports.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlstSports.setToolTipText("");
        jlstSports.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlstSports.setMinimumSize(new java.awt.Dimension(100, 100));
        jlstSports.setPreferredSize(new java.awt.Dimension(255, 225));
        jlstSports.setSize(new java.awt.Dimension(255, 225));
        jlstSports.setValueIsAdjusting(true);
        jlstSports.setVisibleRowCount(6);
        jlstSports.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstSportsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jlstSports);

        jLabel2.setBackground(new java.awt.Color(255, 51, 51));
        jLabel2.setFont(new java.awt.Font("Impact", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Select Your Sport");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));

        jlblLiveStatus.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jlblLiveStatus.setForeground(new java.awt.Color(255, 255, 255));
        jlblLiveStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblLiveStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jlblLiveInfo.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jlblLiveInfo.setForeground(new java.awt.Color(255, 255, 255));
        jlblLiveInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblLiveInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 65, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jlblAway, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblLiveInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblLiveStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlblAway, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jlblLiveInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlblLiveStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jlblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
        );

        jLabel4.setFont(new java.awt.Font("Impact", 0, 30)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Find your live Game");

        jlstTournaments.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jlstTournaments.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstTournamentsValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jlstTournaments);

        jbtnFindOutMore.setBackground(new java.awt.Color(0, 153, 0));
        jbtnFindOutMore.setFont(new java.awt.Font("Phosphate", 0, 14)); // NOI18N
        jbtnFindOutMore.setText("FInd Out More !");
        jbtnFindOutMore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbtnFindOutMore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFindOutMoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnFindOutMore, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel4)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jbtnFindOutMore, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPnlProgramm.addTab("Find a Live Game", jPanel1);

        jPanel9.setBackground(new java.awt.Color(0, 153, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jtblPlayer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Nationality", "Age"
            }
        ));
        jScrollPane3.setViewportView(jtblPlayer);
        if (jtblPlayer.getColumnModel().getColumnCount() > 0) {
            jtblPlayer.getColumnModel().getColumn(0).setResizable(false);
            jtblPlayer.getColumnModel().getColumn(0).setPreferredWidth(175);
            jtblPlayer.getColumnModel().getColumn(1).setResizable(false);
            jtblPlayer.getColumnModel().getColumn(2).setResizable(false);
        }

        jbtnFindPlayer.setBackground(new java.awt.Color(153, 153, 153));
        jbtnFindPlayer.setText("Find Player ");
        jbtnFindPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFindPlayerActionPerformed(evt);
            }
        });

        jtfSearchBar.setText("Find a Player ...");
        jtfSearchBar.setActionCommand("<Not Set>");
        jtfSearchBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfSearchBarFocusGained(evt);
            }
        });
        jtfSearchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfSearchBarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Impact", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Search for any Player");
        jLabel5.setToolTipText("");

        jCmbPlayerSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Football", "Tennis", "Basketball", "Icehockey", "Voleyball", "Handball" }));
        jCmbPlayerSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbPlayerSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jtfSearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCmbPlayerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnFindPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfSearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCmbPlayerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnFindPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        jPanel10.setBackground(new java.awt.Color(51, 51, 51));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jpnlPsearchName.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlPsearchName.setForeground(new java.awt.Color(0, 153, 0));
        jpnlPsearchName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jtbnFavPlayer.setBackground(new java.awt.Color(0, 153, 0));
        jtbnFavPlayer.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jtbnFavPlayer.setText("Add to Your Favourites");
        jtbnFavPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbnFavPlayerActionPerformed(evt);
            }
        });

        jpnlPsearchHeight.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlPsearchHeight.setForeground(new java.awt.Color(0, 153, 0));
        jpnlPsearchHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jpnlPsearchNationality.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlPsearchNationality.setForeground(new java.awt.Color(0, 153, 0));
        jpnlPsearchNationality.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jpnlPsearchValue.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlPsearchValue.setForeground(new java.awt.Color(0, 153, 0));
        jpnlPsearchValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jpnlPsearchAge.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlPsearchAge.setForeground(new java.awt.Color(0, 153, 0));
        jpnlPsearchAge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpnlPsearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlPsearchHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlPsearchAge, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnlPsearchNationality, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGap(132, 132, 132)
                            .addComponent(jlblPlayerPicSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGap(103, 103, 103)
                            .addComponent(jpnlPsearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 114, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jtbnFavPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jlblPlayerPicSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jpnlPsearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlPsearchNationality, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlPsearchAge, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlPsearchHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnlPsearchValue, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(jtbnFavPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPnlProgramm.addTab("Search for a Player ", jPanel2);

        jPanel14.setBackground(new java.awt.Color(51, 51, 51));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel11.setFont(new java.awt.Font("Impact", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Search for any Team");
        jLabel11.setToolTipText("");

        jSbSearchTeam.setText("Find a Player ...");
        jSbSearchTeam.setActionCommand("<Not Set>");
        jSbSearchTeam.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jSbSearchTeamFocusGained(evt);
            }
        });
        jSbSearchTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSbSearchTeamActionPerformed(evt);
            }
        });

        jCobTeamSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Football", "Basketball", "Icehockey", "Voleyball", "Handball" }));
        jCobTeamSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCobTeamSearchActionPerformed(evt);
            }
        });

        jbtnFindTeam.setBackground(new java.awt.Color(153, 153, 153));
        jbtnFindTeam.setText("Find Team");
        jbtnFindTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFindTeamActionPerformed(evt);
            }
        });

        jtblTeam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Nationality"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(jtblTeam);
        if (jtblTeam.getColumnModel().getColumnCount() > 0) {
            jtblTeam.getColumnModel().getColumn(0).setResizable(false);
            jtblTeam.getColumnModel().getColumn(0).setPreferredWidth(175);
            jtblTeam.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jSbSearchTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jCobTeamSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnFindTeam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSbSearchTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCobTeamSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnFindTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(0, 153, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jpnlTeamCountry.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlTeamCountry.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jpnlTeamManager.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlTeamManager.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jpnlTeamVenue.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlTeamVenue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jpnlTeamName.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        jpnlTeamName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jbtnAddFavTeam.setBackground(new java.awt.Color(102, 102, 102));
        jbtnAddFavTeam.setText("Add to Favourties");
        jbtnAddFavTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddFavTeamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jpnlTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpnlTeamCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jpnlTeamManager, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jpnlTeamVenue, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jlblTeamPic, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jbtnAddFavTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(jlblTeamPic, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jpnlTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jpnlTeamCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jpnlTeamManager, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jpnlTeamVenue, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(jbtnAddFavTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jPnlProgramm.addTab("Search for a Team", jPanel13);

        jPanel11.setBackground(new java.awt.Color(0, 153, 0));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Impact", 1, 50)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Your Favourites");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtblFavTeam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Sport", "Nationality"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtblFavTeam.setColumnSelectionAllowed(true);
        jScrollPane4.setViewportView(jtblFavTeam);
        jtblFavTeam.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jtblFavPlayer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Sport", "Nationality", "Age"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtblFavPlayer.setColumnSelectionAllowed(true);
        jtblFavPlayer.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jtblFavPlayer);
        jtblFavPlayer.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel9.setBackground(new java.awt.Color(0, 153, 0));
        jLabel9.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Players");

        jLabel10.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Teams");

        jlblInfo3.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlblInfo3.setForeground(new java.awt.Color(0, 153, 0));
        jlblInfo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jlblFavName.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlblFavName.setForeground(new java.awt.Color(0, 153, 0));
        jlblFavName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jbtnRemovePlayer.setBackground(new java.awt.Color(0, 153, 0));
        jbtnRemovePlayer.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jbtnRemovePlayer.setText("Remove");
        jbtnRemovePlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRemovePlayerActionPerformed(evt);
            }
        });

        jlblInfo1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlblInfo1.setForeground(new java.awt.Color(0, 153, 0));
        jlblInfo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jlblInfo2.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlblInfo2.setForeground(new java.awt.Color(0, 153, 0));
        jlblInfo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jbtnRemoveTeam.setBackground(new java.awt.Color(0, 153, 0));
        jbtnRemoveTeam.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jbtnRemoveTeam.setText("Remove");
        jbtnRemoveTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRemoveTeamActionPerformed(evt);
            }
        });

        jbtnSaveData.setBackground(new java.awt.Color(0, 153, 0));
        jbtnSaveData.setFont(new java.awt.Font("Impact", 0, 14)); // NOI18N
        jbtnSaveData.setText("Save Data");
        jbtnSaveData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jbtnRemovePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(178, 178, 178)
                                .addComponent(jlblFavIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 132, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                            .addComponent(jlblInfo3, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnSaveData, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jlblFavName, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnRemoveTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblFavIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnRemovePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblFavName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnRemoveTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jlblInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlblInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jlblInfo3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(jbtnSaveData, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(151, 151, 151))))))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPnlProgramm.addTab("Your Favourites ", jPanel3);

        jPanel18.setBackground(new java.awt.Color(51, 51, 51));

        jPanel19.setBackground(new java.awt.Color(0, 153, 0));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jCmbAtp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATP List", "WTA List" }));
        jCmbAtp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCmbAtpActionPerformed(evt);
            }
        });

        jbtnGetATP.setText("Find");
        jbtnGetATP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGetATPActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Tennis ATP/WTA Ranking");
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jtblAtp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Name", "Ranking"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jtblAtp.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jtblAtpPropertyChange(evt);
            }
        });
        jScrollPane10.setViewportView(jtblAtp);
        if (jtblAtp.getColumnModel().getColumnCount() > 0) {
            jtblAtp.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        jpnlAtpTennis.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jpnlAtpTennis.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpnlAtpTennis.setName(""); // NOI18N

        jpnlAtpTournaments.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jpnlAtpTournaments.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpnlAtpTournaments.setText("Tournaments");
        jpnlAtpTournaments.setName(""); // NOI18N

        jpnlAtpNationality.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jpnlAtpNationality.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jpnlAtpNationality.setName(""); // NOI18N

        jlablAtpRanking.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlablAtpRanking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlablAtpRanking.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jCmbAtp, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnGetATP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jlblTennisPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(jlablAtpRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpnlAtpTournaments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jpnlAtpNationality, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jpnlAtpTennis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCmbAtp, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnGetATP, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jlblTennisPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlablAtpRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)))
                        .addComponent(jpnlAtpTennis, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jpnlAtpNationality, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jpnlAtpTournaments, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(276, 276, 276)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(290, 290, 290))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPnlProgramm.addTab("Tennis ATP List", jPanel17);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPnlProgramm, javax.swing.GroupLayout.PREFERRED_SIZE, 1369, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPnlProgramm, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfSearchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSearchBarActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_jtfSearchBarActionPerformed

    private void jbtnFindPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFindPlayerActionPerformed
        // This is the Find Button for the Player Search Page
 
        if(jtfSearchBar.getText().length() > 2){ // needs to be over 2 char for APi to accept it
            PlayerSearchResponse pR = getPlayerSearchResponse( noWhiteSpace(jtfSearchBar.getText())); // Gets a Api response with the search bar input
            playerSearchList = pR.getData();
            DefaultTableModel model = (DefaultTableModel)jtblPlayer.getModel();
            model.setRowCount(0); // resets the table
            if(playerSearchList.length == 0){ // if nothing found the add a nothing found Object
                 model.addRow(new Object[]{"Nothing Found"});
            }
            // Appends them to the Mode;
            for(SinglePlayerSearchResponse sr : playerSearchList){
                if(sr.getNationality() != null){ // FOr api to get away of uselsess data
                    model.addRow(new Object[]{sr.getName(),sr.getNationality().toUpperCase(),sr.getAge()});
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please enter a name with more than 2 letters");
        }
    }//GEN-LAST:event_jbtnFindPlayerActionPerformed

    private void jtfSearchBarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSearchBarFocusGained
        // TODO add your handling code here:
        jtfSearchBar.setText(""); // Resets the Text
    }//GEN-LAST:event_jtfSearchBarFocusGained

    private void jbtnFindOutMoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFindOutMoreActionPerformed
        // TODO add your handling code here:
        // FInd out more button to open another window with a Custom MatchDisplay Panel
        
        int selectionSport = jlstSports.getSelectedIndex();
        int selectionGame = jlstTournaments.getSelectedIndex();
        SportTournamentSingleResponse[] gameResponse = getTournamentData(selectionSport + 1).getData();
        if(selectionGame > -1) {
            MatchDisplay playerDisplay = new MatchDisplay(gameResponse[selectionGame]);
            playerDisplay.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,"Nothing FOund");
        }
    }//GEN-LAST:event_jbtnFindOutMoreActionPerformed

    private void jlstTournamentsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstTournamentsValueChanged
        // TODO add your handling code here:
        // Getting the selected Index of the Current live Game and then displays Information on the Window

        int newSelection = jlstTournaments.getSelectedIndex();
        BufferedImage imageHome = null;
        BufferedImage imageAway = null;

        ArrayList<Match> list = listMatches.getData();
        if(newSelection > -1){
            try {
                URL url= new URL(list.get(newSelection).getHomeTeam().getLogo());
                imageHome = ImageIO.read(url);
                Image newImageHome = imageHome.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                URL url2 = new URL(list.get(newSelection).getAwayTeam().getLogo());
                imageAway = ImageIO.read(url2);
                Image newImageAway = imageAway.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                jlblHome.setIcon(new ImageIcon(newImageHome));
                jlblAway.setIcon(new ImageIcon(newImageAway));
                jlblLiveInfo.setText(list.get(newSelection).getName());
                jlblLiveStatus.setText(list.get(newSelection).getStatus());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Nothing FOund");
            }
        }
    }//GEN-LAST:event_jlstTournamentsValueChanged

    private void jlstSportsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstSportsValueChanged
        // TODO add your handling code here:
        // It gets the selected Index and then displays the correct Live Sports for the selected Sport

        int selection = jlstSports.getSelectedIndex();
        CustomListModelMatches listT = new CustomListModelMatches();
        SportTournamentSingleResponse[] responseData = getTournamentData(selection + 1).getData();

        for(SportTournamentSingleResponse r: responseData){
            listT.addElement(new Match(r.getName(),r.getHomeTeam(),r.getAwayTeam(),r.getStatus()));
        }

        if(listT.getSize() == 0){
            listT.addElement(new Match("No Current Live Game",null,null,null));
        }
        listMatches = listT;

        jlstTournaments.setModel(updateTournamentList(listT.getData()));
    }//GEN-LAST:event_jlstSportsValueChanged

    private void jCmbPlayerSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbPlayerSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbPlayerSearchActionPerformed

    private void jtbnFavPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbnFavPlayerActionPerformed
        // TODO add your handling code here:
        // Favouratizes a player and adds them to the List
        if(jtblPlayer.getSelectedRow() > -1){
            SinglePlayerSearchResponse player = playerSearchList[jtblPlayer.getSelectedRow()];
            DefaultTableModel model = (DefaultTableModel)jtblFavPlayer.getModel();
            model.addRow(new Object[]{player.getName(),getSport(player.getSport_id()),player.getAge(),player.getNationality().toUpperCase()});
            playerFavList.add(player);
        }
        else{
             JOptionPane.showMessageDialog(this, "Please select a Player");
        }
    }//GEN-LAST:event_jtbnFavPlayerActionPerformed

    private void jbtnFindTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFindTeamActionPerformed
        // TODO add your handling code here:
        // Button to Find the Teams and Display them in the Table
        if(jSbSearchTeam.getText().length() > 2){ // Again over 2 char..
            int teamSearchIndex = jCobTeamSearch.getSelectedIndex();
            if(teamSearchIndex > 0){
                teamSearchIndex = teamSearchIndex + 2; // Weard with Tennis not beeing in the List
            }else{
                teamSearchIndex = 1;
            }
            String noWhiteSpaceName = noWhiteSpace(jSbSearchTeam.getText());
            TeamResponse teamResponse = getTeamSearchData(teamSearchIndex,noWhiteSpaceName);
            teamSearchList = teamResponse.getData();
            DefaultTableModel model = (DefaultTableModel)jtblTeam.getModel();
            model.setRowCount(0);

            for(SingleTeamResponse sr : teamSearchList){
                if(sr.getCountry() != null){
                    model.addRow(new Object[]{sr.getName(),sr.getCountry().toUpperCase()});
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Please enter a name with more than 2 letters");
        }
    }//GEN-LAST:event_jbtnFindTeamActionPerformed

    private void jSbSearchTeamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jSbSearchTeamFocusGained
        // TODO add your handling code here:
        jSbSearchTeam.setText("");
    }//GEN-LAST:event_jSbSearchTeamFocusGained

    private void jSbSearchTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSbSearchTeamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSbSearchTeamActionPerformed

    private void jCobTeamSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCobTeamSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCobTeamSearchActionPerformed

    private void jbtnAddFavTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddFavTeamActionPerformed
        // TODO add your handling code here:
        // Favouratizes a Team and adds them to the List
        
        if(jtblTeam.getSelectedRow() > -1){
        SingleTeamResponse team = teamSearchList[jtblTeam.getSelectedRow()];
        DefaultTableModel model = (DefaultTableModel)jtblFavTeam.getModel();
        model.addRow(new Object[]{team.getName(),getSport(team.getSports_id()),team.getCountry().toUpperCase()});
        teamFavList.add(team);
        }
        else{
             JOptionPane.showMessageDialog(this, "Please select a Team");
        }
    }//GEN-LAST:event_jbtnAddFavTeamActionPerformed

    private void jCmbAtpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCmbAtpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbAtpActionPerformed

    private void jbtnGetATPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGetATPActionPerformed
        // TODO add your handling code here:
        
        // Gets the ATP List from the API and puts it on the Screen
        
        int atpIndex = jCmbAtp.getSelectedIndex();
        setAtpResponse(atpIndex);
        DefaultTableModel model = (DefaultTableModel)jtblAtp.getModel();
        model.setRowCount(0);

        for(SingleAtpRankingResponse sr : listRankingTennis){
            model.addRow(new Object[]{sr.getTeam().getName(),sr.getRanking()});
        }
        
    }//GEN-LAST:event_jbtnGetATPActionPerformed

    private void jtblAtpPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtblAtpPropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jtblAtpPropertyChange

    private void jbtnRemovePlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRemovePlayerActionPerformed
        // TODO add your handling code here:
        
        // Removes a player from both the list and the model 
         if(jtblFavPlayer.getSelectedRow() > -1){
            playerFavList.remove(jtblFavPlayer.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel)jtblFavPlayer.getModel();
            model.removeRow(jtblFavPlayer.getSelectedRow());
        }
        else{
            JOptionPane.showMessageDialog(this, "Please select a Player");
        }
    }//GEN-LAST:event_jbtnRemovePlayerActionPerformed

    private void jbtnRemoveTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRemoveTeamActionPerformed
        // TODO add your handling code here:
        
        // Removes a Team from both list and Model
        
        if(jtblFavTeam.getSelectedRow() > -1){
            teamFavList.remove(jtblFavTeam.getSelectedRow());
            DefaultTableModel model = (DefaultTableModel)jtblFavTeam.getModel();
            model.removeRow(jtblFavTeam.getSelectedRow());
        }
        else{
            JOptionPane.showMessageDialog(this, "Please select a Team");
        }
    }//GEN-LAST:event_jbtnRemoveTeamActionPerformed

    private void jbtnSaveDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveDataActionPerformed
        // TODO add your handling code here:
        // Saves the Data
        WriteToFile(gson.toJson(new SaveData(teamFavList,playerFavList)));
    }//GEN-LAST:event_jbtnSaveDataActionPerformed
    
    
                                                         
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SportsHub.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SportsHub.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SportsHub.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SportsHub.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SportsHub().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> jCmbAtp;
    private javax.swing.JComboBox<String> jCmbPlayerSearch;
    private javax.swing.JComboBox<String> jCobTeamSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jPnlProgramm;
    private javax.swing.JTextField jSbSearchTeam;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton jbtnAddFavTeam;
    private javax.swing.JButton jbtnFindOutMore;
    private javax.swing.JButton jbtnFindPlayer;
    private javax.swing.JButton jbtnFindTeam;
    private javax.swing.JButton jbtnGetATP;
    private javax.swing.JButton jbtnRemovePlayer;
    private javax.swing.JButton jbtnRemoveTeam;
    private javax.swing.JButton jbtnSaveData;
    private javax.swing.JLabel jlablAtpRanking;
    private javax.swing.JLabel jlblAway;
    private javax.swing.JLabel jlblFavIcon;
    private javax.swing.JLabel jlblFavName;
    private javax.swing.JLabel jlblHome;
    private javax.swing.JLabel jlblInfo1;
    private javax.swing.JLabel jlblInfo2;
    private javax.swing.JLabel jlblInfo3;
    private javax.swing.JLabel jlblLiveInfo;
    private javax.swing.JLabel jlblLiveStatus;
    private javax.swing.JLabel jlblPlayerPicSearch;
    private javax.swing.JLabel jlblTeamPic;
    private javax.swing.JLabel jlblTennisPlayer;
    private javax.swing.JList<String> jlstSports;
    private javax.swing.JList<String> jlstTournaments;
    private javax.swing.JLabel jpnlAtpNationality;
    private javax.swing.JLabel jpnlAtpTennis;
    private javax.swing.JLabel jpnlAtpTournaments;
    private javax.swing.JPanel jpnlIcon;
    private javax.swing.JLabel jpnlPsearchAge;
    private javax.swing.JLabel jpnlPsearchHeight;
    private javax.swing.JLabel jpnlPsearchName;
    private javax.swing.JLabel jpnlPsearchNationality;
    private javax.swing.JLabel jpnlPsearchValue;
    private javax.swing.JLabel jpnlTeamCountry;
    private javax.swing.JLabel jpnlTeamManager;
    private javax.swing.JLabel jpnlTeamName;
    private javax.swing.JLabel jpnlTeamVenue;
    private javax.swing.JTable jtblAtp;
    private javax.swing.JTable jtblFavPlayer;
    private javax.swing.JTable jtblFavTeam;
    private javax.swing.JTable jtblPlayer;
    private javax.swing.JTable jtblTeam;
    private javax.swing.JButton jtbnFavPlayer;
    private javax.swing.JTextField jtfSearchBar;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel pnlIcon;
    // End of variables declaration//GEN-END:variables
}
