
package com.raven.component;

import Utils.db;
import com.raven.entity.users;
import com.raven.form.MaHoa;
import com.raven.model.ModelLogin;
import com.raven.model.ModelUser;
import com.raven.swing.Button;
import com.raven.swing.MyPasswordField;
import com.raven.swing.MyTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;
import com.raven.component.TypeEmail;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {
    private users user;
    private ModelLogin dataLogin;
    int checkEmail;
    public ForgotPass typeEmail;
     List<String> loggedInAccounts = loadLoggedInAccountsFromDatabase();
    public users getUser(){
        return user;
    }
    
    public ModelLogin getDataLogin(){
        return dataLogin;
    }
    
    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin, ActionListener eventForgot) {
        initComponents();
        initRegister(eventRegister);
        initLogin(eventLogin,eventForgot);
        login.setVisible(false);
        register.setVisible(true);
        
        typeEmail = new ForgotPass();
    }
    
    

    public void initRegister(ActionListener eventRegister){
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]10[]35[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sansserif",1,30));
        label.setForeground(new Color(7,164,121));
        register.add(label);
        // đoạn này tạo textfield mới xem video phần 1 phút 24
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/user.png")));
        txtUser.setHint("Name");
        register.add(txtUser,"w 60%");
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/mail.png")));
        txtEmail.setHint("Email");
        register.add(txtEmail,"w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPass.setSuffixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/eye20.png")));
        txtPass.setHint("Password");
        register.add(txtPass,"w 60%");
        MyPasswordField txtPassConfirm = new MyPasswordField();
        txtPassConfirm.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPassConfirm.setSuffixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/eye20.png")));
        txtPassConfirm.setHint("Password Confirm");
        register.add(txtPassConfirm,"w 60%");
        Button cmd= new Button();
        JPanel online = new JPanel(new MigLayout("align center"));
        online.setBackground(new Color(255, 255, 255));
        // Thêm radio button "Nhân viên"
        JRadioButton employeeRadioButton = new JRadioButton("Nhân viên");
        employeeRadioButton.setOpaque(false); // Để hiển thị nền trong suốt
       

        // Thêm radio button "Khách hàng"
        JRadioButton customerRadioButton = new JRadioButton("Khách hàng");
        customerRadioButton.setOpaque(false); // Để hiển thị nền trong suốt

        
        // Gom nhóm các radio button lại với nhau để chỉ cho phép chọn một trong số chúng
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(employeeRadioButton);
        radioButtonGroup.add(customerRadioButton);
        online.add(employeeRadioButton);
        online.add(customerRadioButton);
        register.add(online);
        
        cmd.setBackground(new Color(7,164,121));
        cmd.setForeground(new Color(250,250,250));
        cmd.addActionListener(eventRegister);
        cmd.setText("SIGN UP");
        register.add(cmd,"w 40%, h 40");
       
        cmd.addActionListener(new ActionListener() {
            boolean kiemtra = false;       
            @Override
            public void actionPerformed(ActionEvent e) {
                int soluong = 0; 
                String userID = "";
                try {
                    String sql = "Select count(*) AS total from users"; 
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    
                    if(rs.next()){
                        soluong = rs.getInt("total");
                        soluong++;
                    }
                    
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                int accountType = -1;
                if(employeeRadioButton.isSelected()){
                    accountType = 1;
                    userID = "emp" + soluong;
                    
                }else if(customerRadioButton.isSelected()){
                    accountType = 2;
                    userID = "cus" + soluong;
                }
            
                String userName = txtUser.getText().trim();
                String email = txtEmail.getText().trim();
                String password= "";
                if(txtPass.getText().equals(txtPassConfirm.getText())){
                    password = MaHoa.hashPassword(txtPass.getText());
                    System.out.println(password);
                }
                

                user = new users(userID,userName,accountType,password,email);
                
                
            }
        });
        
        
        
    }
    
    
  
    
    public void initLogin(ActionListener eventLogin,ActionListener eventForgot){
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label);

        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/mail.png")));
        txtEmail.setHint("Email");
