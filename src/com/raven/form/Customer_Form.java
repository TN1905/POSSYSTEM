/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Utils.Data;
import Utils.MsgBox;
import Utils.db;
import com.raven.component.Form;
import com.raven.dao.customerDAO;
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
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import raven.cell.TableActionCellEditor;
import raven.cell.TableActionCellRender;
import raven.cell.TableActionEvent;
import searchwing.EventCallBack;
import searchwing.EventTextField;
/**
 *
 * @author ADMIN
 */
public class Customer_Form extends Form {
    private Setting_Form settingForm;
    customerDAO cusDAO = new customerDAO();
    int index = 0;
    private users user;
    public void setUser(users user1){
        user = user1;
    }
    String ngayThangNamGioPhutGiay = "2023-08-03 14:30:00";
        String pattern = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
     long currentTimestamp = getCurrentTimestamp();
    /**
     * Creates new form Sales_Form
     */
    public Customer_Form(users user) {
        initComponents();
        init();
        this.user = user;
        tblKhachHang.getTableHeader().setForeground(new Color(255,255,255));  
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(51, 153, 255));
        headerRenderer.setForeground(new Color(255, 255, 255));
        tblKhachHang.getTableHeader().setDefaultRenderer(headerRenderer);
        CusUserID.setText(user.getUserID());
        loadTable();
        loadTableInactive();
        System.out.println(currentTimestamp);
    }
  
    public long getCurrentTimestamp() {
        Instant currentInstant = Instant.now();
        currentTimestamp = currentInstant.getEpochSecond();
        System.out.println(currentTimestamp);
        return currentInstant.getEpochSecond();
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
        jPanel5.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setOpaque(false);
        jPanel8.setBackground(new Color(0, 0, 0, 0));
        jPanel8.setOpaque(false);
        jPanel7.setBackground(new Color(0, 0, 0, 0));
        jPanel7.setOpaque(false);
        tabs.setBackground(new Color(0, 0, 0, 0));
        tabs.setOpaque(false);

        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setOpaque(false);

        fillComboBoxName();
        fillComboBoxNumber();
        fillComboBoxEmail();
        fillComboBoxName1();
        fillComboBoxNumber1();
        fillComboBoxEmail1();
        
    }
    

 
    
    void loadTable(){
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {

            List<customer>list =  cusDAO.selectAll();
      
            for(customer nh: list){
                Object[] row = {          
                    nh.getCusID(),
                    nh.getCusName(),
                    nh.getCusNumber(),
                    nh.getCusEmail(),
                    
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void loadTableInactive(){
        DefaultTableModel model = (DefaultTableModel) tblKhachHang2.getModel();
        model.setRowCount(0);
        try {

            List<customer>list =  cusDAO.selectAllInactive();
      
            for(customer nh: list){
                Object[] row = {          
                    nh.getCusID(),
                    nh.getCusName(),
                    nh.getCusNumber(),
                    nh.getCusEmail(),
                    
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void fillComboBoxName(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusName.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAll();
            for(customer cd:list){
                model.addElement(cd.getCusName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxNumber(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusNumber.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAll();
            for(customer cd:list){
                model.addElement(cd.getCusNumber());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxEmail(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusEmail.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAll();
            for(customer cd:list){
                model.addElement(cd.getCusEmail());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxName1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusName1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAllInactive();
            for(customer cd:list){
                model.addElement(cd.getCusName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxNumber1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusNumber1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAllInactive();
            for(customer cd:list){
                model.addElement(cd.getCusNumber());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxEmail1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusEmail1.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAllInactive();
            for(customer cd:list){
                model.addElement(cd.getCusEmail());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void setModel(customer model){
        lblCusID.setText(model.getCusID());
        cusName.setText(model.getCusName());
        cusNumber.setText(String.valueOf(model.getCusNumber()));
        cusEmail.setText(model.getCusEmail());
        cusCity.setText(model.getCusCity());
        CusBillAdd.setText(model.getBillCusAdd());
        CusShipAdd.setText(model.getShipCusAdd());
        cusBank.setText(model.getCusBank());
        cusBankNo.setText(String.valueOf(model.getCusBankNo()));
        cusContactName.setText(model.getCusContactPerson());
        cusContactEmail.setText(model.getCusEmailPerson());
        cusContactNumber.setText(String.valueOf(model.getCusNumberPerson()));
        CusOnline.setText(model.getCusOnline());
        CusOtherInfo.setText(model.getOtherCusInfo());
        CusDescription.setText(model.getCusDescription());
        CusUserID.setText(model.getCusUserID());
    }
    
    customer getModel() throws ParseException {
        customer model = new customer();
        int countNV=0;
        String employeeID = "";
       
                employeeID = "cus" + currentTimestamp;
                System.out.println(currentTimestamp);
        
        model.setCusID(employeeID);
        model.setCusName(cusName.getText());
        String Number = cusNumber.getText();
        if(data.ContainNumberOnly(Number)==false){
            model.setCusNumber(0);
        }else{
            if(Number.isEmpty()){
           model.setCusNumber(0);
            }else{
                model.setCusNumber(Integer.parseInt(Number));
            }
        }
        
        model.setCusEmail(cusEmail.getText());
        model.setCusCity(cusCity.getText());
        model.setBillCusAdd(CusBillAdd.getText());
        model.setShipCusAdd(CusShipAdd.getText());
        model.setCusBank(cusBank.getText());
        String bankNO = cusBankNo.getText();
        if(data.ContainNumberOnly(bankNO)==false){
            model.setCusBankNo(0);
        }else{
            if(bankNO.isEmpty()){
           model.setCusBankNo(0);
            }else{
                model.setCusBankNo(Integer.parseInt(bankNO));
            }
        }
        
        model.setCusContactPerson(cusContactName.getText());
        model.setCusEmailPerson(cusContactEmail.getText());
        String contactNumber = cusContactNumber.getText();
        if(data.ContainNumberOnly(contactNumber)==false){
            model.setCusNumberPerson(0);
        }else{
            if(contactNumber.isEmpty()){
           model.setCusNumberPerson(0);
            }else{
                model.setCusNumberPerson(Integer.parseInt(contactNumber));
            }
        }
        
        model.setCusOnline(CusOnline.getText());
        model.setOtherCusInfo(CusOtherInfo.getText());
        model.setCusDescription(CusDescription.getText());
        model.setCusUserID(CusUserID.getText());
        
        return model;
    }
    
    customer getModelUpdate() {
        customer model = new customer();
    
        model.setCusID(lblCusID.getText());
        model.setCusName(cusName.getText());
        String Number = cusNumber.getText();
        if(data.ContainNumberOnly(Number)==false){
            model.setCusNumber(0);
        }else{
            if(Number.isEmpty()){
           model.setCusNumber(0);
            }else{
                model.setCusNumber(Integer.parseInt(Number));
            }
        }
        
        model.setCusEmail(cusEmail.getText());
        model.setCusCity(cusCity.getText());
        model.setBillCusAdd(CusBillAdd.getText());
        model.setShipCusAdd(CusShipAdd.getText());
        model.setCusBank(cusBank.getText());
        String bankNO = cusBankNo.getText();
        if(data.ContainNumberOnly(bankNO)==false){
            model.setCusBankNo(0);
        }else{
            if(bankNO.isEmpty()){
           model.setCusBankNo(0);
            }else{
                model.setCusBankNo(Integer.parseInt(bankNO));
            }
        }
        
        model.setCusContactPerson(cusContactName.getText());
        model.setCusEmailPerson(cusContactEmail.getText());
        String contactNumber = cusContactNumber.getText();
        if(data.ContainNumberOnly(contactNumber)==false){
            model.setCusNumberPerson(0);
        }else{
            if(contactNumber.isEmpty()){
           model.setCusNumberPerson(0);
            }else{
                model.setCusNumberPerson(Integer.parseInt(contactNumber));
            }
        }
        
        model.setCusOnline(CusOnline.getText());
        model.setOtherCusInfo(CusOtherInfo.getText());
        model.setCusDescription(CusDescription.getText());
        
        
        return model;
    }
    
    public boolean checkTrungMa(JTextField txt){
        txt.setBackground(white);
        if(cusDAO.selectByID(txt.getText())==null){
            return true;
        }else{
            txt.setBackground(pink);
            MsgBox.alert(this, txt.getName() + "Đã bị tồn tại");
            return false;
        }
    }
    
    public void setTrang(){
        cusName.setBorder(white);
        cusNumber.setBorder(white);
        cusEmail.setBorder(white);
        cusCity.setBorder(white);
        CusBillAdd.setBorder(white);
        CusShipAdd.setBorder(white);
        cusBank.setBorder(white);
        cusBankNo.setBorder(white);
        lblCusID.setBackground(white);
        cusContactName.setBorder(white);
        cusContactNumber.setBorder(white);
        cusContactEmail.setBorder(white);
        CusOnline.setBorder(white);
        CusOtherInfo.setBorder(white);
        CusDescription.setBorder(white);
    }
    
    void clear(){
        setTrang();
        customer model = new customer();
        this.setModel(model);
    }
    
    void edit(){
        setTrang();
        try {
            String manv = (String) tblKhachHang.getValueAt(this.index, 0);
            customer model = cusDAO.selectByID(manv);
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
                        ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE customer_id = '"+search+"'");

                        if(rs.next()){
                            System.out.println("cai ccm");
                            lblCusID.setText(rs.getString("customer_id"));
                            cusName.setText(rs.getString("cus_name"));
                            cusNumber.setText(rs.getString("cus_number"));
                            cusEmail.setText(rs.getString("cus_email"));
                            cusCity.setText(rs.getString("cus_city"));
                            CusBillAdd.setText(rs.getString("cus_bill_add"));
                            CusShipAdd.setText(rs.getString("cus_ship_add"));
                            cusBank.setText(rs.getString("cus_bank"));
                            cusBankNo.setText(rs.getString("cus_bank_no"));
                            cusContactName.setText(rs.getString("cus_contact_name"));
                            cusContactEmail.setText(rs.getString("cus_contact_email"));
                            cusContactNumber.setText(rs.getString("cus_contact_number"));
                            CusOnline.setText(rs.getString("cus_contact_online"));
                            CusOtherInfo.setText(rs.getString("cus_contact_otherinfo"));
                            CusDescription.setText(rs.getString("cus_contact_description"));
                            CusUserID.setText(rs.getString("users_id"));      
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
        txt1.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                //  Test
                try {
                    
                    String name = txt1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblKhachHang.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE cus_name LIKE '%"+name+"%'");   
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
        customer model = getModel();
        cusName.setBorder(Color.WHITE);
        cusNumber.setBorder(Color.WHITE);
        cusContactName.setBorder(white);
        cusEmail.setBorder(white);
        cusCity.setBorder(white);
        CusBillAdd.setBorder(white);
        CusShipAdd.setBorder(white);
        cusBank.setBorder(white);
        cusBankNo.setBorder(white);      
        cusContactName.setBorder(white);
        cusContactNumber.setBorder(white);
        cusContactEmail.setBorder(white);
        CusOnline.setBorder(white);
        CusOtherInfo.setBorder(white);
        CusDescription.setBorder(white);
        if(cusName.getText().length()==0 || cusNumber.getText().length()==0 || cusEmail.getText().length()==0||cusCity.getText().length()==0||CusBillAdd.getText().length()==0 
                ||CusShipAdd.getText().length()==0 ||cusBank.getText().length()==0 ||cusBankNo.getText().length()==0 
                ||cusContactName.getText().length()==0 ||cusContactNumber.getText().length()==0 ||cusContactEmail.getText().length()==0 ||CusOnline.getText().length()==0 ||CusOtherInfo.getText().length()==0 ||CusDescription.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            if(cusName.getText().length()==0){
                cusName.setBorder(Color.red);
            }
            if(cusNumber.getText().length()==0){
                cusNumber.setBorder(Color.red);
            }
            if(cusContactName.getText().length()==0){
                cusContactName.setBorder(Color.red);
            }
            if(cusEmail.getText().length()==0){
                cusEmail.setBorder(Color.red);
            }
            if(cusCity.getText().length()==0){
                cusCity.setBorder(Color.red);
            }
            if(CusBillAdd.getText().length()==0){
                CusBillAdd.setBorder(Color.red);
            }
            if(CusShipAdd.getText().length()==0){
                CusShipAdd.setBorder(Color.red);
            }
            if(cusBank.getText().length()==0){
                cusBank.setBorder(Color.red);
            }
            if(cusBankNo.getText().length()==0){
                cusBankNo.setBorder(Color.red);
            }
            if(cusContactName.getText().length()==0){
                cusContactName.setBorder(Color.red);
            }
            if(cusContactNumber.getText().length()==0){
                cusContactNumber.setBorder(Color.red);
            }
            if(cusContactEmail.getText().length()==0){
                cusContactEmail.setBorder(Color.red);
            }
            if(CusOnline.getText().length()==0){
                CusOnline.setBorder(Color.red);
            }
            if(CusOtherInfo.getText().length()==0){
                CusOtherInfo.setBorder(Color.red);
            }
            if(CusDescription.getText().length()==0){
                CusDescription.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{      
                if(data.isName(cusName.getText())==false){
                    MsgBox.alert(this, "Ten nguoi hoc khong dung dinh dang");
                    cusName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(cusNumber.getText())==false){
                    MsgBox.alert(this, "Hoc phi khong dung dinh dang");
                    cusNumber.setBorder(Color.red);
                    check = false;
                }else if(data.isEmail(cusEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    cusEmail.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(cusContactNumber.getText())==false){
                    MsgBox.alert(this, "Hoc phi khong dung dinh dang");
                    cusContactNumber.setBorder(Color.red);
                    check = false;
                }else if(data.isEmail(cusContactEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    cusContactEmail.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(cusBankNo.getText())==false){
                    MsgBox.alert(this, "Hoc phi khong dung dinh dang");
                    cusBankNo.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }

                if(check==true){
                    try {
                        cusDAO.insert(model);
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
        customer model = getModelUpdate();
        cusName.setBorder(Color.WHITE);
        cusNumber.setBorder(Color.WHITE);
        cusContactName.setBorder(white);
        cusEmail.setBorder(white);
        cusCity.setBorder(white);
        CusBillAdd.setBorder(white);
        CusShipAdd.setBorder(white);
        cusBank.setBorder(white);
        cusBankNo.setBorder(white);      
        cusContactName.setBorder(white);
        cusContactNumber.setBorder(white);
        cusContactEmail.setBorder(white);
        CusOnline.setBorder(white);
        CusOtherInfo.setBorder(white);
        CusDescription.setBorder(white);
        if(cusName.getText().length()==0 || cusNumber.getText().length()==0 || cusEmail.getText().length()==0||cusCity.getText().length()==0||CusBillAdd.getText().length()==0 
                ||CusShipAdd.getText().length()==0 ||cusBank.getText().length()==0 ||cusBankNo.getText().length()==0 
                ||cusContactName.getText().length()==0 ||cusContactNumber.getText().length()==0 ||cusContactEmail.getText().length()==0 ||CusOnline.getText().length()==0 ||CusOtherInfo.getText().length()==0 ||CusDescription.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            if(cusName.getText().length()==0){
                cusName.setBorder(Color.red);
            }
            if(cusNumber.getText().length()==0){
                cusNumber.setBorder(Color.red);
            }
            if(cusContactName.getText().length()==0){
                cusContactName.setBorder(Color.red);
            }
            if(cusEmail.getText().length()==0){
                cusEmail.setBorder(Color.red);
            }
            if(cusCity.getText().length()==0){
                cusCity.setBorder(Color.red);
            }
            if(CusBillAdd.getText().length()==0){
                CusBillAdd.setBorder(Color.red);
            }
            if(CusShipAdd.getText().length()==0){
                CusShipAdd.setBorder(Color.red);
            }
            if(cusBank.getText().length()==0){
                cusBank.setBorder(Color.red);
            }
            if(cusBankNo.getText().length()==0){
                cusBankNo.setBorder(Color.red);
            }
            if(cusContactName.getText().length()==0){
                cusContactName.setBorder(Color.red);
            }
            if(cusContactNumber.getText().length()==0){
                cusContactNumber.setBorder(Color.red);
            }
            if(cusContactEmail.getText().length()==0){
                cusContactEmail.setBorder(Color.red);
            }
            if(CusOnline.getText().length()==0){
                CusOnline.setBorder(Color.red);
            }
            if(CusOtherInfo.getText().length()==0){
                CusOtherInfo.setBorder(Color.red);
            }
            if(CusDescription.getText().length()==0){
                CusDescription.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{      
                if(data.isName(cusName.getText())==false){
                    MsgBox.alert(this, "Ten nguoi hoc khong dung dinh dang");
                    cusName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(cusNumber.getText())==false){
                    MsgBox.alert(this, "Hoc phi khong dung dinh dang");
                    cusNumber.setBorder(Color.red);
                    check = false;
                }else if(data.isEmail(cusEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    cusEmail.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(cusContactNumber.getText())==false){
                    MsgBox.alert(this, "Hoc phi khong dung dinh dang");
                    cusContactNumber.setBorder(Color.red);
                    check = false;
                }else if(data.isEmail(cusContactEmail.getText())==false){
                    MsgBox.alert(this, "Email khong dung dinh dang");
                    cusContactEmail.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(cusBankNo.getText())==false){
                    MsgBox.alert(this, "Hoc phi khong dung dinh dang");
                    cusBankNo.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }

                if(check==true){
                    try {
                        cusDAO.update(model);
                        this.loadTable();
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
            String manv = lblCusID.getText();
            try {
                cusDAO.delete(manv);
                this.loadTable();
                loadTableInactive();
                this.clear();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    void returnCus(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa người này")){
            int row = tblKhachHang2.getSelectedRow();
            String manv = tblKhachHang2.getValueAt(row, 0).toString();
            try {
                cusDAO.returnCus(manv);
                loadTable();
                loadTableInactive();
                this.clear();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    public void search(){
        String email = cboCusEmail.getSelectedItem().toString();
        String name = cboCusName.getSelectedItem().toString();
        String number = cboCusNumber.getSelectedItem().toString();
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
            DefaultTableModel dtm = (DefaultTableModel) tblKhachHang.getModel();
            dtm.setRowCount(0);
            
                        Statement s =db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE  "
                    + " cus_number LIKE N'%"+number+"%' AND cus_name LIKE N'%"+name+"%' AND cus_email LIKE N'%"+email+"%'");
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("customer_id"));
                v.add(rs.getString("cus_name"));
                v.add(rs.getString("cus_number"));
                v.add(rs.getString("cus_email"));

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
      
        String name = cboCusName1.getSelectedItem().toString();
      
        String number = cboCusNumber1.getSelectedItem().toString();
        String email = cboCusEmail1.getSelectedItem().toString();
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
            DefaultTableModel dtm = (DefaultTableModel) tblKhachHang2.getModel();
            dtm.setRowCount(0);
            
                        Statement s =db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE   cus_number LIKE N'%"+number+"%' "
                    + " AND cus_name LIKE N'%"+name+"%' AND cus_email LIKE N'%"+email+"%' and status = 'Inactive'");
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("customer_id"));
                v.add(rs.getString("cus_name"));
                v.add(rs.getString("cus_number"));
                v.add(rs.getString("cus_email"));

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
        txt1 = new searchwing.TextFieldAnimation();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboCusName = new combo_suggestion.ComboBoxSuggestion();
        cboCusNumber = new combo_suggestion.ComboBoxSuggestion();
        cboCusEmail = new combo_suggestion.ComboBoxSuggestion();
        myButton10 = new button.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        myButton7 = new button.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHang1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt2 = new searchwing.TextFieldAnimation();
        lblCusID = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cusName = new textfield_suggestion.TextFieldSuggestion();
        cusNumber = new textfield_suggestion.TextFieldSuggestion();
        jLabel10 = new javax.swing.JLabel();
        cusEmail = new textfield_suggestion.TextFieldSuggestion();
        jLabel11 = new javax.swing.JLabel();
        cusCity = new textfield_suggestion.TextFieldSuggestion();
        jLabel12 = new javax.swing.JLabel();
        CusBillAdd = new textfield_suggestion.TextFieldSuggestion();
        jLabel13 = new javax.swing.JLabel();
        CusShipAdd = new textfield_suggestion.TextFieldSuggestion();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cusBank = new textfield_suggestion.TextFieldSuggestion();
        cusBankNo = new textfield_suggestion.TextFieldSuggestion();
        jLabel16 = new javax.swing.JLabel();
        jCheckBoxCustom1 = new checkbox.JCheckBoxCustom();
        jLabel17 = new javax.swing.JLabel();
        myButton1 = new button.MyButton();
        myButton2 = new button.MyButton();
        myButton3 = new button.MyButton();
        myButton4 = new button.MyButton();
        jLabel18 = new javax.swing.JLabel();
        cusContactName = new textfield_suggestion.TextFieldSuggestion();
        cusContactEmail = new textfield_suggestion.TextFieldSuggestion();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cusContactNumber = new textfield_suggestion.TextFieldSuggestion();
        CusOnline = new textfield_suggestion.TextFieldSuggestion();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        CusOtherInfo = new textfield_suggestion.TextFieldSuggestion();
        CusDescription = new textfield_suggestion.TextFieldSuggestion();
        jLabel22 = new javax.swing.JLabel();
        CusUserID = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtCusReportName = new textfield_suggestion.TextFieldSuggestion();
        jLabel25 = new javax.swing.JLabel();
        txtContactReportNumber = new textfield_suggestion.TextFieldSuggestion();
        txtContactReportCity = new textfield_suggestion.TextFieldSuggestion();
        jLabel26 = new javax.swing.JLabel();
        myButton5 = new button.MyButton();
        myButton6 = new button.MyButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt3 = new searchwing.TextFieldAnimation();
        myButton8 = new button.MyButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        myButton9 = new button.MyButton();
        cboCusName1 = new combo_suggestion.ComboBoxSuggestion();
        cboCusNumber1 = new combo_suggestion.ComboBoxSuggestion();
        cboCusEmail1 = new combo_suggestion.ComboBoxSuggestion();
        myButton11 = new button.MyButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblKhachHang3 = new javax.swing.JTable();

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
        jLabel2.setText("Customer Name:");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("Mobile Number:");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setText("Email:");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboCusName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCusNameActionPerformed(evt);
            }
        });
        cboCusName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCusNameKeyReleased(evt);
            }
        });

        cboCusNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCusNumberActionPerformed(evt);
            }
        });
        cboCusNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCusNumberKeyReleased(evt);
            }
        });

        cboCusEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCusEmailActionPerformed(evt);
            }
        });
        cboCusEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCusEmailKeyReleased(evt);
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
                    .addComponent(cboCusName, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboCusNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboCusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCusNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null}
            },
            new String [] {
                "CusID", "CusName", "CusNumber", "CusEmail"
            }
        ));
        tblKhachHang.setFocusable(false);
        tblKhachHang.setRowHeight(40);
        tblKhachHang.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblKhachHang.getTableHeader().setReorderingAllowed(false);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton7.setText("Save To Excel");
        myButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton7ActionPerformed(evt);
            }
        });

        tblKhachHang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CusID", "CusName", "CusNumber", "CusEmail", "CusCity", "CusBillAdd", "CusShipAdd", "CusBank", "CusBankNo", "CusContactName", "CusContactEmail", "CusContactNumber", "CusOnline", "CusOtherInfo", "CusDescription", "UserID"
            }
        ));
        tblKhachHang1.setFocusable(false);
        tblKhachHang1.setRowHeight(40);
        tblKhachHang1.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblKhachHang1.getTableHeader().setReorderingAllowed(false);
        tblKhachHang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhachHang1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHang1);

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
                .addContainerGap(86, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tabs.addTab("All Customer's", jPanel1);

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

        lblCusID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCusID.setForeground(new java.awt.Color(51, 153, 255));
        lblCusID.setText(" ");
        lblCusID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("Name:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cusName.setForeground(new java.awt.Color(51, 153, 255));
        cusName.setToolTipText("");

        cusNumber.setForeground(new java.awt.Color(51, 153, 255));
        cusNumber.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 255));
        jLabel10.setText("Office Number:");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cusEmail.setForeground(new java.awt.Color(51, 153, 255));
        cusEmail.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 255));
        jLabel11.setText("Customer Email:");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cusCity.setForeground(new java.awt.Color(51, 153, 255));
        cusCity.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("City:");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        CusBillAdd.setForeground(new java.awt.Color(51, 153, 255));
        CusBillAdd.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Billing Address:");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        CusShipAdd.setForeground(new java.awt.Color(51, 153, 255));
        CusShipAdd.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Shipping Address:");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 255));
        jLabel15.setText("Bank:");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cusBank.setForeground(new java.awt.Color(51, 153, 255));
        cusBank.setToolTipText("");

        cusBankNo.setForeground(new java.awt.Color(51, 153, 255));
        cusBankNo.setToolTipText("");

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

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Contact Person:");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cusContactName.setForeground(new java.awt.Color(51, 153, 255));
        cusContactName.setToolTipText("");

        cusContactEmail.setForeground(new java.awt.Color(51, 153, 255));
        cusContactEmail.setToolTipText("");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 153, 255));
        jLabel19.setText("Email:");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 255));
        jLabel21.setText("Mobile Number:");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cusContactNumber.setForeground(new java.awt.Color(51, 153, 255));
        cusContactNumber.setToolTipText("");

        CusOnline.setForeground(new java.awt.Color(51, 153, 255));
        CusOnline.setToolTipText("");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 153, 255));
        jLabel20.setText("Online:");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 153, 255));
        jLabel23.setText("Other's Info");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        CusOtherInfo.setForeground(new java.awt.Color(51, 153, 255));
        CusOtherInfo.setToolTipText("");

        CusDescription.setForeground(new java.awt.Color(51, 153, 255));
        CusDescription.setToolTipText("");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 255));
        jLabel22.setText("Desctiption:");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        CusUserID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CusUserID.setForeground(new java.awt.Color(51, 153, 255));
        CusUserID.setText("UserID:");
        CusUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/information.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCusID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(cusName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(cusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(cusCity, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(CusBillAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(CusShipAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cusBank, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cusBankNo, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cusNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cusContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel18)
                                                .addComponent(jLabel21)
                                                .addComponent(jLabel20)
                                                .addComponent(jLabel23)
                                                .addComponent(jLabel22)
                                                .addComponent(jLabel37))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cusContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CusOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CusOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CusDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cusContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabel17)
                                    .addComponent(CusUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(114, 114, 114))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
                        .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(598, 598, 598))))
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
                        .addComponent(lblCusID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(CusUserID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cusContactName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(cusContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cusContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CusOnline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CusOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CusDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel37))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cusNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cusCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(CusBillAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(CusShipAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cusBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cusBankNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabs.addTab("Add Customer's", jPanel2);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 153, 255));
        jLabel24.setText("Contact Name:");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtCusReportName.setForeground(new java.awt.Color(51, 153, 255));
        txtCusReportName.setToolTipText("");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 153, 255));
        jLabel25.setText("Contact Number:");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtContactReportNumber.setForeground(new java.awt.Color(51, 153, 255));
        txtContactReportNumber.setToolTipText("");

        txtContactReportCity.setForeground(new java.awt.Color(51, 153, 255));
        txtContactReportCity.setToolTipText("");

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
                            .addComponent(txtCusReportName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtContactReportNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(myButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtContactReportCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(43, 43, 43))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtCusReportName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtContactReportNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtContactReportCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        myButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/business.png"))); // NOI18N
        myButton6.setText("All Customer Full Detail's");
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
                .addContainerGap(568, Short.MAX_VALUE))
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
                .addContainerGap(292, Short.MAX_VALUE))
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

        tabs.addTab("Customer's Reports", jPanel6);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("Search:");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        myButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton8.setText("Save To Excel");

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Customer Name:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 153, 255));
        jLabel30.setText("Mobile Number:");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 153, 255));
        jLabel31.setText("Email:");
        jLabel31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/update.png"))); // NOI18N
        myButton9.setText("Return");
        myButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton9ActionPerformed(evt);
            }
        });

        cboCusName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCusName1ActionPerformed(evt);
            }
        });
        cboCusName1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCusName1KeyReleased(evt);
            }
        });

        cboCusNumber1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCusNumber1ActionPerformed(evt);
            }
        });
        cboCusNumber1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCusNumber1KeyReleased(evt);
            }
        });

        cboCusEmail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCusEmail1ActionPerformed(evt);
            }
        });
        cboCusEmail1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboCusEmail1KeyReleased(evt);
            }
        });

        myButton11.setText("Filter");
        myButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboCusName1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboCusNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(cboCusEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCusName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCusNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCusEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblKhachHang2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null},
                {"", "", "", null}
            },
            new String [] {
                "CusID", "CusName", "CusNumber", "CusEmail"
            }
        ));
        tblKhachHang2.setFocusable(false);
        tblKhachHang2.setRowHeight(40);
        tblKhachHang2.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblKhachHang2.getTableHeader().setReorderingAllowed(false);
        tblKhachHang2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHang2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhachHang2);

        tblKhachHang3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CusID", "CusName", "CusNumber", "CusEmail", "CusCity", "CusBillAdd", "CusShipAdd", "CusBank", "CusBankNo", "CusContactName", "CusContactEmail", "CusContactNumber", "CusOnline", "CusOtherInfo", "CusDescription", "UserID"
            }
        ));
        tblKhachHang3.setFocusable(false);
        tblKhachHang3.setRowHeight(40);
        tblKhachHang3.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblKhachHang3.getTableHeader().setReorderingAllowed(false);
        tblKhachHang3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKhachHang3MousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblKhachHang3);

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
                        .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
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

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void txt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2ActionPerformed

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

    private void txt2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2KeyReleased
        String search = txt2.getText();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE customer_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblCusID.setText(rs.getString("customer_id"));
                            cusName.setText(rs.getString("cus_name"));
                            cusNumber.setText(rs.getString("cus_number"));
                            cusEmail.setText(rs.getString("cus_email"));
                            cusCity.setText(rs.getString("cus_city"));
                            CusBillAdd.setText(rs.getString("cus_bill_add"));
                            CusShipAdd.setText(rs.getString("cus_ship_add"));
                            cusBank.setText(rs.getString("cus_bank"));
                            cusBankNo.setText(rs.getString("cus_bank_no"));
                            cusContactName.setText(rs.getString("cus_contact_name"));
                            cusContactEmail.setText(rs.getString("cus_contact_email"));
                            cusContactNumber.setText(rs.getString("cus_contact_number"));
                            CusOnline.setText(rs.getString("cus_contact_online"));
                            CusOtherInfo.setText(rs.getString("cus_contact_otherinfo"));
                            CusDescription.setText(rs.getString("cus_contact_description"));
                            CusUserID.setText(rs.getString("users_id"));      
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
    }//GEN-LAST:event_txt2KeyReleased

    private void txt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1KeyReleased
        String name = txt1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblKhachHang.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE cus_name LIKE N'%"+name+"%'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("customer_id"));
                v.add(rs.getString("cus_name"));
                v.add(rs.getString("cus_number"));
                v.add(rs.getString("cus_email"));

                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_txt1KeyReleased

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/AllCustomers.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

                    // Giả sử bạn đã có ID của hóa đơn, ở đây tôi đặt là 12345
                    

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, db.myCon());

                    // Tạo tên tệp sử dụng ID của hóa đơn
                    String outputFileName = "AllCustomers.pdf";

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
        String paraName = "%" + txtCusReportName.getText() + "%";
        String paraNumber = "%" + txtContactReportNumber.getText() + "%";
        String paraCity = "%" + txtContactReportCity.getText() + "%";

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
                    InputStream file = getClass().getResourceAsStream("/Reports/AllCustomer.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, para, db.myCon());
                    String outputFileName = "Customers" +txtCusReportName.getText()+".pdf";

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

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        int row = tblKhachHang.getSelectedRow();
            String supID = tblKhachHang.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblKhachHang1.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from customers where customer_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("customer_id"));
                    v.add(rs.getString("cus_name"));
                    v.add(rs.getString("cus_number"));
                    v.add(rs.getString("cus_email"));
                    v.add(rs.getString("cus_city"));
                    v.add(rs.getString("cus_bill_add"));
                    v.add(rs.getString("cus_ship_add"));
                    v.add(rs.getString("cus_bank"));
                    v.add(rs.getString("cus_bank_no"));
                    v.add(rs.getString("cus_contact_name"));
                    v.add(rs.getString("cus_contact_email"));
                    v.add(rs.getString("cus_contact_number"));
                    v.add(rs.getString("cus_contact_online"));
                    v.add(rs.getString("cus_contact_otherinfo"));
                    v.add(rs.getString("cus_contact_description"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void tblKhachHang1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHang1MousePressed
       if(evt.getClickCount()==2){
                    String search = tblKhachHang1.getValueAt(tblKhachHang1.getSelectedRow(), 0).toString();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE customer_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblCusID.setText(rs.getString("customer_id"));
                            cusName.setText(rs.getString("cus_name"));
                            cusNumber.setText(rs.getString("cus_number"));
                            cusEmail.setText(rs.getString("cus_email"));
                            cusCity.setText(rs.getString("cus_city"));
                            CusBillAdd.setText(rs.getString("cus_bill_add"));
                            CusShipAdd.setText(rs.getString("cus_ship_add"));
                            cusBank.setText(rs.getString("cus_bank"));
                            cusBankNo.setText(rs.getString("cus_bank_no"));
                            cusContactName.setText(rs.getString("cus_contact_name"));
                            cusContactEmail.setText(rs.getString("cus_contact_email"));
                            cusContactNumber.setText(rs.getString("cus_contact_number"));
                            CusOnline.setText(rs.getString("cus_contact_online"));
                            CusOtherInfo.setText(rs.getString("cus_contact_otherinfo"));
                            CusDescription.setText(rs.getString("cus_contact_description"));
                            CusUserID.setText(rs.getString("users_id"));      
                        }
                        tabs.setSelectedIndex(1);
                        s.close();
                        rs.close();
                            db.myCon().close();
                            tabs.setSelectedIndex(1);
                            }catch(Exception e){
                                System.out.println(e);
                            }
        }
    }//GEN-LAST:event_tblKhachHang1MousePressed

    private void txt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt3ActionPerformed

    private void txt3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt3KeyReleased
        String name = txt3.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblKhachHang2.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM customers WHERE cus_name LIKE N'%"+name+"%' and status = 'Inactive'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("customer_id"));
                v.add(rs.getString("cus_name"));
                v.add(rs.getString("cus_number"));
                v.add(rs.getString("cus_email"));

                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_txt3KeyReleased

    private void tblKhachHang2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHang2MouseClicked
        int row = tblKhachHang2.getSelectedRow();
            String supID = tblKhachHang2.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblKhachHang3.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from customers where customer_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("customer_id"));
                    v.add(rs.getString("cus_name"));
                    v.add(rs.getString("cus_number"));
                    v.add(rs.getString("cus_email"));
                    v.add(rs.getString("cus_city"));
                    v.add(rs.getString("cus_bill_add"));
                    v.add(rs.getString("cus_ship_add"));
                    v.add(rs.getString("cus_bank"));
                    v.add(rs.getString("cus_bank_no"));
                    v.add(rs.getString("cus_contact_name"));
                    v.add(rs.getString("cus_contact_email"));
                    v.add(rs.getString("cus_contact_number"));
                    v.add(rs.getString("cus_contact_online"));
                    v.add(rs.getString("cus_contact_otherinfo"));
                    v.add(rs.getString("cus_contact_description"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblKhachHang2MouseClicked

    private void tblKhachHang3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHang3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblKhachHang3MousePressed

    private void myButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton9ActionPerformed
        returnCus();
    }//GEN-LAST:event_myButton9ActionPerformed

    private void myButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton7ActionPerformed
        try {
            XSSFWorkbook wordbook = new XSSFWorkbook();
            XSSFSheet sheet = wordbook.createSheet("Customer Infomation");
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(3);
            cell = row.createCell(1,CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(2,CellType.STRING);
            cell.setCellValue("CustomerID");
            cell = row.createCell(3,CellType.STRING);
            cell.setCellValue("CustomerName");
            cell = row.createCell(4,CellType.STRING);
            cell.setCellValue("CustomerNumber");
            cell = row.createCell(5,CellType.STRING);
            cell.setCellValue("CustomerEmail");
            cell = row.createCell(6,CellType.STRING);
            cell.setCellValue("CustomerCity");
            cell = row.createCell(7,CellType.STRING);
            cell.setCellValue("CustomerBillAddress");
            cell = row.createCell(8,CellType.STRING);
            cell.setCellValue("CustomerShipAddress");
            cell = row.createCell(9,CellType.STRING);
            cell.setCellValue("CustomerBank");
            cell = row.createCell(10,CellType.STRING);
            cell.setCellValue("CustomerBankNo");
            cell = row.createCell(11,CellType.STRING);
            cell.setCellValue("CustomerContactName");
            cell = row.createCell(12,CellType.STRING);
            cell.setCellValue("CustomerContactEmail");
            cell = row.createCell(13,CellType.STRING);
            cell.setCellValue("CustomerContactNumber");
            cell = row.createCell(14,CellType.STRING);
            cell.setCellValue("CustomerContactOnline");
            cell = row.createCell(15,CellType.STRING);
            cell.setCellValue("CustomerContactOtherInfo");
            cell = row.createCell(16,CellType.STRING);
            cell.setCellValue("CustomerUserID");
            cell = row.createCell(17,CellType.STRING);
            cell.setCellValue("CustomerStatus");
            
            
            try {

                List<customer> list = cusDAO.selectAll();
                
                for (int i=0;i<list.size();i++) {
                    row = sheet.createRow(4+i);
                    cell=row.createCell(0,CellType.NUMERIC);
                    cell.setCellValue(i+1);
                    
                    cell=row.createCell(1,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusID());
                    
                    cell=row.createCell(2,CellType.NUMERIC);
                    cell.setCellValue(list.get(i).getCusName());
                    
                    cell=row.createCell(3,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusNumber());
                    
                    cell=row.createCell(4,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusEmail());
                    
                    cell=row.createCell(5,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusCity());
                    
                    cell=row.createCell(6,CellType.STRING);
                    cell.setCellValue(list.get(i).getBillCusAdd());
                    
                    cell=row.createCell(7,CellType.STRING);
                    cell.setCellValue(list.get(i).getShipCusAdd());
                    
                    cell=row.createCell(8,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusBank());
                    
                    cell=row.createCell(9,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusBankNo());
                    
                    cell=row.createCell(10,CellType.NUMERIC);
                    cell.setCellValue(list.get(i).getCusContactPerson());
                    
                    cell=row.createCell(11,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusEmailPerson());
                    
                    cell=row.createCell(12,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusNumberPerson());
                    
                    cell=row.createCell(13,CellType.NUMERIC);
                    cell.setCellValue(list.get(i).getCusOnline());
                    
                    cell=row.createCell(14,CellType.STRING);
                    cell.setCellValue(list.get(i).getOtherCusInfo());
                    
                    cell=row.createCell(15,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusDescription());
                    
                    cell=row.createCell(16,CellType.STRING);
                    cell.setCellValue(list.get(i).getCusUserID());
                    
                    cell=row.createCell(17,CellType.STRING);
                    cell.setCellValue(list.get(i).getStatus());

                }
                
                File f = new File("D://CustomerInfomation.xlsx");
                try {
                    FileOutputStream fis = new FileOutputStream(f);
                    wordbook.write(fis);
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Print Succeed");
            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_myButton7ActionPerformed

    private void cboCusNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusNameActionPerformed
        
        
    }//GEN-LAST:event_cboCusNameActionPerformed

    private void cboCusNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusNameKeyReleased

    }//GEN-LAST:event_cboCusNameKeyReleased

    private void cboCusNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusNumberActionPerformed

    private void cboCusNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusNumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusNumberKeyReleased

    private void cboCusEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusEmailActionPerformed

    private void cboCusEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusEmailKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusEmailKeyReleased

    private void myButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton10ActionPerformed
        search();
        
    }//GEN-LAST:event_myButton10ActionPerformed

    private void cboCusName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusName1ActionPerformed

    private void cboCusName1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusName1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusName1KeyReleased

    private void cboCusNumber1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusNumber1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusNumber1ActionPerformed

    private void cboCusNumber1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusNumber1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusNumber1KeyReleased

    private void cboCusEmail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusEmail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusEmail1ActionPerformed

    private void cboCusEmail1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusEmail1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCusEmail1KeyReleased

    private void myButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton11ActionPerformed
        search1();
    }//GEN-LAST:event_myButton11ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private textfield_suggestion.TextFieldSuggestion CusBillAdd;
    private textfield_suggestion.TextFieldSuggestion CusDescription;
    private textfield_suggestion.TextFieldSuggestion CusOnline;
    private textfield_suggestion.TextFieldSuggestion CusOtherInfo;
    private textfield_suggestion.TextFieldSuggestion CusShipAdd;
    private javax.swing.JLabel CusUserID;
    private combo_suggestion.ComboBoxSuggestion cboCusEmail;
    private combo_suggestion.ComboBoxSuggestion cboCusEmail1;
    private combo_suggestion.ComboBoxSuggestion cboCusName;
    private combo_suggestion.ComboBoxSuggestion cboCusName1;
    private combo_suggestion.ComboBoxSuggestion cboCusNumber;
    private combo_suggestion.ComboBoxSuggestion cboCusNumber1;
    private textfield_suggestion.TextFieldSuggestion cusBank;
    private textfield_suggestion.TextFieldSuggestion cusBankNo;
    private textfield_suggestion.TextFieldSuggestion cusCity;
    private textfield_suggestion.TextFieldSuggestion cusContactEmail;
    private textfield_suggestion.TextFieldSuggestion cusContactName;
    private textfield_suggestion.TextFieldSuggestion cusContactNumber;
    private textfield_suggestion.TextFieldSuggestion cusEmail;
    private textfield_suggestion.TextFieldSuggestion cusName;
    private textfield_suggestion.TextFieldSuggestion cusNumber;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JLabel lblCusID;
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
    private raven.tabbed.TabbedPaneCustom tabs;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblKhachHang1;
    private javax.swing.JTable tblKhachHang2;
    private javax.swing.JTable tblKhachHang3;
    private searchwing.TextFieldAnimation txt1;
    private searchwing.TextFieldAnimation txt2;
    private searchwing.TextFieldAnimation txt3;
    private textfield_suggestion.TextFieldSuggestion txtContactReportCity;
    private textfield_suggestion.TextFieldSuggestion txtContactReportNumber;
    private textfield_suggestion.TextFieldSuggestion txtCusReportName;
    // End of variables declaration//GEN-END:variables
}
