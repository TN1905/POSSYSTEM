/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Utils.MsgBox;
import Utils.db;
import com.raven.component.Form;
import com.raven.dao.customerDAO;
import com.raven.dao.invoiceDAO;
import com.raven.dao.purchaseOrderDAO;
import com.raven.dao.supplierDAO;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import com.raven.entity.customer;
import com.raven.entity.invoice;
import com.raven.entity.purchaseOrder;
import com.raven.entity.supplier;
import com.raven.event.EventColorChange;
import com.raven.menu.EventMenu;
import com.raven.properties.SystemProperties;
import com.raven.theme.SystemTheme;
import com.raven.theme.ThemeColor;
import com.raven.theme.ThemeColorChange;
import java.awt.Color;
import com.raven.form.Setting_Form;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import textfield_suggestion.TextFieldSuggestion;
/**
 *
 * @author ADMIN
 */
public class PO_Bill_Form extends Form {
private Setting_Form settingForm;
customerDAO cusDAO = new customerDAO();
    supplierDAO supDAO = new supplierDAO();
    invoiceDAO inDAO = new invoiceDAO();
    purchaseOrderDAO poDAO = new purchaseOrderDAO();
     DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
     DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
     
    /**
     * Creates new form Sales_Form
     */
    public PO_Bill_Form() {
        initComponents();
        init();
        fillComboBoxSupplier();
    }
    
