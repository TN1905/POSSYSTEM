/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

/**
 *
 * @author ADMIN
 */
public class sales {
    private int salesID;
    private int salesInvoiceID;
    private String salesProductID;
    private int salesProductQuanlity;
    private String salesDefaultType;
    private float salesUnitPrice;
    private float salesTotalPrice;
    private String salesUserID;

    public sales(int salesID, int salesInvoiceID, String salesProductID, int salesProductQuanlity, String salesDefaultType, float salesUnitPrice, float salesTotalPrice, String salesUserID) {
        this.salesID = salesID;
        this.salesInvoiceID = salesInvoiceID;
        this.salesProductID = salesProductID;
        this.salesProductQuanlity = salesProductQuanlity;
        this.salesDefaultType = salesDefaultType;
        this.salesUnitPrice = salesUnitPrice;
        this.salesTotalPrice = salesTotalPrice;
        this.salesUserID = salesUserID;
    }

    public int getSalesID() {
        return salesID;
    }

    public void setSalesID(int salesID) {
        this.salesID = salesID;
    }

    public int getSalesInvoiceID() {
        return salesInvoiceID;
    }

    public void setSalesInvoiceID(int salesInvoiceID) {
        this.salesInvoiceID = salesInvoiceID;
    }

    public String getSalesProductID() {
        return salesProductID;
    }

    public void setSalesProductID(String salesProductID) {
        this.salesProductID = salesProductID;
    }

    public int getSalesProductQuanlity() {
        return salesProductQuanlity;
    }

    public void setSalesProductQuanlity(int salesProductQuanlity) {
        this.salesProductQuanlity = salesProductQuanlity;
    }

    public String getSalesDefaultType() {
        return salesDefaultType;
    }

    public void setSalesDefaultType(String salesDefaultType) {
        this.salesDefaultType = salesDefaultType;
    }

    public float getSalesUnitPrice() {
        return salesUnitPrice;
    }

    public void setSalesUnitPrice(float salesUnitPrice) {
        this.salesUnitPrice = salesUnitPrice;
    }

    public float getSalesTotalPrice() {
        return salesTotalPrice;
    }

    public void setSalesTotalPrice(float salesTotalPrice) {
        this.salesTotalPrice = salesTotalPrice;
    }

    public String getSalesUserID() {
        return salesUserID;
    }

    public void setSalesUserID(String salesUserID) {
        this.salesUserID = salesUserID;
    }
    
    
}
