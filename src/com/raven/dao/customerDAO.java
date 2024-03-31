/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.customer;
import com.raven.entity.supplier;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author ADMIN
 */
public class customerDAO extends posDAO<customer,String>{
    final String INSERT_SQL = "INSERT INTO customers(customer_id,cus_name,cus_number,cus_email,cus_city,cus_bill_add,cus_ship_add,cus_bank,cus_bank_no,cus_contact_name,cus_contact_email,cus_contact_number,cus_contact_online,cus_contact_otherinfo,cus_contact_description,users_id,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE customers SET cus_name = ?, cus_number = ?, cus_email = ?, cus_city = ?, cus_bill_add = ?, cus_ship_add = ?, cus_bank = ?, cus_bank_no = ?, cus_contact_name = ?, cus_contact_email = ?, cus_contact_number = ?, cus_contact_online = ?, cus_contact_otherinfo = ?, cus_contact_description = ? WHERE customer_id = ?";
    final String DELETE_SQL = "UPDATE customers SET status = 'Inactive' WHERE customer_id = ?";
    final String Return_SQL = "UPDATE customers SET status = 'Active' WHERE customer_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM customers where status = 'Active'";
    final String SELECT_Delete_SQL = "SELECT * FROM customers where  status = 'Inactive'";
    final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = ?";
    
    @Override
    public void insert(customer entity) {
        db.update(INSERT_SQL, entity.getCusID(),entity.getCusName(),entity.getCusNumber(),entity.getCusEmail(),entity.getCusCity(),entity.getBillCusAdd(),entity.getShipCusAdd(),entity.getCusBank(),entity.getCusBankNo(),entity.getCusContactPerson(),entity.getCusEmailPerson(),entity.getCusNumberPerson(),entity.getCusOnline(),entity.getOtherCusInfo(),entity.getCusDescription(),entity.getCusUserID(),entity.getStatus());
    }

    @Override
    public void update(customer entity) {
        db.update(UPDATE_SQL, entity.getCusName(),entity.getCusNumber(),entity.getCusEmail(),entity.getCusCity(),entity.getBillCusAdd(),entity.getShipCusAdd(),entity.getCusBank(),entity.getCusBankNo(),entity.getCusContactPerson(),entity.getCusEmailPerson(),entity.getCusNumberPerson(),entity.getCusOnline(),entity.getOtherCusInfo(),entity.getCusDescription(),entity.getCusID());
    }

    @Override
    public void delete(String id) {
        db.update(DELETE_SQL, id);
    }
    
    public void returnCus(String id) {
        db.update(Return_SQL, id);
    }

    @Override
    public List<customer> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<customer> selectAllInactive() {
        return selectBySQLInactive(SELECT_Delete_SQL);
    }

    @Override
    public customer selectByID(String id) {
        List<customer>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<customer> selectBySQL(String sql, Object... args) {
        List<customer> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                customer entity = new customer();
                entity.setCusID(rs.getString("customer_id"));
                entity.setCusName(rs.getString("cus_name"));
                entity.setCusNumber(rs.getInt("cus_number"));
                entity.setCusEmail(rs.getString("cus_email"));
                entity.setCusCity(rs.getString("cus_city"));
                entity.setBillCusAdd(rs.getString("cus_bill_add"));
                entity.setShipCusAdd(rs.getString("cus_ship_add"));
                entity.setCusBank(rs.getString("cus_bank"));
                entity.setCusBankNo(rs.getInt("cus_bank_no"));
                entity.setCusContactPerson(rs.getString("cus_contact_name"));
                entity.setCusEmailPerson(rs.getString("cus_contact_email"));
                entity.setCusNumberPerson(rs.getInt("cus_contact_number"));
                entity.setCusOnline(rs.getString("cus_contact_online"));
                entity.setOtherCusInfo(rs.getString("cus_contact_otherinfo"));
                entity.setCusDescription(rs.getString("cus_contact_description"));
                entity.setCusUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    

    public List<customer> selectBySQLInactive(String sql, Object... args) {
        List<customer> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                customer entity = new customer();
                entity.setCusID(rs.getString("customer_id"));
                entity.setCusName(rs.getString("cus_name"));
                entity.setCusNumber(rs.getInt("cus_number"));
                entity.setCusEmail(rs.getString("cus_email"));
                entity.setCusCity(rs.getString("cus_city"));
                entity.setBillCusAdd(rs.getString("cus_bill_add"));
                entity.setShipCusAdd(rs.getString("cus_ship_add"));
                entity.setCusBank(rs.getString("cus_bank"));
                entity.setCusBankNo(rs.getInt("cus_bank_no"));
                entity.setCusContactPerson(rs.getString("cus_contact_name"));
                entity.setCusEmailPerson(rs.getString("cus_contact_email"));
                entity.setCusNumberPerson(rs.getInt("cus_contact_number"));
                entity.setCusOnline(rs.getString("cus_contact_online"));
                entity.setOtherCusInfo(rs.getString("cus_contact_otherinfo"));
                entity.setCusDescription(rs.getString("cus_contact_description"));
                entity.setCusUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    
    
}