    public void init(){
        jPanel1.setOpaque(false);
        jPanel2.setOpaque(false);
        jPanel3.setOpaque(false);
        jPanel4.setOpaque(false);
        jPanel5.setOpaque(false);
        jPanel6.setOpaque(false);
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        jPanel3.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setBackground(new Color(0, 0, 0, 0));
        jPanel6.setBackground(new Color(0, 0, 0, 0));
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
        });
        loadInvoiceAmount();
        loadInvoiceUnpaidAmount();
        loadInvoicePaidCount();
        loadInvoiceUnpaidCount();
        loadTable();
    }
    
    void loadTable(){
        DefaultTableModel model = (DefaultTableModel) tblPO.getModel();
        model.setRowCount(0);
        try {

            List<purchaseOrder>list =  poDAO.selectAll();
      
            for(purchaseOrder nh: list){
                Object[] row = {          
                    nh.getPOID(),
                    nh.getPODate(),
                    nh.getPOTime(),
                    nh.getPOSupName(),
                    nh.getPOProduct(),
                    nh.getPOQuanlity(),
                    nh.getPOTotalAmount(),
                    nh.getPOStatus(),
                    nh.getPOPaidAmount(),                   
                    nh.getPODueBalance(),
                    nh.getPOPaymentType(),
                    nh.getPONote(),
                    nh.getPOUser(),
                };
                model.addRow(row);
            }
            loadTotalAmount();
            loadDueBalance();
            loadPaidAmount();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println(e);
        }
    }
    
    void fillComboBoxSupplier(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupplierName.getModel();
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
    
    public void loadTotalAmount(){
            int rowTable = tblPO.getRowCount();
            float total = 0f;
            for(int i=0;i<rowTable;i++){
                 Double values=Double.parseDouble(tblPO.getValueAt(i, 6).toString());
                 total+= values;
            }
            txtTotalAmount.setText(String.valueOf(total));
      }
      
      public void loadPaidAmount(){
            int rowTable = tblPO.getRowCount();
            float total = 0f;
            for(int i=0;i<rowTable;i++){
                 Double values=Double.parseDouble(tblPO.getValueAt(i, 8).toString());
                 total+= values;
            }
            txtPaidAmount.setText(String.valueOf(total));
      }
      
      public void loadDueBalance(){
            int rowTable = tblPO.getRowCount();
            float total = 0f;
            for(int i=0;i<rowTable;i++){
                Double values=Double.parseDouble(tblPO.getValueAt(i, 9).toString());
                total+= values;
                System.out.println(total);
            }
            txtDueBalance.setText(String.valueOf(total));
      }
      
      public void loadInvoiceAmount(){
          try {
              String sql = "select sum(purchase_paid_amount) as total from PO where purchase_status = 'Paid'";
              Statement s = db.myCon().createStatement();
              ResultSet rs = s.executeQuery(sql);
              if(rs.next()){
                  if(rs.getString("total").isEmpty()){
                      jLabel14.setText("0");
                  }else{
                      jLabel14.setText(rs.getString("total"));
                  }
                  
              }
          } catch (Exception e) {
              System.out.println(e);
          }
      }
      
      public void loadInvoiceUnpaidAmount(){
          try {
              String sql = "select sum(purchase_paid_amount) as total from PO where purchase_status = 'Unpaid'";
              Statement s = db.myCon().createStatement();
              ResultSet rs = s.executeQuery(sql);
              if(rs.next()){
                  if(rs.getString("total").isEmpty()){
                      jLabel18.setText("0");
                  }else{
                      jLabel18.setText(rs.getString("total"));
                  }
                  
              }
          } catch (Exception e) {
              System.out.println(e);
          }
      }
      
      public void loadInvoicePaidCount(){
          try {
              String sql = "select count(purchase_paid_amount) as total from PO where purchase_status = 'Paid'";
              Statement s = db.myCon().createStatement();
              ResultSet rs = s.executeQuery(sql);
              if(rs.next()){
                  if(rs.getString("total").isEmpty()){
                      jLabel20.setText("0");
                  }else{
                      jLabel20.setText(rs.getString("total"));
                  }
                  
              }
          } catch (Exception e) {
              System.out.println(e);
          }
      }
      
      public void loadInvoiceUnpaidCount(){
          try {
              String sql = "select count(purchase_paid_amount) as total from PO where purchase_status = 'Unpaid'";
              Statement s = db.myCon().createStatement();
              ResultSet rs = s.executeQuery(sql);
              if(rs.next()){
                  if(rs.getString("total").isEmpty()){
                      jLabel19.setText("0");
                  }else{
                      jLabel19.setText(rs.getString("total"));
                  }
                  
              }
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

        comboSuggestionUI1 = new combo_suggestion.ComboSuggestionUI();
        dateChooser = new com.raven.datechooser.DateChooser();
        dateChooser1 = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt1 = new searchwing.TextFieldAnimation();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboSupplierName = new combo_suggestion.ComboBoxSuggestion();
        cboStatus = new combo_suggestion.ComboBoxSuggestion();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTotalAmount = new javax.swing.JTextField();
        txtDueBalance = new javax.swing.JTextField();
        txtPaidAmount = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        comboBoxSuggestion4 = new combo_suggestion.ComboBoxSuggestion();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        txtDate1 = new javax.swing.JTextField();
        txtDate2 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        myButton7 = new button.MyButton();
        myButton8 = new button.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPO = new javax.swing.JTable();

        dateChooser.setTextRefernce(txtDate1);

        dateChooser1.setTextRefernce(txtDate2);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("PO ID:");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setText("Supplier Name:");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Status:");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupplierNameActionPerformed(evt);
            }
        });

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Unpaid", "Partial", "Paid" }));
        cboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboSupplierName, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)))
                .addGap(80, 80, 80))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 255));
        jLabel11.setText("Total Amount:");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("Paid Amount:");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Due Balance:");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtDueBalance.setEditable(false);
        txtDueBalance.setBackground(new java.awt.Color(255, 0, 51));

        txtPaidAmount.setBackground(new java.awt.Color(102, 255, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel11)
                .addGap(153, 153, 153)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(105, 105, 105))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtTotalAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDueBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPaidAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(txtTotalAmount)
                    .addComponent(txtDueBalance))
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        comboBoxSuggestion4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Unpaid", "Partial", "Paid" }));
        comboBoxSuggestion4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxSuggestion4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("Status:");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("From Date:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 255));
        jLabel10.setText("To Date:");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxSuggestion4, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(21, 21, 21)
                        .addComponent(txtDate2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jButton5)
                    .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButton6)
                    .addComponent(txtDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxSuggestion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("All Paid Invoice Amount:");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Unpaid Invoice Amount:");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("All Unpaid Invoice Count:");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("All Invoice Amount:");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("0");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("0");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 153, 255));
        jLabel19.setText("0");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 153, 255));
        jLabel20.setText("0");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/money.png"))); // NOI18N
        myButton7.setText("Pay PO");

        myButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/excel.png"))); // NOI18N
        myButton8.setText("Save To Excel");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tblPO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "POID", "Date", "Time", "SupplierName", "ProductName", "Quantity", "Total_Amount", "Status", "Paid Amount", "Balance", "Payment Type", "Note", "UserID"
            }
        ));
        jScrollPane1.setViewportView(tblPO);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt1ActionPerformed

    private void comboBoxSuggestion4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxSuggestion4ActionPerformed
          try {
        Date date1 = inputFormat.parse(txtDate1.getText());
        Date date2 = inputFormat.parse(txtDate2.getText());
        String convertFromDate = outputFormat.format(date1);
        String convertToDate = outputFormat.format(date2);
                System.out.println(convertToDate);
                System.out.println(convertFromDate);
        try {
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            if(comboBoxSuggestion4.getSelectedItem().toString().equals("All")){
               String sql = "SELECT * FROM PO WHERE purchase_order_date >= '"+convertFromDate+"' AND purchase_order_date <='"+convertFromDate+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
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
                    dt.addRow(v);
                                
               
                }
            }else{
                String sql = "SELECT * FROM PO WHERE purchase_order_date >= '"+convertFromDate+"' AND purchase_order_date <='"+convertToDate+"' AND purchase_status = N'"+comboBoxSuggestion4.getSelectedItem().toString()+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
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
                    dt.addRow(v);
                                
                    
                }
                s.close();
                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    } catch (ParseException ex) {
        Logger.getLogger(Invoice_Form.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_comboBoxSuggestion4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

            dateChooser.showPopup();       
    
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        dateChooser1.showPopup();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void cboSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupplierNameActionPerformed
                String cusName = cboSupplierName.getSelectedItem().toString();
               System.out.println(cusName);
               String supID = "";
               try {
                   String sql = "SELECT supplier_id FROM suppliers where sup_name = N'"+cusName+"'";
                   Statement s = db.myCon().createStatement();
                   ResultSet rs = s.executeQuery(sql);
                   if(rs.next()){
                       supID = rs.getString("supplier_id");
                   }
                } catch (Exception e) {
                    System.out.println(e);
                }
               System.out.println(supID);
        String status = cboStatus.getSelectedItem().toString();
        if(status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO WHERE supplier_id like N'"+supID+"'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }else if(cusName.equalsIgnoreCase("Selected") && status.equalsIgnoreCase("All")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }else if(cusName.equalsIgnoreCase("Selected") && !status.equalsIgnoreCase("All")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO Where purchase_status = N'"+status+"'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }else if(!status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO WHERE supplier_id like N'"+supID+"' AND purchase_status = N'"+status+"'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }
    }//GEN-LAST:event_cboSupplierNameActionPerformed

    private void cboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatusActionPerformed
                        String cusName = cboSupplierName.getSelectedItem().toString();
               System.out.println(cusName);
               String supID = "";
               try {
                   String sql = "SELECT supplier_id FROM suppliers where sup_name = N'"+cusName+"'";
                   Statement s = db.myCon().createStatement();
                   ResultSet rs = s.executeQuery(sql);
                   if(rs.next()){
                       supID = rs.getString("supplier_id");
                   }
                } catch (Exception e) {
                    System.out.println(e);
                }
        String status = cboStatus.getSelectedItem().toString();
        if(status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO WHERE supplier_id like N'"+supID+"'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }else if(cusName.equalsIgnoreCase("Selected") && status.equalsIgnoreCase("All")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }else if(cusName.equalsIgnoreCase("Selected") && !status.equalsIgnoreCase("All")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO Where purchase_status = N'"+status+"'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }else if(!status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")){
            try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO WHERE supplier_id like N'"+supID+"' AND purchase_status = N'"+status+"'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
        }
    }//GEN-LAST:event_cboStatusActionPerformed

    private void txt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1KeyReleased
        String name = txt1.getText();
        try{
            DefaultTableModel dt = (DefaultTableModel) tblPO.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM PO WHERE purchase_order_id LIKE N'%"+name+"%'");   
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
                dt.addRow(v);
            }
            s.close();
            rs.close();
         
                db.myCon().close();
        }catch(Exception e){
             System.out.println(e);
        }
    }//GEN-LAST:event_txt1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combo_suggestion.ComboBoxSuggestion cboStatus;
    private combo_suggestion.ComboBoxSuggestion cboSupplierName;
    private combo_suggestion.ComboBoxSuggestion comboBoxSuggestion4;
    private combo_suggestion.ComboSuggestionUI comboSuggestionUI1;
    private com.raven.datechooser.DateChooser dateChooser;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JScrollPane jScrollPane1;
    private button.MyButton myButton7;
    private button.MyButton myButton8;
    private javax.swing.JTable tblPO;
    private searchwing.TextFieldAnimation txt1;
    private javax.swing.JTextField txtDate1;
    private javax.swing.JTextField txtDate2;
    private javax.swing.JTextField txtDueBalance;
    private javax.swing.JTextField txtPaidAmount;
    private javax.swing.JTextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables
}
