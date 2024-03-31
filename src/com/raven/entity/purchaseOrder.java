/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class purchaseOrder {
    private int POID;
    private Date PODate;
    private String POTime;
    private String POSupName;
    private String POProduct;
    private int POQuanlity;
    private float POTotalAmount;
    private String POStatus;
    private float POPaidAmount;
    private float PODueBalance;
    private String POPaymentType;
    private String PONote;
    private String POUser;

    public purchaseOrder(int POID, Date PODate, String POTime, String POSupName, String POProduct, int POQuanlity, float POTotalAmount, String POStatus, float POPaidAmount, float PODueBalance, String POPaymentType, String PONote, String POUser) {
        this.POID = POID;
        this.PODate = PODate;
        this.POTime = POTime;
        this.POSupName = POSupName;
        this.POProduct = POProduct;
        this.POQuanlity = POQuanlity;
        this.POTotalAmount = POTotalAmount;
        this.POStatus = POStatus;
        this.POPaidAmount = POPaidAmount;
        this.PODueBalance = PODueBalance;
        this.POPaymentType = POPaymentType;
        this.PONote = PONote;
        this.POUser = POUser;
    }

    public purchaseOrder() {
    }

    public int getPOID() {
        return POID;
    }

    public void setPOID(int POID) {
        this.POID = POID;
    }

    public Date getPODate() {
        return PODate;
    }

    public void setPODate(Date PODate) {
        this.PODate = PODate;
    }

    public String getPOTime() {
        return POTime;
    }

    public void setPOTime(String POTime) {
        this.POTime = POTime;
    }

    public String getPOSupName() {
        return POSupName;
    }

    public void setPOSupName(String POSupName) {
        this.POSupName = POSupName;
    }

    public String getPOProduct() {
        return POProduct;
    }

    public void setPOProduct(String POProduct) {
        this.POProduct = POProduct;
    }

    public int getPOQuanlity() {
        return POQuanlity;
    }

    public void setPOQuanlity(int POQuanlity) {
        this.POQuanlity = POQuanlity;
    }

    public float getPOTotalAmount() {
        return POTotalAmount;
    }

    public void setPOTotalAmount(float POTotalAmount) {
        this.POTotalAmount = POTotalAmount;
    }

    public String getPOStatus() {
        return POStatus;
    }

    public void setPOStatus(String POStatus) {
        this.POStatus = POStatus;
    }

    public float getPOPaidAmount() {
        return POPaidAmount;
    }

    public void setPOPaidAmount(float POPaidAmount) {
        this.POPaidAmount = POPaidAmount;
    }

    public float getPODueBalance() {
        return PODueBalance;
    }

    public void setPODueBalance(float PODueBalance) {
        this.PODueBalance = PODueBalance;
    }

    public String getPOPaymentType() {
        return POPaymentType;
    }

    public void setPOPaymentType(String POPaymentType) {
        this.POPaymentType = POPaymentType;
    }

    public String getPONote() {
        return PONote;
    }

    public void setPONote(String PONote) {
        this.PONote = PONote;
    }

    public String getPOUser() {
        return POUser;
    }

    public void setPOUser(String POUser) {
        this.POUser = POUser;
    }

    
    
    
}
