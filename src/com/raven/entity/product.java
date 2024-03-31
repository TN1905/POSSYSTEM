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
public class product {
    private String proID;   
    private int barcode;
    private String proName;
    private String proCateID;
    private String proSupID;
    private float proUnitPrice;
    private float proSalesPrice;
    private int proQuanlity;
    private String defaultProType;
    private String proDescription;
    private Date mfProDate;
    private Date expProDate;
    private String proCompanyName;
    private String proBrandName;
    private String proPrivateNote;
    private String status;
    private String proUserID;

    public product(String proID, int barcode, String proName, String proCateID, String proSupID, float proUnitPrice, float proSalesPrice, int proQuanlity, String defaultProType, String proDescription, Date mfProDate, Date expProDate, String proCompanyName, String proBrandName, String proPrivateNote, String status, String proUserID) {
        this.proID = proID;
        this.barcode = barcode;
        this.proName = proName;
        this.proCateID = proCateID;
        this.proSupID = proSupID;
        this.proUnitPrice = proUnitPrice;
        this.proSalesPrice = proSalesPrice;
        this.proQuanlity = proQuanlity;
        this.defaultProType = defaultProType;
        this.proDescription = proDescription;
        this.mfProDate = mfProDate;
        this.expProDate = expProDate;
        this.proCompanyName = proCompanyName;
        this.proBrandName = proBrandName;
        this.proPrivateNote = proPrivateNote;
        this.status = status;
        this.proUserID = proUserID;
    }

    public product() {
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProCateID() {
        return proCateID;
    }

    public void setProCateID(String proCateID) {
        this.proCateID = proCateID;
    }

    public String getProSupID() {
        return proSupID;
    }

    public void setProSupID(String proSupID) {
        this.proSupID = proSupID;
    }

    public float getProUnitPrice() {
        return proUnitPrice;
    }

    public void setProUnitPrice(float proUnitPrice) {
        this.proUnitPrice = proUnitPrice;
    }

    public float getProSalesPrice() {
        return proSalesPrice;
    }

    public void setProSalesPrice(float proSalesPrice) {
        this.proSalesPrice = proSalesPrice;
    }

    public int getProQuanlity() {
        return proQuanlity;
    }

    public void setProQuanlity(int proQuanlity) {
        this.proQuanlity = proQuanlity;
    }

    public String getDefaultProType() {
        return defaultProType;
    }

    public void setDefaultProType(String defaultProType) {
        this.defaultProType = defaultProType;
    }

    public String getProDescription() {
        return proDescription;
    }

    public void setProDescription(String proDescription) {
        this.proDescription = proDescription;
    }

    public Date getMfProDate() {
        return mfProDate;
    }

    public void setMfProDate(Date mfProDate) {
        this.mfProDate = mfProDate;
    }

    public Date getExpProDate() {
        return expProDate;
    }

    public void setExpProDate(Date expProDate) {
        this.expProDate = expProDate;
    }

    public String getProCompanyName() {
        return proCompanyName;
    }

    public void setProCompanyName(String proCompanyName) {
        this.proCompanyName = proCompanyName;
    }

    public String getProBrandName() {
        return proBrandName;
    }

    public void setProBrandName(String proBrandName) {
        this.proBrandName = proBrandName;
    }

    public String getProPrivateNote() {
        return proPrivateNote;
    }

    public void setProPrivateNote(String proPrivateNote) {
        this.proPrivateNote = proPrivateNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProUserID() {
        return proUserID;
    }

    public void setProUserID(String proUserID) {
        this.proUserID = proUserID;
    }

    
    
}
