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
public class receiveOrder {
    private int receiveProductID;
    private Date receiveOrderDate;
    private String receiveOrderTime;
    private String receiveSupID;
    private String receiveProID;
    private int receiveQuanlity;
    private float receiveTotalAmount;
    private String receiveStatus;
    private float receivePaidAmount;
    private String receivePaymentType;
    private float receiveDueBalance;
    private Date receiveDateOfPay;
    private String receiveNote;
    private int receiveOrderID;
    private String receiveUserID;
    private int purchaseNpOrderID;

    public receiveOrder(int receiveProductID, Date receiveOrderDate, String receiveOrderTime, String receiveSupID, String receiveProID, int receiveQuanlity, float receiveTotalAmount, String receiveStatus, float receivePaidAmount, String receivePaymentType, float receiveDueBalance, Date receiveDateOfPay, String receiveNote, int receiveOrderID, String receiveUserID, int purchaseNpOrderID) {
        this.receiveProductID = receiveProductID;
        this.receiveOrderDate = receiveOrderDate;
        this.receiveOrderTime = receiveOrderTime;
        this.receiveSupID = receiveSupID;
        this.receiveProID = receiveProID;
        this.receiveQuanlity = receiveQuanlity;
        this.receiveTotalAmount = receiveTotalAmount;
        this.receiveStatus = receiveStatus;
        this.receivePaidAmount = receivePaidAmount;
        this.receivePaymentType = receivePaymentType;
        this.receiveDueBalance = receiveDueBalance;
        this.receiveDateOfPay = receiveDateOfPay;
        this.receiveNote = receiveNote;
        this.receiveOrderID = receiveOrderID;
        this.receiveUserID = receiveUserID;
        this.purchaseNpOrderID = purchaseNpOrderID;
    }

    public receiveOrder() {
    }

    public int getReceiveProductID() {
        return receiveProductID;
    }

    public void setReceiveProductID(int receiveProductID) {
        this.receiveProductID = receiveProductID;
    }

    public Date getReceiveOrderDate() {
        return receiveOrderDate;
    }

    public void setReceiveOrderDate(Date receiveOrderDate) {
        this.receiveOrderDate = receiveOrderDate;
    }

    public String getReceiveOrderTime() {
        return receiveOrderTime;
    }

    public void setReceiveOrderTime(String receiveOrderTime) {
        this.receiveOrderTime = receiveOrderTime;
    }

    public String getReceiveSupID() {
        return receiveSupID;
    }

    public void setReceiveSupID(String receiveSupID) {
        this.receiveSupID = receiveSupID;
    }

    public String getReceiveProID() {
        return receiveProID;
    }

    public void setReceiveProID(String receiveProID) {
        this.receiveProID = receiveProID;
    }

    public int getReceiveQuanlity() {
        return receiveQuanlity;
    }

    public void setReceiveQuanlity(int receiveQuanlity) {
        this.receiveQuanlity = receiveQuanlity;
    }

    public float getReceiveTotalAmount() {
        return receiveTotalAmount;
    }

    public void setReceiveTotalAmount(float receiveTotalAmount) {
        this.receiveTotalAmount = receiveTotalAmount;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public float getReceivePaidAmount() {
        return receivePaidAmount;
    }

    public void setReceivePaidAmount(float receivePaidAmount) {
        this.receivePaidAmount = receivePaidAmount;
    }

    public String getReceivePaymentType() {
        return receivePaymentType;
    }

    public void setReceivePaymentType(String receivePaymentType) {
        this.receivePaymentType = receivePaymentType;
    }

    public float getReceiveDueBalance() {
        return receiveDueBalance;
    }

    public void setReceiveDueBalance(float receiveDueBalance) {
        this.receiveDueBalance = receiveDueBalance;
    }

    public Date getReceiveDateOfPay() {
        return receiveDateOfPay;
    }

    public void setReceiveDateOfPay(Date receiveDateOfPay) {
        this.receiveDateOfPay = receiveDateOfPay;
    }

    public String getReceiveNote() {
        return receiveNote;
    }

    public void setReceiveNote(String receiveNote) {
        this.receiveNote = receiveNote;
    }

    public int getReceiveOrderID() {
        return receiveOrderID;
    }

    public void setReceiveOrderID(int receiveOrderID) {
        this.receiveOrderID = receiveOrderID;
    }

    public String getReceiveUserID() {
        return receiveUserID;
    }

    public void setReceiveUserID(String receiveUserID) {
        this.receiveUserID = receiveUserID;
    }

    public int getPurchaseNpOrderID() {
        return purchaseNpOrderID;
    }

    public void setPurchaseNpOrderID(int purchaseNpOrderID) {
        this.purchaseNpOrderID = purchaseNpOrderID;
    }
    
    
}
