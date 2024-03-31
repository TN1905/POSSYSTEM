/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Utils.Data;
import Utils.MsgBox;
import Utils.db;
import com.raven.component.Form;
import com.raven.dao.employeeDAO;
import com.raven.dao.supplierDAO;
import com.raven.entity.employee;
import com.raven.entity.supplier;
import com.raven.entity.users;
import com.raven.event.EventColorChange;
import com.raven.menu.EventMenu;
import com.raven.properties.SystemProperties;
import com.raven.theme.SystemTheme;
import com.raven.theme.ThemeColor;
import com.raven.theme.ThemeColorChange;
import java.awt.Color;
import com.raven.form.Setting_Form;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import searchwing.EventCallBack;
import searchwing.EventTextField;
/**
 *
 * @author ADMIN
 */
public class Supplier_Form extends Form {
private Setting_Form settingForm;
    int index = 0;
    private users user;
    
    supplierDAO supDAO = new supplierDAO();
    public void setUser(users user1){
        user = user1;
    }
    String ngayThangNamGioPhutGiay = "2023-08-03 14:30:00";
        String pattern = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     long currentTimestamp = getCurrentTimestamp();
    public Supplier_Form(users user) {
        initComponents();
        init();
        tblSupplier.getTableHeader().setForeground(new Color(255,255,255));  
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(51, 153, 255));
        headerRenderer.setForeground(new Color(255, 255, 255));
        tblSupplier.getTableHeader().setDefaultRenderer(headerRenderer);
        supUserID.setText(user.getUserID());
        loadTableSupPro();
        loadTableSupProInactive();
        loadTable();
    }
    
    public void init(){
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel4.setOpaque(false);
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel3.setBackground(new Color(0, 0, 0, 0));
        jPanel3.setOpaque(false);
        jPanel5.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setOpaque(false);
        tabs.setBackground(new Color(0, 0, 0, 0));
        tabs.setOpaque(false);
  
        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setOpaque(false);
        fillComboBoxName();
        fillComboBoxProduct();
        fillComboBoxEmail();
        fillComboBoxName1();
        fillComboBoxProduct1();
        fillComboBoxEmail1();
 
        
    }
    
    public long getCurrentTimestamp() {
        Instant currentInstant = Instant.now();
        currentTimestamp = currentInstant.getEpochSecond();
        System.out.println(currentTimestamp);
        return currentInstant.getEpochSecond();
    }
    
    void loadTable(){
        DefaultTableModel model = (DefaultTableModel) tblSupplier1.getModel();
        model.setRowCount(0);
        try {
  
            List<supplier>list =  supDAO.selectAll();
      
            for(supplier nh: list){
                Object[] row = {          
                    nh.getSupID(),
                    nh.getSupName(),
                    nh.getSupNumber(),
                    nh.getSupEmail(),
                    nh.getSupCity(),
                    nh.getBillSupAdd(),
                    nh.getShipSupAdd(),
                    nh.getSupBank(),
                    nh.getSupBankNo(),
                    nh.getSupContactPerson(),
                    nh.getSupEmailPerson(),
                    nh.getSupNumberPerson(),
                    nh.getSupOnline(),
                    nh.getOtherSupInfo(),
                    nh.getSupDescription(),
                    nh.getSupUserID(),
                    
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    public void loadTableSupPro(){
        DefaultTableModel model = (DefaultTableModel) tblSupplier.getModel();
        model.setRowCount(0);
        try {
            String sql = "select suppliers.supplier_id as supid,sup_name,sup_number,sup_contact_email,pro_name from suppliers inner join products on suppliers.supplier_id = products.supplier_id where suppliers.status = 'Active'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("supid"));
                v.add(rs.getString("sup_name"));
                v.add(rs.getString("sup_number"));
                v.add(rs.getString("sup_contact_email"));
                v.add(rs.getString("pro_name"));
                model.addRow(v);
            }

        } catch (Exception e) {
        }
    }
    
    public void loadTableSupProInactive(){
        DefaultTableModel model = (DefaultTableModel) tblSupplier2.getModel();
        model.setRowCount(0);
        try {
            String sql = "select suppliers.supplier_id as supid,sup_name,sup_number,sup_contact_email,pro_name from suppliers inner join products on suppliers.supplier_id = products.supplier_id where suppliers.status = 'Inactive'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("supid"));
                v.add(rs.getString("sup_name"));
                v.add(rs.getString("sup_number"));
                v.add(rs.getString("sup_contact_email"));
                v.add(rs.getString("pro_name"));
                model.addRow(v);
            }

        } catch (Exception e) {
        }
    }
    
    void fillComboBoxName(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupName.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<supplier> list = supDAO.selectAll();
            for(supplier cd:list){
                model.addElement(cd.getSupName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxProduct(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupPro.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<supplier> list = supDAO.selectAll();
            for(supplier cd:list){
                try {
                    String sql = "select * from products where supplier_id = '"+cd.getSupID()+"'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    if(rs.next()){
                        model.addElement(rs.getString("pro_name"));
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxEmail(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupEmail.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<supplier> list = supDAO.selectAll();
            for(supplier cd:list){
                model.addElement(cd.getSupEmail());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxName1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupName1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<supplier> list = supDAO.selectAllInactive();
            for(supplier cd:list){
                model.addElement(cd.getSupName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxProduct1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupPro1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<supplier> list = supDAO.selectAllInactive();
            for(supplier cd:list){
                try {
                    String sql = "select * from products where supplier_id = '"+cd.getSupID()+"'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    if(rs.next()){
                        model.addElement(rs.getString("pro_name"));
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxEmail1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupEmail1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<supplier> list = supDAO.selectAllInactive();
            for(supplier cd:list){
                model.addElement(cd.getSupEmail());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    
    
    void setModel(supplier model){
        lblSupID.setText(model.getSupID());
        supName.setText(model.getSupName());
        supNumber.setText(String.valueOf(model.getSupNumber()));
        supEmail.setText(model.getSupEmail());
        supCity.setText(model.getSupCity());
        supBillAdd.setText(model.getBillSupAdd());
        supShipAdd.setText(model.getShipSupAdd());
        supBank.setText(model.getSupBank());
        supBankNo.setText(String.valueOf(model.getSupBankNo()));
        supContactName.setText(model.getSupContactPerson());
        supContactEmail.setText(model.getSupEmailPerson());
        supContactNumber.setText(String.valueOf(model.getSupNumberPerson()));
        supOnline.setText(model.getSupOnline());
        supOtherInfo.setText(model.getOtherSupInfo());
        supDescription.setText(model.getSupDescription());
        supUserID.setText(model.getSupUserID());
    }
    
    supplier getModel() throws ParseException {
        supplier model = new supplier();
        int countNV=0;
        String employeeID = "";
       
                employeeID = "sup" + currentTimestamp;
            
        
        model.setSupID(employeeID);
        model.setSupName(supName.getText());
        String Number = supNumber.getText();
        if(data.ContainNumberOnly(Number)==false){
            model.setSupNumber(0);
        }else{
            if(Number.isEmpty()){
           model.setSupNumber(0);
            }else{
                model.setSupNumber(Integer.parseInt(Number));
            }
        }
        
        model.setSupEmail(supEmail.getText());
        model.setSupCity(supCity.getText());
        model.setBillSupAdd(supBillAdd.getText());
        model.setShipSupAdd(supShipAdd.getText());
        model.setSupBank(supBank.getText());
        String bankNO = supBankNo.getText();
        if(data.ContainNumberOnly(bankNO)==false){
            model.setSupBankNo(0);
        }else{
            if(bankNO.isEmpty()){
           model.setSupBankNo(0);
            }else{
                model.setSupBankNo(Integer.parseInt(bankNO));
            }
        }
        
        model.setSupContactPerson(supContactName.getText());
        model.setSupEmailPerson(supContactEmail.getText());
        String contactNumber = supContactNumber.getText();
        if(data.ContainNumberOnly(contactNumber)==false){
            model.setSupNumberPerson(0);
        }else{
            if(contactNumber.isEmpty()){
           model.setSupNumberPerson(0);
            }else{
                model.setSupNumberPerson(Integer.parseInt(contactNumber));
            }
        }
        
        model.setSupOnline(supOnline.getText());
        model.setOtherSupInfo(supOtherInfo.getText());
        model.setSupDescription(supDescription.getText());
        model.setSupUserID(supUserID.getText());
        model.setStatus("Active");
        return model;
    }
    
    supplier getModelUpdate() {
        supplier model = new supplier();
        int countNV=0;
        
        model.setSupID(lblSupID.getText());
        model.setSupName(supName.getText());
        String Number = supNumber.getText();
        if(data.ContainNumberOnly(Number)==false){
            model.setSupNumber(0);
        }else{
            if(Number.isEmpty()){
           model.setSupNumber(0);
            }else{
                model.setSupNumber(Integer.parseInt(Number));
            }
        }
        
        model.setSupEmail(supEmail.getText());
        model.setSupCity(supCity.getText());
        model.setBillSupAdd(supBillAdd.getText());
        model.setShipSupAdd(supShipAdd.getText());
        model.setSupBank(supBank.getText());
        String bankNO = supBankNo.getText();
        if(data.ContainNumberOnly(bankNO)==false){
            model.setSupBankNo(0);
        }else{
            if(bankNO.isEmpty()){
           model.setSupBankNo(0);
            }else{
                model.setSupBankNo(Integer.parseInt(bankNO));
            }
        }
        
        model.setSupContactPerson(supContactName.getText());
        model.setSupEmailPerson(supContactEmail.getText());
        String contactNumber = supContactNumber.getText();
        if(data.ContainNumberOnly(contactNumber)==false){
            model.setSupNumberPerson(0);
        }else{
            if(contactNumber.isEmpty()){
           model.setSupNumberPerson(0);
            }else{
                model.setSupNumberPerson(Integer.parseInt(contactNumber));
            }
        }
        
        model.setSupOnline(supOnline.getText());
        model.setOtherSupInfo(supOtherInfo.getText());
        model.setSupDescription(supDescription.getText());
        
        
        return model;
    }
    
    public boolean checkTrungMa(JTextField txt){
        txt.setBackground(white);
        if(supDAO.selectByID(txt.getText())==null){
            return true;
        }else{
            txt.setBackground(pink);
            MsgBox.alert(this, txt.getName() + "Đã bị tồn tại");
            return false;
        }
    }
    
    public void setTrang(){
        supName.setBorder(white);
        supNumber.setBorder(white);
        supEmail.setBorder(white);
        supCity.setBorder(white);
        supBillAdd.setBorder(white);
        supShipAdd.setBorder(white);
        supBank.setBorder(white);
        supBankNo.setBorder(white);
        lblSupID.setBackground(white);
        supContactName.setBorder(white);
        supContactNumber.setBorder(white);
        supContactEmail.setBorder(white);
        supOnline.setBorder(white);
        supOtherInfo.setBorder(white);
        supDescription.setBorder(white);
    }
    
    void clear(){
        setTrang();
        supplier model = new supplier();
        this.setModel(model);
    }
    
    void edit(){
        setTrang();
        try {
            String manv = (String) tblSupplier.getValueAt(this.index, 0);
            supplier model = supDAO.selectByID(manv);
            if(model!=null){
                this.setModel(model);
              
                tabs.setSelectedIndex(1);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    public void searchID(){
        
        txt2.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                //  Test
                try {                   
                     String search = txt2.getText();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM suppliers WHERE supplier_id = '"+search+"'");

                        if(rs.next()){
                            lblSupID.setText(rs.getString("supplier_id"));
                            supName.setText(rs.getString("sup_name"));
                            supNumber.setText(rs.getString("sup_number"));
                            supEmail.setText(rs.getString("sup_email"));
                            supCity.setText(rs.getString("sup_city"));
                            supBillAdd.setText(rs.getString("sup_bill_add"));
                            supShipAdd.setText(rs.getString("sup_ship_add"));
                            supBank.setText(rs.getString("sup_bank"));
                            supBankNo.setText(rs.getString("sup_bank_no"));
                            supContactName.setText(rs.getString("sup_contact_name"));
                            supContactEmail.setText(rs.getString("sup_contact_email"));
                            supContactNumber.setText(rs.getString("sup_contact_number"));
                            supOnline.setText(rs.getString("sup_contact_online"));
                            supOtherInfo.setText(rs.getString("sup_contact_otherinfo"));
                            supDescription.setText(rs.getString("sup_contact_description"));
                            supUserID.setText(rs.getString("users_id"));      
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
                     
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
        txt1.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                //  Test
                try {
                    
                    String name = txt1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblSupplier.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM suppliers WHERE sup_name LIKE '%"+name+"%'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
                v.add(rs.getString(7));
                v.add(rs.getString(8));
                v.add(rs.getString(9));
                v.add(rs.getString(10));
                v.add(rs.getString(11));
                v.add(rs.getString(12));
                v.add(rs.getString(13));
                v.add(rs.getString(14));
                dt.addRow(v);
            }
            s.close();
            rs.close();
                db.myCon().close();
                loadTableSupPro();
        }catch(Exception e){
             System.out.println(e);
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
        supplier model = getModel();
        supName.setBorder(Color.WHITE);
        supNumber.setBorder(white);
        supEmail.setBorder(white);
        supCity.setBorder(white);
        supBillAdd.setBorder(white);
        supShipAdd.setBorder(white);
        supBank.setBorder(white);
        supBankNo.setBorder(white);      
        supContactName.setBorder(white);
        supContactNumber.setBorder(white);
        supContactEmail.setBorder(white);
        supOnline.setBorder(white);
        supOtherInfo.setBorder(white);
        supDescription.setBorder(white);
        if(supName.getText().length()==0 || supNumber.getText().length()==0 || supEmail.getText().length()==0||supCity.getText().length()==0||supBillAdd.getText().length()==0 
                ||supShipAdd.getText().length()==0 ||supBank.getText().length()==0 ||supBankNo.getText().length()==0 
                ||supContactName.getText().length()==0 ||supContactNumber.getText().length()==0 ||supContactEmail.getText().length()==0 ||supOnline.getText().length()==0 ||supOtherInfo.getText().length()==0 ||supDescription.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            if(supName.getText().length()==0){
                supName.setBorder(Color.red);
            }
           
            if(supNumber.getText().length()==0){
                supNumber.setBorder(Color.red);
            }

            if(supEmail.getText().length()==0){
                supEmail.setBorder(Color.red);
            }
            if(supCity.getText().length()==0){
                supCity.setBorder(Color.red);
            }
            if(supBillAdd.getText().length()==0){
                supBillAdd.setBorder(Color.red);
            }
            if(supShipAdd.getText().length()==0){
                supShipAdd.setBorder(Color.red);
            }
            if(supBank.getText().length()==0){
                supBank.setBorder(Color.red);
            }
            if(supBankNo.getText().length()==0){
                supBankNo.setBorder(Color.red);
            }
            if(supContactName.getText().length()==0){
                supContactName.setBorder(Color.red);
            }
            if(supContactNumber.getText().length()==0){
                supContactNumber.setBorder(Color.red);
            }
            if(supContactEmail.getText().length()==0){
                supContactEmail.setBorder(Color.red);
            }
            if(supOnline.getText().length()==0){
                supOnline.setBorder(Color.red);
            }
            if(supOtherInfo.getText().length()==0){
                supOtherInfo.setBorder(Color.red);
            }
            if(supDescription.getText().length()==0){
                supDescription.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{      
                if(data.isName(supName.getText())==false){
                    MsgBox.alert(this, "Ten supplier khong dung dinh dang");
                    supName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(supNumber.getText())==false){
                    MsgBox.alert(this, "Number khong dung dinh dang");
                    supNumber.setBorder(Color.red);
                    check = false;
                }else if(data.isEmail(supEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    supEmail.setBorder(Color.red);
                    check = false;
                }else if(data.isName(supContactName.getText())==false){
                    MsgBox.alert(this, "Ten supplier contact khong dung dinh dang");
                    supContactName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(supContactNumber.getText())==false){
                    MsgBox.alert(this, "Contact Number khong dung dinh dang");
                    supContactNumber.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(supBankNo.getText())==false){
                    MsgBox.alert(this, "Bank No khong dung dinh dang");
                    supBankNo.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }
                
                
                
                
                if(data.isEmail(supContactEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    supContactEmail.setBorder(Color.red);
                    check = false;
                }
                
                if(check==true){
                    try {
                        supDAO.insert(model);
                        loadTableSupPro();
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
        supplier model = getModelUpdate();
        supName.setBorder(Color.WHITE);
        supNumber.setBorder(white);
        supEmail.setBorder(white);
        supCity.setBorder(white);
        supBillAdd.setBorder(white);
        supShipAdd.setBorder(white);
        supBank.setBorder(white);
        supBankNo.setBorder(white);      
        supContactName.setBorder(white);
        supContactNumber.setBorder(white);
        supContactEmail.setBorder(white);
        supOnline.setBorder(white);
        supOtherInfo.setBorder(white);
        supDescription.setBorder(white);
        if(supName.getText().length()==0 || supNumber.getText().length()==0 || supEmail.getText().length()==0||supCity.getText().length()==0||supBillAdd.getText().length()==0 
                ||supShipAdd.getText().length()==0 ||supBank.getText().length()==0 ||supBankNo.getText().length()==0 
                ||supContactName.getText().length()==0 ||supContactNumber.getText().length()==0 ||supContactEmail.getText().length()==0 ||supOnline.getText().length()==0 ||supOtherInfo.getText().length()==0 ||supDescription.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            if(supName.getText().length()==0){
                supName.setBorder(Color.red);
            }
           
            if(supNumber.getText().length()==0){
                supNumber.setBorder(Color.red);
            }

            if(supEmail.getText().length()==0){
                supEmail.setBorder(Color.red);
            }
            if(supCity.getText().length()==0){
                supCity.setBorder(Color.red);
            }
            if(supBillAdd.getText().length()==0){
                supBillAdd.setBorder(Color.red);
            }
            if(supShipAdd.getText().length()==0){
                supShipAdd.setBorder(Color.red);
            }
            if(supBank.getText().length()==0){
                supBank.setBorder(Color.red);
            }
            if(supBankNo.getText().length()==0){
                supBankNo.setBorder(Color.red);
            }
            if(supContactName.getText().length()==0){
                supContactName.setBorder(Color.red);
            }
            if(supContactNumber.getText().length()==0){
                supContactNumber.setBorder(Color.red);
            }
            if(supContactEmail.getText().length()==0){
                supContactEmail.setBorder(Color.red);
            }
            if(supOnline.getText().length()==0){
                supOnline.setBorder(Color.red);
            }
            if(supOtherInfo.getText().length()==0){
                supOtherInfo.setBorder(Color.red);
            }
            if(supDescription.getText().length()==0){
                supDescription.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{      
                if(data.isName(supName.getText())==false){
                    MsgBox.alert(this, "Ten nguoi hoc khong dung dinh dang");
                    supName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(supNumber.getText())==false){
                    MsgBox.alert(this, "Number khong dung dinh dang");
                    supNumber.setBorder(Color.red);
                    check = false;
                }else if(data.isEmail(supEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    supEmail.setBorder(Color.red);
                    check = false;
                }else if(data.isName(supContactName.getText())==false){
                    MsgBox.alert(this, "Ten supplier contact khong dung dinh dang");
                    supContactName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(supContactNumber.getText())==false){
                    MsgBox.alert(this, "Contact Number khong dung dinh dang");
                    supContactNumber.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(supBankNo.getText())==false){
                    MsgBox.alert(this, "Bank No khong dung dinh dang");
                    supBankNo.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                } 
                
                
                if(check==true){
                    try {
                        supDAO.update(model);
                        loadTableSupPro();
                        MsgBox.alert(this, "Cập nhật thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại");
                        e.printStackTrace();
                    }
                    break;
                }
            }while(check==true);
        }
        
    }
    
    void delete(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa người này")){
            String manv = lblSupID.getText();
            try {
                supDAO.delete(manv);
                loadTableSupPro();
                loadTableSupProInactive();
                this.clear();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    void returnSup(){
        if(MsgBox.confirm(this, "Bạn thực sự return người này")){
            int row = tblSupplier2.getSelectedRow();
            String manv = tblSupplier2.getValueAt(row, 0).toString();
            try {
                supDAO.returnSup(manv);
                loadTableSupPro();
                loadTableSupProInactive();
                this.clear();
                MsgBox.alert(this, "Return thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Return thất bại");
            }
        }
    }
    
    public void search(){

        String name = cboSupName.getSelectedItem().toString();
        String email = cboSupEmail.getSelectedItem().toString();
        String product = cboSupPro.getSelectedItem().toString();
        if(name.equalsIgnoreCase("Selected")){
            name = "";
        }
        if(email.equalsIgnoreCase("Selected")){
            email = "";
        }
        if(product.equalsIgnoreCase("Selected")){
            product = "";
        }

        try{
            DefaultTableModel dt = (DefaultTableModel) tblSupplier.getModel();
            dt.setRowCount(0);
            String sql = "select suppliers.supplier_id as supid,suppliers.sup_name,suppliers.sup_number,suppliers.sup_contact_email,pro_name from suppliers inner join products on suppliers.supplier_id = products.supplier_id where suppliers.sup_name like N'%"+name+"%' AND pro_name like N'%"+product+"%' AND suppliers.sup_contact_email like N'%"+email+"%' and suppliers.status = 'Active' ";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("supid"));
                v.add(rs.getString("sup_name"));
                v.add(rs.getString("sup_number"));
                v.add(rs.getString("sup_contact_email"));
                v.add(rs.getString("pro_name"));
                dt.addRow(v);
            }
        }catch(Exception e){
             
        }
    }
    
    public void search1(){

        String name = cboSupName1.getSelectedItem().toString();
        String email = cboSupEmail1.getSelectedItem().toString();
        String product = cboSupPro1.getSelectedItem().toString();
        if(name.equalsIgnoreCase("Selected")){
            name = "";
        }
        if(email.equalsIgnoreCase("Selected")){
            email = "";
        }
        if(product.equalsIgnoreCase("Selected")){
            product = "";
        }


        try{
            DefaultTableModel dt = (DefaultTableModel) tblSupplier1.getModel();
            dt.setRowCount(0);
            String sql = "select suppliers.supplier_id as supid,suppliers.sup_name,suppliers.sup_number,suppliers.sup_contact_email,pro_name from suppliers inner join products on suppliers.supplier_id = products.supplier_id where sup_name like N'%"+name+"%' AND pro_name like N'%"+product+"%' AND sup_contact_email like N'%"+email+"%' and status = 'Inactive'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("supid"));
                v.add(rs.getString("sup_name"));
                v.add(rs.getString("sup_number"));
                v.add(rs.getString("sup_contact_email"));
                v.add(rs.getString("pro_name"));
                dt.addRow(v);
            }
        }catch(Exception e){
             
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
        txt1 = new searchwing.TextFieldAnimation();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboSupName = new combo_suggestion.ComboBoxSuggestion();
        cboSupPro = new combo_suggestion.ComboBoxSuggestion();
        cboSupEmail = new combo_suggestion.ComboBoxSuggestion();
        myButton10 = new button.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();
        myButton7 = new button.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSupplier1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt2 = new searchwing.TextFieldAnimation();
        lblSupID = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        supName = new textfield_suggestion.TextFieldSuggestion();
        supNumber = new textfield_suggestion.TextFieldSuggestion();
        jLabel10 = new javax.swing.JLabel();
        supEmail = new textfield_suggestion.TextFieldSuggestion();
        jLabel11 = new javax.swing.JLabel();
        supBillAdd = new textfield_suggestion.TextFieldSuggestion();
        jLabel13 = new javax.swing.JLabel();
        supShipAdd = new textfield_suggestion.TextFieldSuggestion();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        supBankNo = new textfield_suggestion.TextFieldSuggestion();
        jLabel16 = new javax.swing.JLabel();
        jCheckBoxCustom1 = new checkbox.JCheckBoxCustom();
        jLabel17 = new javax.swing.JLabel();
        myButton1 = new button.MyButton();
        myButton2 = new button.MyButton();
        myButton3 = new button.MyButton();
        myButton4 = new button.MyButton();
        supBank = new textfield_suggestion.TextFieldSuggestion();
        supCity = new textfield_suggestion.TextFieldSuggestion();
        jLabel24 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        supContactName = new textfield_suggestion.TextFieldSuggestion();
        supContactEmail = new textfield_suggestion.TextFieldSuggestion();
        jLabel19 = new javax.swing.JLabel();
        supContactNumber = new textfield_suggestion.TextFieldSuggestion();
        jLabel21 = new javax.swing.JLabel();
        supOnline = new textfield_suggestion.TextFieldSuggestion();
        jLabel20 = new javax.swing.JLabel();
        supOtherInfo = new textfield_suggestion.TextFieldSuggestion();
        jLabel23 = new javax.swing.JLabel();
        supDescription = new textfield_suggestion.TextFieldSuggestion();
        jLabel22 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        supUserID = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txt3 = new searchwing.TextFieldAnimation();
        jLabel8 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        myButton5 = new button.MyButton();
        cboSupName1 = new combo_suggestion.ComboBoxSuggestion();
        cboSupPro1 = new combo_suggestion.ComboBoxSuggestion();
        cboSupEmail1 = new combo_suggestion.ComboBoxSuggestion();
        myButton11 = new button.MyButton();
        myButton8 = new button.MyButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSupplier2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSupplier3 = new javax.swing.JTable();

        tabs.setSelectedColor(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Search:");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1ActionPerformed(evt);
            }
        });
        txt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt1KeyReleased(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Supplier Name:");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("Product:");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("Contact Mail:");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboSupName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupNameActionPerformed(evt);
            }
        });
        cboSupName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupNameKeyReleased(evt);
            }
        });

        cboSupPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupProActionPerformed(evt);
            }
        });
        cboSupPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupProKeyReleased(evt);
            }
        });

        cboSupEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupEmailActionPerformed(evt);
            }
        });
        cboSupEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupEmailKeyReleased(evt);
            }
        });

        myButton10.setText("Filter");
        myButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSupName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSupPro, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboSupEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jLabel5)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboSupName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSupPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSupEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null},
                {"", "", "", null, null},
                {"", "", "", null, null},
                {"", "", "", null, null}
            },
            new String [] {
                "supID", "supName", "supNumber", "supContactEmail", "Product Name"
            }
        ));
        tblSupplier.setFocusable(false);
        tblSupplier.setRowHeight(40);
        tblSupplier.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblSupplier.getTableHeader().setReorderingAllowed(false);
        tblSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSupplier);

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton7.setText("Save To Excel");

        tblSupplier1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "supID", "supName", "supNumber", "supEmail", "supProduct", "supCity", "supBillAdd", "supShipAdd", "supBank", "supBankNo", "supContactName", "supContactEmail", "supContactNumber", "supOnline", "supOtherInfo", "supDescription", "UserID"
            }
        ));
        tblSupplier1.setFocusable(false);
        tblSupplier1.setRowHeight(40);
        tblSupplier1.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblSupplier1.getTableHeader().setReorderingAllowed(false);
        tblSupplier1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplier1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSupplier1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblSupplier1);

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
                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("All Supplier's", jPanel1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Search:");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt2ActionPerformed(evt);
            }
        });
        txt2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt2KeyReleased(evt);
            }
        });

        lblSupID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSupID.setForeground(new java.awt.Color(51, 153, 255));
        lblSupID.setText(" ");
        lblSupID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("Name:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supName.setForeground(new java.awt.Color(51, 153, 255));
        supName.setToolTipText("");

        supNumber.setForeground(new java.awt.Color(51, 153, 255));
        supNumber.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 255));
        jLabel10.setText("Supplier Number:");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supEmail.setForeground(new java.awt.Color(51, 153, 255));
        supEmail.setToolTipText("");
        supEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supEmailActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 255));
        jLabel11.setText("Supplier Email:");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supBillAdd.setForeground(new java.awt.Color(51, 153, 255));
        supBillAdd.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Billing Address:");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supShipAdd.setForeground(new java.awt.Color(51, 153, 255));
        supShipAdd.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Shipping Address:");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 255));
        jLabel15.setText("Bank:");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supBankNo.setForeground(new java.awt.Color(51, 153, 255));
        supBankNo.setToolTipText("");

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
        myButton4.setText("All Customer's Supplier");
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        supBank.setForeground(new java.awt.Color(51, 153, 255));
        supBank.setToolTipText("");
        supBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supBankActionPerformed(evt);
            }
        });

        supCity.setForeground(new java.awt.Color(51, 153, 255));
        supCity.setToolTipText("");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 153, 255));
        jLabel24.setText("City:");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Contact Person:");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supContactName.setForeground(new java.awt.Color(51, 153, 255));
        supContactName.setToolTipText("");

        supContactEmail.setForeground(new java.awt.Color(51, 153, 255));
        supContactEmail.setToolTipText("");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 153, 255));
        jLabel19.setText("Email:");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supContactNumber.setForeground(new java.awt.Color(51, 153, 255));
        supContactNumber.setToolTipText("");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 255));
        jLabel21.setText("Mobile Number:");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supOnline.setForeground(new java.awt.Color(51, 153, 255));
        supOnline.setToolTipText("");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 153, 255));
        jLabel20.setText("Online:");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supOtherInfo.setForeground(new java.awt.Color(51, 153, 255));
        supOtherInfo.setToolTipText("");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 153, 255));
        jLabel23.setText("Other's Info");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        supDescription.setForeground(new java.awt.Color(51, 153, 255));
        supDescription.setToolTipText("");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 255));
        jLabel22.setText("Desctiption:");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/information.png"))); // NOI18N

        supUserID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        supUserID.setForeground(new java.awt.Color(51, 153, 255));
        supUserID.setText("UserID:");
        supUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSupID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(598, 598, 598))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supBillAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supBankNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(31, 31, 31)
                        .addComponent(supBank, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(supName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(supCity, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supShipAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(supContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel23)
                                        .addComponent(jLabel22))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(supContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(supOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(supOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(supDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(supContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(supUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(114, 114, 114))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel37)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSupID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(supUserID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supContactName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(supContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supOnline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel37)
                        .addGap(57, 57, 57))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supCity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(supBillAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supShipAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supBank, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(supBankNo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tabs.addTab("Add Supplier's", jPanel2);

        txt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt3ActionPerformed(evt);
            }
        });
        txt3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt3KeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("Search:");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("Supplier Name:");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 153, 255));
        jLabel26.setText("Product:");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Contact Email:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/update.png"))); // NOI18N
        myButton5.setText("Return");
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        cboSupName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupName1ActionPerformed(evt);
            }
        });
        cboSupName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupName1KeyReleased(evt);
            }
        });

        cboSupPro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupPro1ActionPerformed(evt);
            }
        });
        cboSupPro1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupPro1KeyReleased(evt);
            }
        });

        cboSupEmail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupEmail1ActionPerformed(evt);
            }
        });
        cboSupEmail1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupEmail1KeyReleased(evt);
            }
        });

        myButton11.setText("Filter");
        myButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSupName1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboSupPro1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cboSupEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboSupName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboSupPro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboSupEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(42, 42, 42)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        myButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton8.setText("Save To Excel");

        tblSupplier2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null},
                {"", "", "", null, null},
                {"", "", "", null, null},
                {"", "", "", null, null}
            },
            new String [] {
                "supID", "supName", "supNumber", "supContactEmail", "Product Name"
            }
        ));
        tblSupplier2.setFocusable(false);
        tblSupplier2.setRowHeight(40);
        tblSupplier2.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblSupplier2.getTableHeader().setReorderingAllowed(false);
        tblSupplier2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplier2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSupplier2);

        tblSupplier3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "supID", "supName", "supNumber", "supEmail", "supProduct", "supCity", "supBillAdd", "supShipAdd", "supBank", "supBankNo", "supContactName", "supContactEmail", "supContactNumber", "supOnline", "supOtherInfo", "supDescription", "UserID"
            }
        ));
        tblSupplier3.setFocusable(false);
        tblSupplier3.setRowHeight(40);
        tblSupplier3.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblSupplier3.getTableHeader().setReorderingAllowed(false);
        tblSupplier3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSupplier3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSupplier3MousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblSupplier3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("BlackList", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myButton4ActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
    
        delete();
        
    }//GEN-LAST:event_myButton3ActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        try {
        update();
    } catch (ParseException ex) {
        Logger.getLogger(Supplier_Form.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_myButton2ActionPerformed

    private void txt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2ActionPerformed

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void supBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supBankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supBankActionPerformed

    private void supEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_supEmailActionPerformed

    private void txt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1KeyReleased
        String name = txt1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblSupplier.getModel();
            dt.setRowCount(0);
            String sql = "select suppliers.supplier_id as supid,sup_name,sup_number,sup_contact_email,pro_name from suppliers inner join products on suppliers.supplier_id = products.supplier_id where sup_name like N'%"+name+"%'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("supid"));
                v.add(rs.getString("sup_name"));
                v.add(rs.getString("sup_number"));
                v.add(rs.getString("sup_contact_email"));
                v.add(rs.getString("pro_name"));
                dt.addRow(v);
            }
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_txt1KeyReleased

    private void txt2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2KeyReleased
        String search = txt2.getText();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM suppliers WHERE supplier_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblSupID.setText(rs.getString("supplier_id"));
                            supName.setText(rs.getString("sup_name"));
                            supNumber.setText(rs.getString("sup_number"));
                            supEmail.setText(rs.getString("sup_email"));
                            supCity.setText(rs.getString("sup_city"));
                            supBillAdd.setText(rs.getString("sup_bill_add"));
                            supShipAdd.setText(rs.getString("sup_ship_add"));
                            supBank.setText(rs.getString("sup_bank"));
                            supBankNo.setText(rs.getString("sup_bank_no"));
                            supContactName.setText(rs.getString("sup_contact_name"));
                            supContactEmail.setText(rs.getString("sup_contact_email"));
                            supContactNumber.setText(rs.getString("sup_contact_number"));
                            supOnline.setText(rs.getString("sup_contact_online"));
                            supOtherInfo.setText(rs.getString("sup_contact_otherinfo"));
                            supDescription.setText(rs.getString("sup_contact_description"));
                            supUserID.setText(rs.getString("users_id"));      
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
    }//GEN-LAST:event_txt2KeyReleased

    private void tblSupplier1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplier1MouseClicked
              
            
    }//GEN-LAST:event_tblSupplier1MouseClicked

    private void tblSupplier1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplier1MousePressed
       if(evt.getClickCount()==2){
                    String search = tblSupplier1.getValueAt(tblSupplier1.getSelectedRow(), 0).toString();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM suppliers WHERE supplier_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblSupID.setText(rs.getString("supplier_id"));
                            supName.setText(rs.getString("sup_name"));
                            supNumber.setText(rs.getString("sup_number"));
                            supEmail.setText(rs.getString("sup_email"));
                            supCity.setText(rs.getString("sup_city"));
                            supBillAdd.setText(rs.getString("sup_bill_add"));
                            supShipAdd.setText(rs.getString("sup_ship_add"));
                            supBank.setText(rs.getString("sup_bank"));
                            supBankNo.setText(rs.getString("sup_bank_no"));
                            supContactName.setText(rs.getString("sup_contact_name"));
                            supContactEmail.setText(rs.getString("sup_contact_email"));
                            supContactNumber.setText(rs.getString("sup_contact_number"));
                            supOnline.setText(rs.getString("sup_contact_online"));
                            supOtherInfo.setText(rs.getString("sup_contact_otherinfo"));
                            supDescription.setText(rs.getString("sup_contact_description"));
                            supUserID.setText(rs.getString("users_id"));      
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            tabs.setSelectedIndex(1);
                            }catch(Exception e){
                                System.out.println(e);
                            }
        }
    }//GEN-LAST:event_tblSupplier1MousePressed

    private void tblSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplierMouseClicked
        int row = tblSupplier.getSelectedRow();
            String supID = tblSupplier.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblSupplier1.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from suppliers where supplier_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("supplier_id"));
                    v.add(rs.getString("sup_name"));
                    v.add(rs.getString("sup_number"));
                    v.add(rs.getString("sup_email"));
                    v.add(rs.getString("sup_city"));
                    v.add(rs.getString("sup_bill_add"));
                    v.add(rs.getString("sup_ship_add"));
                    v.add(rs.getString("sup_bank"));
                    v.add(rs.getString("sup_bank_no"));
                    v.add(rs.getString("sup_contact_name"));
                    v.add(rs.getString("sup_contact_email"));
                    v.add(rs.getString("sup_contact_number"));
                    v.add(rs.getString("sup_contact_online"));
                    v.add(rs.getString("sup_contact_otherinfo"));
                    v.add(rs.getString("sup_contact_description"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblSupplierMouseClicked

    private void txt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt3ActionPerformed

    private void txt3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt3KeyReleased
        String name = txt3.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblSupplier1.getModel();
            dt.setRowCount(0);
            String sql = "select suppliers.supplier_id as supid,sup_name,sup_number,sup_contact_email,pro_name from suppliers inner join products on suppliers.supplier_id = products.supplier_id where sup_name like N'%"+name+"%' and supplier_id.status = 'Inactive'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("supid"));
                v.add(rs.getString("sup_name"));
                v.add(rs.getString("sup_number"));
                v.add(rs.getString("sup_contact_email"));
                v.add(rs.getString("pro_name"));
                dt.addRow(v);
            }
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_txt3KeyReleased

    private void tblSupplier2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplier2MouseClicked
        int row = tblSupplier1.getSelectedRow();
            String supID = tblSupplier1.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblSupplier3.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from suppliers where supplier_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("supplier_id"));
                    v.add(rs.getString("sup_name"));
                    v.add(rs.getString("sup_number"));
                    v.add(rs.getString("sup_email"));
                    v.add(rs.getString("sup_city"));
                    v.add(rs.getString("sup_bill_add"));
                    v.add(rs.getString("sup_ship_add"));
                    v.add(rs.getString("sup_bank"));
                    v.add(rs.getString("sup_bank_no"));
                    v.add(rs.getString("sup_contact_name"));
                    v.add(rs.getString("sup_contact_email"));
                    v.add(rs.getString("sup_contact_number"));
                    v.add(rs.getString("sup_contact_online"));
                    v.add(rs.getString("sup_contact_otherinfo"));
                    v.add(rs.getString("sup_contact_description"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblSupplier2MouseClicked

    private void tblSupplier3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplier3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSupplier3MouseClicked

    private void tblSupplier3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupplier3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSupplier3MousePressed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
    try {
        insert();
    } catch (ParseException ex) {
        Logger.getLogger(Supplier_Form.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_myButton1ActionPerformed

    private void myButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton5ActionPerformed
        returnSup();
    }//GEN-LAST:event_myButton5ActionPerformed

    private void cboSupNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupNameActionPerformed

    }//GEN-LAST:event_cboSupNameActionPerformed

    private void cboSupNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupNameKeyReleased

    }//GEN-LAST:event_cboSupNameKeyReleased

    private void cboSupProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupProActionPerformed

    private void cboSupProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupProKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupProKeyReleased

    private void cboSupEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupEmailActionPerformed

    private void cboSupEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupEmailKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupEmailKeyReleased

    private void myButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton10ActionPerformed
        search();

    }//GEN-LAST:event_myButton10ActionPerformed

    private void cboSupName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupName1ActionPerformed

    private void cboSupName1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupName1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupName1KeyReleased

    private void cboSupPro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupPro1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupPro1ActionPerformed

    private void cboSupPro1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupPro1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupPro1KeyReleased

    private void cboSupEmail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupEmail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupEmail1ActionPerformed

    private void cboSupEmail1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupEmail1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSupEmail1KeyReleased

    private void myButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton11ActionPerformed
        search1();
    }//GEN-LAST:event_myButton11ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combo_suggestion.ComboBoxSuggestion cboSupEmail;
    private combo_suggestion.ComboBoxSuggestion cboSupEmail1;
    private combo_suggestion.ComboBoxSuggestion cboSupName;
    private combo_suggestion.ComboBoxSuggestion cboSupName1;
    private combo_suggestion.ComboBoxSuggestion cboSupPro;
    private combo_suggestion.ComboBoxSuggestion cboSupPro1;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblSupID;
    private button.MyButton myButton1;
    private button.MyButton myButton10;
    private button.MyButton myButton11;
    private button.MyButton myButton2;
    private button.MyButton myButton3;
    private button.MyButton myButton4;
    private button.MyButton myButton5;
    private button.MyButton myButton7;
    private button.MyButton myButton8;
    private textfield_suggestion.TextFieldSuggestion supBank;
    private textfield_suggestion.TextFieldSuggestion supBankNo;
    private textfield_suggestion.TextFieldSuggestion supBillAdd;
    private textfield_suggestion.TextFieldSuggestion supCity;
    private textfield_suggestion.TextFieldSuggestion supContactEmail;
    private textfield_suggestion.TextFieldSuggestion supContactName;
    private textfield_suggestion.TextFieldSuggestion supContactNumber;
    private textfield_suggestion.TextFieldSuggestion supDescription;
    private textfield_suggestion.TextFieldSuggestion supEmail;
    private textfield_suggestion.TextFieldSuggestion supName;
    private textfield_suggestion.TextFieldSuggestion supNumber;
    private textfield_suggestion.TextFieldSuggestion supOnline;
    private textfield_suggestion.TextFieldSuggestion supOtherInfo;
    private textfield_suggestion.TextFieldSuggestion supShipAdd;
    private javax.swing.JLabel supUserID;
    private raven.tabbed.TabbedPaneCustom tabs;
    private javax.swing.JTable tblSupplier;
    private javax.swing.JTable tblSupplier1;
    private javax.swing.JTable tblSupplier2;
    private javax.swing.JTable tblSupplier3;
    private searchwing.TextFieldAnimation txt1;
    private searchwing.TextFieldAnimation txt2;
    private searchwing.TextFieldAnimation txt3;
    // End of variables declaration//GEN-END:variables
}
