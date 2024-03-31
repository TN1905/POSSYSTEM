
package com.raven.main;

import static Utils.Auth.user;
import Utils.MsgBox;
import Utils.XDate;
import Utils.db;
import com.raven.component.ForgotPass;
import com.raven.component.Message;
import com.raven.component.Message.MessageType;
import com.raven.component.PanelCover;
import com.raven.component.PanelLoading;
import com.raven.component.PanelLoginAndRegister;
import com.raven.component.PanelVerifyCode;
import com.raven.dao.userDAO;
import com.raven.entity.users;
import com.raven.model.ModelLogin;
import com.raven.model.ModelMessage;
import com.raven.model.ModelUser;
import com.raven.service.ServiceMail;

import com.raven.service.ServiceUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import com.raven.component.TypeEmail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Time;
import java.time.LocalDateTime;

public class Main1 extends javax.swing.JFrame {
    userDAO userdao = new userDAO();
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize=61;
    private final DecimalFormat df = new DecimalFormat("##0.###",DecimalFormatSymbols.getInstance(Locale.US));
    private ServiceUser service;
    int checkEmail;
    boolean check2lop;
    users user11;
    public void setUser(users getUser){
        this.user11 = getUser;
    }
    private ForgotPass typeEmail;
    public Main1() {
        initComponents();
        init(); 
        
    }
    
