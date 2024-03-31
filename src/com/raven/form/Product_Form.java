/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Utils.Data;
import Utils.MsgBox;
import Utils.XDate;
import Utils.db;
import com.raven.component.Form;
import com.raven.dao.categoryDAO;
import com.raven.dao.customerDAO;
import com.raven.dao.productDAO;
import com.raven.dao.supplierDAO;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import com.raven.entity.category;
import com.raven.entity.customer;
import com.raven.entity.product;
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
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
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
import textfield_suggestion.TextFieldSuggestion;
import textfield_suggestion.TextFieldSuggestionUI;
/**
 *
 * @author ADMIN
 */
public class Product_Form extends Form {
    private Setting_Form settingForm;
    customerDAO cusDAO = new customerDAO();
    categoryDAO cateDAO = new categoryDAO();
    supplierDAO supDAO = new supplierDAO();
    productDAO proDAO = new productDAO();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    int index = 0;
    private users user;
    public void setUser(users user1){
        user = user1;
    }
    String ngayThangNamGioPhutGiay = "2023-08-03 14:30:00";
    String pattern = "yyyy-MM-dd HH:mm:ss";

    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    long currentTimestamp = getCurrentTimestamp();

    public Product_Form(users user) {
        initComponents();
        init();
        tblProduct.getTableHeader().setForeground(new Color(255,255,255));  
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(51, 153, 255));
        headerRenderer.setForeground(new Color(255, 255, 255));
        tblProduct.getTableHeader().setDefaultRenderer(headerRenderer);
        tblProduct2.getTableHeader().setForeground(new Color(255,255,255));  
        DefaultTableCellRenderer headerRenderer1 = new DefaultTableCellRenderer();
        headerRenderer1.setBackground(new Color(51, 153, 255));
        headerRenderer1.setForeground(new Color(255, 255, 255));
        tblProduct2.getTableHeader().setDefaultRenderer(headerRenderer1);
        loadTableCategory();
        loadTableProduct();
        fillComboBoxSupplier();
        fillComboBoxCategory();
        loadTableMostSellingProduct();
         this.user = user;
         lblUserID.setText(user.getUserID());

