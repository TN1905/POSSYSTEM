/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.invoice;
import com.raven.entity.product;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class invoiceDAO extends posDAO<invoice,String>{
    final String SELECT_ALL_SQL = "SELECT * FROM invoices where invoice_status = N'Paid' or invoice_status = N'Partial'";
    final String SELECT_BY_ID_SQL = "SELECT * FROM invoices WHERE invoice_id = ?";
    final String UPDATE_STATUS_INVOICE = "UPDATE invoices SET invoice_status = 'Paid', invoice_paid_amount = ?, balance = ? where invoice_id = ?";
            
    @Override
    public void insert(invoice entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(invoice entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void updateStatus(float payAmount,float Balance,String id) {
        db.update(UPDATE_STATUS_INVOICE, payAmount,Balance,id);
    }

    @Override
    public List<invoice> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public invoice selectByID(String id) {
        List<invoice>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<invoice> selectBySQL(String sql, Object... args) {
        List<invoice> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                invoice entity = new invoice();
                entity.setInvoiceID(rs.getInt("invoice_id"));
                entity.setInvoiceDate(rs.getDate("invoice_date"));
                entity.setInvoiceTime(rs.getString("invoice_time"));
                entity.setInvoiceCusName(rs.getString("customer_name"));
                entity.setInvoiceQuanlity(rs.getInt("invoice_quantity"));
                entity.setInvoiceTotalAmount(rs.getFloat("invoice_total_amount"));
                entity.setInvoiceStatus(rs.getString("invoice_status"));
                entity.setInvoicePaidAmount(rs.getFloat("invoice_paid_amount"));
                entity.setInvoiceDueBalance(rs.getFloat("balance"));
                entity.setInvoicePaymentType(rs.getString("invoice_payment_type"));
                entity.setInvoiceNote(rs.getString("invoice_note"));
                entity.setInvoiceUser(rs.getString("users_id"));
      
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
