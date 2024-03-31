/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

/**
 *
 * @author ADMIN
 */
public class users {
    private String userID;
    private String userName;
    private int userAccountType;
    private String userPassword;
    private String email; 

    public users(String userID, String userName, int userAccountType, String userPassword, String email) {
        this.userID = userID;
        this.userName = userName;
        this.userAccountType = userAccountType;
        this.userPassword = userPassword;
        this.email = email;
    }
    


    public users() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(int userAccountType) {
        this.userAccountType = userAccountType;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
