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
import com.raven.dao.invoiceDAO;
import com.raven.dao.returnCusDAO;
import com.raven.dao.returnSupDAO;
import com.raven.entity.customer;
import com.raven.entity.product;
import com.raven.entity.returnCus;
import com.raven.entity.returnSup;
import com.raven.entity.users;
import com.raven.event.EventColorChange;
import com.raven.menu.EventMenu;
import com.raven.properties.SystemProperties;
import com.raven.theme.SystemTheme;
import com.raven.theme.ThemeColor;
import com.raven.theme.ThemeColorChange;
import java.awt.Color;
import com.raven.form.Setting_Form;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
public class Return_Form extends Form {
    private Setting_Form settingForm;
    returnCusDAO reCusDAO = new returnCusDAO();
    returnSupDAO reSupDAO = new returnSupDAO();
    customerDAO cusDAO = new customerDAO();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private users user;
    public void setUser(users user1){
        user = user1;
    }
    /**
     * Creates new form Sales_Form
     */
    public Return_Form(users user) {
        initComponents();
        init();
        tblReturnCus.getTableHeader().setForeground(new Color(255,255,255));  
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(51, 153, 255));
        headerRenderer.setForeground(new Color(255, 255, 255));
        tblReturnCus.getTableHeader().setDefaultRenderer(headerRenderer);
        this.user = user;
        txtUserID1.setText(user.getUserID());
        txtUserID.setText(user.getUserID());
    }
    
    public void init(){
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
       
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        
        jPanel6.setBackground(new Color(0, 0, 0, 0));
        jPanel6.setOpaque(false);
        jPanel7.setBackground(new Color(0, 0, 0, 0));
        jPanel7.setOpaque(false);
        jPanel8.setOpaque(false);
        jPanel8.setBackground(new Color(0, 0, 0, 0));
        tabbedPaneCustom1.setBackground(new Color(0, 0, 0, 0));
        tabbedPaneCustom1.setOpaque(false);
        searchAnimate();
        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setOpaque(false);
        loadTableReturnCus();
        loadTableReturnSup();
        
    }
    
    public void loadTableReturnCus(){
        DefaultTableModel model = (DefaultTableModel) tblReturnCus.getModel();
        model.setRowCount(0);
        try {

            List<returnCus>list =  reCusDAO.selectAll();
      
            for(returnCus nh: list){
                Object[] row = {          
                    nh.getReturnCus_id(),
                    nh.getInvoice_id(),
                    nh.getProduct_name(),                   
                    nh.getQuantity(),                   
                    nh.getUnit_price(),
                    nh.getAmount(),
                    nh.getCustomer_name(),
                    nh.getDescription(),
                    nh.getReturnCus_date(),
                    nh.getUserID(),
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    public void loadProductReturnCus(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboProCus.getModel();
        model.removeAllElements();
        try {
            int invoice_id = Integer.parseInt(txtINID.getText());
            String sql = "select * from sales where invoice_id = '"+invoice_id+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                model.addElement(rs.getString("product_name"));
                try {
                    String sqll = "select * from invoices where invoice_id = '" + rs.getString("invoice_id") + "'";
                    Statement ss = db.myCon().createStatement();
                    ResultSet rss = ss.executeQuery(sqll);
                    if (rss.next()) {
                        txtCus.setText(rss.getString("customer_name"));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void loadProductReturnSup(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboProSup.getModel();
        model.removeAllElements();
        try {
            int invoice_id = Integer.parseInt(txtPOID.getText());
            String sql = "select * from salesPO where po_id = '"+invoice_id+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                model.addElement(rs.getString("product_name"));
                try {
                    String sqll = "select * from PO where purchase_order_id = '" + rs.getString("po_id") + "'";
                    Statement ss = db.myCon().createStatement();
                    ResultSet rss = ss.executeQuery(sqll);
                    if (rss.next()) {
                        try {
                            String sqlll = "Select * from suppliers where supplier_id = '"+rss.getString("supplier_id")+"'";
                            Statement sss = db.myCon().createStatement();
                            ResultSet rsss = sss.executeQuery(sqlll);
                            if(rsss.next()){
                                txtSup.setText(rsss.getString("sup_name"));
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                } catch (Exception e) {
                    System.out.println("sai1");
                    System.out.println(e);
                }
            }

        } catch (Exception e) {
            System.out.println("sai2");
            System.out.println(e);
        }
    }
    
    public void loadTableReturnSup(){
        DefaultTableModel model = (DefaultTableModel) tblReturnSup.getModel();
        model.setRowCount(0);
        try {

            List<returnSup>list =  reSupDAO.selectAll();
      
            for(returnSup nh: list){
                Object[] row = {          
                    nh.getReturnSup_id(),
                    nh.getPo_id(),
                    nh.getProduct_name(),                   
                    nh.getQuantity(),                   
                    nh.getUnit_price(),
                    nh.getAmount(),
                    nh.getSupplier_name(),
                    nh.getDescription(),
                    nh.getReturnSup_date(),
                    nh.getUserID(),
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    public void searchAnimate(){
        txt1.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                //  Test
                try {
                    for (int i = 1; i <= 100; i++) {

                        Thread.sleep(10);                    }
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
    
    public void totalAmount(){

        float unitPrice = Float.parseFloat(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        float totalAmount = unitPrice * qty;
        txtAmount.setText(String.valueOf(totalAmount));       
    }
    
    public void totalAmount1(){
   
        float unitPrice = Float.parseFloat(txtUnitPrice1.getText());
        int qty = Integer.parseInt(txtQty1.getText());
        float totalAmount = unitPrice * qty;
        txtAmount1.setText(String.valueOf(totalAmount));       
    }
    
    Data data = new Data();
    returnCus getModelCus() throws ParseException{
        returnCus model = new returnCus();
       
        String invID = txtINID.getText();
        if(data.ContainNumberOnly(invID)==false){
            model.setInvoice_id(0);
        }else{
            if(invID.isEmpty()){
                model.setInvoice_id(0);
            }else{
                model.setInvoice_id(Integer.parseInt(invID));
            }
        }
        model.setProduct_name(cboProCus.getSelectedItem().toString());
        String quantity = txtQty.getText();
        if(data.ContainNumberOnly(quantity)==false){
            model.setQuantity(0);
        }else{
            if(quantity.isEmpty()){
                model.setQuantity(0);
            }else{
                model.setQuantity(Integer.parseInt(quantity));
            }
        }
        String unitprice = txtUnitPrice.getText();
        if(data.ContainNumberOnly(unitprice)==false){
            model.setUnit_price(0);
        }else{
            if(unitprice.isEmpty()){
                model.setUnit_price(0);
            }else{
                model.setUnit_price(Integer.parseInt(unitprice));
            }
        }
        String amount = txtAmount.getText();
        if(data.ContainNumberOnly(amount)==false){
            model.setAmount(0);
        }else{
            if(amount.isEmpty()){
                model.setAmount(0);
            }else{
                model.setAmount(Integer.parseInt(amount));
            }
        }       
        model.setCustomer_name(txtCus.getText());
        model.setDescription(txtDes.getText());

        model.setReturnCus_date(format.parse(txtDate.getText()));
        model.setUserID(txtUserID.getText());
        return model;
    }
    
    returnSup getModelSup() throws ParseException{
        returnSup model = new returnSup();
       
        String POID = txtPOID.getText();
        if(data.ContainNumberOnly(POID)==false){
            model.setPo_id(0);
        }else{
            if(POID.isEmpty()){
                model.setPo_id(0);
            }else{
                model.setPo_id(Integer.parseInt(POID));
            }
        }
        System.out.println(cboProSup.getSelectedItem().toString());
        model.setProduct_name(cboProSup.getSelectedItem().toString());
        String quantity = txtQty1.getText();
        if(data.ContainNumberOnly(quantity)==false){
            model.setQuantity(0);
        }else{
            if(quantity.isEmpty()){
                model.setQuantity(0);
            }else{
                model.setQuantity(Integer.parseInt(quantity));
            }
        }
        String unitprice = txtUnitPrice1.getText();
        if(data.ContainNumberOnly(unitprice)==false){
            model.setUnit_price(0);
        }else{
            if(unitprice.isEmpty()){
                model.setUnit_price(0);
            }else{
                model.setUnit_price(Integer.parseInt(unitprice));
            }
        }
        String amount = txtAmount1.getText();
        if(data.ContainNumberOnly(amount)==false){
            model.setAmount(0);
        }else{
            if(amount.isEmpty()){
                model.setAmount(0);
            }else{
                model.setAmount(Integer.parseInt(amount));
            }
        }       
        model.setSupplier_name(txtSup.getText());
        model.setDescription(txtDes1.getText());

        model.setReturnSup_date(format.parse(txtDate1.getText()));
        model.setUserID(txtUserID1.getText());
        return model;
    }
    
    
    
    void insertReturnCus() throws ParseException{
        returnCus model = getModelCus();
        txtINID.setBorder(Color.WHITE);
        txtQty.setBorder(Color.WHITE);    
        txtUnitPrice.setBorder(Color.WHITE);
        txtAmount.setBorder(Color.WHITE);
        txtCus.setBorder(Color.WHITE);
        txtDes.setBorder(Color.WHITE);
        txtDate.setBackground(Color.WHITE);
        if( txtINID.getText().length()==0 ||txtQty.getText().length()==0 ||txtUnitPrice.getText().length()==0 ||txtAmount.getText().length()==0
                ||txtCus.getText().length()==0 ||txtDes.getText().length()==0 ||txtDate.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            
            if(txtINID.getText().length()==0){
                txtINID.setBorder(Color.red);
            }
            if(txtQty.getText().length()==0){
                txtQty.setBorder(Color.red);
            }
            if(txtUnitPrice.getText().length()==0){
                txtUnitPrice.setBorder(Color.red);
            }
            if(txtAmount.getText().length()==0){
                txtAmount.setBorder(Color.red);
            }
            if(txtCus.getText().length()==0){
                txtCus.setBorder(Color.red);
            }
            if(txtDes.getText().length()==0){
                txtDes.setBorder(Color.red);
            }
            if(txtDate.getText().length()==0){
                txtDate.setBackground(Color.red);
            }
        }else{
            boolean check = true;
            do{                                      
                if(data.ContainNumberOnly(txtINID.getText())==false){
                    MsgBox.alert(this, "INID không đúng định dạng");
                    txtINID.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(txtQty.getText())==false){
                    MsgBox.alert(this, "Số lượng không đúng định dạng");
                    txtQty.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }
                
                if(check==true){
                    try {
                        addStock();
                        removeSales();
                        setPayInvoice();
                        reCusDAO.insert(model);
                        loadTableReturnCus();
                        MsgBox.alert(this, "Thêm thành công");
                    } catch (Exception e) {
                        System.out.println(model.getProduct_name());
                        MsgBox.alert(this, "Thêm thất bại");
                        System.out.println(e);
                    }                    
                    break;
                }
            }while(check==true);
        }

    }
    
    void insertReturnSup() throws ParseException{
        returnSup model = getModelSup();
        System.out.println(model.getProduct_name());
        txtPOID.setBorder(Color.WHITE);
        txtQty1.setBorder(Color.WHITE);    
        txtUnitPrice1.setBorder(Color.WHITE);
        txtAmount1.setBorder(Color.WHITE);
        txtSup.setBorder(Color.WHITE);
        txtDes1.setBorder(Color.WHITE);
        txtDate1.setBackground(Color.WHITE);
        if( txtPOID.getText().length()==0 ||txtQty1.getText().length()==0 ||txtUnitPrice1.getText().length()==0 ||txtAmount1.getText().length()==0
                ||txtSup.getText().length()==0 ||txtDes1.getText().length()==0 ||txtDate1.getText().length()==0){
            MsgBox.alert(this, "Không được bỏ trống");
            
            if(txtPOID.getText().length()==0){
                txtPOID.setBorder(Color.red);
            }
            if(txtQty1.getText().length()==0){
                txtQty1.setBorder(Color.red);
            }
            if(txtUnitPrice1.getText().length()==0){
                txtUnitPrice1.setBorder(Color.red);
            }
            if(txtAmount1.getText().length()==0){
                txtAmount1.setBorder(Color.red);
            }
            if(txtSup.getText().length()==0){
                txtSup.setBorder(Color.red);
            }
            if(txtDes1.getText().length()==0){
                txtDes1.setBorder(Color.red);
            }
            if(txtDate1.getText().length()==0){
                txtDate1.setBackground(Color.red);
            }
        }else{
            boolean check = true;
            do{                                      
                if(data.ContainNumberOnly(txtPOID.getText())==false){
                    MsgBox.alert(this, "INID không đúng định dạng");
                    txtPOID.setBorder(Color.red);
                    check = false;
                }else if(data.ContainNumberOnly(txtQty1.getText())==false){
                    MsgBox.alert(this, "Số lượng không đúng định dạng");
                    txtQty1.setBorder(Color.red);
                    check = false;
                }else{
                    check = true;
                }
                
                if(check==true){
                    try {
                        removeStock();
                        removeSales1();
                        setPayPO1();
                        reSupDAO.insert(model);
                        loadTableReturnCus();
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
    
    public void addStock(){
        int qtyProCus = Integer.parseInt(txtQty.getText());
        String product = cboProCus.getSelectedItem().toString();
        try {
            String sql = "update products set pro_quantity = pro_quantity + '"+qtyProCus+"' WHERE pro_name = N'"+product+"'";
            Statement s = db.myCon().createStatement();
            s.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("sai3");
            System.out.println(e);
        }
        
    }
    
    public void removeStock(){
        int qtyProSup = Integer.parseInt(txtQty1.getText());
        String product = cboProSup.getSelectedItem().toString();
        try {
            String sql = "update products set pro_quantity = pro_quantity - '"+qtyProSup+"' WHERE pro_name = N'"+product+"'";
            Statement s = db.myCon().createStatement();
            s.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("sai4");
            System.out.println(e);
        }
        
    }
    
    public void removeSales(){
        int qtyProCus = Integer.parseInt(txtQty.getText());
        int inID = Integer.parseInt(txtINID.getText());
        String product = cboProCus.getSelectedItem().toString();
        try {
            String sql = "Select * from sales where product_name = N'"+product+"' and invoice_id = '"+inID+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                if(qtyProCus==rs.getInt("quantity")){
                    try {
                        String sqll = "Delete from sales where product_name = N'"+product+"' and invoice_id = '"+inID+"'";
                        Statement ss = db.myCon().createStatement();
                        ss.executeUpdate(sqll);
                    } catch (Exception e) {
                        System.out.println("sai1");
                        System.out.println(e);
                    }
                }else if(qtyProCus<rs.getInt("quantity")){
                    try {
                        String sqll = "UPDATE sales SET quantity = quantity - '"+qtyProCus+"' where product_name = N'"+product+"' and invoice_id = '"+inID+"'";
                        Statement ss = db.myCon().createStatement();
                        ss.executeUpdate(sqll);
                    } catch (Exception e) {
                        System.out.println("sai2");
                        System.out.println(e);
                    }
                }
                    
            }
        } catch (Exception e) {
            System.out.println("sai3");
            System.out.println(e);
        }
    }
    
    public void removeSales1(){
        int qtyProSup = Integer.parseInt(txtQty1.getText());
        int poID = Integer.parseInt(txtPOID.getText());
        String product = cboProSup.getSelectedItem().toString();
        try {
            String sql = "Select * from salesPO where product_name = N'"+product+"' and po_id = '"+poID+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                if(qtyProSup==rs.getInt("quantity")){
                    try {
                        String sqll = "Delete from salesPO where product_name = N'"+product+"' and po_id = '"+poID+"'";
                        Statement ss = db.myCon().createStatement();
                        ss.executeUpdate(sqll);
                    } catch (Exception e) {
                        System.out.println("sai1");
                        System.out.println(e);
                    }
                }else if(qtyProSup<rs.getInt("quantity")){
                    try {
                        String sqll = "UPDATE salesPO SET quantity = quantity - '"+qtyProSup+"' where product_name = N'"+product+"' and po_id = '"+poID+"'";
                        Statement ss = db.myCon().createStatement();
                        ss.executeUpdate(sqll);
                    } catch (Exception e) {
                        System.out.println("sai2");
                        System.out.println(e);
                    }
                }
                    
            }
        } catch (Exception e) {
            System.out.println("sai3");
            System.out.println(e);
        }
    }
    
    public void setPayInvoice(){
        int inID = Integer.parseInt(txtINID.getText());
        float amount = Float.parseFloat(txtAmount.getText());
        int quantity = Integer.parseInt(txtQty.getText());
        try {
            String sql = "UPDATE invoices SET invoice_paid_amount = invoice_paid_amount - '"+amount+"',invoice_total_amount = invoice_total_amount - '"+amount+"',invoice_quantity = invoice_quantity - '"+quantity+"' where invoice_id = '"+inID+"'";
            Statement s = db.myCon().createStatement();
            s.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("sai4");
            System.out.println(e);
        }
    }
    
    public void setPayPO1(){
        int poID = Integer.parseInt(txtPOID.getText());
        float amount = Float.parseFloat(txtAmount1.getText());
        int quantity = Integer.parseInt(txtQty1.getText());
        try {
            String sql = "UPDATE PO SET purchase_paid_amount = purchase_paid_amount - '"+amount+"',purchase_total_amount = purchase_total_amount - '"+amount+"',purchase_quantity = purchase_quantity - '"+quantity+"' where purchase_order_id = '"+poID+"'";
            Statement s = db.myCon().createStatement();
            s.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("sai4");
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

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        tabbedPaneCustom1 = new raven.tabbed.TabbedPaneCustom();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtINID = new textfield_suggestion.TextFieldSuggestion();
        txtQty = new textfield_suggestion.TextFieldSuggestion();
        jLabel4 = new javax.swing.JLabel();
        txtUnitPrice = new textfield_suggestion.TextFieldSuggestion();
        jLabel5 = new javax.swing.JLabel();
        txtAmount = new textfield_suggestion.TextFieldSuggestion();
        jLabel6 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtCus = new textfield_suggestion.TextFieldSuggestion();
        txtDes = new textfield_suggestion.TextFieldSuggestion();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        txtDate = new javax.swing.JTextField();
        cboProCus = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtUserID = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReturnCus = new javax.swing.JTable();
        myButton7 = new button.MyButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt1 = new searchwing.TextFieldAnimation();
        jPanel6 = new javax.swing.JPanel();
        myButton5 = new button.MyButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txt3 = new searchwing.TextFieldAnimation();
        jLabel34 = new javax.swing.JLabel();
        myButton9 = new button.MyButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        txtPOID = new textfield_suggestion.TextFieldSuggestion();
        txtQty1 = new textfield_suggestion.TextFieldSuggestion();
        jLabel38 = new javax.swing.JLabel();
        txtUnitPrice1 = new textfield_suggestion.TextFieldSuggestion();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtSup = new textfield_suggestion.TextFieldSuggestion();
        jLabel42 = new javax.swing.JLabel();
        txtDes1 = new textfield_suggestion.TextFieldSuggestion();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        txtDate1 = new javax.swing.JTextField();
        cboProSup = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        txtUserID1 = new javax.swing.JLabel();
        txtAmount1 = new textfield_suggestion.TextFieldSuggestion();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        myButton10 = new button.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReturnSup = new javax.swing.JTable();

        dateChooser1.setTextRefernce(txtDate);

        dateChooser2.setTextRefernce(txtDate1);

        tabbedPaneCustom1.setSelectedColor(new java.awt.Color(51, 153, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("INID:");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtINID.setForeground(new java.awt.Color(51, 153, 255));
        txtINID.setToolTipText("");
        txtINID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtINIDActionPerformed(evt);
            }
        });
        txtINID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtINIDKeyReleased(evt);
            }
        });

        txtQty.setForeground(new java.awt.Color(51, 153, 255));
        txtQty.setToolTipText("");
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("QTY:");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtUnitPrice.setEditable(false);
        txtUnitPrice.setForeground(new java.awt.Color(51, 153, 255));
        txtUnitPrice.setToolTipText("");
        txtUnitPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnitPriceKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("Unit Price:");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtAmount.setForeground(new java.awt.Color(51, 153, 255));
        txtAmount.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setText("Amount:");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 153, 255));
        jLabel26.setText("Customer Name:");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtCus.setForeground(new java.awt.Color(51, 153, 255));
        txtCus.setToolTipText("");

        txtDes.setForeground(new java.awt.Color(51, 153, 255));
        txtDes.setToolTipText("");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 153, 255));
        jLabel29.setText("Description:");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 153, 255));
        jLabel30.setText("Date:");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        jButton6.setText("...");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        cboProCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProCusActionPerformed(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(51, 153, 255));
        jLabel46.setText("Product Name:");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 153, 255));
        jLabel25.setText("UserID:");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtUserID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtUserID.setForeground(new java.awt.Color(51, 153, 255));
        txtUserID.setText("UserID:");
        txtUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCus, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDes, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel32)))
                        .addGap(133, 133, 133)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserID)
                        .addGap(171, 171, 171))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtINID, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboProCus, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(98, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtINID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(6, 6, 6)
                        .addComponent(cboProCus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25)
                                .addComponent(txtUserID)))
                        .addGap(8, 8, 8)))
                .addGap(8, 8, 8))
        );

        tblReturnCus.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null},
                {"", "", "", "", null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null}
            },
            new String [] {
                "ReturnCusID", "InvoiceID", "ProductName", "Quantity", "UnitPrice", "Amount", "CustomerName", "Description", "ReturnDate", "UserID"
            }
        ));
        tblReturnCus.setFocusable(false);
        tblReturnCus.setRowHeight(40);
        tblReturnCus.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblReturnCus.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblReturnCus);

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton7.setText("Save To Excel");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Customer Return Info:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 153, 255));
        jLabel24.setText("Search:");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt1ActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        myButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/save.png"))); // NOI18N
        myButton5.setText("Save");
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel24)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Customer Return Info", jPanel1);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 153, 255));
        jLabel31.setText("Supplier Return Info:");
        jLabel31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 153, 255));
        jLabel33.setText("Search:");
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt3ActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 153, 255));
        jLabel34.setText("ID");
        jLabel34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton9.setText("Save To Excel");

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 153, 255));
        jLabel35.setText("POID:");
        jLabel35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtPOID.setForeground(new java.awt.Color(51, 153, 255));
        txtPOID.setToolTipText("");
        txtPOID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPOIDKeyReleased(evt);
            }
        });

        txtQty1.setForeground(new java.awt.Color(51, 153, 255));
        txtQty1.setToolTipText("");
        txtQty1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQty1KeyReleased(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(51, 153, 255));
        jLabel38.setText("QTY:");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtUnitPrice1.setEditable(false);
        txtUnitPrice1.setForeground(new java.awt.Color(51, 153, 255));
        txtUnitPrice1.setToolTipText("");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(51, 153, 255));
        jLabel39.setText("Unit Price:");
        jLabel39.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 153, 255));
        jLabel41.setText("Supplier Name:");
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtSup.setForeground(new java.awt.Color(51, 153, 255));
        txtSup.setToolTipText("");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(51, 153, 255));
        jLabel42.setText("Product Name:");
        jLabel42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtDes1.setForeground(new java.awt.Color(51, 153, 255));
        txtDes1.setToolTipText("");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 153, 255));
        jLabel43.setText("Description:");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 153, 255));
        jLabel44.setText("Date:");
        jLabel44.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        jButton7.setText("...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        cboProSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProSupActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 153, 255));
        jLabel36.setText("UserID:");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtUserID1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtUserID1.setForeground(new java.awt.Color(51, 153, 255));
        txtUserID1.setText("UserID:");
        txtUserID1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtAmount1.setForeground(new java.awt.Color(51, 153, 255));
        txtAmount1.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Amount:");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPOID, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboProSup, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQty1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnitPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSup, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDes1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel45)))
                        .addGap(102, 102, 102)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserID1)
                        .addGap(157, 157, 157))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnitPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel42))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQty1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(cboProSup, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel36)
                                .addComponent(txtUserID1))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel45)))
                        .addGap(8, 8, 8)))
                .addGap(8, 8, 8))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        myButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/save.png"))); // NOI18N
        myButton10.setText("Save");
        myButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        tblReturnSup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null},
                {"", "", "", null, null, null, null, null, null, null}
            },
            new String [] {
                "ReturnSupID", "POID", "ProductName", "Quantity", "UnitPrice", "Amount", "SupplierName", "Description", "ReturnDate", "UserID"
            }
        ));
        tblReturnSup.setFocusable(false);
        tblReturnSup.setRowHeight(40);
        tblReturnSup.setSelectionBackground(new java.awt.Color(56, 138, 112));
        tblReturnSup.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblReturnSup);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(86, 86, 86))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel33)
                    .addComponent(txt3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Supplier Return Info", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        dateChooser1.showPopup();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        dateChooser2.showPopup();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtINIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtINIDKeyReleased
        if(txtINID.getText().isEmpty()){
            
        }else{
            loadProductReturnCus();
        }
        
    }//GEN-LAST:event_txtINIDKeyReleased

    private void cboProCusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProCusActionPerformed
        String pro_name = "";
        Object selectedItem = cboProCus.getSelectedItem();
        if (selectedItem != null) {
            pro_name = selectedItem.toString();
        }
        if (!pro_name.isEmpty()) {
            try {
                String sql = "SELECT * FROM sales WHERE product_name = '" + pro_name + "'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    txtQty.setText(rs.getString("quantity"));
                    txtUnitPrice.setText(rs.getString("sales_unit_price"));
                } else {
                    MsgBox.alert(this, "Hóa đơn không hợp lệ");
                }
                totalAmount();
            } catch (Exception e) {
                // Xử lý exception, ví dụ: hiển thị thông báo lỗi hoặc ghi log
                System.err.println("Lỗi: " + e.getMessage());
            }
        } else {
            // Xử lý trường hợp pro_name rỗng
            // Ví dụ: hiển thị thông báo hoặc xóa nội dung trường txtQty và txtUnitPrice
        }
    }//GEN-LAST:event_cboProCusActionPerformed

    private void txtUnitPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnitPriceKeyReleased
        
    }//GEN-LAST:event_txtUnitPriceKeyReleased

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        totalAmount();
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtINIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtINIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtINIDActionPerformed

    private void myButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton5ActionPerformed
        try {
            insertReturnCus();
            loadTableReturnCus();
        } catch (ParseException ex) {
            Logger.getLogger(Return_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton5ActionPerformed

    private void txtPOIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPOIDKeyReleased
        if(txtPOID.getText().isEmpty()){
            
        }else{
            loadProductReturnSup();
        }
    }//GEN-LAST:event_txtPOIDKeyReleased

    private void myButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton10ActionPerformed
        try {
            insertReturnSup();
            loadTableReturnSup();
        } catch (ParseException ex) {
            Logger.getLogger(Return_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton10ActionPerformed

    private void txtQty1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQty1KeyReleased
        totalAmount1();
    }//GEN-LAST:event_txtQty1KeyReleased

    private void cboProSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProSupActionPerformed
        String pro_name = "";
        Object selectedItem = cboProSup.getSelectedItem();
        if (selectedItem != null) {
            pro_name = selectedItem.toString();
        }
        if (!pro_name.isEmpty()) {
            try {
                String sql = "SELECT * FROM salesPO WHERE product_name = '" + pro_name + "'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    txtQty1.setText(rs.getString("quantity"));
                    txtUnitPrice1.setText(rs.getString("salespo_unit_price"));
                } else {
                    MsgBox.alert(this, "Hóa đơn không hợp lệ");
                }
                totalAmount1();
            } catch (Exception e) {
                System.out.println("sai10");
                System.out.println(e);
                System.err.println("Lỗi: " + e.getMessage());
            }
        } else {
            // Xử lý trường hợp pro_name rỗng
            // Ví dụ: hiển thị thông báo hoặc xóa nội dung trường txtQty và txtUnitPrice
        }
    }//GEN-LAST:event_cboProSupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboProCus;
    private javax.swing.JComboBox<String> cboProSup;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private button.MyButton myButton10;
    private button.MyButton myButton5;
    private button.MyButton myButton7;
    private button.MyButton myButton9;
    private raven.tabbed.TabbedPaneCustom tabbedPaneCustom1;
    private javax.swing.JTable tblReturnCus;
    private javax.swing.JTable tblReturnSup;
    private searchwing.TextFieldAnimation txt1;
    private searchwing.TextFieldAnimation txt3;
    private textfield_suggestion.TextFieldSuggestion txtAmount;
    private textfield_suggestion.TextFieldSuggestion txtAmount1;
    private textfield_suggestion.TextFieldSuggestion txtCus;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDate1;
    private textfield_suggestion.TextFieldSuggestion txtDes;
    private textfield_suggestion.TextFieldSuggestion txtDes1;
    private textfield_suggestion.TextFieldSuggestion txtINID;
    private textfield_suggestion.TextFieldSuggestion txtPOID;
    private textfield_suggestion.TextFieldSuggestion txtQty;
    private textfield_suggestion.TextFieldSuggestion txtQty1;
    private textfield_suggestion.TextFieldSuggestion txtSup;
    private textfield_suggestion.TextFieldSuggestion txtUnitPrice;
    private textfield_suggestion.TextFieldSuggestion txtUnitPrice1;
    private javax.swing.JLabel txtUserID;
    private javax.swing.JLabel txtUserID1;
    // End of variables declaration//GEN-END:variables
}
