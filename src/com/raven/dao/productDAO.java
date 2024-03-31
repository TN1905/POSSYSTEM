/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;


import Utils.db;
import com.raven.entity.category;
import com.raven.entity.product;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class productDAO extends posDAO<product,String>{
    final String INSERT_SQL = "INSERT INTO products(product_id,barcode,pro_name,category_id,unit_price,sales_price,pro_quantity,pro_default_type,pro_description,pro_mf_date,pro_exp_date,pro_company_name,pro_brand_name,pro_private_note,supplier_id,users_id,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE products SET barcode = ?, pro_name = ?, category_id = ?, unit_price = ?, sales_price = ?, pro_quantity = ?, pro_default_type = ?, pro_description = ?, pro_mf_date = ?, pro_exp_date = ?, pro_company_name = ?, pro_brand_name = ?, pro_private_note = ?, supplier_id = ?, users_id = ? WHERE product_id = ?";
    final String DELETE_SQL = "UPDATE products SET status = 'Inactive' WHERE product_id = ?";
    final String Return_SQL = "UPDATE products SET status = 'Active' WHERE product_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM products where  status = 'Active'";
    final String SELECT_Delete_SQL = "SELECT * FROM products where  status = 'Inactive'";
    final String SELECT_BY_ID_SQL = "SELECT * FROM products WHERE product_id = ?";
    
    @Override
    public void insert(product entity) {
        db.update(INSERT_SQL, entity.getProID(),entity.getBarcode(),entity.getProName(),entity.getProCateID(),entity.getProUnitPrice(),entity.getProSalesPrice(),entity.getProQuanlity(),entity.getDefaultProType(),entity.getProDescription(),entity.getMfProDate(),entity.getExpProDate(),entity.getProCompanyName(),entity.getProBrandName(),entity.getProPrivateNote(),entity.getProSupID(),entity.getProUserID(),entity.getStatus());
    }

    @Override
    public void update(product entity) {
        db.update(UPDATE_SQL, entity.getBarcode(),entity.getProName(),entity.getProCateID(),entity.getProUnitPrice(),entity.getProSalesPrice(),entity.getProQuanlity(),entity.getDefaultProType(),entity.getProDescription(),entity.getMfProDate(),entity.getExpProDate(),entity.getProCompanyName(),entity.getProBrandName(),entity.getProPrivateNote(),entity.getProSupID(),entity.getProUserID(),entity.getProID());
    }

    @Override
    public void delete(String id) {
        db.update(DELETE_SQL, id);
    }
    
    public void returnPro(String id) {
        db.update(Return_SQL, id);
    }

    @Override
    public List<product> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<product> selectAllInactive() {
        return selectBySQLInactive(SELECT_Delete_SQL);
    }

    @Override
    public product selectByID(String id) {
        List<product>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<product> selectBySQL(String sql, Object... args) {
        List<product> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                product entity = new product();
                entity.setProID(rs.getString("product_id"));
                entity.setBarcode(rs.getInt("barcode"));
                entity.setProName(rs.getString("pro_name"));
                String cate_name = "";
                try {
                    String sqll = "select cate_name from categories where category_id = '"+rs.getString("category_id")+"'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rss = s.executeQuery(sqll);
                    if(rss.next()){
                        cate_name = rss.getString("cate_name");
                      
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                entity.setProCateID(cate_name);
                entity.setProUnitPrice(rs.getFloat("unit_price"));
                entity.setProSalesPrice(rs.getFloat("sales_price"));
                entity.setProQuanlity(rs.getInt("pro_quantity"));
                entity.setDefaultProType(rs.getString("pro_default_type"));
                entity.setProDescription(rs.getString("pro_description"));
                entity.setMfProDate(rs.getDate("pro_mf_date"));
                entity.setExpProDate(rs.getDate("pro_exp_date"));
                entity.setProCompanyName(rs.getString("pro_company_name"));
                entity.setProBrandName(rs.getString("pro_brand_name"));
                entity.setProPrivateNote(rs.getString("pro_private_note"));
                entity.setProSupID(rs.getString("supplier_id"));
                entity.setProUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<product> selectBySQLInactive(String sql, Object... args) {
        List<product> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                product entity = new product();
                entity.setProID(rs.getString("product_id"));
                entity.setBarcode(rs.getInt("barcode"));
                entity.setProName(rs.getString("pro_name"));
                String cate_name = "";
                try {
                    String sqll = "select cate_name from categories where category_id = '"+rs.getString("category_id")+"'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rss = s.executeQuery(sqll);
                    if(rss.next()){
                        cate_name = rss.getString("cate_name");
                      
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                entity.setProCateID(cate_name);
                entity.setProUnitPrice(rs.getFloat("unit_price"));
                entity.setProSalesPrice(rs.getFloat("sales_price"));
                entity.setProQuanlity(rs.getInt("pro_quantity"));
                entity.setDefaultProType(rs.getString("pro_default_type"));
                entity.setProDescription(rs.getString("pro_description"));
                entity.setMfProDate(rs.getDate("pro_mf_date"));
                entity.setExpProDate(rs.getDate("pro_exp_date"));
                entity.setProCompanyName(rs.getString("pro_company_name"));
                entity.setProBrandName(rs.getString("pro_brand_name"));
                entity.setProPrivateNote(rs.getString("pro_private_note"));
                entity.setProSupID(rs.getString("supplier_id"));
                entity.setProUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
