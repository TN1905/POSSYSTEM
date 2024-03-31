/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

/**
 *
 * @author ADMIN
 */
public class customer {
    private String cusID;
    private String cusName;
    private int cusNumber;
    private String cusEmail;
    private String cusCity;
    private String billCusAdd;
    private String shipCusAdd;
    private String cusBank;
    private int cusBankNo;
    private String cusContactPerson;
    private String cusEmailPerson;
    private int cusNumberPerson;
    private String cusOnline;
    private String otherCusInfo;
    private String cusDescription;
    private String cusUserID;
    private String status;

    public customer(String cusID, String cusName, int cusNumber, String cusEmail, String cusCity, String billCusAdd, String shipCusAdd, String cusBank, int cusBankNo, String cusContactPerson, String cusEmailPerson, int cusNumberPerson, String cusOnline, String otherCusInfo, String cusDescription, String cusUserID, String status) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusNumber = cusNumber;
        this.cusEmail = cusEmail;
        this.cusCity = cusCity;
        this.billCusAdd = billCusAdd;
        this.shipCusAdd = shipCusAdd;
        this.cusBank = cusBank;
        this.cusBankNo = cusBankNo;
        this.cusContactPerson = cusContactPerson;
        this.cusEmailPerson = cusEmailPerson;
        this.cusNumberPerson = cusNumberPerson;
        this.cusOnline = cusOnline;
        this.otherCusInfo = otherCusInfo;
        this.cusDescription = cusDescription;
        this.cusUserID = cusUserID;
        this.status = status;
    }

    public customer() {
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public int getCusNumber() {
        return cusNumber;
    }

    public void setCusNumber(int cusNumber) {
        this.cusNumber = cusNumber;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusCity() {
        return cusCity;
    }

    public void setCusCity(String cusCity) {
        this.cusCity = cusCity;
    }

    public String getBillCusAdd() {
        return billCusAdd;
    }

    public void setBillCusAdd(String billCusAdd) {
        this.billCusAdd = billCusAdd;
    }

    public String getShipCusAdd() {
        return shipCusAdd;
    }

    public void setShipCusAdd(String shipCusAdd) {
        this.shipCusAdd = shipCusAdd;
    }

    public String getCusBank() {
        return cusBank;
    }

    public void setCusBank(String cusBank) {
        this.cusBank = cusBank;
    }

    public int getCusBankNo() {
        return cusBankNo;
    }

    public void setCusBankNo(int cusBankNo) {
        this.cusBankNo = cusBankNo;
    }

    public String getCusContactPerson() {
        return cusContactPerson;
    }

    public void setCusContactPerson(String cusContactPerson) {
        this.cusContactPerson = cusContactPerson;
    }

    public String getCusEmailPerson() {
        return cusEmailPerson;
    }

    public void setCusEmailPerson(String cusEmailPerson) {
        this.cusEmailPerson = cusEmailPerson;
    }

    public int getCusNumberPerson() {
        return cusNumberPerson;
    }

    public void setCusNumberPerson(int cusNumberPerson) {
        this.cusNumberPerson = cusNumberPerson;
    }

    public String getCusOnline() {
        return cusOnline;
    }

    public void setCusOnline(String cusOnline) {
        this.cusOnline = cusOnline;
    }

    public String getOtherCusInfo() {
        return otherCusInfo;
    }

    public void setOtherCusInfo(String otherCusInfo) {
        this.otherCusInfo = otherCusInfo;
    }

    public String getCusDescription() {
        return cusDescription;
    }

    public void setCusDescription(String cusDescription) {
        this.cusDescription = cusDescription;
    }

    public String getCusUserID() {
        return cusUserID;
    }

    public void setCusUserID(String cusUserID) {
        this.cusUserID = cusUserID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    

    
    
    
}
