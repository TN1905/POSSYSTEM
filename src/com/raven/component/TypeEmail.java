package com.raven.component;

import Utils.db;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import com.raven.component.VerifyCode;
//import com.raven.main.Main1;
public class TypeEmail extends javax.swing.JPanel {
    String email1;
//    Main1 main1 = new Main1();
    int check;
    VerifyCode verifyCode;
    public TypeEmail() {
        initComponents();
        setOpaque(false);
        setFocusCycleRoot(true);
        super.setVisible(false);
        addMouseListener(new MouseAdapter() {
        });
        verifyCode = new VerifyCode();
    }

    @Override
    public void setVisible(boolean bln) {
        super.setVisible(bln);
        if (bln) {
            txtCode.grabFocus();
            txtCode.setText("");
        }
    }
    
    public void sendMail(){


                try {
                    Properties p = new Properties();
                    p.put("mail.smtp.auth", "true");
                    p.put("mail.smtp.starttls.enable", "true");
                    p.put("mail.smtp.host","smtp.gmail.com");
                    p.put("mail.smtp.port", 587);
                    p.put("mail.smtp.ssl.trust", "*");
                    
                    String accountName = "0tringuyen46@gmail.com";
                    String accountPass = "fejkeucwhqldwsda";
                    
                    Session ss = Session.getInstance(p,
                            new javax.mail.Authenticator(){
                                protected PasswordAuthentication getPasswordAuthentication(){
                                    return new PasswordAuthentication(accountName,accountPass);
                                }
                            });
                    
                    String from = "0tringuyen46@gmail.com";
                    String tos = txtCode.getText();
                    System.out.println(tos);
                    
                    String subject = "Mã xác nhận";
                    String body = String.valueOf(check);
                    
                    String ccmail = "0tringuyen46@gmail.com";
                    
                    javax.mail.Message msg = new MimeMessage(ss);
                    msg.setFrom(new InternetAddress(from));
                    msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(tos));
                    msg.addRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(ccmail));
                    
                    MimeBodyPart contentPart = new MimeBodyPart();
                    contentPart.setContent(body,"text/html; charset=utf-8");
                    
                    msg.setSubject(subject);
                    msg.setText(body);
                    // 2 dòng này bắt buộc ở trên
                    
  
                    
                    MimeMultipart multipart = new MimeMultipart();
                    
                    multipart.addBodyPart(contentPart);
                    
                    msg.setContent(multipart);
                    
                    Transport.send(msg);
                    
                    JOptionPane.showMessageDialog(null, "Mail was sent successfully", "Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
       
                } catch (Exception e) {
                    System.out.println(e);
                }
            

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new com.raven.swing.PanelRound();
        txtCode = new com.raven.swing.MyTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmdOK = new com.raven.swing.ButtonOutLine();
        cmdCancel = new com.raven.swing.ButtonOutLine();

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));

        txtCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(63, 63, 63));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Email");

        jLabel2.setForeground(new java.awt.Color(63, 63, 63));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Check your mail to get verify code");

        cmdOK.setBackground(new java.awt.Color(18, 138, 62));
        cmdOK.setText("Send");
        cmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOKActionPerformed(evt);
            }
        });

        cmdCancel.setBackground(new java.awt.Color(192, 25, 25));
        cmdCancel.setText("Cancel");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(100, 100, 100))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void cmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOKActionPerformed
        String email = txtCode.getText();
        try {
            String sql = "SELECT email FROM users WHERE email = '"+email+"'";
            Statement ss = db.myCon().createStatement();
            ResultSet rs = ss.executeQuery(sql);
            if(rs.next()){
                Random rand = new Random();
                check = rand.nextInt(900000) + 100000;
                email1 = txtCode.getText();
                sendMail();
                this.setVisible(false);
                verifyCode.setVisible(true);
            }else{
                
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cmdOKActionPerformed
    
    public void sendMail1(){
        
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(grphcs);
    }

    public String getInputCode() {
        return txtCode.getText().trim();
    }

    public void addEventButtonOK(ActionListener event) {
        cmdOK.addActionListener(event);
    }
    
    public String checkVerify(){
        String verify = txtCode.getText();
        return verify;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.ButtonOutLine cmdCancel;
    private com.raven.swing.ButtonOutLine cmdOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.raven.swing.PanelRound panelRound1;
    private com.raven.swing.MyTextField txtCode;
    // End of variables declaration//GEN-END:variables
}