          loadTableProductInactive();
        System.out.println("Total Time (int): " + currentTimestamp);
    }
    
    public void init(){
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        jPanel3.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setOpaque(false);
        jPanel6.setBackground(new Color(0, 0, 0, 0));
        jPanel6.setOpaque(false);
        jPanel8.setBackground(new Color(0, 0, 0, 0));
        jPanel8.setOpaque(false);
        jPanel9.setBackground(new Color(0, 0, 0, 0));
        jPanel9.setOpaque(false);
        jPanel7.setBackground(new Color(0, 0, 0, 0));
        jPanel7.setOpaque(false);
        jPanel10.setBackground(new Color(0, 0, 0, 0));
        jPanel10.setOpaque(false);
     
        tabs.setBackground(new Color(0, 0, 0, 0));
        tabs.setOpaque(false);

        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setOpaque(false);
 
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
        });
        fillComboBoxProduct();
        fillComboBoxCategory1();
    }
    
    void fillComboBoxProduct(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboProduct.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<product> list = proDAO.selectAll();
            for(product cd:list){
                model.addElement(cd.getProName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxCategory1(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCategory.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<category> list = cateDAO.selectAll();
            for(category cd:list){
                model.addElement(cd.getCateName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    public long getCurrentTimestamp() {
        Instant currentInstant = Instant.now();
        currentTimestamp = currentInstant.getEpochSecond();
        System.out.println(currentTimestamp);
        return currentInstant.getEpochSecond();
    }
    
    void loadTableProduct(){
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        try {

            List<product>list =  proDAO.selectAll();
      
            for(product nh: list){
                Object[] row = {          
                    nh.getProID(),
                    nh.getBarcode(),
                    nh.getProName(),                   
                    nh.getProCateID(),                   
                    nh.getProUnitPrice(),
                    nh.getProSalesPrice(),
                    nh.getProQuanlity(),
                    nh.getDefaultProType(),
                    nh.getExpProDate(),
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void loadTableProductInactive(){
        DefaultTableModel model = (DefaultTableModel) tblProduct2.getModel();
        model.setRowCount(0);
        try {

            List<product>list =  proDAO.selectAllInactive();
      
            for(product nh: list){
                Object[] row = {          
                    nh.getProID(),
                    nh.getBarcode(),
                    nh.getProName(),                   
                    nh.getProCateID(),                   
                    nh.getProUnitPrice(),
                    nh.getProSalesPrice(),
                    nh.getProQuanlity(),
                    nh.getDefaultProType(),
                    nh.getExpProDate(),
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void loadTableMostSellingProduct(){
        DefaultTableModel model = (DefaultTableModel) tblMostSell.getModel();
        model.setRowCount(0);
        try {

            String sql = "Select product_name,sum(quantity) as totalQuantity, sum(sales_sub_total) as totalSales from sales group by product_name order by totalQuantity desc";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("product_name"));
                v.add(rs.getInt("totalQuantity"));
                v.add(rs.getString("totalSales"));
                model.addRow(v);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void loadTableCategory(){
        DefaultTableModel model = (DefaultTableModel) tblCategory.getModel();
        model.setRowCount(0);
        try {

            List<category>list =  cateDAO.selectAll();
      
            for(category nh: list){
                Object[] row = {          
                    nh.getCateID(),
                    nh.getCateName(),
                    nh.getCateOtherInfo(),
                    
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    
    
    void setModelCategory(category model){
        lblCateID.setText(model.getCateID());
        cateName.setText(model.getCateName());
        cateOtherInfo.setText(model.getCateOtherInfo());
        
    }
    
    void setModelProduct(product model){
        lblProID.setText(model.getProID());
        proBarcode.setText(String.valueOf(model.getBarcode()));
        proName.setText(model.getProName());
        String cateName = "";
        try {
            String cateID = model.getProCateID();
            String sql = "Select cate_name from categories Where category_id = '"+cateID+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                cateName = rs.getString("cate_name");
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        cboCateName.setSelectedItem(cateName);
        proUnitPrice.setText(String.valueOf(model.getProUnitPrice()));
        proSalesPrice.setText(String.valueOf(model.getProSalesPrice()));
        proQuantity.setText(String.valueOf(model.getProQuanlity()));
        proDefaultType.setText(model.getDefaultProType());
        proDescription.setText(model.getProDescription());
        if(String.valueOf(model.getMfProDate()).isEmpty()){
            txtDate2.setText(XDate.now().toString());
        }else{
            txtDate2.setText(String.valueOf(model.getMfProDate()));
        }
        if(String.valueOf(model.getMfProDate()).isEmpty()){
            txtDate1.setText(XDate.now().toString());
        }else{
            txtDate1.setText(String.valueOf(model.getExpProDate()));
        }
        
        
        String supName = "";
        try {
            String supID = model.getProSupID();
            String sql = "Select sup_name from suppliers Where supplier_id = '"+supID+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                cateName = rs.getString("sup_name");
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        cboSupplierName.setSelectedItem(supName);
        proCompanyName.setText(model.getProCompanyName());
        proBrandName.setText(model.getProBrandName());
        proPrivateNote.setText(model.getProPrivateNote());
        lblUserID.setText(model.getProUserID());
    }
    
    category getModelCategory() throws ParseException {
        category model = new category();
        int countNV=0;
        String employeeID = "";
       
        employeeID = "cate" + currentTimestamp;
           
        model.setCateID(employeeID);
        model.setCateName(cateName.getText());
        model.setCateOtherInfo(cateOtherInfo.getText());
        
        return model;
    }
    
    product getModelProduct() throws ParseException {
        product model = new product();
        int countNV=0;
        String employeeID = "";
        
                employeeID = "pro" + currentTimestamp;
           
        model.setProID(employeeID);
        String barcode = proBarcode.getText();
        if(data.ContainNumberOnly(barcode)==false){
            model.setBarcode(0);
        }else{
            if(barcode.isEmpty()){
                model.setBarcode(0);
            }else{
                model.setBarcode(Integer.parseInt(barcode));
            }
        }
            
        
        model.setProName(proName.getText());
        String cateID = "";
        String cateName = "";
        try {
            cateName = cboCateName.getSelectedItem().toString();
            String sql = "Select category_id from categories Where cate_name = N'"+cateName+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                cateID = rs.getString("category_id");
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        model.setProCateID(cateID);
        String unitPrice = proUnitPrice.getText();
        if(data.ContainNumberOnly(unitPrice)==false){
            model.setProUnitPrice(0);
        }else{
            if(unitPrice.isEmpty()){
           model.setProUnitPrice(0);
            }else{
                model.setProUnitPrice(Integer.parseInt(unitPrice));
            }
        }
        model.setStatus("Active");
            
        
        
        
        String salesPrice = proSalesPrice.getText();
        if(data.ContainNumberOnly(salesPrice)==false){
            model.setProSalesPrice(0);
        }else{
            if(salesPrice.isEmpty()){
           
            }else{
                model.setProSalesPrice(Integer.parseInt(salesPrice));
            }
        }
            
        
        
        String quantity = proQuantity.getText();
        if(data.ContainNumberOnly(quantity)==false){
            model.setProQuanlity(0);
        }else{
            if(quantity.isEmpty()){
                model.setProQuanlity(0);
            }else{
                model.setProQuanlity(Integer.parseInt(quantity));
            }
        }
            
        
        
        model.setDefaultProType(proDefaultType.getText());
        model.setProDescription(proDescription.getText());
        model.setMfProDate(format.parse(txtDate2.getText()));
        model.setExpProDate(format.parse(txtDate1.getText()));
        String supID = "";
        String supName = cboSupplierName.getSelectedItem().toString();
        try {
            String sql = "Select supplier_id from suppliers Where sup_name = N'"+supName+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                supID = rs.getString("supplier_id");
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        model.setProSupID(supID);
        model.setProCompanyName(proCompanyName.getText());
        model.setProBrandName(proBrandName.getText());
        model.setProPrivateNote(proPrivateNote.getText());
        model.setProUserID(lblUserID.getText());
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
    
    public void setTrangCategory(){

        cateName.setBorder(white);
        cateOtherInfo.setBorder(white);
        
    }
    
    public void setTrangProduct(){
   
        proBarcode.setBorder(white);
        proName.setBorder(white);
        proUnitPrice.setBorder(white);
        proSalesPrice.setBorder(white);
        proQuantity.setBorder(white);
        proDefaultType.setBorder(white);
        proDescription.setBorder(white);
        txtDate2.setBackground(white);
        txtDate1.setBackground(white);
        proCompanyName.setBorder(white);
        proBrandName.setBorder(white);
        proPrivateNote.setBorder(white);
        
        
    }
    
    void clearCategory(){
        setTrangCategory();
        category model = new category();
        this.setModelCategory(model);
    }
    
    void clearProduct(){
        setTrangProduct();
        product model = new product();
        this.setModelProduct(model);
    }
    
    void editCategory(){
        setTrangCategory();
        try {
            String manv = (String) tblCategory.getValueAt(this.index, 0);
            category model = cateDAO.selectByID(manv);
            if(model!=null){
                this.setModelCategory(model);
              
              
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void editProduct(){
        setTrangProduct();
        try {
            String manv = (String) tblProduct.getValueAt(this.index, 0);
            product model = proDAO.selectByID(manv);
            if(model!=null){
                this.setModelProduct(model);
              
              
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    
    Data data = new Data();
    void insertCategory() throws ParseException{
        category model = getModelCategory();
        cateName.setBackground(Color.WHITE);
        cateOtherInfo.setBackground(Color.WHITE);       
        if( cateName.getText().length()==0 ||cateOtherInfo.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            
            if(cateName.getText().length()==0){
                cateName.setBackground(Color.red);
            }
            if(cateOtherInfo.getText().length()==0){
                cateOtherInfo.setBackground(Color.red);
            }
        }else{
            boolean check = true;
            do{                                      
                
                if(check==true){
                    try {
                        cateDAO.insert(model);
                        this.loadTableCategory();
                        this.clearCategory();
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
    
    void insertProduct() throws ParseException{
        product model = getModelProduct();
        proBarcode.setBorder(Color.WHITE);
        proName.setBorder(Color.WHITE);    
        proUnitPrice.setBorder(Color.WHITE);
        proSalesPrice.setBorder(Color.WHITE);
        proQuantity.setBorder(Color.WHITE);
        proDefaultType.setBorder(Color.WHITE);
        proDescription.setBorder(Color.WHITE);
        txtDate2.setBackground(Color.WHITE);
        txtDate1.setBackground(Color.WHITE);
        proCompanyName.setBorder(Color.WHITE);
        proBrandName.setBorder(Color.WHITE);
        proPrivateNote.setBorder(Color.WHITE);
        if( proBarcode.getText().length()==0 ||proName.getText().length()==0 ||proUnitPrice.getText().length()==0 ||proSalesPrice.getText().length()==0 
                ||proQuantity.getText().length()==0 ||proDefaultType.getText().length()==0 ||proDescription.getText().length()==0 ||txtDate2.getText().length()==0
                ||txtDate1.getText().length()==0 ||proCompanyName.getText().length()==0 ||proBrandName.getText().length()==0 ||proPrivateNote.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            
            if(proBarcode.getText().length()==0){
                proBarcode.setBorder(Color.red);
            }
            if(proName.getText().length()==0){
                proName.setBorder(Color.red);
            }
            if(proUnitPrice.getText().length()==0){
                proUnitPrice.setBorder(Color.red);
            }
            if(proSalesPrice.getText().length()==0){
                proSalesPrice.setBorder(Color.red);
            }
            if(proQuantity.getText().length()==0){
                proQuantity.setBorder(Color.red);
            }
            if(proDefaultType.getText().length()==0){
                proDefaultType.setBorder(Color.red);
            }
            if(proDescription.getText().length()==0){
                proDescription.setBorder(Color.red);
            }
            if(txtDate2.getText().length()==0){
                txtDate2.setBackground(Color.red);
            }
            if(txtDate1.getText().length()==0){
                txtDate1.setBackground(Color.red);
            }
            if(proBrandName.getText().length()==0){
                proBrandName.setBorder(Color.red);
            }
            if(proPrivateNote.getText().length()==0){
                proPrivateNote.setBorder(Color.red);
            }
            if(proCompanyName.getText().length()==0){
                proCompanyName.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{                                      
                if(data.isName(proName.getText())==false){
                    MsgBox.alert(this, "Product Name không đúng định dạng");
                    proName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proBarcode.getText())==false){
                    MsgBox.alert(this, "Barcode Name không đúng định dạng");
                    proBarcode.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proUnitPrice.getText())==false){
                    MsgBox.alert(this, "Unit price không đúng định dạng");
                    proUnitPrice.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proSalesPrice.getText())==false){
                    MsgBox.alert(this, "Sales price không đúng định dạng");
                    proSalesPrice.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proQuantity.getText())==false){
                    MsgBox.alert(this, "Quantity không đúng định dạng");
                    proQuantity.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }
                
                if(check==true){
                    try {
                        proDAO.insert(model);
                        this.loadTableProduct();
                        this.clearProduct();
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
    
    void updateCategory() throws ParseException{
        category model = getModelCategory();
        try {
            cateDAO.update(model);
            this.loadTableCategory();
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại");
            e.printStackTrace();
        }
    }
    
    void updateProduct() throws ParseException{
        product model = getModelProduct();
        proBarcode.setBorder(Color.WHITE);
        proName.setBorder(Color.WHITE);    
        proUnitPrice.setBorder(Color.WHITE);
        proSalesPrice.setBorder(Color.WHITE);
        proQuantity.setBorder(Color.WHITE);
        proDefaultType.setBorder(Color.WHITE);
        proDescription.setBorder(Color.WHITE);
        txtDate2.setBackground(Color.WHITE);
        txtDate1.setBackground(Color.WHITE);
        proCompanyName.setBorder(Color.WHITE);
        proBrandName.setBorder(Color.WHITE);
        proPrivateNote.setBorder(Color.WHITE);
        if( proBarcode.getText().length()==0 ||proName.getText().length()==0 ||proUnitPrice.getText().length()==0 ||proSalesPrice.getText().length()==0 
                ||proQuantity.getText().length()==0 ||proDefaultType.getText().length()==0 ||proDescription.getText().length()==0 ||txtDate2.getText().length()==0
                ||txtDate1.getText().length()==0 ||proCompanyName.getText().length()==0 ||proBrandName.getText().length()==0 ||proPrivateNote.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            
            if(proBarcode.getText().length()==0){
                proBarcode.setBorder(Color.red);
            }
            if(proName.getText().length()==0){
                proName.setBorder(Color.red);
            }
            if(proUnitPrice.getText().length()==0){
                proUnitPrice.setBorder(Color.red);
            }
            if(proSalesPrice.getText().length()==0){
                proSalesPrice.setBorder(Color.red);
            }
            if(proQuantity.getText().length()==0){
                proQuantity.setBorder(Color.red);
            }
            if(proDefaultType.getText().length()==0){
                proDefaultType.setBorder(Color.red);
            }
            if(proDescription.getText().length()==0){
                proDescription.setBorder(Color.red);
            }
            if(txtDate2.getText().length()==0){
                txtDate2.setBackground(Color.red);
            }
            if(txtDate1.getText().length()==0){
                txtDate1.setBackground(Color.red);
            }
            if(proBrandName.getText().length()==0){
                proBrandName.setBorder(Color.red);
            }
            if(proPrivateNote.getText().length()==0){
                proPrivateNote.setBorder(Color.red);
            }
            if(proCompanyName.getText().length()==0){
                proCompanyName.setBorder(Color.red);
            }
        }else{
            boolean check = true;
            do{                                      
                if(data.isName(proName.getText())==false){
                    MsgBox.alert(this, "Product Name không đúng định dạng");
                    proName.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proBarcode.getText())==false){
                    MsgBox.alert(this, "Barcode Name không đúng định dạng");
                    proBarcode.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proUnitPrice.getText())==false){
                    MsgBox.alert(this, "Unit price không đúng định dạng");
                    proUnitPrice.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proSalesPrice.getText())==false){
                    MsgBox.alert(this, "Sales price không đúng định dạng");
                    proSalesPrice.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(proQuantity.getText())==false){
                    MsgBox.alert(this, "Quantity không đúng định dạng");
                    proQuantity.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }
                if(check==true){
                    try {
                        proDAO.update(model);
                        this.loadTableProduct();
                        this.clearProduct();
                        MsgBox.alert(this, "Cập nhật thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại");
                        System.out.println(e);
                    }                    
                    break;
                }
            }while(check==true);
        }
       
    }
    
    void deleteCategory(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa người này")){
            String manv = lblCateID.getText();
            try {
                cateDAO.delete(manv);
                this.loadTableCategory();
                this.clearCategory();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    
    
    void deleteProduct(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa người này")){
            String manv = lblProID.getText();
            try {
                proDAO.delete(manv);
                this.loadTableProduct();
                this.clearProduct();
                loadTableProductInactive();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    void ReturnProduct(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn return người này")){
            int row = tblProduct2.getSelectedRow();
            String manv = tblProduct2.getValueAt(row, 0).toString();
            try {
                proDAO.returnPro(manv);
                this.loadTableProduct();
                this.clearProduct();
                loadTableProductInactive();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");
            }
        }
    }
    
    public void search(){

        String product = cboProduct.getSelectedItem().toString();
        String category = cboCategory.getSelectedItem().toString();
        String category_id = "";
        if(product.equalsIgnoreCase("Selected")){
            product = "";
        }
        if(category.equalsIgnoreCase("Selected")){
            category = "";
        }else{
            try {
                String sql = "select * from categories where cate_name = N'"+category+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    category_id = rs.getString("category_id");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
        

        try {
            DefaultTableModel dtm = (DefaultTableModel) tblProduct.getModel();
            dtm.setRowCount(0);
            
                        Statement s =db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM products WHERE pro_name LIKE '%"+product+"%' AND category_id LIKE '%"+category_id+"%' ");
           while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("product_id"));
                v.add(rs.getString("barcode"));
                v.add(rs.getString("pro_name"));
                String cate_id = rs.getString("category_id");
                String cate_name = "";
                try {
                    String sqll = "Select cate_name from categories where category_id = '"+cate_id+"'";
                    Statement ss = db.myCon().createStatement();
                    ResultSet rss = ss.executeQuery(sqll);
                    if(rss.next()){
                        cate_name = rss.getString("cate_name");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                v.add(cate_name);
                v.add(rs.getString("unit_price"));
                v.add(rs.getString("sales_price")); 
                v.add(rs.getString("pro_quantity")); 
                v.add(rs.getString("pro_default_type"));
                v.add(rs.getString("pro_exp_date")); 
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

        String name = textFieldSuggestion6.getText();


        String email = textFieldSuggestion7.getText();
        try {
            DefaultTableModel dtm = (DefaultTableModel) tblProduct2.getModel();
            dtm.setRowCount(0);
            
                        Statement s =db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM products WHERE pro_name LIKE '%"+name+"%' AND category_id LIKE '%"+email+"%' and status = 'Inactive'");
           while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("product_id"));
                v.add(rs.getString("barcode"));
                v.add(rs.getString("pro_name"));
                String cate_id = rs.getString("category_id");
                String cate_name = "";
                try {
                    String sqll = "Select cate_name from categories where category_id = '"+cate_id+"'";
                    Statement ss = db.myCon().createStatement();
                    ResultSet rss = ss.executeQuery(sqll);
                    if(rss.next()){
                        cate_name = rss.getString("cate_name");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                v.add(cate_name);
                v.add(rs.getString("unit_price"));
                v.add(rs.getString("sales_price")); 
                v.add(rs.getString("pro_quantity")); 
                v.add(rs.getString("pro_default_type"));
                v.add(rs.getString("pro_exp_date")); 
                dtm.addRow(v);
            }
            s.close();
            rs.close();
                db.myCon().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    void fillComboBoxCategory(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCateName.getModel();
        model.removeAllElements();
        try {
            List<category> list = cateDAO.selectAll();
            for(category cd:list){
                model.addElement(cd.getCateName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void fillComboBoxSupplier(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupplierName.getModel();
        model.removeAllElements();
        try {
            List<supplier> list = supDAO.selectAll();
            for(supplier cd:list){
                model.addElement(cd.getSupName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
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

        dateChooser = new com.raven.datechooser.DateChooser();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        dateChooser3 = new com.raven.datechooser.DateChooser();
        tabs = new raven.tabbed.TabbedPaneCustom();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt1 = new searchwing.TextFieldAnimation();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCategory = new javax.swing.JComboBox<>();
        cboProduct = new javax.swing.JComboBox<>();
        myButton16 = new button.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        myButton7 = new button.MyButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProduct1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txt2 = new searchwing.TextFieldAnimation();
        lblProID = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        proBarcode = new textfield_suggestion.TextFieldSuggestion();
        proName = new textfield_suggestion.TextFieldSuggestion();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        proUnitPrice = new textfield_suggestion.TextFieldSuggestion();
        jLabel12 = new javax.swing.JLabel();
        proSalesPrice = new textfield_suggestion.TextFieldSuggestion();
        jLabel13 = new javax.swing.JLabel();
        proDescription = new textfield_suggestion.TextFieldSuggestion();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        myButton1 = new button.MyButton();
        myButton2 = new button.MyButton();
        myButton3 = new button.MyButton();
        cboCateName = new combo_suggestion.ComboBoxSuggestion();
        proDefaultType = new textfield_suggestion.TextFieldSuggestion();
        jLabel27 = new javax.swing.JLabel();
        proQuantity = new textfield_suggestion.TextFieldSuggestion();
        jLabel22 = new javax.swing.JLabel();
        lblUserID = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtDate2 = new javax.swing.JTextField();
        txtDate1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        cboSupplierName = new combo_suggestion.ComboBoxSuggestion();
        jLabel30 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        proCompanyName = new textfield_suggestion.TextFieldSuggestion();
        jLabel48 = new javax.swing.JLabel();
        proBrandName = new textfield_suggestion.TextFieldSuggestion();
        jLabel49 = new javax.swing.JLabel();
        proPrivateNote = new textfield_suggestion.TextFieldSuggestion();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        lblCateID = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cateName = new textfield_suggestion.TextFieldSuggestion();
        jLabel21 = new javax.swing.JLabel();
        cateOtherInfo = new textfield_suggestion.TextFieldSuggestion();
        myButton8 = new button.MyButton();
        myButton9 = new button.MyButton();
        myButton10 = new button.MyButton();
        myButton11 = new button.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtDate5 = new javax.swing.JTextField();
        txtDate6 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        myButton12 = new button.MyButton();
        myButton13 = new button.MyButton();
        myButton14 = new button.MyButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMostSell = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        myButton6 = new button.MyButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtPara_PID = new textfield_suggestion.TextFieldSuggestion();
        jLabel41 = new javax.swing.JLabel();
        txtParaProName = new textfield_suggestion.TextFieldSuggestion();
        txtParaSupID = new textfield_suggestion.TextFieldSuggestion();
        jLabel42 = new javax.swing.JLabel();
        myButton5 = new button.MyButton();
        jLabel43 = new javax.swing.JLabel();
        txtParaCateID = new textfield_suggestion.TextFieldSuggestion();
        txtParaExpDate = new textfield_suggestion.TextFieldSuggestion();
        jLabel44 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt3 = new searchwing.TextFieldAnimation();
        myButton15 = new button.MyButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        textFieldSuggestion6 = new textfield_suggestion.TextFieldSuggestion();
        textFieldSuggestion7 = new textfield_suggestion.TextFieldSuggestion();
        jLabel15 = new javax.swing.JLabel();
        myButton4 = new button.MyButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblProduct2 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblProduct3 = new javax.swing.JTable();

        dateChooser.setTextRefernce(txtDate1);

        dateChooser1.setTextRefernce(txtDate2);

        dateChooser2.setTextRefernce(txtDate5);

        dateChooser3.setTextRefernce(txtDate6);

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
        jLabel2.setText("Product Name:");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("Category:");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoryActionPerformed(evt);
            }
        });

        cboProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProductActionPerformed(evt);
            }
        });

        myButton16.setText("Filter");
        myButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null}
            },
            new String [] {
                "proID", "Barcode", "proName", "categoryName", "unitPrice", "salesPrice", "proQuantity", "proDefaultType", "proExpDate"
            }
        ));
        tblProduct.setFocusable(false);
        tblProduct.setRowHeight(40);
        tblProduct.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblProduct.getTableHeader().setReorderingAllowed(false);
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton7.setText("Save To Excel");

        tblProduct1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "proID", "Barcode", "proName", "categoryName", "unitPrice", "salesPrice", "proQuantity", "proDefaultType", "proDescription", "proMFDate", "proExpDate", "proCompanyName", "proBrandName", "proPrivateNote", "proSupplierID", "UserID"
            }
        ));
        tblProduct1.setFocusable(false);
        tblProduct1.setRowHeight(40);
        tblProduct1.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblProduct1.getTableHeader().setReorderingAllowed(false);
        tblProduct1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProduct1MousePressed(evt);
            }
        });
        tblProduct1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProduct1KeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblProduct1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(73, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tabs.addTab("All Product's", jPanel1);

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

        lblProID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblProID.setForeground(new java.awt.Color(51, 153, 255));
        lblProID.setText("ID:");
        lblProID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("Barcode:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proBarcode.setForeground(new java.awt.Color(51, 153, 255));
        proBarcode.setToolTipText("");

        proName.setForeground(new java.awt.Color(51, 153, 255));
        proName.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 255));
        jLabel10.setText("Name");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 255));
        jLabel11.setText("Category Name:");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proUnitPrice.setForeground(new java.awt.Color(51, 153, 255));
        proUnitPrice.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("Unit Price:");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proSalesPrice.setForeground(new java.awt.Color(51, 153, 255));
        proSalesPrice.setToolTipText("");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Sales Price:");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proDescription.setForeground(new java.awt.Color(51, 153, 255));
        proDescription.setToolTipText("");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("Description:");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 153, 255));
        jLabel17.setText("Product Info:");
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

        proDefaultType.setForeground(new java.awt.Color(51, 153, 255));
        proDefaultType.setToolTipText("");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Default Type:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proQuantity.setForeground(new java.awt.Color(51, 153, 255));
        proQuantity.setToolTipText("");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 255));
        jLabel22.setText("Quality:");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblUserID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUserID.setForeground(new java.awt.Color(51, 153, 255));
        lblUserID.setText("User ID:");
        lblUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 153, 255));
        jLabel28.setText("MF_Date:");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 153, 255));
        jLabel29.setText("EXP_Date:");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("...");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/supplier.png"))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 153, 255));
        jLabel30.setText("Supplier Name:");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(51, 153, 255));
        jLabel47.setText("Company Name:");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proCompanyName.setForeground(new java.awt.Color(51, 153, 255));
        proCompanyName.setToolTipText("");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(51, 153, 255));
        jLabel48.setText("Brand Name:");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proBrandName.setForeground(new java.awt.Color(51, 153, 255));
        proBrandName.setToolTipText("");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 153, 255));
        jLabel49.setText("Private Note:");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        proPrivateNote.setForeground(new java.awt.Color(51, 153, 255));
        proPrivateNote.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proSalesPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proUnitPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(proName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboCateName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(proBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(proQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(proDefaultType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(proDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUserID)
                                    .addComponent(jLabel17))
                                .addGap(451, 451, 451))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel29)
                                            .addComponent(jLabel28))
                                        .addGap(35, 35, 35)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDate2, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                                            .addComponent(txtDate1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel32))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel31))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel30)
                                            .addComponent(jLabel47)
                                            .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(proBrandName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(proCompanyName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cboSupplierName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                            .addComponent(proPrivateNote, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 26, Short.MAX_VALUE)
                        .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(598, 598, 598))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(lblUserID, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblProID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(proBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28)
                                .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton6)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(proName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel31)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel33)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(cboCateName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel30)
                                .addComponent(cboSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(proUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(proSalesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(proQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(proCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(proBrandName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(proPrivateNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(proDefaultType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(proDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabs.addTab("Add Product's", jPanel2);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Category ID      :");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblCateID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCateID.setForeground(new java.awt.Color(51, 153, 255));
        lblCateID.setText("ID");
        lblCateID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 153, 255));
        jLabel20.setText("Category Name:");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cateName.setForeground(new java.awt.Color(51, 153, 255));
        cateName.setToolTipText("");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 255));
        jLabel21.setText("Other Info         :");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cateOtherInfo.setForeground(new java.awt.Color(51, 153, 255));
        cateOtherInfo.setToolTipText("");

        myButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/save.png"))); // NOI18N
        myButton8.setText("Save");
        myButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton8ActionPerformed(evt);
            }
        });

        myButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/update.png"))); // NOI18N
        myButton9.setText("Update");
        myButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton9ActionPerformed(evt);
            }
        });

        myButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/delete.png"))); // NOI18N
        myButton10.setText("Recycle");
        myButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton10ActionPerformed(evt);
            }
        });

        myButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton11.setText("Save To Excel");

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", ""},
                {"", "", ""},
                {"", "", ""},
                {"", "", ""}
            },
            new String [] {
                "Category ID", "Category Name", "Category Other Info"
            }
        ));
        tblCategory.setFocusable(false);
        tblCategory.setRowHeight(40);
        tblCategory.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblCategory.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblCategory);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/main-menu.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cateOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(lblCateID)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel38))
                                            .addComponent(cateName, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(354, 354, 354)
                        .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(lblCateID)
                        .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cateName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cateOtherInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
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

        tabs.addTab("Add Category", jPanel6);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 153, 255));
        jLabel25.setText("To Date:");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton7.setText("...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("...");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        myButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/search x30.png"))); // NOI18N
        myButton12.setText("View Report");
        myButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton12ActionPerformed(evt);
            }
        });

        myButton13.setText("Filter");
        myButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton13ActionPerformed(evt);
            }
        });

        myButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton14.setText("Save To Excel");

        tblMostSell.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", ""},
                {"", "", ""},
                {"", "", ""},
                {"", "", ""}
            },
            new String [] {
                "ProductName", "Total Quantity", "Total Price"
            }
        ));
        tblMostSell.setFocusable(false);
        tblMostSell.setRowHeight(40);
        tblMostSell.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblMostSell.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblMostSell);

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(51, 153, 255));
        jLabel46.setText("From Date:");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(myButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(myButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel40))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(txtDate5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(txtDate6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8)
                            .addComponent(myButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(myButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel46))
                                .addGap(5, 5, 5)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        tabs.addTab("Most Selling Product", jPanel8);

        myButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/business.png"))); // NOI18N
        myButton6.setText("All Product Full Detail's");
        myButton6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        myButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton6ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 153, 255));
        jLabel26.setText("PID:");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtPara_PID.setForeground(new java.awt.Color(51, 153, 255));
        txtPara_PID.setToolTipText("");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 153, 255));
        jLabel41.setText("Product Name:");
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtParaProName.setForeground(new java.awt.Color(51, 153, 255));
        txtParaProName.setToolTipText("");

        txtParaSupID.setForeground(new java.awt.Color(51, 153, 255));
        txtParaSupID.setToolTipText("");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(51, 153, 255));
        jLabel42.setText("Supplier ID:");
        jLabel42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/search x30.png"))); // NOI18N
        myButton5.setText("View Report");
        myButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 153, 255));
        jLabel43.setText("Category ID:");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtParaCateID.setForeground(new java.awt.Color(51, 153, 255));
        txtParaCateID.setToolTipText("");

        txtParaExpDate.setForeground(new java.awt.Color(51, 153, 255));
        txtParaExpDate.setToolTipText("");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 153, 255));
        jLabel44.setText("Exp Date:");
        jLabel44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jLabel44))
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(myButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(txtParaExpDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtParaSupID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtParaCateID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtParaProName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPara_PID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtPara_PID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtParaProName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtParaSupID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtParaCateID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtParaExpDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(637, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        tabs.addTab("Product Report's", jPanel9);

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

        myButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton15.setText("Save To Excel");

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Product Name:");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        textFieldSuggestion6.setForeground(new java.awt.Color(51, 153, 255));
        textFieldSuggestion6.setToolTipText("");
        textFieldSuggestion6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldSuggestion6KeyReleased(evt);
            }
        });

        textFieldSuggestion7.setForeground(new java.awt.Color(51, 153, 255));
        textFieldSuggestion7.setToolTipText("");
        textFieldSuggestion7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldSuggestion7KeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 255));
        jLabel15.setText("Category:");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/update.png"))); // NOI18N
        myButton4.setText("Return");
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldSuggestion6, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldSuggestion7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldSuggestion7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldSuggestion6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblProduct2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null}
            },
            new String [] {
                "proID", "Barcode", "proName", "categoryName", "unitPrice", "salesPrice", "proQuantity", "proDefaultType", "proExpDate"
            }
        ));
        tblProduct2.setFocusable(false);
        tblProduct2.setRowHeight(40);
        tblProduct2.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblProduct2.getTableHeader().setReorderingAllowed(false);
        tblProduct2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProduct2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblProduct2);

        tblProduct3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "proID", "Barcode", "proName", "categoryName", "unitPrice", "salesPrice", "proQuantity", "proDefaultType", "proDescription", "proMFDate", "proExpDate", "proCompanyName", "proBrandName", "proPrivateNote", "proSupplierID", "UserID"
            }
        ));
        tblProduct3.setFocusable(false);
        tblProduct3.setRowHeight(40);
        tblProduct3.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblProduct3.getTableHeader().setReorderingAllowed(false);
        tblProduct3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProduct3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProduct3MousePressed(evt);
            }
        });
        tblProduct3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProduct3KeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(tblProduct3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(413, 413, 413)
                        .addComponent(myButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed

    }//GEN-LAST:event_txt1ActionPerformed

    private void txt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt2ActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        try {
            updateProduct();
        } catch (ParseException ex) {
            Logger.getLogger(Product_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton2ActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
        deleteProduct();
    }//GEN-LAST:event_myButton3ActionPerformed

    private void myButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton9ActionPerformed
        try {
            updateCategory();
        } catch (ParseException ex) {
            Logger.getLogger(Product_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton9ActionPerformed

    private void myButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton10ActionPerformed
        deleteCategory();
    }//GEN-LAST:event_myButton10ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dateChooser.showPopup();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        dateChooser1.showPopup();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void myButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton8ActionPerformed
        try {
            insertCategory();
        } catch (ParseException ex) {
            Logger.getLogger(Product_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton8ActionPerformed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
        String supName = "";
        
            String supID = "";

            String sup_contact_email = "";
            String sup_number = "";
            String product_name = proName.getText();
            supName = cboSupplierName.getSelectedItem().toString();
            System.out.println(supName);
        try {
            insertProduct();
            try {
                String sql = "Select supplier_id,sup_contact_email,sup_number from suppliers Where sup_name = N'"+supName+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    supID = rs.getString("supplier_id");
                    sup_contact_email = rs.getString("sup_contact_email");
                    sup_number = rs.getString("sup_number");
                }
                rs.close();
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                String sql = "INSERT INTO detailSup(supplier_id,supplier_name,supplier_number,sup_contact_email,product_name) values ('"+supID+"','"+supName+"','"+sup_number+"','"+sup_contact_email+"','"+product_name+"')";
                Statement ss = db.myCon().createStatement();
                ss.executeUpdate(sql);

                
                
                
            } catch (Exception e) {
            }
        } catch (ParseException ex) {
            Logger.getLogger(Product_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton1ActionPerformed

    private void txt2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt2KeyReleased
        String search = txt2.getText();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM products WHERE product_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblProID.setText(rs.getString("product_id"));
                            proBarcode.setText(rs.getString("barcode"));
                            proName.setText(rs.getString("pro_name"));
                            String cateID = "";
                            String cateName = "";
                            try {
                                cateID = rs.getString("category_id");
                                String sql = "Select cate_name from categories where category_id = '"+cateID+"'";
                                Statement ss = db.myCon().createStatement();
                                ResultSet rss = ss.executeQuery(sql);
                                if(rss.next()){
                                    cateName = rss.getString("cate_name");
                                }
                                rss.close();
                                ss.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            cboCateName.setSelectedItem(cateName);
                            proUnitPrice.setText(rs.getString("unit_price"));
                            proSalesPrice.setText(rs.getString("sales_price"));
                            proQuantity.setText(rs.getString("pro_quantity"));
                            proDefaultType.setText(rs.getString("pro_default_type"));
                            proDescription.setText(rs.getString("pro_description"));
                            txtDate2.setText(String.valueOf(rs.getDate("pro_mf_date")));
                            txtDate1.setText(String.valueOf(rs.getString("pro_exp_date")));
                            String supID = "";
                            String supName = "";
                            try {
                                supID = rs.getString("supplier_id");
                                String sql = "Select sup_name from suppliers where supplier_id = '"+supID+"'";
                                Statement ss = db.myCon().createStatement();
                                ResultSet rss = ss.executeQuery(sql);
                                if(rss.next()){
                                    supName = rss.getString("sup_name");
                                }
                                rss.close();
                                ss.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            cboSupplierName.setSelectedItem(supName);
                            proCompanyName.setText(rs.getString("pro_company_name"));
                            proBrandName.setText(rs.getString("pro_brand_name"));
                            proPrivateNote.setText(rs.getString("pro_private_note"));

                            
                        }
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
    }//GEN-LAST:event_txt2KeyReleased

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        dateChooser3.showPopup();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        dateChooser2.showPopup();
    }//GEN-LAST:event_jButton7ActionPerformed
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
         DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
    private void myButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton13ActionPerformed
                     try {
        Date date1 = inputFormat.parse(txtDate5.getText());
        Date date2 = inputFormat.parse(txtDate6.getText());
        String convertFromDate = outputFormat.format(date1);
        String convertToDate = outputFormat.format(date2);
                System.out.println(convertToDate);
                System.out.println(convertFromDate);
        try {
            DefaultTableModel dt = (DefaultTableModel) tblMostSell.getModel();
            dt.setRowCount(0);
          
                String sql = "SELECT product_name,sum(quantity) as totalQuantity,sum(sales_sub_total) as totalSales"
                        + " from sales inner join invoices\n" +
"on sales.invoice_id = invoices.invoice_id WHERE invoices.invoice_date >= '"+convertFromDate+"' AND invoices.invoice_date <='"+convertToDate+"' "
                        + "group by product_name order by totalQuantity desc";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString(1));
                    v.add(rs.getString(2));
                    v.add(rs.getString(3));
                    dt.addRow(v);           
                
            }
                s.close();
                rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    } catch (ParseException ex) {
        Logger.getLogger(Invoice_Form.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_myButton13ActionPerformed

    private void myButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton12ActionPerformed
       Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/MostSellingProduct.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, db.myCon());
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            });
            t.start();        
    }//GEN-LAST:event_myButton12ActionPerformed

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
       Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/AllProducts.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, db.myCon());
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
        String convertFromDate = "";
        try {
            Date date1 = inputFormat.parse(txtParaExpDate.getText());
            convertFromDate = outputFormat.format(date1);
        } catch (Exception e) {
        }
        
        String paraProID = "%" + txtPara_PID.getText() + "%";
        String paraProName = "%" + txtParaProName.getText() + "%";
        String paraSupID = "%" + txtParaSupID.getText() + "%";
        String paraCateID = "%" + txtParaCateID.getText() + "%";
        String paraDateExp = "%" + convertFromDate + "%";

        // Xử lý giá trị rỗng cho các tham số không cung cấp giá trị
        if (paraProID.isEmpty()) {
            paraProID = "%%";
        }
        if (paraProName.isEmpty()) {
            paraProName = "%%";
        }
        if (paraSupID.isEmpty()) {
            paraSupID = "%%";
        }
        if (paraCateID.isEmpty()) {
            paraCateID = "%%";
        }
        if (paraDateExp.isEmpty()) {
            paraDateExp = "%%";
        }
        para.put("Para_id", paraProID);
        para.put("Parapro_name", paraProName);
        para.put("Parasup_id", paraSupID);
        para.put("Paracate_id", paraCateID);
        para.put("Para_date", paraDateExp);

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/AllProduct.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, para, db.myCon());
                    JasperViewer.viewReport(jasperPrint, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }//GEN-LAST:event_myButton5ActionPerformed

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
       
            int row = tblProduct.getSelectedRow();
            String proID = tblProduct.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblProduct1.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from products where product_id = '"+proID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("product_id"));
                    v.add(rs.getString("barcode"));
                    v.add(rs.getString("pro_name"));
                    v.add(rs.getString("category_id"));
                    v.add(rs.getString("unit_price"));
                    v.add(rs.getString("sales_price"));
                    v.add(rs.getString("pro_quantity"));
                    v.add(rs.getString("pro_default_type"));
                    v.add(rs.getString("pro_description"));
                    v.add(rs.getString("pro_mf_date"));
                    v.add(rs.getString("pro_exp_date"));
                    v.add(rs.getString("pro_company_name"));
                    v.add(rs.getString("pro_brand_name"));
                    v.add(rs.getString("pro_private_note"));
                    v.add(rs.getString("supplier_id"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
        
    }//GEN-LAST:event_tblProductMouseClicked

    private void tblProduct1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProduct1KeyPressed
       
    }//GEN-LAST:event_tblProduct1KeyPressed

    private void tblProduct1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProduct1MousePressed
       if(evt.getClickCount()==2){
                    String search = tblProduct1.getValueAt(tblProduct1.getSelectedRow(), 0).toString();
                     
                    try{
                        Statement s = db.myCon().createStatement();
                        ResultSet rs = s.executeQuery("SELECT * FROM products WHERE product_id = '"+search+"'");

                        if(rs.next()){
                           
                            lblProID.setText(rs.getString("product_id"));
                            proBarcode.setText(rs.getString("barcode"));
                            proName.setText(rs.getString("pro_name"));
                            String cateID = "";
                            String cateName = "";
                            try {
                                cateID = rs.getString("category_id");
                                String sql = "Select cate_name from categories where category_id = '"+cateID+"'";
                                Statement ss = db.myCon().createStatement();
                                ResultSet rss = ss.executeQuery(sql);
                                if(rss.next()){
                                    cateName = rss.getString("cate_name");
                                }
                                rss.close();
                                ss.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            cboCateName.setSelectedItem(cateName);
                            proUnitPrice.setText(rs.getString("unit_price"));
                            proSalesPrice.setText(rs.getString("sales_price"));
                            proQuantity.setText(rs.getString("pro_quantity"));
                            proDefaultType.setText(rs.getString("pro_default_type"));
                            proDescription.setText(rs.getString("pro_description"));
                            txtDate2.setText(String.valueOf(rs.getDate("pro_mf_date")));
                            txtDate1.setText(String.valueOf(rs.getString("pro_exp_date")));
                            String supID = "";
                            String supName = "";
                            try {
                                supID = rs.getString("supplier_id");
                                String sql = "Select sup_name from suppliers where supplier_id = '"+supID+"'";
                                Statement ss = db.myCon().createStatement();
                                ResultSet rss = ss.executeQuery(sql);
                                if(rss.next()){
                                    supName = rss.getString("sup_name");
                                }
                                rss.close();
                                ss.close();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            cboSupplierName.setSelectedItem(supName);
                            proCompanyName.setText(rs.getString("pro_company_name"));
                            proBrandName.setText(rs.getString("pro_brand_name"));
                            proPrivateNote.setText(rs.getString("pro_private_note"));

                            
                        }
                        tabs.setSelectedIndex(1);
                        s.close();
                        rs.close();
                            db.myCon().close();
                            }catch(Exception e){
                                System.out.println(e);
                            }
        }
    }//GEN-LAST:event_tblProduct1MousePressed

    private void txt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt3ActionPerformed

    private void tblProduct2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProduct2MouseClicked
               
            int row = tblProduct2.getSelectedRow();
            String proID = tblProduct2.getValueAt(row, 0).toString();
            System.out.println(proID);
            DefaultTableModel model = (DefaultTableModel) tblProduct3.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from products where product_id = '"+proID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("product_id"));
                    v.add(rs.getString("barcode"));
                    v.add(rs.getString("pro_name"));
                    v.add(rs.getString("category_id"));
                    v.add(rs.getString("unit_price"));
                    v.add(rs.getString("sales_price"));
                    v.add(rs.getString("pro_quantity"));
                    v.add(rs.getString("pro_default_type"));
                    v.add(rs.getString("pro_description"));
                    v.add(rs.getString("pro_mf_date"));
                    v.add(rs.getString("pro_exp_date"));
                    v.add(rs.getString("pro_company_name"));
                    v.add(rs.getString("pro_brand_name"));
                    v.add(rs.getString("pro_private_note"));
                    v.add(rs.getString("supplier_id"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }        
    }//GEN-LAST:event_tblProduct2MouseClicked

    private void tblProduct3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProduct3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProduct3MousePressed

    private void tblProduct3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProduct3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProduct3KeyPressed

    private void tblProduct3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProduct3MouseClicked

    }//GEN-LAST:event_tblProduct3MouseClicked

    private void txt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1KeyReleased
        String name = txt1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblProduct.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM products WHERE pro_name LIKE '%"+name+"%'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("product_id"));
                v.add(rs.getString("barcode"));
                v.add(rs.getString("pro_name"));
                String cate_id = rs.getString("category_id");
                String cate_name = "";
                try {
                    String sqll = "Select cate_name from categories where category_id = '"+cate_id+"'";
                    Statement ss = db.myCon().createStatement();
                    ResultSet rss = ss.executeQuery(sqll);
                    if(rss.next()){
                        cate_name = rss.getString("cate_name");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                v.add(cate_name);
                v.add(rs.getString("unit_price"));
                v.add(rs.getString("sales_price")); 
                v.add(rs.getString("pro_quantity")); 
                v.add(rs.getString("pro_default_type"));
                v.add(rs.getString("pro_exp_date")); 
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_txt1KeyReleased

    private void txt3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt3KeyReleased
        String name = txt3.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblProduct2.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM products WHERE pro_name LIKE '%"+name+"%' and status 'Inactive'");   
            while(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("product_id"));
                v.add(rs.getString("barcode"));
                v.add(rs.getString("pro_name"));
                String cate_id = rs.getString("category_id");
                String cate_name = "";
                try {
                    String sqll = "Select cate_name from categories where category_id = '"+cate_id+"'";
                    Statement ss = db.myCon().createStatement();
                    ResultSet rss = ss.executeQuery(sqll);
                    if(rss.next()){
                        cate_name = rss.getString("cate_name");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                v.add(cate_name);
                v.add(rs.getString("unit_price"));
                v.add(rs.getString("sales_price")); 
                v.add(rs.getString("pro_quantity")); 
                v.add(rs.getString("pro_default_type"));
                v.add(rs.getString("pro_exp_date")); 
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             
        }
    }//GEN-LAST:event_txt3KeyReleased

    private void textFieldSuggestion6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldSuggestion6KeyReleased
        search1();
    }//GEN-LAST:event_textFieldSuggestion6KeyReleased

    private void textFieldSuggestion7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldSuggestion7KeyReleased
        search1();
    }//GEN-LAST:event_textFieldSuggestion7KeyReleased

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        ReturnProduct();
    }//GEN-LAST:event_myButton4ActionPerformed

    private void cboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoryActionPerformed
        

    }//GEN-LAST:event_cboCategoryActionPerformed

    private void cboProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProductActionPerformed

    private void myButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton16ActionPerformed
        search();

    }//GEN-LAST:event_myButton16ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private textfield_suggestion.TextFieldSuggestion cateName;
    private textfield_suggestion.TextFieldSuggestion cateOtherInfo;
    private combo_suggestion.ComboBoxSuggestion cboCateName;
    private javax.swing.JComboBox<String> cboCategory;
    private javax.swing.JComboBox<String> cboProduct;
    private combo_suggestion.ComboBoxSuggestion cboSupplierName;
    private com.raven.datechooser.DateChooser dateChooser;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private com.raven.datechooser.DateChooser dateChooser3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblCateID;
    private javax.swing.JLabel lblProID;
    private javax.swing.JLabel lblUserID;
    private button.MyButton myButton1;
    private button.MyButton myButton10;
    private button.MyButton myButton11;
    private button.MyButton myButton12;
    private button.MyButton myButton13;
    private button.MyButton myButton14;
    private button.MyButton myButton15;
    private button.MyButton myButton16;
    private button.MyButton myButton2;
    private button.MyButton myButton3;
    private button.MyButton myButton4;
    private button.MyButton myButton5;
    private button.MyButton myButton6;
    private button.MyButton myButton7;
    private button.MyButton myButton8;
    private button.MyButton myButton9;
    private textfield_suggestion.TextFieldSuggestion proBarcode;
    private textfield_suggestion.TextFieldSuggestion proBrandName;
    private textfield_suggestion.TextFieldSuggestion proCompanyName;
    private textfield_suggestion.TextFieldSuggestion proDefaultType;
    private textfield_suggestion.TextFieldSuggestion proDescription;
    private textfield_suggestion.TextFieldSuggestion proName;
    private textfield_suggestion.TextFieldSuggestion proPrivateNote;
    private textfield_suggestion.TextFieldSuggestion proQuantity;
    private textfield_suggestion.TextFieldSuggestion proSalesPrice;
    private textfield_suggestion.TextFieldSuggestion proUnitPrice;
    private raven.tabbed.TabbedPaneCustom tabs;
    private javax.swing.JTable tblCategory;
    private javax.swing.JTable tblMostSell;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblProduct1;
    private javax.swing.JTable tblProduct2;
    private javax.swing.JTable tblProduct3;
    private textfield_suggestion.TextFieldSuggestion textFieldSuggestion6;
    private textfield_suggestion.TextFieldSuggestion textFieldSuggestion7;
    private searchwing.TextFieldAnimation txt1;
    private searchwing.TextFieldAnimation txt2;
    private searchwing.TextFieldAnimation txt3;
    private javax.swing.JTextField txtDate1;
    private javax.swing.JTextField txtDate2;
    private javax.swing.JTextField txtDate5;
    private javax.swing.JTextField txtDate6;
    private textfield_suggestion.TextFieldSuggestion txtParaCateID;
    private textfield_suggestion.TextFieldSuggestion txtParaExpDate;
    private textfield_suggestion.TextFieldSuggestion txtParaProName;
    private textfield_suggestion.TextFieldSuggestion txtParaSupID;
    private textfield_suggestion.TextFieldSuggestion txtPara_PID;
    // End of variables declaration//GEN-END:variables
}
