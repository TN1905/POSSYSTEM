/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import Utils.db;
import com.raven.entity.users;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class userDAO extends posDAO<users,String>{
    final String INSERT_SQL = "INSERT INTO users(users_id,users_name,account_type,passwords,email) VALUES(?,?,?,?,?)";

    final String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";
    
    @Override
    public void insert(users entity) {
        db.update(INSERT_SQL, entity.getUserID(),entity.getUserName(),entity.getUserAccountType(),entity.getUserPassword(),entity.getEmail());
    }

//    @Override
//    public void update(users entity) {
//        db.update(UPDATE_SQL,entity.getMatKhau(),entity.getHoTen(),entity.isVaiTro(), entity.getMaNV());
//    }
//
//    @Override
//    public void delete(String id) {
//        db.update(DELETE_SQL,id);
//    }
//
//    @Override
//    public List<NhanVien> selectAll() {
//        return selectBySQL(SELECT_ALL_SQL);
//    }
//
//    @Override
//    public NhanVien selectByID(String id) {
//        List<NhanVien>list = selectBySQL(SELECT_BY_ID_SQL,id);
//        if(list.isEmpty()){
//            return null;
//        }
//        return list.get(0);
//    }
//
//    @Override
//    public List<NhanVien> selectBySQL(String sql, Object... args) {
//        List<NhanVien> list = new ArrayList<>();
//        try {
//            ResultSet rs = db.query(sql, args);
//            while(rs.next()){
//                NhanVien entity = new NhanVien();
//                entity.setMaNV(rs.getString("MaNV"));
//                entity.setMatKhau(rs.getString("MatKhau"));
//                entity.setHoTen(rs.getString("HoTen"));
//                entity.setVaiTro(rs.getBoolean("VaiTro"));
//                list.add(entity);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }

    @Override
    public void update(users entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<users> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public users selectByID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<users> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
