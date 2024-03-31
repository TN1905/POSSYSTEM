package com.raven.service;

import Utils.db;
import com.raven.entity.users;
import com.raven.model.ModelLogin;
import com.raven.model.ModelUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;

public class ServiceUser {
    
    private final Connection con;

    public ServiceUser() {
        con = db.myCon();
    }

    public void insertUser(users user) throws SQLException {
        PreparedStatement p = con.prepareStatement("insert into account (users_id,users_name,account_type,passwords,email) values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        String code = generateVerifyCode();
        p.setString(1, user.getUserID());
        p.setString(2, user.getUserName());
        p.setInt(3, user.getUserAccountType());
        p.setString(4, user.getUserPassword());
        p.setString(4, user.getEmail());
        p.execute();
        ResultSet r = p.getGeneratedKeys();
        r.first();
        int userID = r.getInt(1);
        r.close();
        p.close();
 
    }
    
    public users login(ModelLogin login)throws SQLException{
        users data = null;
        PreparedStatement p = con.prepareStatement("select users_id,users_name,account_type,passwords,email from users where users_name = ?");
        p.setString(1, login.getUsername());
        ResultSet r = p.executeQuery();
        if(r.next()){
            String userID = r.getString("users_id");
            String userName = r.getString("users_name");
            int account_type = Integer.parseInt(r.getString("account_type"));
            String passwords = r.getString("passwords");
            String email = r.getString("email");
            data = new users(userID,userName,account_type,passwords,email);
            System.out.println(account_type);
        }
        return data;
    }

    private String generateVerifyCode() throws SQLException {
        DecimalFormat df = new DecimalFormat("000000");
        Random ran = new Random();
        String code = df.format(ran.nextInt(1000000));  //  Random from 0 to 999999
        while (checkDuplicateCode(code)) {
            code = df.format(ran.nextInt(1000000));
        }
        return code;
    }

    private boolean checkDuplicateCode(String code) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select UserID from account where VerifyCode=?");
        p.setString(1, code);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            duplicate = true;
        }
        r.close();
        p.close();
        return duplicate;
    }

    public boolean checkDuplicateUser(String user) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select users_id from users where users_name=?");
        p.setString(1, user);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            duplicate = true;
        }
        rs.close();
        p.close();
        return duplicate;
    }

    public boolean checkDuplicateEmail(String user) throws SQLException {
        boolean duplicate = false;
        PreparedStatement p = con.prepareStatement("select users_id from users where email=?");
        p.setString(1, user);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            duplicate = true;
        }
        rs.close();
        p.close();
        return duplicate;
    }

    public void doneVerify(int userID) throws SQLException {
        PreparedStatement p = con.prepareStatement("update account set VerifyCode='', Status='Verified' where UserID=?");
        p.setInt(1, userID);
        p.execute();
        p.close();
    }

    public boolean verifyCodeWithUser(int userID, String code) throws SQLException {
        boolean verify = false;
        PreparedStatement p = con.prepareStatement("select UserID from account where UserID=? and VerifyCode=?");
        p.setInt(1, userID);
        p.setString(2, code);
        ResultSet r = p.executeQuery();
        if (r.first()) {
            verify = true;
        }
        r.close();
        p.close();
        return verify;
    }
}
