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

         NOVEMBER 30 - 1h - Made it possible to load the logo of each of the games
                            2 PLayers from the URL and then Display it in 2 JPANLS

         DECEMBER 4 - 3 h - Create a new Panel including a JTable and a search bar
                            where players can be searched by their name from the 
                             API.

           
          
// 
 */
package com.mycompany.finalproject;

import com.google.gson.Gson;
import com.mycompany.finalproject.classes.Match;
import com.mycompany.finalproject.classes.SingleSportResponse;
import com.mycompany.finalproject.classes.Sport;
import com.mycompany.finalproject.classes.SportResponse;
import com.mycompany.finalproject.classes.SportTournamentSearchResponse;
import com.mycompany.finalproject.classes.SportTournamentSingleResponse;
import com.mycompany.finalproject.classes.Tournament;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import com.mycompany.finalproject.classes.CustomListModelMatches;
import com.mycompany.finalproject.classes.CustomListModelSport;
import com.mycompany.finalproject.classes.CustomListModelTournament;
import com.mycompany.finalproject.classes.PlayerSearchResponse;
import com.mycompany.finalproject.classes.SinglePlayerSearchResponse;
import com.mycompany.finalproject.panels.PlayerDisplay;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author conradkadel
 */
public class MainProgram extends javax.swing.JFrame {
    
    private CustomListModelSport listSport = new CustomListModelSport();
    private CustomListModelTournament listTournaments = new CustomListModelTournament();
    private CustomListModelMatches listMatches = new CustomListModelMatches();
    
   
    /**
     * Creates new form MainProgram
     */
    public MainProgram() {
        initComponents();
        
        
        // Loads all Sports and adds them to the jList and ListModel 
        
        SportResponse sports = getSports();
        SingleSportResponse[] sportsList = sports.getSportList();
        // THIS IS TO SAFE DATA FROM API
        /**
        *
        * for(int i= 0 ;i < sports.getSportList().length;i++){
                  listSport.addElement(new Sport(sportsList[i].name,sportsList[i].SportsId));
              }
        */
        listSport.addElement(new Sport("Football",1));
        listSport.addElement(new Sport("Tennis",2));
        listSport.addElement(new Sport("Basketball",3));
        listSport.addElement(new Sport("Ice Hockey",4));
        listSport.addElement(new Sport("Volleyball",5));
        listSport.addElement(new Sport("Handball",6));
       
        
        jlstSports.setModel(listSport);
    }
    private SportResponse getSports(){
        // This Function is used to get all important information about all the Sports and returns a SportResponse
        
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/sports"))
		.header("X-RapidAPI-Key", "e36b9689f4msh312a44da63182a1p15c7c7jsncb47faa179ab")
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
        SportResponse respo = gson.fromJson(myJsonData, SportResponse.class);
        
           return respo;
         
    }
    
    private PlayerSearchResponse getPlayerSearchResponse(String name){
       HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/players/search?page=1&name=" + name))
		.header("X-RapidAPI-Key", "e36b9689f4msh312a44da63182a1p15c7c7jsncb47faa179ab")
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
    PlayerSearchResponse respo = gson.fromJson(myJsonData, PlayerSearchResponse.class);
        return respo;
    }
 
