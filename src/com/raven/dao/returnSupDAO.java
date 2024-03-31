/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.returnCus;
import com.raven.entity.returnSup;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class returnSupDAO extends posDAO<returnSup,Integer>{
    final String INSERT_SQL = "INSERT INTO returnSupplier(po_id,product_name,quantity,unit_price,amount,supplier_name,descriptionSup,date_return,users_id) VALUES(?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE returnSupplier SET po_id = ?, product_name = ?, quantity = ?, unit_price = ?, amount = ?, supplier_name = ?, descriptionSup = ?, date_return = ? WHERE returnSup_id = ?";
    final String DELETE_SQL = "DELETE returnSupplier WHERE returnCus_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM returnSupplier";
    final String SELECT_BY_ID_SQL = "SELECT * FROM returnSupplier WHERE returnSup_id = ?";

    @Override
    public void insert(returnSup entity) {
        db.update(INSERT_SQL,entity.getPo_id(),entity.getProduct_name(),entity.getQuantity(),entity.getUnit_price(),entity.getAmount(),entity.getSupplier_name(),entity.getDescription(),entity.getReturnSup_date(),entity.getUserID());
    }

    @Override
    public void update(returnSup entity) {
        db.update(UPDATE_SQL, entity.getPo_id(),entity.getProduct_name(),entity.getQuantity(),entity.getUnit_price(),entity.getAmount(),entity.getSupplier_name(),entity.getDescription(),entity.getReturnSup_date(),entity.getReturnSup_id());
    }

    @Override
    public void delete(Integer id) {
        db.update(DELETE_SQL, id);
    }

    @Override
    public List<returnSup> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public returnSup selectByID(Integer id) {
        List<returnSup>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<returnSup> selectBySQL(String sql, Object... args) {
       List<returnSup> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                returnSup entity = new returnSup();
                entity.setReturnSup_id(rs.getInt("returnSup_id"));
                entity.setPo_id(rs.getInt("po_id"));
                entity.setProduct_name(rs.getString("product_name"));
                entity.setQuantity(rs.getInt("quantity"));
                entity.setUnit_price(rs.getFloat("unit_price"));
                entity.setAmount(rs.getFloat("amount"));
                entity.setSupplier_name(rs.getString("supplier_name"));
                entity.setDescription(rs.getString("descriptionSup"));
                entity.setReturnSup_date(rs.getDate("date_return"));
                entity.setUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
