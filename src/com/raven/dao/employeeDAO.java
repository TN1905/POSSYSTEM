/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;
import Utils.db;
import com.raven.entity.employee;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ADMIN
 */
public class employeeDAO extends posDAO<employee,String>{
    final String INSERT_SQL = "INSERT INTO employees(employee_id,emp_name,emp_number,emp_email,emp_city,emp_bill_add,emp_ship_add,emp_bank,emp_bank_no,emp_contact_name,emp_contact_email,emp_contact_number,emp_contact_online,emp_contact_otherinfo,emp_contact_description,users_id,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE employees SET emp_name = ?, emp_number = ?, emp_email = ?, emp_city = ?, emp_bill_add = ?, emp_ship_add = ?, emp_bank = ?, emp_bank_no = ?, emp_contact_name = ?, emp_contact_email = ?, emp_contact_number = ?, emp_contact_online = ?, emp_contact_otherinfo = ?, emp_contact_description = ? WHERE employee_id = ?";
    final String DELETE_SQL = "UPDATE employees SET status = 'Inactive' WHERE employee_id = ?";
    final String Return_SQL = "UPDATE employees SET status = 'Active' WHERE employee_id = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM employees where status = 'Active'";
    final String SELECT_Delete_SQL = "SELECT * FROM employees where  status = 'Inactive'";
    final String SELECT_BY_ID_SQL = "SELECT * FROM employees WHERE employee_id = ?";
    
    @Override
    public void insert(employee entity) {
        db.update(INSERT_SQL, entity.getEmpID(),entity.getEmpName(),entity.getEmpNumber(),entity.getEmpEmail(),entity.getEmpCity(),entity.getBillAdd(),entity.getShipEmpAdd(),entity.getEmpEmpBank(),entity.getEmpBankNo(),entity.getEmpContactPerson(),entity.getEmpEmailPerson(),entity.getEmpNumberPerson(),entity.getEmpOnline(),entity.getOtherEmpInfo(),entity.getEmpDescription(),entity.getEmpUserID());
    }

    @Override
    public void update(employee entity) {
        db.update(UPDATE_SQL, entity.getEmpName(),entity.getEmpNumber(),entity.getEmpEmail(),entity.getEmpCity(),entity.getBillAdd(),entity.getShipEmpAdd(),entity.getEmpEmpBank(),entity.getEmpBankNo(),entity.getEmpContactPerson(),entity.getEmpEmailPerson(),entity.getEmpNumberPerson(),entity.getEmpOnline(),entity.getOtherEmpInfo(),entity.getEmpDescription(),entity.getEmpID());
    }

    @Override
    public void delete(String id) {
        db.update(DELETE_SQL, id);
    }
    
    public void returnCus(String id) {
        db.update(Return_SQL, id);
    }

    @Override
    public List<employee> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<employee> selectAllInactive() {
        return selectBySQLInactive(SELECT_Delete_SQL);
    }

    @Override
    public employee selectByID(String id) {
        List<employee>list = selectBySQL(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<employee> selectBySQL(String sql, Object... args) {
        List<employee> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                employee entity = new employee();
                entity.setEmpID(rs.getString("employee_id"));
                entity.setEmpName(rs.getString("emp_name"));
                entity.setEmpNumber(rs.getInt("emp_number"));
                entity.setEmpEmail(rs.getString("emp_email"));
                entity.setEmpCity(rs.getString("emp_city"));
                entity.setBillAdd(rs.getString("emp_bill_add"));
                entity.setShipEmpAdd(rs.getString("emp_ship_add"));
                entity.setEmpEmpBank(rs.getString("emp_bank"));
                entity.setEmpBankNo(rs.getInt("emp_bank_no"));
                entity.setEmpContactPerson(rs.getString("emp_contact_name"));
                entity.setEmpEmailPerson(rs.getString("emp_contact_email"));
                entity.setEmpNumberPerson(rs.getInt("emp_contact_number"));
                entity.setEmpOnline(rs.getString("emp_contact_online"));
                entity.setOtherEmpInfo(rs.getString("emp_contact_otherinfo"));
                entity.setEmpDescription(rs.getString("emp_contact_description"));
                entity.setEmpUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    

    public List<employee> selectBySQLInactive(String sql, Object... args) {
        List<employee> list = new ArrayList<>();
        try {
            ResultSet rs = db.query(sql, args);
            while(rs.next()){
                employee entity = new employee();
                entity.setEmpID(rs.getString("employee_id"));
                entity.setEmpName(rs.getString("emp_name"));
                entity.setEmpNumber(rs.getInt("emp_number"));
                entity.setEmpEmail(rs.getString("emp_email"));
                entity.setEmpCity(rs.getString("emp_city"));
                entity.setBillAdd(rs.getString("emp_bill_add"));
                entity.setShipEmpAdd(rs.getString("emp_ship_add"));
                entity.setEmpEmpBank(rs.getString("emp_bank"));
                entity.setEmpBankNo(rs.getInt("emp_bank_no"));
                entity.setEmpContactPerson(rs.getString("emp_contact_name"));
                entity.setEmpEmailPerson(rs.getString("emp_contact_email"));
                entity.setEmpNumberPerson(rs.getInt("emp_contact_number"));
                entity.setEmpOnline(rs.getString("emp_contact_online"));
                entity.setOtherEmpInfo(rs.getString("emp_contact_otherinfo"));
                entity.setEmpDescription(rs.getString("emp_contact_description"));
                entity.setEmpUserID(rs.getString("users_id"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
