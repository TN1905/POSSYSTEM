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
public class returnSup {
    private int returnSup_id;
    private int po_id;
    private String product_name;
    private int quantity;
    private float unit_price;
    private float amount;
    private String supplier_name;
    private String description;
    private Date returnSup_date;
    private String userID;

    public returnSup(int returnSup_id, int po_id, String product_name, int quantity, float unit_price, float amount, String supplier_name, String description, Date returnSup_date, String userID) {
        this.returnSup_id = returnSup_id;
        this.po_id = po_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.amount = amount;
        this.supplier_name = supplier_name;
        this.description = description;
        this.returnSup_date = returnSup_date;
        this.userID = userID;
    }

    public returnSup() {
    }

    public int getReturnSup_id() {
        return returnSup_id;
    }

    public void setReturnSup_id(int returnSup_id) {
        this.returnSup_id = returnSup_id;
    }

    public int getPo_id() {
        return po_id;
    }

    public void setPo_id(int po_id) {
        this.po_id = po_id;
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

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReturnSup_date() {
        return returnSup_date;
    }

    public void setReturnSup_date(Date returnSup_date) {
        this.returnSup_date = returnSup_date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    
}