    private void init(){
        service = new ServiceUser();
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        typeEmail = new ForgotPass();
        ActionListener evenRegister = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            register();
        }
        };
        ActionListener eventLogin = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               login();
            }
            
        };
        ActionListener eventForgot = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
               forgot();
            }
            
        };
        
        loginAndRegister = new PanelLoginAndRegister(evenRegister,eventLogin,eventForgot);
        setUser(loginAndRegister.getUser());
        TimingTarget target = new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin; 
                double size=coverSize;
                if(fraction<=0.5f){
                    size+=fraction*size;
                }else{
                    size+= addSize - fraction*addSize;
                }
                if(isLogin){
                    fractionCover = 1f-fraction;
                    fractionLogin= fraction;
                    if(fraction>=0.5f){
                        cover.registerRight(fractionCover*100);
                    }else{
                        cover.loginRight(fractionLogin * 100);
                    }
                }else{
                    fractionCover = fraction;
                    fractionLogin = 1f- fraction;
                    if(fraction<=0.5f){
                        cover.registerLeft(fraction*100);
                    }else{
                        cover.loginLeft((1f-fraction)*100);
                    }
                }
                if(fraction>=0.5f){
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size+ "%, pos" + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize+ "%, pos" + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin=!isLogin;
            }
        };
        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0); // for smooth animation
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        bg.add(loading,"pos 0 0 100% 100%");
        bg.add(verifyCode,"pos 0 0 100% 100%");
        bg.add(cover,"width " +coverSize+ "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister,"width "+loginSize+"%, pos 1al 0 n 100%"); // 1al as 100%
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!animator.isRunning()){
                    animator.start();
                }
            }
        });
        
        
        verifyCode.addEventButtonOK(new ActionListener(){
            
            boolean kiemtra = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = "SELECT * FROM TimeRecord WHERE TimeRecord.TimeRecord >= DATEADD(minute, -3, GETDATE()) AND TimeStampID = '"+user11.getEmail()+"'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    if (rs.next()) {
                        if (verifyCode.checkVerify().equals(rs.getString("code"))) {

                            kiemtra = true;
                            System.out.println(check2lop);
                            showMessage(Message.MessageType.SUCCESS, "Register Success");
                            verifyCode.setVisible(false);

                        } else {
                            kiemtra = false;
                            showMessage(MessageType.ERROR, "Verify code incorrect");
                        }

                    } else {
                        MsgBox.alert(null, "Mã xác nhận quá 3 phút");
                        kiemtra = false;
                    }
                    
                    if(kiemtra==true){
                       
                        userdao.insert(user11);
                        user11 = new users();
                    }
                    
                } catch (Exception ex) {
                    showMessage(MessageType.ERROR,"Error");
                    System.out.println(ex);
                }
            }
        });
        
        
    }
 
    LocalDateTime now = LocalDateTime.now();

    // Định dạng kiểu LocalDateTime thành chuỗi
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    
    private String updateFormattedDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        formattedDateTime = now.format(formatter);
        return formattedDateTime;
    }

    private void register(){
        check2lop = false;
        users user = loginAndRegister.getUser();
        String user1 = user.getUserID();
        String username1 = user.getUserName();
        int userAccountType = user.getUserAccountType();
        String userPassword1 = user.getUserPassword();
        String userEmail1 = user.getEmail();
        user11= new users(user1,username1,userAccountType,userPassword1,userEmail1);
        System.out.println(user.getUserID());
        System.out.println(user.getUserName());
        System.out.println(user.getUserAccountType());
        System.out.println(user.getEmail());
        System.out.println(user.getUserPassword());
        String formattedDateTime1 = updateFormattedDateTime();
        try {
            if(service.checkDuplicateUser(user.getUserName())){
                showMessage(Message.MessageType.ERROR,"User name already exsist");
                
            }else if(service.checkDuplicateEmail(user.getEmail())){
                showMessage(Message.MessageType.ERROR,"Email already exsist");
            }else if(user.getUserName().equals("")){
                showMessage(Message.MessageType.ERROR,"User name not fill");
            }else if(user.getUserPassword().equals("")){
                showMessage(Message.MessageType.ERROR,"Pass not fill or not same");
            }else if(user.getEmail().equals("")){
                showMessage(Message.MessageType.ERROR,"Email not fill");
            }else if(user.getUserAccountType()==-1){
                showMessage(Message.MessageType.ERROR,"Account type not fill");
            }else{         
                Random rand = new Random();
                checkEmail = rand.nextInt(900000) + 100000;
                System.out.println(checkEmail);
                sendMail(user);
                try {
                    String sql = "SELECT * FROM TimeRecord WHERE TimeStampID = ?";
                    try (PreparedStatement ps = db.myCon().prepareStatement(sql)) {
                        ps.setString(1, user.getEmail());
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                String updateSql = "UPDATE TimeRecord SET TimeRecord = ?, code = ? WHERE TimeStampID = ?";
                                try (PreparedStatement updatePs = db.myCon().prepareStatement(updateSql)) {
                                    updatePs.setString(1, formattedDateTime1);
                                    updatePs.setString(2, String.valueOf(checkEmail));
                                    updatePs.setString(3, user.getEmail());
                                    updatePs.executeUpdate();
                                }
                            } else {
                                String insertSql = "INSERT INTO TimeRecord(TimeStampID, TimeRecord, code) VALUES (?, CONVERT(DATETIME, ?, 120), ?)";
                                try (PreparedStatement insertPs = db.myCon().prepareStatement(insertSql)) {
                                    insertPs.setString(1, user.getEmail());
                                    insertPs.setString(2, formattedDateTime1);
                                    insertPs.setString(3, String.valueOf(checkEmail));
                                    insertPs.executeUpdate();
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println(check2lop);
            }
           
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR,"Error Register");
            System.out.println(e);
          
        }
    }
    
    private void forgot(){
        this.dispose();
        typeEmail.setVisible(true);
    }
    
    
    
    private void login(){
        ModelLogin data = loginAndRegister.getDataLogin();
        System.out.println(data);
        
        try {
            users user = service.login(data);
            System.out.println(user.getUserName());
            System.out.println(user.getUserAccountType());
            if(data!=null){
                this.dispose();
                Main.main(user);
            }else{
                showMessage(Message.MessageType.ERROR,"Email and Password incorrect");
            }
        } catch (Exception e) {
        }
    }
    

    
    public ModelMessage sendMailCode(String toMail){
                ModelMessage ms = new ModelMessage(false,"");
                try {
                    Properties p = new Properties();
                    p.put("mail.smtp.auth", "true");
                    p.put("mail.smtp.starttls.enable", "true");
                    p.put("mail.smtp.host","smtp.gmail.com");
                    p.put("mail.smtp.port", 587);
                    p.put("mail.smtp.ssl.trust", "*");
                    
                    String accountName = "0tringuyen46@gmail.com";
                    String accountPass = "uehsokvuasrjtert";
                    
                    Session ss = Session.getInstance(p,
                            new javax.mail.Authenticator(){
                                protected PasswordAuthentication getPasswordAuthentication(){
                                    return new PasswordAuthentication(accountName,accountPass);
                                }
                            });
                    
                    String from = "0tringuyen46@gmail.com";
                    String tos = toMail;
                    System.out.println(tos);
                    
                    String subject = "Mã xác nhận";
                    String body = String.valueOf(checkEmail);
                    
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
                    ms.setSuccess(true);
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
                        if(e.getMessage().equals("Invalid Addresses")){
                            ms.setMessage("Invalid email");
                        }else{
                            ms.setMessage("Error");
                        }
                }
            
                return ms;
    }
    
    private void sendMail(users user){
        new Thread(new Runnable(){
            @Override
            public void run(){
                loading.setVisible(true);
                ModelMessage ms = sendMailCode(user.getEmail());
                if(ms.isSuccess()){
                    loading.setVisible(false);
                    verifyCode.setVisible(true);
                }else{
                    loading.setVisible(false);
                    showMessage(MessageType.ERROR,ms.getMessage());
                }
            }
        }).start();
    }
    
    private void showMessage(Message.MessageType messageType, String message){
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter(){
            @Override
            public void begin() {
                if(!ms.isShow()){
                    bg.add(ms,"pos 0.5al -30",0); // insert to bg fist index 0 
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if(ms.isShow()){
                    f = 40*(1f-fraction);
                }else{
                    f = 40*fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int)(f-30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if(ms.isShow()){
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                }else{
                    ms.setShow(true);
                }
            }
            
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    animator.start();
                }catch(InterruptedException e){
                    System.err.println(e);
                }
            }
        }).start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 693, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bg)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
