/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.entity;

/**
 *
 * @author ADMIN
 */
public class category {
    private String cateID;
    private String cateName;
    private String cateOtherInfo;

    public category(String cateID, String cateName, String cateOtherInfo) {
        this.cateID = cateID;
        this.cateName = cateName;
        this.cateOtherInfo = cateOtherInfo;
    }

    public category() {
    }

    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateOtherInfo() {
        return cateOtherInfo;
    }

    public void setCateOtherInfo(String cateOtherInfo) {
        this.cateOtherInfo = cateOtherInfo;
    }
    
    
}