    private SportTournamentSearchResponse getTournamentData(int sportsID){
         // This Function is used to get all important information about all the Sports current live Events and returns a SportTournamentSearchResponse
         
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://sportscore1.p.rapidapi.com/sports/" + sportsID + "/events/live"))
		.header("X-RapidAPI-Key", "e36b9689f4msh312a44da63182a1p15c7c7jsncb47faa179ab")
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
    
     private CustomListModelTournament updateTournamentList(ArrayList list){
        // updates the TournmaentList
        listTournaments = new CustomListModelTournament();
        for(var t : list){
            listTournaments.addElement((Tournament)t);
        }
        return listTournaments;
     
     
    }
     private CustomListModelMatches updateMatchList(ArrayList list){
         // updates the Matchlist
        listMatches = new CustomListModelMatches();
        for(var m : list){
            listMatches.addElement((Match)m);
        }
        return listMatches;
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPnlProgramm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlstSports = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlstTournaments = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jlblHome = new javax.swing.JLabel();
        jlblAway = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jtfSearchBar = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblPLayer = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Find A Live Game ");

        jlstSports.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jlstSports.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jlstSports.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jlstSports.setToolTipText("");
        jlstSports.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jlstSports.setPreferredSize(new java.awt.Dimension(64, 225));
        jlstSports.setValueIsAdjusting(true);
        jlstSports.setVisibleRowCount(6);
        jlstSports.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstSportsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jlstSports);

        jlstTournaments.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jlstTournaments.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlstTournamentsValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jlstTournaments);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sport");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Live Games");

        jButton1.setText("FInd Out More !");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Add to your Favourites !");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(116, 116, 116)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jlblAway, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jlblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80))))))
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlblHome, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlblAway, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPnlProgramm.addTab("Find a Live Game", jPanel1);

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

        jtblPLayer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Nationality", "Sport", "Age"
            }
        ));
        jScrollPane3.setViewportView(jtblPLayer);
        if (jtblPLayer.getColumnModel().getColumnCount() > 0) {
            jtblPLayer.getColumnModel().getColumn(0).setResizable(false);
            jtblPLayer.getColumnModel().getColumn(0).setPreferredWidth(175);
            jtblPLayer.getColumnModel().getColumn(1).setResizable(false);
            jtblPLayer.getColumnModel().getColumn(2).setResizable(false);
            jtblPLayer.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton3.setText("Find out more");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Find Player ");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(185, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jtfSearchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(61, 61, 61))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfSearchBar, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(259, 259, 259))
        );

        jPnlProgramm.addTab("Search for a Player ", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1181, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        jPnlProgramm.addTab("Your Favourites ", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlProgramm)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnlProgramm)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        CustomListModelTournament tournamnt = ((CustomListModelTournament)jlstTournaments.getModel());
        int selectionT = jlstTournaments.getSelectedIndex();
        if(selectionT > -1 && tournamnt.getSize() > 0) {
            JOptionPane.showMessageDialog(null,tournamnt.getFullElementAt(selectionT).getInformation());
            PlayerDisplay playerDisplay = new PlayerDisplay();
            playerDisplay.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,"Nothing FOund");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jlstTournamentsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstTournamentsValueChanged
        // TODO add your handling code here:
        // Getting the selected Index of the Current live Game and then displays Information on the Window

        int newSelection = jlstTournaments.getSelectedIndex();

        BufferedImage imageHome = null;
        BufferedImage imageAway = null;

        ArrayList<Tournament> list = listTournaments.getData();

        try {
            URL url= new URL(list.get(newSelection).getHomeTeam().logo);
            imageHome = ImageIO.read(url);
            Image newImageHome = imageHome.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
            URL url2 = new URL(list.get(newSelection).getAwayTeam().logo);
            imageAway = ImageIO.read(url2);
            Image newImageAway = imageAway.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
            jlblHome.setIcon(new ImageIcon(newImageHome));
            jlblAway.setIcon(new ImageIcon(newImageAway));
            lblInfo.setText(list.get(newSelection).getInformation());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Nothing FOund");
        }

    }//GEN-LAST:event_jlstTournamentsValueChanged

    private void jlstSportsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlstSportsValueChanged
        // TODO add your handling code here:
        // It gets the selected Index and then displays the correct Live Sports for the selected Sport

        int selection = jlstSports.getSelectedIndex();
        CustomListModelTournament listT = new CustomListModelTournament();
        SportTournamentSingleResponse[] responseData = getTournamentData(selection + 1).getData();

        for(SportTournamentSingleResponse r: responseData){
            listT.addElement(new Tournament(r.name,r.id,r.homeTeam,r.awayTeam,r.status));
        }
        
        if(listT.getSize() == 0){
            listT.addElement(new Tournament("No Current Live Game",0,null,null,null));
        }
        listTournaments = listT;

        jlstTournaments.setModel(updateTournamentList(listT.getData()));
    }//GEN-LAST:event_jlstSportsValueChanged

    private void jtfSearchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfSearchBarActionPerformed
        // TODO add your handling code here:
        /// TO ASK IS IF TO PUT BOTH SEACH METHODS IN ONE LIST FOR PLAYERS AND GAMES OR 
        
        
  
    }//GEN-LAST:event_jtfSearchBarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        PlayerSearchResponse pR = getPlayerSearchResponse(jtfSearchBar.getText());
    
        SinglePlayerSearchResponse[] responseList = pR.getData();
        DefaultTableModel model = (DefaultTableModel)jtblPLayer.getModel();
        model.setRowCount(0);
       
        for(SinglePlayerSearchResponse sr : responseList){
            if(sr.getAge() != 0){
                model.addRow(new Object[]{sr.getName(),sr.getNationality(),sr.getSport_id(),sr.getAge()});
            }
        }
    
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jtfSearchBarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfSearchBarFocusGained
        // TODO add your handling code here:
        jtfSearchBar.setText("");
    }//GEN-LAST:event_jtfSearchBarFocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //jPnlProgramm.addTab("test", panel2,);
    }//GEN-LAST:event_jButton3ActionPerformed
        
    
                                                         
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
            java.util.logging.Logger.getLogger(MainProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainProgram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainProgram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jPnlProgramm;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlblAway;
    private javax.swing.JLabel jlblHome;
    private javax.swing.JList<String> jlstSports;
    private javax.swing.JList<String> jlstTournaments;
    private javax.swing.JTable jtblPLayer;
    private javax.swing.JTextField jtfSearchBar;
    private javax.swing.JLabel lblInfo;
    // End of variables declaration//GEN-END:variables
}
