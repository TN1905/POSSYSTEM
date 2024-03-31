/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.invoice;
import com.raven.entity.purchaseOrder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class purchaseOrderDAO extends posDAO<purchaseOrder,String> {
    final String SELECT_ALL_SQL = "SELECT * FROM PO";
    final String SELECT_BY_ID_SQL = "SELECT * FROM PO WHERE purchase_order_id = ?";
    @Override
    public void insert(purchaseOrder entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(purchaseOrder entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<purchaseOrder> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public purchaseOrder selectByID(String id) {
        List<purchaseOrder>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<purchaseOrder> selectBySQL(String sql, Object... args) {
        List<purchaseOrder> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                purchaseOrder entity = new purchaseOrder();
                entity.setPOID(rs.getInt("purchase_order_id"));
                entity.setPODate(rs.getDate("purchase_order_date"));
                entity.setPOTime(rs.getString("purchase_order_time"));
                entity.setPOSupName(rs.getString("supplier_id"));
                entity.setPOProduct(rs.getString("product_id"));
                entity.setPOQuanlity(rs.getInt("purchase_quantity"));
                entity.setPOTotalAmount(rs.getFloat("purchase_total_amount"));
                entity.setPOStatus(rs.getString("purchase_status"));
                entity.setPOPaidAmount(rs.getFloat("purchase_paid_amount"));
                entity.setPODueBalance(rs.getFloat("purchase_due_balance"));
                entity.setPOPaymentType(rs.getString("purchase_payment_type"));
                entity.setPONote(rs.getString("purchase_note"));
                entity.setPOUser(rs.getString("users_id"));
      
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         return list;
    }

    
}
