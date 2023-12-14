/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.finalproject.panels;

import com.mycompany.finalproject.classes.SportTournamentSingleResponse;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author conradkadel
 */
public class MatchDisplay extends javax.swing.JFrame {

    /**
     * Creates new form PlayerDisplay
     * This match display will receive a match and then load and will the speicific
     * fields 
     */
    public MatchDisplay(SportTournamentSingleResponse m) {
        initComponents();
        
        BufferedImage imageHome = null;
        BufferedImage imageAway = null;

        try {
            URL url= new URL(m.getHomeTeam().getLogo());
            imageHome = ImageIO.read(url);
            Image newImageHome = imageHome.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            URL url2 = new URL(m.getAwayTeam().getLogo());
            imageAway = ImageIO.read(url2);
            Image newImageAway = imageAway.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            jlblHomePic.setIcon(new ImageIcon(newImageHome));
            jlblAwayPic.setIcon(new ImageIcon(newImageAway));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"No Picture Found");
        }
        
        jlblSeason.setText(m.getSeason().name);
        jlblTitle.setText(m.getName());
        if(m.getTime().played > 0){
            StringBuilder time = new StringBuilder();
            String t = m.getTime().played + "";
            for(int i = 0; i< t.length(); i++) {
                // access each character
                char a = t.charAt(i);
                if(i == 2){ // if white space then dont append
                    time.append(":");
                }
                
                time.append(a);
            jlblTime.setText(time.toString());
        }
            jlblTime.setText("" + m.getTime().played);
        }else{
             jlblTime.setText("No Time Found");
        }
        if(m.getHomeScore().points > -1){
            jlblHomePoints.setText("" + m.getHomeScore().points);
        }
        else{
            jlblHomePoints.setText("Nothing Found");
        }
        if(m.getHomeTeam().getName().length() > 0){
            jlblHomeName.setText(m.getHomeTeam().getName());
        } else{
            jlblHomeName.setText("Nothing Found");
        }
        if(m.getMatchOdds() != null || m.getMatchOdds().homeOdds.value > 0){
            jlblHomeOdds.setText("Odds :" + m.getMatchOdds().homeOdds.value);
        } else{
            jlblHomePoints.setText("Nothing Found");
        }
        if(m.getAwayScore() != null || m.getAwayScore().points > -1){
            jlblAwayPoints.setText("" + m.getAwayScore().points);
        } else{
            jlblAwayPoints.setText("Nothing Found");
        }
        if(m.getAwayTeam() != null || m.getAwayTeam().getName().length()>0){
            jlblAwayName.setText(m.getAwayTeam().getName());
        } else{
            jlblAwayName.setText("Nothing Found");
        }
        if(m.getMatchOdds()!= null || m.getMatchOdds().awayOdds.value > 0){
            jlblAwayOdds.setText("Odds :" + m.getMatchOdds().awayOdds.value);
        }
        else{
            jlblAwayOdds.setText("Nothing Found");
        }
    }
    
 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jlblAwayPic = new javax.swing.JLabel();
        jlblHomePoints = new javax.swing.JLabel();
        jlblAwayPoints = new javax.swing.JLabel();
        jlblHomeOdds = new javax.swing.JLabel();
        jlblAwayName = new javax.swing.JLabel();
        jlblAwayOdds = new javax.swing.JLabel();
        jlblTitle = new javax.swing.JLabel();
        jlblHomeName = new javax.swing.JLabel();
        jlblTime = new javax.swing.JLabel();
        jlblSeason = new javax.swing.JLabel();
        jlblHomePic = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 238, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INFO");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Head 2 Head");

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jlblHomePoints.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlblHomePoints.setForeground(new java.awt.Color(0, 153, 0));
        jlblHomePoints.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblHomePoints.setText("Points");

        jlblAwayPoints.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlblAwayPoints.setForeground(new java.awt.Color(0, 153, 0));
        jlblAwayPoints.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblAwayPoints.setText("Points");

        jlblHomeOdds.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlblHomeOdds.setForeground(new java.awt.Color(0, 153, 0));
        jlblHomeOdds.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblHomeOdds.setText("Odds");

        jlblAwayName.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlblAwayName.setForeground(new java.awt.Color(0, 153, 0));
        jlblAwayName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblAwayName.setText("Name");

        jlblAwayOdds.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlblAwayOdds.setForeground(new java.awt.Color(0, 153, 0));
        jlblAwayOdds.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblAwayOdds.setText("Odds");

        jlblTitle.setFont(new java.awt.Font("Impact", 0, 30)); // NOI18N
        jlblTitle.setForeground(new java.awt.Color(0, 153, 0));
        jlblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTitle.setText("Name");

        jlblHomeName.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jlblHomeName.setForeground(new java.awt.Color(0, 153, 0));
        jlblHomeName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblHomeName.setText("Name");

        jlblTime.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        jlblTime.setForeground(new java.awt.Color(0, 153, 0));
        jlblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblTime.setText("Time");

        jlblSeason.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jlblSeason.setForeground(new java.awt.Color(0, 153, 0));
        jlblSeason.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblSeason.setText("Season");

        jLabel2.setFont(new java.awt.Font("Impact", 0, 28)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("--Score--");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jlblHomePic, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblSeason, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jlblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlblAwayPic, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblHomeOdds, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblHomeName, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblHomePoints, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblAwayName, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblAwayOdds, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlblAwayPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(65, 65, 65))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jlblTitle)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlblAwayPic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblHomePic, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jlblSeason)
                        .addGap(38, 38, 38)
                        .addComponent(jlblTime)))
                .addGap(17, 17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblAwayPoints, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblHomePoints, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblHomeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblAwayName, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblHomeOdds, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblAwayOdds, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MatchDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MatchDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MatchDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MatchDisplay.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jlblAwayName;
    private javax.swing.JLabel jlblAwayOdds;
    private javax.swing.JLabel jlblAwayPic;
    private javax.swing.JLabel jlblAwayPoints;
    private javax.swing.JLabel jlblHomeName;
    private javax.swing.JLabel jlblHomeOdds;
    private javax.swing.JLabel jlblHomePic;
    private javax.swing.JLabel jlblHomePoints;
    private javax.swing.JLabel jlblSeason;
    private javax.swing.JLabel jlblTime;
    private javax.swing.JLabel jlblTitle;
    // End of variables declaration//GEN-END:variables
}
