/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Utils.Auth;
import Utils.Data;
import Utils.MsgBox;
import Utils.XDate;
import Utils.db;
import com.raven.component.Form;
import com.raven.dao.employeeDAO;
import com.raven.entity.customer;
import com.raven.entity.employee;
import com.raven.entity.users;
import com.raven.event.EventColorChange;
import com.raven.menu.EventMenu;
import com.raven.properties.SystemProperties;
import com.raven.theme.SystemTheme;
import com.raven.theme.ThemeColor;
import com.raven.theme.ThemeColorChange;
import java.awt.Color;
import com.raven.form.Setting_Form;
import com.raven.main.Main;
import com.raven.menu.Menu;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import searchwing.EventCallBack;
import searchwing.EventTextField;
/**
 *
 * @author ADMIN
 */
public class Employee_Form extends Form {
    Menu menu = new Menu();
    int index = 0;
    private users user;
    private Setting_Form settingForm;
    employeeDAO empDAO = new employeeDAO();
    public void setUser(users user1){
        user = user1;
    }
    
    long currentTimestamp = getCurrentTimestamp();
    public Employee_Form(users user) {
        this.user = user;
        initComponents();
        init();
        tblNhanVien.getTableHeader().setForeground(new Color(255,255,255));  
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(51, 153, 255));
        headerRenderer.setForeground(new Color(255, 255, 255));
        tblNhanVien.getTableHeader().setDefaultRenderer(headerRenderer);
        System.out.println(user.getUserID());
        nvUserID.setText(user.getUserID());
        loadTable();
        loadTableInactive();
        System.out.println(currentTimestamp);
       
    }
    
    public void init(){
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        jPanel3.setBackground(new Color(0, 0, 0, 0));
       
        jPanel6.setBackground(new Color(0, 0, 0, 0));
        jPanel6.setOpaque(false);
        jPanel7.setBackground(new Color(0, 0, 0, 0));
        jPanel7.setOpaque(false);
        tabs.setBackground(new Color(0, 0, 0, 0));
        tabs.setOpaque(false);
       
        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setOpaque(false);
        jPanel5.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setOpaque(false);
        jPanel8.setBackground(new Color(0, 0, 0, 0));
        jPanel8.setOpaque(false);

        searchName();
        searchID();
        fillComboBoxName();
        fillComboBoxNumber();
        fillComboBoxEmail();
        fillComboBoxName1();
        fillComboBoxNumber1();
        fillComboBoxEmail1();
    }
  
    
    public long getCurrentTimestamp() {
        Instant currentInstant = Instant.now();
        currentTimestamp = currentInstant.getEpochSecond();
        System.out.println(currentTimestamp);
        return currentInstant.getEpochSecond();
    }
    
    void loadTable(){
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            String keyword = c_search_tbl.getText();
            List<employee>list =  empDAO.selectAll();
      
            for(employee nh: list){
                Object[] row = {          
                    nh.getEmpID(),
                    nh.getEmpName(),
                    nh.getEmpNumber(),
                    nh.getEmpEmail(),
                    
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void fillComboBoxName(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboEmpName.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<employee> list = empDAO.selectAll();
            for(employee cd:list){
                model.addElement(cd.getEmpName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxNumber(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboEmpNumber.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<employee> list = empDAO.selectAll();
            for(employee cd:list){
                model.addElement(cd.getEmpNumber());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxEmail(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboEmpEmail.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<employee> list = empDAO.selectAll();
            for(employee cd:list){
                model.addElement(cd.getEmpEmail());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxName1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboEmpName1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<employee> list = empDAO.selectAllInactive();
            for(employee cd:list){
                model.addElement(cd.getEmpName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxNumber1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboEmpNumber1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<employee> list = empDAO.selectAllInactive();
            for(employee cd:list){
                model.addElement(cd.getEmpNumber());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxEmail1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboEmpEmail1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<employee> list = empDAO.selectAllInactive();
            for(employee cd:list){
                model.addElement(cd.getEmpEmail());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void loadTableInactive(){
        DefaultTableModel model = (DefaultTableModel) tblNhanVien2.getModel();
        model.setRowCount(0);
        try {
            String keyword = c_search_tbl.getText();
            List<employee>list =  empDAO.selectAllInactive();
      
            for(employee nh: list){
                Object[] row = {          
                    nh.getEmpID(),
                    nh.getEmpName(),
                    nh.getEmpNumber(),
                    nh.getEmpEmail(),
                    
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    
    
    void setModel(employee model){
        lblNVID.setText(model.getEmpID());
        nvName.setText(model.getEmpName());
        nvNumber.setText(String.valueOf(model.getEmpNumber()));
        nvEmail.setText(model.getEmpEmail());
        nvCity.setText(model.getEmpCity());
        nvBillAdd.setText(model.getBillAdd());
        nvShipAdd.setText(model.getShipEmpAdd());
        nvBank.setText(model.getEmpEmpBank());
        nvBankNo.setText(String.valueOf(model.getEmpBankNo()));
        nvContactName.setText(model.getEmpContactPerson());
        nvContactEmail.setText(model.getEmpEmailPerson());
        nvContactNumber.setText(String.valueOf(model.getEmpNumberPerson()));
        nvOnline.setText(model.getEmpOnline());
        nvOtherInfo.setText(model.getOtherEmpInfo());
        nvDescription.setText(model.getEmpDescription());
        nvUserID.setText(model.getEmpUserID());
    }
    
    employee getModel() throws ParseException {
        employee model = new employee();
        int countNV=0;
        String employeeID = "";
       
                employeeID = "emp" + currentTimestamp;
            
        model.setEmpID(employeeID);
        model.setEmpName(nvName.getText());
        String Number = nvNumber.getText();
        if(data.ContainNumberOnly(Number)==false){
            model.setEmpNumber(0);
        }else{
            if(Number.isEmpty()){
           model.setEmpNumber(0);
            }else{
                model.setEmpNumber(Integer.parseInt(Number));
            }
        }
        
        model.setEmpEmail(nvEmail.getText());
        model.setEmpCity(nvCity.getText());
        model.setBillAdd(nvBillAdd.getText());
        model.setShipEmpAdd(nvShipAdd.getText());
        model.setEmpEmpBank(nvBank.getText());
        String bankNO = nvBankNo.getText();
        if(data.ContainNumberOnly(bankNO)==false){
            model.setEmpBankNo(0);
        }else{
            if(bankNO.isEmpty()){
           model.setEmpBankNo(0);
            }else{
                model.setEmpBankNo(Integer.parseInt(bankNO));
            }
        }
        
        model.setEmpContactPerson(nvContactName.getText());
        model.setEmpEmailPerson(nvContactEmail.getText());
        String contactNumber = nvContactNumber.getText();
        if(data.ContainNumberOnly(contactNumber)==false){
            model.setEmpNumberPerson(0);
        }else{
            if(contactNumber.isEmpty()){
           model.setEmpNumberPerson(0);
            }else{
                model.setEmpNumberPerson(Integer.parseInt(contactNumber));
            }
        }
        
        model.setEmpOnline(nvOnline.getText());
        model.setOtherEmpInfo(nvOtherInfo.getText());
        model.setEmpDescription(nvDescription.getText());
        model.setEmpUserID(nvUserID.getText());
        
        return model;
    }
    
    employee getModelUpdate() {
        employee model = new employee();
        int countNV=0;
        
        model.setEmpID(lblNVID.getText());
        model.setEmpName(nvName.getText());
        String Number = nvNumber.getText();
        if(data.ContainNumberOnly(Number)==false){
            model.setEmpNumber(0);
        }else{
            if(Number.isEmpty()){
           model.setEmpNumber(0);
            }else{
                model.setEmpNumber(Integer.parseInt(Number));
            }
        }
        
        model.setEmpEmail(nvEmail.getText());
        model.setEmpCity(nvCity.getText());
        model.setBillAdd(nvBillAdd.getText());
        model.setShipEmpAdd(nvShipAdd.getText());
        model.setEmpEmpBank(nvBank.getText());
        String bankNO = nvBankNo.getText();
        if(data.ContainNumberOnly(bankNO)==false){
            model.setEmpBankNo(0);
        }else{
            if(bankNO.isEmpty()){
           model.setEmpBankNo(0);
            }else{
                model.setEmpBankNo(Integer.parseInt(bankNO));
            }
        }
        
        model.setEmpContactPerson(nvContactName.getText());
        model.setEmpEmailPerson(nvContactEmail.getText());
        String contactNumber = nvContactNumber.getText();
        if(data.ContainNumberOnly(contactNumber)==false){
            model.setEmpNumberPerson(0);
        }else{
            if(contactNumber.isEmpty()){
           model.setEmpNumberPerson(0);
            }else{
                model.setEmpNumberPerson(Integer.parseInt(contactNumber));
            }
        }
        
        model.setEmpOnline(nvOnline.getText());
        model.setOtherEmpInfo(nvOtherInfo.getText());
        model.setEmpDescription(nvDescription.getText());
        
        
        return model;
    }
    
    public boolean checkTrungMa(JTextField txt){
        txt.setBackground(white);
        if(empDAO.selectByID(txt.getText())==null){
            return true;
        }else{
            txt.setBackground(pink);
            MsgBox.alert(this, txt.getName() + "Đã bị tồn tại");
            return false;
        }
    }
    
    public void setTrang(){
        nvName.setBorder(white);
        nvNumber.setBorder(white);
        nvEmail.setBorder(white);
        nvCity.setBorder(white);
        nvBillAdd.setBorder(white);
        nvShipAdd.setBorder(white);
        nvBank.setBorder(white);
        nvBankNo.setBorder(white);
        lblNVID.setBackground(white);
        nvContactName.setBorder(white);
        nvContactNumber.setBorder(white);
        nvContactEmail.setBorder(white);
        nvOnline.setBorder(white);
        nvOtherInfo.setBorder(white);
        nvDescription.setBorder(white);
    }
    
    void clear(){
        setTrang();
        employee model = new employee();
        this.setModel(model);
    }
    
    void edit(){
        setTrang();
        try {
            String manv = (String) tblNhanVien.getValueAt(this.index, 0);
            employee model = empDAO.selectByID(manv);
            if(model!=null){
                this.setModel(model);
              
                tabs.setSelectedIndex(1);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    public void searchID(){
        
        p_src.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                //  Test
                try {                   
                     String search = p_src.getText();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE employee_id = '"+search+"'");

                        if(rs.next()){
                            System.out.println("cai ccm");
                            lblNVID.setText(rs.getString("employee_id"));
                            nvName.setText(rs.getString("emp_name"));
                            nvNumber.setText(rs.getString("emp_number"));
                            nvEmail.setText(rs.getString("emp_email"));
                            nvCity.setText(rs.getString("emp_city"));
                            nvBillAdd.setText(rs.getString("emp_bill_add"));
                            nvShipAdd.setText(rs.getString("emp_ship_add"));
                            nvBank.setText(rs.getString("emp_bank"));
                            nvBankNo.setText(rs.getString("emp_bank_no"));
                            nvContactName.setText(rs.getString("emp_contact_name"));
                            nvContactEmail.setText(rs.getString("emp_contact_email"));
                            nvContactNumber.setText(rs.getString("emp_contact_number"));
                            nvOnline.setText(rs.getString("emp_contact_online"));
                            nvOtherInfo.setText(rs.getString("emp_contact_otherinfo"));
                            nvDescription.setText(rs.getString("emp_contact_description"));
                            nvUserID.setText(rs.getString("users_id"));      
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
                            loadTable();
                            call.done();
                            } catch (Exception e) {
                                System.err.println(e);
                                System.out.println(e);
                            }
                        }

            @Override
            public void onCancel() {
                
            }
        });
    }
    
    public void searchName(){
        c_search_tbl.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                //  Test
                try {
                    
                    String name = c_search_tbl.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblNhanVien.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM employee WHERE employee_name LIKE '%"+name+"%'");   
            while(rs.next()){
                System.out.println("cai cc");
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
                v.add(rs.getString(7));
                dt.addRow(v);
            }
            s.close();
            rs.close();
                db.myCon().close();
        }catch(Exception e){
             loadTable();
        }
                    call.done();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }
    
    Data data = new Data();
    void insert() throws ParseException{
        employee model = getModel();
        nvName.setBorder(Color.WHITE);
        nvNumber.setBorder(white);
        nvEmail.setBorder(white);
        nvCity.setBorder(white);
        nvBillAdd.setBorder(white);
        nvShipAdd.setBorder(white);
        nvBank.setBorder(white);
        nvBankNo.setBorder(white);      
        nvContactName.setBorder(white);
        nvContactNumber.setBorder(white);
        nvContactEmail.setBorder(white);
        nvOnline.setBorder(white);
        nvOtherInfo.setBorder(white);
        nvDescription.setBorder(white);
        if(nvName.getText().length()==0 || nvNumber.getText().length()==0 || nvEmail.getText().length()==0||nvCity.getText().length()==0||nvBillAdd.getText().length()==0 
                ||nvShipAdd.getText().length()==0 ||nvBank.getText().length()==0 ||nvBankNo.getText().length()==0 
                ||nvContactName.getText().length()==0 ||nvContactNumber.getText().length()==0 ||nvContactEmail.getText().length()==0 ||nvOnline.getText().length()==0 ||nvOtherInfo.getText().length()==0 ||nvDescription.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            if(nvName.getText().length()==0){
                nvName.setBorder(Color.red);
            }
           
            if(nvNumber.getText().length()==0){
                nvNumber.setBorder(Color.red);
            }
            if(nvEmail.getText().length()==0){
                nvEmail.setBorder(Color.red);
            }
            if(nvCity.getText().length()==0){
                nvCity.setBorder(Color.red);
            }
            if(nvBillAdd.getText().length()==0){
                nvBillAdd.setBorder(Color.red);
            }
            if(nvShipAdd.getText().length()==0){
                nvShipAdd.setBorder(Color.red);
            }
            if(nvBank.getText().length()==0){
                nvBank.setBorder(Color.red);
            }
            if(nvBankNo.getText().length()==0){
                nvBankNo.setBorder(Color.red);
            }
            if(nvContactName.getText().length()==0){
                nvContactName.setBorder(Color.red);
            }
            if(nvContactNumber.getText().length()==0){
                nvContactNumber.setBorder(Color.red);
            }
            if(nvContactEmail.getText().length()==0){
                nvContactEmail.setBorder(Color.red);
            }
            if(nvOnline.getText().length()==0){
                nvOnline.setBorder(Color.red);
            }
            if(nvOtherInfo.getText().length()==0){
                nvOtherInfo.setBorder(Color.red);
            }
            if(nvDescription.getText().length()==0){
                nvDescription.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{      
                if(data.isName(nvName.getText())==false){
                    MsgBox.alert(this, "Ten nhan vien khong dung dinh dang");
                    nvName.setBackground(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(nvNumber.getText())==false){
                    MsgBox.alert(this, "Number khong dung dinh dang");
                    nvNumber.setBackground(Color.red);
                    check = false;
                }else if(data.isEmail(nvEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    nvEmail.setBackground(Color.red);
                    check = false;
                }else if(data.isName(nvContactName.getText())==false){
                    MsgBox.alert(this, "Contact name khong dung dinh dang");
                    nvContactName.setBackground(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(nvContactNumber.getText())==false){
                    MsgBox.alert(this, "Contact Number khong dung dinh dang");
                    nvContactNumber.setBackground(Color.red);
                    check = false;
                }else if(data.isEmail(nvContactEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    nvContactEmail.setBackground(Color.red);
                    check = false;
                }else if(data.isEmail(nvBankNo.getText())==false){
                    MsgBox.alert(this, "Bank No khong dung dinh dang");
                    nvBankNo.setBackground(Color.red);
                    check = false;
                }else{
                    check = true;
                }
                
                
                 
                
                
                
                if(check==true){
                    try {
                        empDAO.insert(model);
                        this.loadTable();
                        this.clear();
                        MsgBox.alert(this, "Thêm thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Thêm thất bại");
                        System.out.println(e);
                    }                    
                    break;
                }
            }while(check==true);
        }

    }
    
    void update() throws ParseException{
        employee model = getModelUpdate();
         nvName.setBorder(Color.WHITE);
        nvNumber.setBorder(white);
        nvEmail.setBorder(white);
        nvCity.setBorder(white);
        nvBillAdd.setBorder(white);
        nvShipAdd.setBorder(white);
        nvBank.setBorder(white);
        nvBankNo.setBorder(white);
        nvContactName.setBorder(white);
        nvContactNumber.setBorder(white);
        nvContactEmail.setBorder(white);
        nvOnline.setBorder(white);
        nvOtherInfo.setBorder(white);
        nvDescription.setBorder(white);
        if (nvName.getText().length() == 0 || nvNumber.getText().length() == 0 || nvEmail.getText().length() == 0 || nvCity.getText().length() == 0 || nvBillAdd.getText().length() == 0
                || nvShipAdd.getText().length() == 0 || nvBank.getText().length() == 0 || nvBankNo.getText().length() == 0
                || nvContactName.getText().length() == 0 || nvContactNumber.getText().length() == 0 || nvContactEmail.getText().length() == 0 || nvOnline.getText().length() == 0 || nvOtherInfo.getText().length() == 0 || nvDescription.getText().length() == 0) {
            MsgBox.alert(this, "Không được bỏ trống");
            if (nvName.getText().length() == 0) {
                nvName.setBorder(Color.red);
            }

            if (nvNumber.getText().length() == 0) {
                nvNumber.setBorder(Color.red);
            }
            if (nvEmail.getText().length() == 0) {
                nvEmail.setBorder(Color.red);
            }
            if (nvCity.getText().length() == 0) {
                nvCity.setBorder(Color.red);
            }
            if (nvBillAdd.getText().length() == 0) {
                nvBillAdd.setBorder(Color.red);
            }
            if (nvShipAdd.getText().length() == 0) {
                nvShipAdd.setBorder(Color.red);
            }
            if (nvBank.getText().length() == 0) {
                nvBank.setBorder(Color.red);
            }
            if (nvBankNo.getText().length() == 0) {
                nvBankNo.setBorder(Color.red);
            }
            if (nvContactName.getText().length() == 0) {
                nvContactName.setBorder(Color.red);
            }
            if (nvContactNumber.getText().length() == 0) {
                nvContactNumber.setBorder(Color.red);
            }
            if (nvContactEmail.getText().length() == 0) {
                nvContactEmail.setBorder(Color.red);
            }
            if (nvOnline.getText().length() == 0) {
                nvOnline.setBorder(Color.red);
            }
            if (nvOtherInfo.getText().length() == 0) {
                nvOtherInfo.setBorder(Color.red);
            }
            if (nvDescription.getText().length() == 0) {
                nvDescription.setBorder(Color.red);
            }
        } else {
            boolean check = true;
            do {
                if (data.isName(nvName.getText()) == false) {
                    MsgBox.alert(this, "Ten nhan vien khong dung dinh dang");
                    nvName.setBackground(Color.red);
                    check = false;
                } else if (data.ContainNumberOnly(nvNumber.getText()) == false) {
                    MsgBox.alert(this, "Number khong dung dinh dang");
                    nvNumber.setBackground(Color.red);
                    check = false;
                } else if (data.isEmail(nvEmail.getText()) == false) {
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    nvEmail.setBackground(Color.red);
                    check = false;
                } else if (data.isName(nvContactName.getText()) == false) {
                    MsgBox.alert(this, "Contact name khong dung dinh dang");
                    nvContactName.setBackground(Color.red);
                    check = false;
                } else if (data.ContainNumberOnly(nvContactNumber.getText()) == false) {
                    MsgBox.alert(this, "Contact Number khong dung dinh dang");
                    nvContactNumber.setBackground(Color.red);
                    check = false;
                } else if (data.isEmail(nvContactEmail.getText()) == false) {
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    nvContactEmail.setBackground(Color.red);
                    check = false;
                } else if (data.isEmail(nvBankNo.getText()) == false) {
                    MsgBox.alert(this, "Bank No khong dung dinh dang");
                    nvBankNo.setBackground(Color.red);
                    check = false;
                } else {
                    check = true;
                }

                if (check == true) {
                    try {
                        empDAO.update(model);
                        this.loadTable();
                        MsgBox.alert(this, "Cập nhật thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại");
                        e.printStackTrace();
                    }
                    break;
                }
            } while (check == true);
        }
        
    }
    
    void delete(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa người này")){
            String manv = lblNVID.getText();
            try {
                empDAO.delete(manv);
                this.loadTable();
                this.clear();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    void returnEmp(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn Return người này")){
            int row = tblNhanVien2.getSelectedRow();
            String manv = tblNhanVien2.getValueAt(row, 0).toString();
            try {
                empDAO.returnCus(manv);
                loadTable();
                loadTableInactive();
                this.clear();
                MsgBox.alert(this, "Return thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Return thất bại");
            }
        }
    }
    
    public void search(){

        String name = cboEmpName.getSelectedItem().toString();
        String number = cboEmpNumber.getSelectedItem().toString();
        String email = cboEmpEmail.getSelectedItem().toString();
        if(name.equalsIgnoreCase("Selected")){
            name = "";
        }
        if(email.equalsIgnoreCase("Selected")){
            email = "";
        }
        if(number.equalsIgnoreCase("Selected")){
            number = "";
        }
        try {
            DefaultTableModel dtm = (DefaultTableModel) tblNhanVien.getModel();
            dtm.setRowCount(0);
            
                        Statement s =db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE emp_number LIKE '%"+number+"%' "
                    + "AND emp_name LIKE N'%"+name+"%' AND emp_email LIKE '%"+email+"%' And status = 'Active'");
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("employee_id"));
                v.add(rs.getString("emp_name"));
                v.add(rs.getString("emp_number"));
                v.add(rs.getString("emp_email"));
                
                dtm.addRow(v);
            }
            s.close();
            rs.close();
                db.myCon().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void search1(){

        String name = cboEmpName1.getSelectedItem().toString();
        String number = cboEmpNumber1.getSelectedItem().toString();
        String email = cboEmpEmail1.getSelectedItem().toString();
        if(name.equalsIgnoreCase("Selected")){
            name = "";
        }
        if(email.equalsIgnoreCase("Selected")){
            email = "";
        }
        if(number.equalsIgnoreCase("Selected")){
            number = "";
        }
        try {
            DefaultTableModel dtm = (DefaultTableModel) tblNhanVien2.getModel();
            dtm.setRowCount(0);
            
                        Statement s =db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE emp_number LIKE '%"+number+"%' "
                    + "AND emp_name LIKE N'%"+name+"%' AND emp_email LIKE '%"+email+"%' And status = 'Inactive'");
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("employee_id"));
                v.add(rs.getString("emp_name"));
                v.add(rs.getString("emp_number"));
                v.add(rs.getString("emp_email"));
                
                dtm.addRow(v);
            }
            s.close();
            rs.close();
                db.myCon().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new raven.tabbed.TabbedPaneCustom();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        c_search_tbl = new searchwing.TextFieldAnimation();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        myButton10 = new button.MyButton();
        cboEmpNumber = new combo_suggestion.ComboBoxSuggestion();
        cboEmpEmail = new combo_suggestion.ComboBoxSuggestion();
        cboEmpName = new combo_suggestion.ComboBoxSuggestion();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        myButton7 = new button.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        p_src = new searchwing.TextFieldAnimation();
        lblNVID = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nvName = new textfield_suggestion.TextFieldSuggestion();
        nvContactName = new textfield_suggestion.TextFieldSuggestion();
        jLabel10 = new javax.swing.JLabel();
        nvEmail = new textfield_suggestion.TextFieldSuggestion();
        jLabel11 = new javax.swing.JLabel();
        nvCity = new textfield_suggestion.TextFieldSuggestion();
        jLabel12 = new javax.swing.JLabel();
        nvBillAdd = new textfield_suggestion.TextFieldSuggestion();
        jLabel13 = new javax.swing.JLabel();
        nvShipAdd = new textfield_suggestion.TextFieldSuggestion();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        nvBank = new textfield_suggestion.TextFieldSuggestion();
        nvBankNo = new textfield_suggestion.TextFieldSuggestion();
        jLabel16 = new javax.swing.JLabel();
        jCheckBoxCustom1 = new checkbox.JCheckBoxCustom();
        jLabel17 = new javax.swing.JLabel();
        myButton1 = new button.MyButton();
        myButton2 = new button.MyButton();
        myButton3 = new button.MyButton();
        myButton4 = new button.MyButton();
        nvUserID = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        nvContactEmail = new textfield_suggestion.TextFieldSuggestion();
        nvContactNumber = new textfield_suggestion.TextFieldSuggestion();
        nvOnline = new textfield_suggestion.TextFieldSuggestion();
        nvOtherInfo = new textfield_suggestion.TextFieldSuggestion();
        nvDescription = new textfield_suggestion.TextFieldSuggestion();
        nvNumber = new textfield_suggestion.TextFieldSuggestion();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        empNameReport = new textfield_suggestion.TextFieldSuggestion();
        jLabel25 = new javax.swing.JLabel();
        empNumberReport = new textfield_suggestion.TextFieldSuggestion();
        empCityReport = new textfield_suggestion.TextFieldSuggestion();
        jLabel26 = new javax.swing.JLabel();
        myButton5 = new button.MyButton();
        myButton6 = new button.MyButton();
        jPanel5 = new javax.swing.JPanel();
        c_search_tbl1 = new searchwing.TextFieldAnimation();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        myButton9 = new button.MyButton();
        myButton11 = new button.MyButton();
        cboEmpNumber1 = new combo_suggestion.ComboBoxSuggestion();
        cboEmpEmail1 = new combo_suggestion.ComboBoxSuggestion();
        cboEmpName1 = new combo_suggestion.ComboBoxSuggestion();
        myButton8 = new button.MyButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNhanVien2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNhanVien3 = new javax.swing.JTable();

        tabs.setSelectedColor(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Search:");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        c_search_tbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_search_tblActionPerformed(evt);
            }
        });
        c_search_tbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                c_search_tblKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                c_search_tblKeyReleased(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Employee Name:");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("Employee Email:");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("Mobile Number:");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton10.setText("Filter");
        myButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton10ActionPerformed(evt);
            }
        });

        cboEmpNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpNumberActionPerformed(evt);
            }
        });
        cboEmpNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboEmpNumberKeyReleased(evt);
            }
        });

        cboEmpEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpEmailActionPerformed(evt);
            }
        });
        cboEmpEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboEmpEmailKeyReleased(evt);
            }
        });

        cboEmpName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpNameActionPerformed(evt);
            }
        });
        cboEmpName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboEmpNameKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboEmpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboEmpNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboEmpName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboEmpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboEmpNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null}
            },
            new String [] {
                "empID", "empName", "empNumber", "empEmail"
            }
        ));
        tblNhanVien.setFocusable(false);
        tblNhanVien.setRowHeight(40);
        tblNhanVien.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblNhanVien.getTableHeader().setReorderingAllowed(false);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton7.setText("Save To Excel");

        tblNhanVien1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "empID", "empName", "empNumber", "empEmail", "empCity", "empBillAddress", "empShipAddress", "empBank", "empBankNo", "empContactName", "empContactEmail", "empContactNumber", "empOnline", "empOtherInfo", "empDescription", "UserID"
            }
        ));
        tblNhanVien1.setFocusable(false);
        tblNhanVien1.setRowHeight(40);
        tblNhanVien1.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblNhanVien1.getTableHeader().setReorderingAllowed(false);
        tblNhanVien1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNhanVien1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(c_search_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(c_search_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("All Employees", jPanel1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Search:");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        p_src.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_srcActionPerformed(evt);
            }
        });
        p_src.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p_srcKeyReleased(evt);
            }
        });

        lblNVID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNVID.setForeground(new java.awt.Color(51, 153, 255));
        lblNVID.setText(" ");
        lblNVID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("Employee Name:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nvName.setForeground(new java.awt.Color(51, 153, 255));
        nvName.setToolTipText("");

        nvContactName.setForeground(new java.awt.Color(51, 153, 255));
        nvContactName.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 255));
        jLabel10.setText("Office Number:");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nvEmail.setForeground(new java.awt.Color(51, 153, 255));
        nvEmail.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 255));
        jLabel11.setText("Customer Email:");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nvCity.setForeground(new java.awt.Color(51, 153, 255));
        nvCity.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("City:");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nvBillAdd.setForeground(new java.awt.Color(51, 153, 255));
        nvBillAdd.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Billing Address:");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nvShipAdd.setForeground(new java.awt.Color(51, 153, 255));
        nvShipAdd.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Shipping Address:");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 255));
        jLabel15.setText("Bank:");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        nvBank.setForeground(new java.awt.Color(51, 153, 255));
        nvBank.setToolTipText("");

        nvBankNo.setForeground(new java.awt.Color(51, 153, 255));
        nvBankNo.setToolTipText("");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("Account No:");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jCheckBoxCustom1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBoxCustom1.setForeground(new java.awt.Color(51, 153, 255));
        jCheckBoxCustom1.setText("Same as Billing");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 153, 255));
        jLabel17.setText("Contact Person Info:");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/save.png"))); // NOI18N
        myButton1.setText("Save");
        myButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton1ActionPerformed(evt);
            }
        });

        myButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/update.png"))); // NOI18N
        myButton2.setText("Update");
        myButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton2ActionPerformed(evt);
            }
        });

        myButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/delete.png"))); // NOI18N
        myButton3.setText("Recycle");
        myButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton3ActionPerformed(evt);
            }
        });

        myButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/annual-report (1).png"))); // NOI18N
        myButton4.setText("All Customer's Reports");
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        nvUserID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nvUserID.setForeground(new java.awt.Color(51, 153, 255));
        nvUserID.setText("UserID:");
        nvUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Contact Person:");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 153, 255));
        jLabel19.setText("Email:");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 255));
        jLabel21.setText("Mobile Number:");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 153, 255));
        jLabel20.setText("Online:");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 153, 255));
        jLabel23.setText("Other's Info");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 255));
        jLabel22.setText("Desctiption:");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/information.png"))); // NOI18N

        nvContactEmail.setForeground(new java.awt.Color(51, 153, 255));
        nvContactEmail.setToolTipText("");

        nvContactNumber.setForeground(new java.awt.Color(51, 153, 255));
        nvContactNumber.setToolTipText("");

        nvOnline.setForeground(new java.awt.Color(51, 153, 255));
        nvOnline.setToolTipText("");

        nvOtherInfo.setForeground(new java.awt.Color(51, 153, 255));
        nvOtherInfo.setToolTipText("");

        nvDescription.setForeground(new java.awt.Color(51, 153, 255));
        nvDescription.setToolTipText("");

        nvNumber.setForeground(new java.awt.Color(51, 153, 255));
        nvNumber.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNVID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(nvName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(nvEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(nvCity, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nvBank, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nvBankNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nvNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nvBillAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nvShipAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nvUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17))
                                        .addGap(412, 412, 412))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(nvContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel18)
                                                    .addComponent(jLabel21)
                                                    .addComponent(jLabel20)
                                                    .addComponent(jLabel23)
                                                    .addComponent(jLabel22))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(nvContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(nvOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(nvOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(nvDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(nvContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(110, 110, 110))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jLabel37)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 27, Short.MAX_VALUE)
                        .addComponent(p_src, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(598, 598, 598))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(p_src, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNVID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(nvName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(nvNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(nvEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(nvCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvBillAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(nvUserID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvContactName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(nvContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvOnline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nvShipAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(nvBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nvBankNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 42, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabs.addTab("Add Employee's", jPanel2);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 153, 255));
        jLabel24.setText("Enployee Name:");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        empNameReport.setForeground(new java.awt.Color(51, 153, 255));
        empNameReport.setToolTipText("");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 153, 255));
        jLabel25.setText("Employee Number:");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        empNumberReport.setForeground(new java.awt.Color(51, 153, 255));
        empNumberReport.setToolTipText("");

        empCityReport.setForeground(new java.awt.Color(51, 153, 255));
        empCityReport.setToolTipText("");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 153, 255));
        jLabel26.setText("City:");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/search x30.png"))); // NOI18N
        myButton5.setText("View Report");
        myButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(empNameReport, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                            .addComponent(empNumberReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(myButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(empCityReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(43, 43, 43))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(empNameReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(empNumberReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(empCityReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        myButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/business.png"))); // NOI18N
        myButton6.setText("All Employee Full Detail's");
        myButton6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        myButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(576, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(782, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(317, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(494, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Employee's Report", jPanel6);

        c_search_tbl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_search_tbl1ActionPerformed(evt);
            }
        });
        c_search_tbl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                c_search_tbl1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                c_search_tbl1KeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("Search:");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Employee Name:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 153, 255));
        jLabel28.setText("Employee Email:");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 153, 255));
        jLabel29.setText("Mobile Number:");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/update.png"))); // NOI18N
        myButton9.setText("Return");
        myButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton9ActionPerformed(evt);
            }
        });

        myButton11.setText("Filter");
        myButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton11ActionPerformed(evt);
            }
        });

        cboEmpNumber1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpNumber1ActionPerformed(evt);
            }
        });
        cboEmpNumber1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboEmpNumber1KeyReleased(evt);
            }
        });

        cboEmpEmail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpEmail1ActionPerformed(evt);
            }
        });
        cboEmpEmail1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboEmpEmail1KeyReleased(evt);
            }
        });

        cboEmpName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEmpName1ActionPerformed(evt);
            }
        });
        cboEmpName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboEmpName1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboEmpName1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboEmpEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(cboEmpNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEmpName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEmpEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEmpNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(42, 42, 42)))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        myButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton8.setText("Save To Excel");

        tblNhanVien2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null}
            },
            new String [] {
                "empID", "empName", "empNumber", "empEmail"
            }
        ));
        tblNhanVien2.setFocusable(false);
        tblNhanVien2.setRowHeight(40);
        tblNhanVien2.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblNhanVien2.getTableHeader().setReorderingAllowed(false);
        tblNhanVien2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVien2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNhanVien2);

        tblNhanVien3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "empID", "empName", "empNumber", "empEmail", "empCity", "empBillAddress", "empShipAddress", "empBank", "empBankNo", "empContactName", "empContactEmail", "empContactNumber", "empOnline", "empOtherInfo", "empDescription", "UserID"
            }
        ));
        tblNhanVien3.setFocusable(false);
        tblNhanVien3.setRowHeight(40);
        tblNhanVien3.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblNhanVien3.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblNhanVien3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(c_search_tbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(c_search_tbl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("BlackList", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void c_search_tblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_search_tblActionPerformed
 
    }//GEN-LAST:event_c_search_tblActionPerformed

    private void p_srcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_srcActionPerformed
      
    }//GEN-LAST:event_p_srcActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        try {
            update();
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        this.loadTable();
    }//GEN-LAST:event_myButton2ActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
        delete();
        this.loadTable();
    }//GEN-LAST:event_myButton3ActionPerformed

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myButton4ActionPerformed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
        try {
            insert();
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        this.loadTable();
    }//GEN-LAST:event_myButton1ActionPerformed

    private void c_search_tblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_search_tblKeyPressed
 
    }//GEN-LAST:event_c_search_tblKeyPressed

    private void c_search_tblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_search_tblKeyReleased
        String name = c_search_tbl.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblNhanVien.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE emp_name LIKE '%"+name+"%'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("employee_id"));
                v.add(rs.getString("emp_name"));
                v.add(rs.getString("emp_number"));
                v.add(rs.getString("emp_email"));
                
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_c_search_tblKeyReleased

    private void p_srcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_srcKeyReleased
        String search = p_src.getText();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE employee_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblNVID.setText(rs.getString("employee_id"));
                            nvName.setText(rs.getString("emp_name"));
                            nvNumber.setText(rs.getString("emp_number"));
                            nvEmail.setText(rs.getString("emp_email"));
                            nvCity.setText(rs.getString("emp_city"));
                            nvBillAdd.setText(rs.getString("emp_bill_add"));
                            nvShipAdd.setText(rs.getString("emp_ship_add"));
                            nvBank.setText(rs.getString("emp_bank"));
                            nvBankNo.setText(rs.getString("emp_bank_no"));
                            nvContactName.setText(rs.getString("emp_contact_name"));
                            nvContactEmail.setText(rs.getString("emp_contact_email"));
                            nvContactNumber.setText(rs.getString("emp_contact_number"));
                            nvOnline.setText(rs.getString("emp_contact_online"));
                            nvOtherInfo.setText(rs.getString("emp_contact_otherinfo"));
                            nvDescription.setText(rs.getString("emp_contact_description"));
                            nvUserID.setText(rs.getString("users_id"));      
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
    }//GEN-LAST:event_p_srcKeyReleased

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
       Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/AllEmployeess.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, db.myCon());
                    
                    String outputFileName = "Employees.pdf";

                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);

                    // Mở tệp PDF sau khi lưu nếu bạn muốn
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File(outputFileName));
                    }
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            });
            t.start();
    }//GEN-LAST:event_myButton6ActionPerformed

    private void myButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton5ActionPerformed
        HashMap para = new HashMap();
        String paraName = "%" + empNameReport.getText() + "%";
        String paraNumber = "%" + empNumberReport.getText() + "%";
        String paraCity = "%" + empCityReport.getText() + "%";

        // Xử lý giá trị rỗng cho các tham số không cung cấp giá trị
        if (paraName.isEmpty()) {
            paraName = "%%";
        }
        if (paraNumber.isEmpty()) {
            paraNumber = "%%";
        }
        if (paraCity.isEmpty()) {
            paraCity = "%%";
        }
        para.put("Para_name",paraName);
        para.put("Para_number", paraNumber);
        para.put("Para_city", paraCity);
        
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/AllEmployee.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, para, db.myCon());
                    String outputFileName = "Employees"+empNameReport.getText()+".pdf";

                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);

                    // Mở tệp PDF sau khi lưu nếu bạn muốn
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File(outputFileName));
                    }
                    JasperViewer.viewReport(jasperPrint, false);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            });
            t.start();
    }//GEN-LAST:event_myButton5ActionPerformed

    private void c_search_tbl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_search_tbl1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_search_tbl1ActionPerformed

    private void c_search_tbl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_search_tbl1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_search_tbl1KeyPressed

    private void c_search_tbl1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_c_search_tbl1KeyReleased
               String name = c_search_tbl1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblNhanVien2.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE emp_name LIKE N'%"+name+"%'  and status = 'Inactive'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("employee_id"));
                v.add(rs.getString("emp_name"));
                v.add(rs.getString("emp_number"));
                v.add(rs.getString("emp_email"));
                
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_c_search_tbl1KeyReleased

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        int row = tblNhanVien.getSelectedRow();
            String supID = tblNhanVien.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblNhanVien1.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from employees where employee_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("employee_id"));
                    v.add(rs.getString("emp_name"));
                    v.add(rs.getString("emp_number"));
                    v.add(rs.getString("emp_email"));
                    v.add(rs.getString("emp_city"));
                    v.add(rs.getString("emp_bill_add"));
                    v.add(rs.getString("emp_ship_add"));
                    v.add(rs.getString("emp_bank"));
                    v.add(rs.getString("emp_bank_no"));
                    v.add(rs.getString("emp_contact_name"));
                    v.add(rs.getString("emp_contact_email"));
                    v.add(rs.getString("emp_contact_number"));
                    v.add(rs.getString("emp_contact_online"));
                    v.add(rs.getString("emp_contact_otherinfo"));
                    v.add(rs.getString("emp_contact_description"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void tblNhanVien1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVien1MousePressed
       if(evt.getClickCount()==2){
                    String search = tblNhanVien1.getValueAt(tblNhanVien1.getSelectedRow(), 0).toString();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE employee_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblNVID.setText(rs.getString("employee_id"));
                            nvName.setText(rs.getString("emp_name"));
                            nvNumber.setText(rs.getString("emp_number"));
                            nvEmail.setText(rs.getString("emp_email"));
                            nvCity.setText(rs.getString("emp_city"));
                            nvBillAdd.setText(rs.getString("emp_bill_add"));
                            nvShipAdd.setText(rs.getString("emp_ship_add"));
                            nvBank.setText(rs.getString("emp_bank"));
                            nvBankNo.setText(rs.getString("emp_bank_no"));
                            nvContactName.setText(rs.getString("emp_contact_name"));
                            nvContactEmail.setText(rs.getString("emp_contact_email"));
                            nvContactNumber.setText(rs.getString("emp_contact_number"));
                            nvOnline.setText(rs.getString("emp_contact_online"));
                            nvOtherInfo.setText(rs.getString("emp_contact_otherinfo"));
                            nvDescription.setText(rs.getString("emp_contact_description"));
                            nvUserID.setText(rs.getString("users_id"));        
                        }
                        tabs.setSelectedIndex(1);
                        s.close();
                        rs.close();
                            db.myCon().close();
                                                      }catch(Exception e){
                                System.out.println(e);
                            }
        }
    }//GEN-LAST:event_tblNhanVien1MousePressed

    private void myButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton9ActionPerformed
        returnEmp();
    }//GEN-LAST:event_myButton9ActionPerformed

    private void tblNhanVien2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVien2MouseClicked
        int row = tblNhanVien2.getSelectedRow();
            String supID = tblNhanVien2.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblNhanVien3.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from employees where employee_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("employee_id"));
                    v.add(rs.getString("emp_name"));
                    v.add(rs.getString("emp_number"));
                    v.add(rs.getString("emp_email"));
                    v.add(rs.getString("emp_city"));
                    v.add(rs.getString("emp_bill_add"));
                    v.add(rs.getString("emp_ship_add"));
                    v.add(rs.getString("emp_bank"));
                    v.add(rs.getString("emp_bank_no"));
                    v.add(rs.getString("emp_contact_name"));
                    v.add(rs.getString("emp_contact_email"));
                    v.add(rs.getString("emp_contact_number"));
                    v.add(rs.getString("emp_contact_online"));
                    v.add(rs.getString("emp_contact_otherinfo"));
                    v.add(rs.getString("emp_contact_description"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblNhanVien2MouseClicked

    private void myButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton10ActionPerformed
        search();

    }//GEN-LAST:event_myButton10ActionPerformed

    private void cboEmpNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpNumberActionPerformed

    private void cboEmpNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEmpNumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpNumberKeyReleased

    private void cboEmpEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpEmailActionPerformed

    private void cboEmpEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEmpEmailKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpEmailKeyReleased

    private void cboEmpNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpNameActionPerformed

    }//GEN-LAST:event_cboEmpNameActionPerformed

    private void cboEmpNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEmpNameKeyReleased

    }//GEN-LAST:event_cboEmpNameKeyReleased

    private void myButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton11ActionPerformed
        search1();
    }//GEN-LAST:event_myButton11ActionPerformed

    private void cboEmpNumber1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpNumber1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpNumber1ActionPerformed

    private void cboEmpNumber1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEmpNumber1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpNumber1KeyReleased

    private void cboEmpEmail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpEmail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpEmail1ActionPerformed

    private void cboEmpEmail1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEmpEmail1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpEmail1KeyReleased

    private void cboEmpName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEmpName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpName1ActionPerformed

    private void cboEmpName1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboEmpName1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEmpName1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private searchwing.TextFieldAnimation c_search_tbl;
    private searchwing.TextFieldAnimation c_search_tbl1;
    private combo_suggestion.ComboBoxSuggestion cboEmpEmail;
    private combo_suggestion.ComboBoxSuggestion cboEmpEmail1;
    private combo_suggestion.ComboBoxSuggestion cboEmpName;
    private combo_suggestion.ComboBoxSuggestion cboEmpName1;
    private combo_suggestion.ComboBoxSuggestion cboEmpNumber;
    private combo_suggestion.ComboBoxSuggestion cboEmpNumber1;
    private textfield_suggestion.TextFieldSuggestion empCityReport;
    private textfield_suggestion.TextFieldSuggestion empNameReport;
    private textfield_suggestion.TextFieldSuggestion empNumberReport;
    private checkbox.JCheckBoxCustom jCheckBoxCustom1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblNVID;
    private button.MyButton myButton1;
    private button.MyButton myButton10;
    private button.MyButton myButton11;
    private button.MyButton myButton2;
    private button.MyButton myButton3;
    private button.MyButton myButton4;
    private button.MyButton myButton5;
    private button.MyButton myButton6;
    private button.MyButton myButton7;
    private button.MyButton myButton8;
    private button.MyButton myButton9;
    private textfield_suggestion.TextFieldSuggestion nvBank;
    private textfield_suggestion.TextFieldSuggestion nvBankNo;
    private textfield_suggestion.TextFieldSuggestion nvBillAdd;
    private textfield_suggestion.TextFieldSuggestion nvCity;
    private textfield_suggestion.TextFieldSuggestion nvContactEmail;
    private textfield_suggestion.TextFieldSuggestion nvContactName;
    private textfield_suggestion.TextFieldSuggestion nvContactNumber;
    private textfield_suggestion.TextFieldSuggestion nvDescription;
    private textfield_suggestion.TextFieldSuggestion nvEmail;
    private textfield_suggestion.TextFieldSuggestion nvName;
    private textfield_suggestion.TextFieldSuggestion nvNumber;
    private textfield_suggestion.TextFieldSuggestion nvOnline;
    private textfield_suggestion.TextFieldSuggestion nvOtherInfo;
    private textfield_suggestion.TextFieldSuggestion nvShipAdd;
    private javax.swing.JLabel nvUserID;
    private searchwing.TextFieldAnimation p_src;
    private raven.tabbed.TabbedPaneCustom tabs;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblNhanVien1;
    private javax.swing.JTable tblNhanVien2;
    private javax.swing.JTable tblNhanVien3;
    // End of variables declaration//GEN-END:variables
}
