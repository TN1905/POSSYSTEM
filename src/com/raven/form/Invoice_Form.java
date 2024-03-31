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
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import com.raven.entity.category;
import com.raven.entity.customer;
import com.raven.entity.invoice;
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
public class Invoice_Form extends Form {

    private Setting_Form settingForm;
    customerDAO cusDAO = new customerDAO();
    invoiceDAO inDAO = new invoiceDAO();
    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form Sales_Form
     */
    public Invoice_Form() {
        initComponents();
        init();
        fillComboBoxCustomer();
    }

    public void init() {
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
        loadTable();
        loadInvoiceAmount();
        loadInvoiceUnpaidAmount();
        loadInvoicePaidCount();
        loadInvoiceUnpaidCount();
    }

    void loadTable() {
        DefaultTableModel model = (DefaultTableModel) tblInvoice.getModel();
        model.setRowCount(0);
        try {

            List<invoice> list = inDAO.selectAll();

            for (invoice nh : list) {
                Object[] row = {
                    nh.getInvoiceID(),
                    nh.getInvoiceCusName(),
                    nh.getInvoiceQuanlity(),
                    nh.getInvoiceTotalAmount(),
                    nh.getInvoiceStatus(),
                    nh.getInvoicePaidAmount(),
                    nh.getInvoiceDueBalance(),};
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

    void fillComboBoxCustomer() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCusName.getModel();
        model.removeAllElements();
        model.addElement("Selected");
        try {
            List<customer> list = cusDAO.selectAll();
            for (customer cd : list) {
                model.addElement(cd.getCusName());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    public void loadTotalAmount() {
        int rowTable = tblInvoice.getRowCount();
        float total = 0f;
        for (int i = 0; i < rowTable; i++) {
            Float values = Float.parseFloat(tblInvoice.getValueAt(i, 3).toString());
            total += values;
        }
        txtTotalAmount.setText(String.valueOf(total));
    }

    public void loadPaidAmount() {
        int rowTable = tblInvoice.getRowCount();
        float total = 0f;
        for (int i = 0; i < rowTable; i++) {
            Float values = Float.parseFloat(tblInvoice.getValueAt(i, 5).toString());
            total += values;
        }
        txtPaidAmount.setText(String.valueOf(total));
    }

    public void loadDueBalance() {
        int rowTable = tblInvoice.getRowCount();
        float total = 0f;
        for (int i = 0; i < rowTable; i++) {
            Float values = Float.parseFloat(tblInvoice.getValueAt(i, 6).toString());
            total += values;
            System.out.println(total);
        }
        txtDueBalance.setText(String.valueOf(total));
    }

    public void loadInvoiceAmount() {
        try {
            String sql = "select sum(invoice_paid_amount) as total from invoices where invoice_status = 'Paid'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("total").isEmpty()) {
                    jLabel14.setText("0");
                } else {
                    jLabel14.setText(rs.getString("total"));
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadInvoiceUnpaidAmount() {
        try {
            String sql = "select sum(invoice_paid_amount) as total from invoices where invoice_status = 'Unpaid'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("total").isEmpty()) {
                    jLabel15.setText("0");
                } else {
                    jLabel15.setText(rs.getString("total"));
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadInvoicePaidCount() {
        try {
            String sql = "select count(invoice_paid_amount) as total from invoices where invoice_status = 'Paid'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("total").isEmpty()) {
                    jLabel16.setText("0");
                } else {
                    jLabel16.setText(rs.getString("total"));
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadInvoiceUnpaidCount() {
        try {
            String sql = "select count(invoice_paid_amount) as total from invoices where invoice_status = 'Unpaid'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("total").isEmpty()) {
                    jLabel17.setText("0");
                } else {
                    jLabel17.setText(rs.getString("total"));
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
        cboCusName = new combo_suggestion.ComboBoxSuggestion();
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
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        myButton7 = new button.MyButton();
        myButton8 = new button.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInvoice1 = new javax.swing.JTable();

        dateChooser.setTextRefernce(txtDate1);

        dateChooser1.setTextRefernce(txtDate2);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("Invoice ID:");
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
        jLabel6.setText("Customer Name:");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Status:");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        cboStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paid", "Partial", "Unpaid" }));
        cboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboStatusActionPerformed(evt);
            }
        });
        cboStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboStatusKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboCusName, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 194, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(55, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
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

        txtTotalAmount.setEditable(false);
        txtTotalAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalAmountKeyReleased(evt);
            }
        });

        txtDueBalance.setEditable(false);
        txtDueBalance.setBackground(new java.awt.Color(255, 0, 51));

        txtPaidAmount.setEditable(false);
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

        comboBoxSuggestion4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paid", "Partial", "Unpaid" }));
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

        txtDate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDate1ActionPerformed(evt);
            }
        });
        txtDate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDate1KeyReleased(evt);
            }
        });

        txtDate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDate2ActionPerformed(evt);
            }
        });
        txtDate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDate2KeyReleased(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
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

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 255));
        jLabel15.setText("0");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("0");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 153, 255));
        jLabel17.setText("0");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        myButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/money.png"))); // NOI18N
        myButton7.setText("Pay Invoice");
        myButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton7ActionPerformed(evt);
            }
        });

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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(myButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(myButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "INID", "CustomerName", "Quantity", "Total_Amount", "Status", "Paid Amount", "Balance"
            }
        ));
        tblInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceMouseClicked(evt);
            }
        });
        tblInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblInvoiceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblInvoiceKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblInvoice);

        tblInvoice1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "INID", "Date", "Time", "CustomerName", "Quantity", "Total_Amount", "Status", "Paid Amount", "Payment Type", "Balance", "Note", "UserID"
            }
        ));
        jScrollPane2.setViewportView(tblInvoice1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                if (comboBoxSuggestion4.getSelectedItem().toString().equals("All")) {
                    String sql = "SELECT * FROM invoices WHERE invoice_date <= '" + convertToDate + "' AND invoice_date >='" + convertFromDate + "'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while (rs.next()) {
                        Vector v = new Vector();
                        v.add(rs.getString("invoice_id"));
                        v.add(rs.getString("customer_name"));
                        v.add(rs.getString("invoice_quantity"));
                        v.add(rs.getString("invoice_total_amount"));
                        v.add(rs.getString("invoice_status"));
                        v.add(rs.getString("invoice_paid_amount"));
                        v.add(rs.getString("balance"));

                        dt.addRow(v);

                    }
                } else {
                    String sql = "SELECT * FROM invoices WHERE invoice_date >= '" + convertFromDate + "' AND invoice_date <='" + convertToDate + "' AND invoice_status = N'" + comboBoxSuggestion4.getSelectedItem().toString() + "'";
                    Statement s = db.myCon().createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while (rs.next()) {
                        Vector v = new Vector();
                        v.add(rs.getString("invoice_id"));
                        v.add(rs.getString("customer_name"));
                        v.add(rs.getString("invoice_quantity"));
                        v.add(rs.getString("invoice_total_amount"));
                        v.add(rs.getString("invoice_status"));
                        v.add(rs.getString("invoice_paid_amount"));
                        v.add(rs.getString("balance"));
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

    private void cboCusNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCusNameActionPerformed
        String cusName = cboCusName.getSelectedItem().toString();
        System.out.println(cusName);
        String status = cboStatus.getSelectedItem().toString();
        if (status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices WHERE customer_name like N'" + cusName + "'");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance"));
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cusName.equalsIgnoreCase("Selected") && status.equalsIgnoreCase("All")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance"));
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cusName.equalsIgnoreCase("Selected") && !status.equalsIgnoreCase("All")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices Where invoice_status = N'" + status + "'");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance"));
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (!status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices WHERE customer_name like N'" + cusName + "' AND invoice_status = N'" + status + "'");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance"));
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_cboCusNameActionPerformed

    private void cboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboStatusActionPerformed
        String cusName = cboCusName.getSelectedItem().toString();
        System.out.println(cusName);
        String status = cboStatus.getSelectedItem().toString();
        if (status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices WHERE customer_name like N'" + cusName + "'");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance")); 
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cusName.equalsIgnoreCase("Selected") && status.equalsIgnoreCase("All")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance")); 
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cusName.equalsIgnoreCase("Selected") && !status.equalsIgnoreCase("All")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices Where invoice_status = N'" + status + "'");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance")); 
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (!status.equalsIgnoreCase("All") && !cusName.equalsIgnoreCase("Selected")) {
            try {
                DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
                dt.setRowCount(0);
                Statement s = db.myCon().createStatement();

                ResultSet rs = s.executeQuery("SELECT * FROM invoices WHERE customer_name like N'" + cusName + "' AND invoice_status = N'" + status + "'");
                while (rs.next()) {
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance")); 
                    dt.addRow(v);
                }
                s.close();
                rs.close();

                db.myCon().close();
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_cboStatusActionPerformed

    private void txt1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt1KeyReleased
        String name = txt1.getText();
        try {
            DefaultTableModel dt = (DefaultTableModel) tblInvoice.getModel();
            dt.setRowCount(0);
            Statement s = db.myCon().createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM invoices WHERE invoice_id LIKE N'%" + name + "%'");
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("invoice_id"));
                v.add(rs.getString("customer_name"));
                v.add(rs.getString("invoice_quantity"));
                v.add(rs.getString("invoice_total_amount"));
                v.add(rs.getString("invoice_status"));
                v.add(rs.getString("invoice_paid_amount"));
                v.add(rs.getString("balance"));
                dt.addRow(v);
            }
            s.close();
            rs.close();

            db.myCon().close();
            loadPaidAmount();
            loadTotalAmount();
            loadDueBalance();
        } catch (Exception e) {
            System.out.println(e);
        }

        
    }//GEN-LAST:event_txt1KeyReleased

    private void txtTotalAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalAmountKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAmountKeyReleased

    private void cboCusNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCusNameKeyReleased

    }//GEN-LAST:event_cboCusNameKeyReleased

    private void cboStatusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboStatusKeyReleased


    }//GEN-LAST:event_cboStatusKeyReleased

    private void txtDate1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate1KeyReleased

    }//GEN-LAST:event_txtDate1KeyReleased

    private void txtDate2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDate2KeyReleased

    }//GEN-LAST:event_txtDate2KeyReleased

    private void txtDate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate1ActionPerformed

    }//GEN-LAST:event_txtDate1ActionPerformed

    private void txtDate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDate2ActionPerformed

    }//GEN-LAST:event_txtDate2ActionPerformed

    private void tblInvoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblInvoiceKeyReleased

    }//GEN-LAST:event_tblInvoiceKeyReleased

    private void tblInvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblInvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblInvoiceKeyPressed

    private void tblInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceMouseClicked
        int row = tblInvoice.getSelectedRow();
            String supID = tblInvoice.getValueAt(row, 0).toString();
            DefaultTableModel model = (DefaultTableModel) tblInvoice1.getModel();
            model.setRowCount(0);
            try {
                String sql = "select * from invoices where invoice_id = '"+supID+"'";
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery(sql);
                if(rs.next()){
                    Vector v = new Vector();
                    v.add(rs.getString("invoice_id"));
                    v.add(rs.getString("invoice_date"));
                    v.add(rs.getString("invoice_time"));
                    v.add(rs.getString("customer_name"));
                    v.add(rs.getString("invoice_quantity"));
                    v.add(rs.getString("invoice_total_amount"));
                    v.add(rs.getString("invoice_status"));
                    v.add(rs.getString("invoice_paid_amount"));
                    v.add(rs.getString("balance"));
                    v.add(rs.getString("invoice_payment_type"));
                    v.add(rs.getString("invoice_note"));
                    v.add(rs.getString("users_id"));
                    model.addRow(v);
                }
                loadPaidAmount();
                loadTotalAmount();
                loadDueBalance();
                

            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
                System.out.println(e);
            }
    }//GEN-LAST:event_tblInvoiceMouseClicked

    private void myButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton7ActionPerformed
        int row = tblInvoice.getSelectedRow();
        String invoiceID = tblInvoice.getValueAt(row, 0).toString();
        try {
            String sql = "SELECT * FROM invoices where invoice_status = 'Unpaid' and invoice_id = '"+invoiceID+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                float PayAmount = Float.parseFloat(MsgBox.prompt(this, "Nhập giá tiền thanh toán"));
                float Balance = PayAmount-rs.getFloat("invoice_total_amount");
                boolean check =  MsgBox.confirm(this, "Cập nhật trạng thái thanh toán");
                if(check==true){
                    inDAO.updateStatus(PayAmount,Balance,invoiceID);
                }
            }
            loadTable();
            loadInvoiceAmount();
            loadInvoiceUnpaidAmount();
            loadInvoicePaidCount();
            loadInvoiceUnpaidCount();
        } catch (Exception e) {
            MsgBox.alert(this, "Hóa đơn này không ở trạng thái Unpaid");
        }
    }//GEN-LAST:event_myButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private combo_suggestion.ComboBoxSuggestion cboCusName;
    private combo_suggestion.ComboBoxSuggestion cboStatus;
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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private button.MyButton myButton7;
    private button.MyButton myButton8;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTable tblInvoice1;
    private searchwing.TextFieldAnimation txt1;
    private javax.swing.JTextField txtDate1;
    private javax.swing.JTextField txtDate2;
    private javax.swing.JTextField txtDueBalance;
    private javax.swing.JTextField txtPaidAmount;
    private javax.swing.JTextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables
}
