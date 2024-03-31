/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.product;
import com.raven.entity.returnCus;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class returnCusDAO extends posDAO<returnCus,Integer>{
    final String INSERT_SQL = "INSERT INTO returnCustomer(invoice_id,product_name,quantity,unit_price,amount,customer_name,descriptionCus,date_return,users_id) VALUES(?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE returnCustomer SET invoice_id = ?, product_name = ?, quantity = ?, unit_price = ?, amount = ?, customer_name = ?, descriptionCus = ?, date_return = ? WHERE returnCus_id = ?";
    final String DELETE_SQL = "DELETE returnCustomer WHERE returnCus_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM returnCustomer";
    final String SELECT_BY_ID_SQL = "SELECT * FROM returnCustomer WHERE returnCus_id = ?";
    
    @Override
    public void insert(returnCus entity) {
        db.update(INSERT_SQL,entity.getInvoice_id(),entity.getProduct_name(),entity.getQuantity(),entity.getUnit_price(),entity.getAmount(),entity.getCustomer_name(),entity.getDescription(),entity.getReturnCus_date(),entity.getUserID());
    }

    @Override
    public void update(returnCus entity) {
        db.update(UPDATE_SQL, entity.getInvoice_id(),entity.getProduct_name(),entity.getQuantity(),entity.getUnit_price(),entity.getAmount(),entity.getCustomer_name(),entity.getDescription(),entity.getReturnCus_date(),entity.getReturnCus_id());
    }

    @Override
    public void delete(Integer id) {
        db.update(DELETE_SQL, id);
    }

    @Override
    public List<returnCus> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public List<returnCus> selectBySQL(String sql, Object... args) {
        List<returnCus> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                returnCus entity = new returnCus();
                entity.setReturnCus_id(rs.getInt("returnCus_id"));
                entity.setInvoice_id(rs.getInt("invoice_id"));
                entity.setProduct_name(rs.getString("product_name"));
                entity.setQuantity(rs.getInt("quantity"));
                entity.setUnit_price(rs.getFloat("unit_price"));
                entity.setAmount(rs.getFloat("amount"));
                entity.setCustomer_name(rs.getString("customer_name"));
                entity.setDescription(rs.getString("descriptionCus"));
                entity.setReturnCus_date(rs.getDate("date_return"));
                entity.setUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public returnCus selectByID(Integer id) {
        List<returnCus>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
}
