/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.category;
import com.raven.entity.users;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class categoryDAO extends posDAO<category,String>{
    final String INSERT_SQL = "INSERT INTO categories(category_id,cate_name,cate_info) VALUES(?,?,?)";
    final String UPDATE_SQL = "UPDATE categories SET cate_name = ?, cate_info = ? WHERE category_id = ?";
    final String DELETE_SQL = "DELETE FROM categories WHERE category_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM categories";
    final String SELECT_BY_ID_SQL = "SELECT * FROM categories WHERE category_id = ?";

    @Override
    public void insert(category entity) {
        db.update(INSERT_SQL, entity.getCateID(),entity.getCateName(),entity.getCateOtherInfo());
    }

    @Override
    public void update(category entity) {
        db.update(UPDATE_SQL,entity.getCateName(),entity.getCateOtherInfo(),entity.getCateID());
    }

    @Override
    public void delete(String id) {
        db.update(DELETE_SQL, id);
    }

    @Override
    public List<category> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public category selectByID(String id) {
        List<category>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<category> selectBySQL(String sql, Object... args) {
        List<category> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                category entity = new category();
                entity.setCateID(rs.getString("category_id"));
                entity.setCateName(rs.getString("cate_name"));
                entity.setCateOtherInfo(rs.getString("cate_info"));
                
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    
}