//        txtEmail.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                showSuggestion(loggedInAccounts,txtEmail);
//            }
//        });
        login.add(txtEmail, "w 60%");

        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/pass.png")));
        txtPass.setSuffixIcon(new ImageIcon(getClass().getResource("/com/raven/icon/eye20.png")));
        txtPass.setHint("Password");
        login.add(txtPass, "w 60%");

        JButton cmdForget = new JButton("Forgot your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        cmdForget.addActionListener(eventForgot);
        cmdForget.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            
        });
        Button cmd = new Button();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLogin);
        cmd.setText("SIGN IN");
        login.add(cmd, "w 40%, h 40");

        JPanel online = new JPanel(new MigLayout("align center"));
        online.setBackground(new Color(255, 255, 255));
        JLabel txtOr = new JLabel("Or Sign In With: ");
        JButton cmdFaceBook = new JButton();
        cmdFaceBook.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/facebook.png")));
        cmdFaceBook.setBackground(new Color(255,255,255));
        cmdFaceBook.setBorderPainted(false);
        cmdFaceBook.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton cmdGmail = new JButton();
        cmdGmail.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/google-plus.png")));
        cmdGmail.setBackground(new Color(255,255,255));
        cmdGmail.setBorderPainted(false);
        cmdGmail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton cmdTwitter = new JButton();
        cmdTwitter.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/twitter.png")));
        cmdTwitter.setBackground(new Color(255,255,255));
        cmdTwitter.setBorderPainted(false);
        cmdTwitter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        online.add(txtOr);
        online.add(cmdFaceBook);
        online.add(cmdGmail);
        online.add(cmdTwitter);
        login.add(online);
        cmd.addActionListener(new ActionListener() {
                      
            @Override
            public void actionPerformed(ActionEvent e) {  
                try {
                    String username = txtEmail.getText().trim();
                    String sql = "SELECT * FROM users WHERE users_name = '"+username+"'";  
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);       
                    if(rs.next()){
                        Boolean dungk = MaHoa.checkPassword(txtPass.getText(), rs.getString("passwords"));
                        if(dungk){
                            dataLogin = new ModelLogin(username);
                        }
                        
                    }
                    
                    
                } catch (Exception ex) {
                    System.out.println(ex);
                }
               
            }
        });
    }
    
    private List<String> loadLoggedInAccountsFromDatabase() {
        // Thực hiện các thao tác để tải dữ liệu từ cơ sở dữ liệu
        // Ví dụ: Kết nối cơ sở dữ liệu, truy vấn dữ liệu từ bảng tài khoản đăng nhập
        // và trả về danh sách tài khoản đã đăng nhập
        List<String> loggedInAccounts = new ArrayList<>();
//        try {
//            String sql = "Select usersname from taikhoandangnhap";
//            
//        } catch (Exception e) {
//        }
            
        // ... Đoạn mã để tải danh sách tài khoản đăng nhập từ cơ sở dữ liệu

        // Ví dụ tạm thời với một số giá trị mẫu
        loggedInAccounts.add("user1@example.com");
        loggedInAccounts.add("user2@example.com");
        loggedInAccounts.add("user3@example.com");

        return loggedInAccounts;
    }
    
    private void showSuggestion(List<String> loggedInAccounts,JTextField txtEmail) {
        JPopupMenu popupMenu = new JPopupMenu();

        for (String account : loggedInAccounts) {
            JMenuItem menuItem = new JMenuItem(account);
            menuItem.addActionListener(e -> {
                txtEmail.setText(account);
                popupMenu.setVisible(false);
            });
            popupMenu.add(menuItem);
        }

        if (popupMenu.getComponentCount() > 0) {
            popupMenu.show(txtEmail, 0, txtEmail.getHeight());
        } else {
            popupMenu.setVisible(false);
        }
    }
    
    public void showRegister(boolean show){
        if(show){
            register.setVisible(true);
            login.setVisible(false);
        }else{
            register.setVisible(false);
            login.setVisible(true);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 315, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
