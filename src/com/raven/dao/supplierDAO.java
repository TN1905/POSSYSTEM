/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.product;
import com.raven.entity.supplier;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class supplierDAO extends posDAO<supplier,String>{
    final String INSERT_SQL = "INSERT INTO suppliers(supplier_id,sup_name,sup_number,sup_email,sup_city,sup_bill_add,sup_ship_add,sup_bank,sup_bank_no,sup_contact_name,sup_contact_email,sup_contact_number,sup_contact_online,sup_contact_otherinfo,sup_contact_description,users_id,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE suppliers SET sup_name = ?, sup_number = ?, sup_email = ?, sup_city = ?, sup_bill_add = ?, sup_ship_add = ?, sup_bank = ?, sup_bank_no = ?, sup_contact_name = ?, sup_contact_email = ?, sup_contact_number = ?, sup_contact_online = ?, sup_contact_otherinfo = ?, sup_contact_description = ?, WHERE supplier_id = ?";
    final String DELETE_SQL = "UPDATE suppliers SET status = 'Inactive' WHERE supplier_id = ?";
    final String Return_SQL = "UPDATE suppliers SET status = 'Active' WHERE supplier_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM suppliers where  status = 'Active'";
    final String SELECT_Delete_SQL = "SELECT * FROM suppliers where  status = 'Inactive'";
    final String SELECT_BY_ID_SQL = "SELECT * FROM suppliers WHERE supplier_id = ?";
    
    @Override
    public void insert(supplier entity) {
        db.update(INSERT_SQL, entity.getSupID(),entity.getSupName(),entity.getSupNumber(),entity.getSupEmail(),entity.getSupCity(),entity.getBillSupAdd(),entity.getShipSupAdd(),entity.getSupBank(),entity.getSupBankNo(),entity.getSupContactPerson(),entity.getSupEmailPerson(),entity.getSupNumberPerson(),entity.getSupOnline(),entity.getOtherSupInfo(),entity.getSupDescription(),entity.getSupUserID(),entity.getStatus());
    }

    @Override
    public void update(supplier entity) {
        db.update(UPDATE_SQL,entity.getSupName(),entity.getSupNumber(),entity.getSupEmail(),entity.getSupCity(),entity.getBillSupAdd(),entity.getShipSupAdd(),entity.getSupBank(),entity.getSupBankNo(),entity.getSupContactPerson(),entity.getSupEmailPerson(),entity.getSupNumberPerson(),entity.getSupOnline(),entity.getOtherSupInfo(),entity.getSupDescription(),entity.getSupUserID(), entity.getSupID());
    }

    @Override
    public void delete(String id) {
        db.update(DELETE_SQL, id);
    }
    
    public void returnSup(String id) {
        db.update(Return_SQL, id);
    }

    @Override
    public List<supplier> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<supplier> selectAllInactive() {
        return selectBySQLInactive(SELECT_Delete_SQL);
    }

    @Override
    public supplier selectByID(String id) {
        List<supplier>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<supplier> selectBySQL(String sql, Object... args) {
        List<supplier> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                supplier entity = new supplier();
                entity.setSupID(rs.getString("supplier_id"));
                entity.setSupName(rs.getString("sup_name"));
                entity.setSupNumber(rs.getInt("sup_number"));
                entity.setSupEmail(rs.getString("sup_email"));
                entity.setSupCity(rs.getString("sup_city"));
                entity.setBillSupAdd(rs.getString("sup_bill_add"));
                entity.setShipSupAdd(rs.getString("sup_ship_add"));
                entity.setSupBank(rs.getString("sup_bank"));
                entity.setSupBankNo(rs.getInt("sup_bank_no"));
                entity.setSupContactPerson(rs.getString("sup_contact_name"));
                entity.setSupEmailPerson(rs.getString("sup_contact_email"));
                entity.setSupNumberPerson(rs.getInt("sup_contact_number"));
                entity.setSupOnline(rs.getString("sup_contact_online"));
                entity.setOtherSupInfo(rs.getString("sup_contact_otherinfo"));
                entity.setSupDescription(rs.getString("sup_contact_description"));
                entity.setSupUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<supplier> selectBySQLInactive(String sql, Object... args) {
        List<supplier> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                supplier entity = new supplier();
                entity.setSupID(rs.getString("supplier_id"));
                entity.setSupName(rs.getString("sup_name"));
                entity.setSupNumber(rs.getInt("sup_number"));
                entity.setSupEmail(rs.getString("sup_email"));
                entity.setSupCity(rs.getString("sup_city"));
                entity.setBillSupAdd(rs.getString("sup_bill_add"));
                entity.setShipSupAdd(rs.getString("sup_ship_add"));
                entity.setSupBank(rs.getString("sup_bank"));
                entity.setSupBankNo(rs.getInt("sup_bank_no"));
                entity.setSupContactPerson(rs.getString("sup_contact_name"));
                entity.setSupEmailPerson(rs.getString("sup_contact_email"));
                entity.setSupNumberPerson(rs.getInt("sup_contact_number"));
                entity.setSupOnline(rs.getString("sup_contact_online"));
                entity.setOtherSupInfo(rs.getString("sup_contact_otherinfo"));
                entity.setSupDescription(rs.getString("sup_contact_description"));
                entity.setSupUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    
    
}
