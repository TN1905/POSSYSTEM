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
public class invoice {
    private int invoiceID;
    private Date invoiceDate;
    private String invoiceTime;
    private String invoiceCusName;
    private int invoiceQuanlity;
    private float invoiceTotalAmount;
    private String invoiceStatus;
    private float invoicePaidAmount;
    private float invoiceDueBalance;
    private String invoicePaymentType;
    private String invoiceNote;
    private String invoiceUser;

    public invoice(int invoiceID, Date invoiceDate, String invoiceTime, String invoiceCusName, int invoiceQuanlity, float invoiceTotalAmount, String invoiceStatus, float invoicePaidAmount, float invoiceDueBalance, String invoicePaymentType, String invoiceNote, String invoiceUser) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.invoiceTime = invoiceTime;
        this.invoiceCusName = invoiceCusName;
        this.invoiceQuanlity = invoiceQuanlity;
        this.invoiceTotalAmount = invoiceTotalAmount;
        this.invoiceStatus = invoiceStatus;
        this.invoicePaidAmount = invoicePaidAmount;
        this.invoiceDueBalance = invoiceDueBalance;
        this.invoicePaymentType = invoicePaymentType;
        this.invoiceNote = invoiceNote;
        this.invoiceUser = invoiceUser;
    }

    public invoice() {
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getInvoiceCusName() {
        return invoiceCusName;
    }

    public void setInvoiceCusName(String invoiceCusName) {
        this.invoiceCusName = invoiceCusName;
    }

    public int getInvoiceQuanlity() {
        return invoiceQuanlity;
    }

    public void setInvoiceQuanlity(int invoiceQuanlity) {
        this.invoiceQuanlity = invoiceQuanlity;
    }

    public float getInvoiceTotalAmount() {
        return invoiceTotalAmount;
    }

    public void setInvoiceTotalAmount(float invoiceTotalAmount) {
        this.invoiceTotalAmount = invoiceTotalAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public float getInvoicePaidAmount() {
        return invoicePaidAmount;
    }

    public void setInvoicePaidAmount(float invoicePaidAmount) {
        this.invoicePaidAmount = invoicePaidAmount;
    }

    public float getInvoiceDueBalance() {
        return invoiceDueBalance;
    }

    public void setInvoiceDueBalance(float invoiceDueBalance) {
        this.invoiceDueBalance = invoiceDueBalance;
    }

    public String getInvoicePaymentType() {
        return invoicePaymentType;
    }

    public void setInvoicePaymentType(String invoicePaymentType) {
        this.invoicePaymentType = invoicePaymentType;
    }

    public String getInvoiceNote() {
        return invoiceNote;
    }

    public void setInvoiceNote(String invoiceNote) {
        this.invoiceNote = invoiceNote;
    }

    public String getInvoiceUser() {
        return invoiceUser;
    }

    public void setInvoiceUser(String invoiceUser) {
        this.invoiceUser = invoiceUser;
    }

    
    
}
