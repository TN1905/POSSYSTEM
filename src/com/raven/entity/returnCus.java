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
public class returnCus {
    private int returnCus_id;
    private int invoice_id;
    private String product_name;
    private int quantity;
    private float unit_price;
    private float amount;
    private String customer_name;
    private String description;
    private Date returnCus_date;
    private String userID;

    public returnCus(int returnCus_id, int invoice_id, String product_name, int quantity, float unit_price, float amount, String customer_name, String description, Date returnCus_date, String userID) {
        this.returnCus_id = returnCus_id;
        this.invoice_id = invoice_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.amount = amount;
        this.customer_name = customer_name;
        this.description = description;
        this.returnCus_date = returnCus_date;
        this.userID = userID;
    }

    public returnCus() {
    }

    public int getReturnCus_id() {
        return returnCus_id;
    }

    public void setReturnCus_id(int returnCus_id) {
        this.returnCus_id = returnCus_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReturnCus_date() {
        return returnCus_date;
    }

    public void setReturnCus_date(Date returnCus_date) {
        this.returnCus_date = returnCus_date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    
    
    
    
}
