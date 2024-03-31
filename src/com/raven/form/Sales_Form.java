
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import Utils.Data;
import Utils.MsgBox;
import Utils.db;

import com.google.gson.Gson;
import com.raven.component.Form;
import com.raven.dao.customerDAO;
import com.raven.dao.productDAO;
import com.raven.dao.supplierDAO;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
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
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import textfield_suggestion.TextFieldSuggestion;
/**
 *
 * @author ADMIN
 */
public class Sales_Form extends Form {
    supplierDAO supDAO = new supplierDAO();
    productDAO proDAO = new productDAO();
    customerDAO cusDAO = new customerDAO();
    public int Stcok_qty= 0; 
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
    private users user;
    public void setUser(users user1){
        user = user1;
    }
private Setting_Form settingForm;
    int extra = 0;
    public Sales_Form(users user) {
        initComponents();
        init();
        fillComboBoxSupplier();
        fillComboBoxProduct();
        loadExtra();
        this.user = user;
        SalesUserID.setText(user.getUserID());
        
        loadTime();
        // Lấy ngày hiện tại
        LocalDate ngay_hien_tai = LocalDate.now();
        System.out.println("Ngày hiện tại: " + ngay_hien_tai);
        lblDate.setText(String.valueOf(ngay_hien_tai));
    }
    
    public void init(){
        jPanel1.setOpaque(false);        
        jPanel1.setBackground(new Color(0, 0, 0, 0));
        jPanel2.setOpaque(false);        
        jPanel2.setBackground(new Color(0, 0, 0, 0));
        jPanel3.setOpaque(false);        
        jPanel3.setBackground(new Color(0, 0, 0, 0));
        jPanel4.setOpaque(false);        
        jPanel4.setBackground(new Color(0, 0, 0, 0));
        jPanel5.setOpaque(false);        
        jPanel5.setBackground(new Color(0, 0, 0, 0));
        jPanel6.setOpaque(false);        
        jPanel6.setBackground(new Color(0, 0, 0, 0));
        jPanel7.setOpaque(false);        
        jPanel7.setBackground(new Color(0, 0, 0, 0));
        jPanel9.setOpaque(false);        
        jPanel9.setBackground(new Color(0, 0, 0, 0));
        jPanel10.setOpaque(false);        
        jPanel10.setBackground(new Color(0, 0, 0, 0));
  
        jPanel8.setOpaque(false);        
        jPanel8.setBackground(new Color(0, 0, 0, 0));
        jPanel12.setOpaque(false);        
        jPanel12.setBackground(new Color(0, 0, 0, 0));
        
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
        });

    }
    
    public void loadTime(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Lấy thời gian hiện tại
                Date thoi_gian_hien_tai = new Date();

                // Định dạng theo yêu cầu (hh:mm:ss a)
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                String thoi_gian_dinh_dang = sdf.format(thoi_gian_hien_tai);
                System.out.print("\rThời gian hiện tại (định dạng hh:mm:ss a): " + thoi_gian_dinh_dang);
                lblTime.setText(thoi_gian_dinh_dang);
            }
        }, 0, 1000); // Cập nhật thời gian sau mỗi giây (1000 ms)
    }
    
    public void pro_tot_cal(){
     // product calculation
        Double qt = Double.valueOf(p_qty.getText());
        Double price = Double.valueOf(u_price.getText());
        Double tot;
        
        tot = qt*price;
        
        tot_price.setText(String.valueOf(tot));
    }
    
    public void cart_total(){
        int numofrow = jTable1.getRowCount();
        
        double total = 0;
        
        for(int i =0;i<numofrow;i++){
            double value = Double.valueOf(jTable1.getValueAt(i, 5).toString());
            total+=value;
        }
        bill_tot.setText(Double.toString(total));
        txtSubTotal.setText(Double.toString(total));
        
        // total qty count
        int numofrows = jTable1.getRowCount();
        
        int totals = 0;
        
        for(int i =0;i<numofrow;i++){
            double values = Double.valueOf(jTable1.getValueAt(i, 3).toString());
            totals+=values;
        }
        tot_qty.setText(String.valueOf(totals));
        
        int totalss = numofrow;
        txtItem.setText(String.valueOf(totalss));

    }
    
    void fillComboBoxSupplier(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSupplierName.getModel();
        model.removeAllElements();
        try {
            List<customer> list = cusDAO.selectAll();
            model.addElement("Selected");
            for(customer cd:list){
                model.addElement(cd.getCusName());
            }
            loadComboBoxSupplier();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void loadComboBoxSupplier(){
        String supName = cboSupplierName.getSelectedItem().toString();
        String supID = "";
        String supCity = "";
        try {
            String sql = "Select customer_id,cus_city from customers where cus_name = N'"+supName+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                supID = rs.getString("customer_id");
                supCity = rs.getString("cus_city");
            }
            txtSupplierID.setText(supID);
            txtSupplierCity.setText(supCity);
        } catch (Exception e) {
        }
    }
    
    void fillComboBoxProduct(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboProductName.getModel();
        model.removeAllElements();
        try {
            List<product> list = proDAO.selectAll();
            for(product cd:list){
                model.addElement(cd.getProName());
            }
            loadComboBoxProduct();
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    void loadComboBoxProduct(){
        String proName = cboProductName.getSelectedItem().toString();
        String barcode = "";
        try {
            String sql = "Select barcode from products where pro_name = N'"+proName+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                barcode = rs.getString("barcode");
                
                
            }
            txtBarcode.setText(barcode);
    
        } catch (Exception e) {
        }
    }
    
    void loadExtra(){
        
        try{
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM extra WHERE exid =1");
            if(rs.next()){
                inid.setText(rs.getString("val"));
            }
            s.close();
            rs.close();
            db.myCon().close();
        }catch(Exception e){
            System.out.println(e);
        }

    }
    
     public void tot(){
        String paid = pay_amt.getText();
        double total =0d;
        Double paidAMT = 0d;
        if(paid.isEmpty()){
            paidAMT = 0d;
        }else{
            paidAMT = Double.parseDouble(paid);
        }
        int numofrow = jTable1.getRowCount();
        
        double totall = 0d;
        
        for(int i =0;i<numofrow;i++){
            double value = Double.valueOf(jTable1.getValueAt(i, 5).toString());
            totall+=value;
        }
        bill_tot.setText(Double.toString(totall));
        total = Double.parseDouble(bill_tot.getText())+Double.parseDouble(txtDiscountRate.getText())+Double.parseDouble(txtTaxAmount.getText())+Double.parseDouble(txtShippingCost.getText());
        Double due=0d;
        bill_tot.setText(String.valueOf(total));
        due = paidAMT -total+Double.parseDouble(txtDiscountAmount.getText());
        balance.setText(String.valueOf(due));
    }
     
     public void stckup(){
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        int rc = dt.getRowCount();
        for(int i=0;i<rc;i++){
            String bcode = dt.getValueAt(i, 2).toString();
            String sell_qty = dt.getValueAt(i, 3).toString();
            
            try {
                Statement s = db.myCon().createStatement();
                ResultSet rs = s.executeQuery("SELECT pro_quantity FROM products WHERE barcode = '"+bcode+"'");
                if(rs.next()){
                    Stcok_qty = Integer.parseInt(rs.getString("pro_quantity"));
                }
                s.close();
                rs.close();
                db.myCon().close();
            } catch (Exception e) {
                System.out.println(e);
            }
            
            int st_qty = Stcok_qty;
            int Sel_qty = Integer.valueOf(sell_qty);
            int new_qty = st_qty - Sel_qty;
            String nqty = String.valueOf(new_qty);
            try {
                Statement ss = db.myCon().createStatement();
                ss.executeUpdate("UPDATE products SET pro_quantity = '"+nqty+"' WHERE barcode = '"+bcode+"'");
                ss.close();
                db.myCon().close();
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
    }
     
     private void loadBanks() {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.vietqr.io/v2/banks")
                    .build();

            Response response = client.newCall(request).execute();
            String responseData = response.body().string();

            Gson gson = new Gson();
            Bank bankData = gson.fromJson(responseData, Bank.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private void generateQRCode() {
        try {
            BankDatum selectedBank = new BankDatum();
            String apiUrl = "https://api.vietqr.io/v2/generate";   
            selectedBank.id = 970416;
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.accountNo = Long.parseLong("7307727");
            apiRequest.accountName = "CHIEM NGUYEN TRI NGUYEN";
            apiRequest.acqId = selectedBank.id;
            apiRequest.amount = Integer.parseInt(txtAmount.getText());
            apiRequest.format = "text";
            apiRequest.template = "compact";

            Gson gson = new Gson();
            String jsonRequest = gson.toJson(apiRequest);

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
            RequestBody requestBody = RequestBody.create(mediaType, jsonRequest);
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            ApiResponse apiResponse = gson.fromJson(responseData, ApiResponse.class);
            System.out.println(responseData);
            System.out.println(apiResponse);
            System.out.println(apiResponse.data);
            
            if (apiResponse != null) {
            if ("22".equals(apiResponse.code)) {
                String errorDesc = apiResponse.desc;
                System.out.println("Error: " + errorDesc);
                JOptionPane.showMessageDialog(this, "Error: " + errorDesc, "QR Code Generation Error", JOptionPane.ERROR_MESSAGE);
                return; // Thoát khỏi phương thức nếu có lỗi acqId
            }

            if (apiResponse.data != null) {
                String qrDataURL = apiResponse.data.qrDataURL.replace("data:image/png;base64,", "");
                byte[] qrImageData = Base64.getDecoder().decode(qrDataURL);
                ImageIcon qrIcon = new ImageIcon(qrImageData);

                // Resize hình ảnh để vừa với kích thước của JLabel
                Image scaledImage = qrIcon.getImage().getScaledInstance(lblQRCode.getWidth(), lblQRCode.getHeight(), Image.SCALE_SMOOTH);

                lblQRCode.setIcon(new ImageIcon(scaledImage));
                lblQRCode.setText("");
            } else {
                lblQRCode.setText("QR Code generation failed.");
                lblQRCode.setIcon(null);
            }
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ApiRequest {
        public long accountNo;
        public String accountName;
        public int acqId;
        public int amount;
        public String format;
        public String template;
    }

    private static class ApiResponse {
        public String code;
        public String desc;
        public Data data;
    }

    private static class Data {
        public String qrDataURL;
    }

    private static class BankDatum {
        public int id;
        public String name;
        public String code;
        public String bin;
        public String shortName;
        public String logo;
        public int transferSupported;
        public int lookupSupported;
        public String short_name;
        public int support;
        public int isTransfer;
        public String swift_code;

        @Override
        public String toString() {
            return "(" + bin + ")" + shortName;
        }
    }

    private static class Bank {
        public String code;
        public String desc;
        public List<BankDatum> data;
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
        inid = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        cboSupplierName = new combo_suggestion.ComboBoxSuggestion();
        txtSupplierCity = new textfield_suggestion.TextFieldSuggestion();
        jLabel11 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txtSupplierID = new textfield_suggestion.TextFieldSuggestion();
        jLabel9 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        SalesUserID = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBarcode = new textfield_suggestion.TextFieldSuggestion();
        cboProductName = new combo_suggestion.ComboBoxSuggestion();
        jLabel13 = new javax.swing.JLabel();
        p_qty = new textfield_suggestion.TextFieldSuggestion();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        u_price = new javax.swing.JLabel();
        sadsadasdga = new javax.swing.JLabel();
        tot_price = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        l_stqty = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        myButton8 = new button.MyButton();
        myButton9 = new button.MyButton();
        myButton10 = new button.MyButton();
        textFieldSuggestion24 = new textfield_suggestion.TextFieldSuggestion();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtShipCost = new textfield_suggestion.TextFieldSuggestion();
        jLabel19 = new javax.swing.JLabel();
        cboTaxAmount = new combo_suggestion.ComboBoxSuggestion();
        jLabel21 = new javax.swing.JLabel();
        cboDiscountRate = new combo_suggestion.ComboBoxSuggestion();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtSubTotal = new javax.swing.JLabel();
        txtShippingCost = new javax.swing.JLabel();
        txtTaxAmount = new javax.swing.JLabel();
        txtDiscountAmount = new javax.swing.JLabel();
        bill_tot = new javax.swing.JTextField();
        balance = new javax.swing.JTextField();
        txtDiscountRate = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        tot_qty = new textfield_suggestion.TextFieldSuggestion();
        txtItem = new textfield_suggestion.TextFieldSuggestion();
        jPanel12 = new javax.swing.JPanel();
        myButton11 = new button.MyButton();
        tabbedPaneCustom1 = new raven.tabbed.TabbedPaneCustom();
        jPanel13 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        pay_amt = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cboPaymentType = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtVoucher = new javax.swing.JTextField();
        myButton12 = new button.MyButton();
        jPanel15 = new javax.swing.JPanel();
        lblQRCode = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtAmount = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        myButton13 = new button.MyButton();
        txtGetVoucher = new textfield_suggestion.TextFieldSuggestion();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        inid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        inid.setForeground(new java.awt.Color(51, 153, 255));
        inid.setText("0");
        inid.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/calendar (1).png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Date:");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(51, 153, 255));
        lblDate.setText("2019-05-20");
        lblDate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/clock.png"))); // NOI18N

        lblTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(51, 153, 255));
        lblTime.setText("03:10:18: PM");
        lblTime.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("Time:");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 153, 255));
        jLabel26.setText("INVOICE NO:");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inid)
                .addGap(476, 476, 476)
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(lblTime)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inid, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTime)
                                .addComponent(jLabel7))
                            .addComponent(jLabel33)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(lblDate))
                            .addComponent(jLabel32))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/customer.png"))); // NOI18N

        cboSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSupplierNameActionPerformed(evt);
            }
        });
        cboSupplierName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSupplierNameKeyReleased(evt);
            }
        });

        txtSupplierCity.setEditable(false);
        txtSupplierCity.setForeground(new java.awt.Color(51, 153, 255));
        txtSupplierCity.setToolTipText("");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 153, 255));
        jLabel11.setText(" City:");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/buildings20.png"))); // NOI18N

        txtSupplierID.setEditable(false);
        txtSupplierID.setForeground(new java.awt.Color(51, 153, 255));
        txtSupplierID.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 153, 255));
        jLabel9.setText("ID/QR:");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/qr-code20.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("Sales User:");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        SalesUserID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        SalesUserID.setForeground(new java.awt.Color(51, 153, 255));
        SalesUserID.setText("0");
        SalesUserID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addGap(18, 18, 18)
                .addComponent(cboSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SalesUserID)
                .addGap(127, 127, 127))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(230, 230, 230)
                    .addComponent(jLabel39)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel9)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtSupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(25, 25, 25)
                    .addComponent(jLabel40)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel11)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtSupplierCity, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(231, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(SalesUserID)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtSupplierCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(4, 4, 4)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        txtBarcode.setEditable(false);
        txtBarcode.setForeground(new java.awt.Color(51, 153, 255));
        txtBarcode.setToolTipText("");

        cboProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProductNameActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 153, 255));
        jLabel13.setText("Quantity:");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        p_qty.setForeground(new java.awt.Color(51, 153, 255));
        p_qty.setToolTipText("");
        p_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                p_qtyKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Barcode:");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setText("Unit Price:");

        u_price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        u_price.setForeground(new java.awt.Color(51, 153, 255));
        u_price.setText("00.00");

        sadsadasdga.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sadsadasdga.setForeground(new java.awt.Color(51, 153, 255));
        sadsadasdga.setText("Total price:");

        tot_price.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tot_price.setForeground(new java.awt.Color(51, 153, 255));
        tot_price.setText("00.00");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 153, 255));
        jLabel15.setText("Stock Qty:");

        l_stqty.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        l_stqty.setForeground(new java.awt.Color(51, 153, 255));
        l_stqty.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_stqty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(u_price)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sadsadasdga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tot_price)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(u_price)
                        .addComponent(sadsadasdga)
                        .addComponent(tot_price)
                        .addComponent(jLabel15)
                        .addComponent(l_stqty))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addComponent(p_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        myButton8.setText("Add to Card");
        myButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton8ActionPerformed(evt);
            }
        });

        myButton9.setText("Remove");
        myButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton9ActionPerformed(evt);
            }
        });

        myButton10.setText("Remove All");
        myButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton10ActionPerformed(evt);
            }
        });

        textFieldSuggestion24.setForeground(new java.awt.Color(51, 153, 255));
        textFieldSuggestion24.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 153, 255));
        jLabel10.setText("Note:");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldSuggestion24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(myButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(myButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(myButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(myButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldSuggestion24, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Inv", "ProductName", "Barcode", "Quantity", "UnitPrice", "TotalPrice"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 153, 255));
        jLabel24.setText("Sub Total:");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("Shipping Cost:");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("Discount Amount:");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 153, 255));
        jLabel25.setText("Tax Amount:");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(0, 204, 102));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Grand Total Amount:");

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 0, 51));
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setText("Total Due/Balance:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 153, 255));
        jLabel27.setText("Discount Rate:");
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel24)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Shipping Cost:");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtShipCost.setForeground(new java.awt.Color(51, 153, 255));
        txtShipCost.setToolTipText("");
        txtShipCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtShipCostKeyReleased(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 153, 255));
        jLabel19.setText("Tax AMount:");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboTaxAmount.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0%", "8%", "10%" }));
        cboTaxAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTaxAmountActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 153, 255));
        jLabel21.setText("Discount Rate:");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboDiscountRate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0%", "5%", "8%", "10%", "15%", "20%", " " }));
        cboDiscountRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDiscountRateActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 153, 255));
        jLabel28.setText("%");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 153, 255));
        jLabel29.setText("%");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtShipCost, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboDiscountRate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(cboTaxAmount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShipCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cboTaxAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cboDiscountRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSubTotal.setForeground(new java.awt.Color(51, 153, 255));
        txtSubTotal.setText("0.0");
        txtSubTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtShippingCost.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtShippingCost.setForeground(new java.awt.Color(51, 153, 255));
        txtShippingCost.setText("00.00");
        txtShippingCost.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtTaxAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTaxAmount.setForeground(new java.awt.Color(51, 153, 255));
        txtTaxAmount.setText("00.00");
        txtTaxAmount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtDiscountAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscountAmount.setForeground(new java.awt.Color(51, 153, 255));
        txtDiscountAmount.setText("00.00");
        txtDiscountAmount.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        bill_tot.setEditable(false);
        bill_tot.setBackground(new java.awt.Color(0, 204, 102));
        bill_tot.setForeground(new java.awt.Color(255, 255, 255));
        bill_tot.setText("00.00");

        balance.setEditable(false);
        balance.setBackground(new java.awt.Color(255, 0, 51));
        balance.setForeground(new java.awt.Color(255, 255, 255));
        balance.setText("00.00");

        txtDiscountRate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiscountRate.setForeground(new java.awt.Color(51, 153, 255));
        txtDiscountRate.setText("00.00");
        txtDiscountRate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSubTotal)
                        .addComponent(txtShippingCost)
                        .addComponent(txtTaxAmount)
                        .addComponent(txtDiscountAmount)
                        .addComponent(bill_tot)
                        .addComponent(balance))
                    .addComponent(txtDiscountRate))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSubTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtShippingCost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTaxAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiscountAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiscountRate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bill_tot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 153, 255));
        jLabel22.setText("NO of Quantity:");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 153, 255));
        jLabel23.setText("NO of Item:");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tot_qty.setEditable(false);
        tot_qty.setForeground(new java.awt.Color(51, 153, 255));
        tot_qty.setToolTipText("");

        txtItem.setEditable(false);
        txtItem.setForeground(new java.awt.Color(51, 153, 255));
        txtItem.setToolTipText("");

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        myButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/checkout.png"))); // NOI18N
        myButton11.setText("Checkout");
        myButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 153, 255));
        jLabel20.setText("Paid Amount:");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pay_amt.setText("0");
        pay_amt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pay_amtKeyReleased(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 153, 255));
        jLabel37.setText("Payment Type:");
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cboPaymentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Credit", "Cash", "Cheque", "Baking" }));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(51, 153, 255));
        jLabel38.setText("Ref NO/Check NO:");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextField6.setEditable(false);
        jTextField6.setBackground(new java.awt.Color(255, 255, 255));
        jTextField6.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel20))
                    .addComponent(pay_amt, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel37))
                    .addComponent(cboPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel38)
                        .addGap(10, 10, 10)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboPaymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pay_amt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37))
                        .addGap(28, 28, 28)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        tabbedPaneCustom1.addTab("Cash", jPanel13);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "discount_id", "voucher_price", "user_id"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setText("Voucher Code:");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtVoucher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtVoucherKeyReleased(evt);
            }
        });

        myButton12.setText("GiftVoucher");
        myButton12.setBorderColor(new java.awt.Color(255, 51, 51));
        myButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(myButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                        .addGap(196, 196, 196))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabbedPaneCustom1.addTab("Voucher", jPanel14);

        jButton1.setText("Generate Code");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(txtAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(lblQRCode, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        tabbedPaneCustom1.addTab("QR PAYMENT", jPanel15);

        myButton13.setText("User Voucher");
        myButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton13ActionPerformed(evt);
            }
        });

        txtGetVoucher.setEditable(false);
        txtGetVoucher.setForeground(new java.awt.Color(51, 153, 255));
        txtGetVoucher.setToolTipText("");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tot_qty, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(txtItem, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtGetVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(tot_qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(myButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGetVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabbedPaneCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboSupplierNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSupplierNameKeyReleased

        loadComboBoxSupplier();
    }//GEN-LAST:event_cboSupplierNameKeyReleased

    private void cboSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSupplierNameActionPerformed

        loadComboBoxSupplier();
    }//GEN-LAST:event_cboSupplierNameActionPerformed

    private void cboProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProductNameActionPerformed
        String name = cboProductName.getSelectedItem().toString();
        try{
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery("SELECT barcode,unit_price,pro_quantity FROM products WHERE pro_name = '"+name+"'");
            
            if(rs.next()){
                u_price.setText(String.valueOf(rs.getFloat("unit_price")));
                
                txtBarcode.setText(rs.getString("barcode"));
                l_stqty.setText(String.valueOf(rs.getInt("pro_quantity")));
                
            }
            pro_tot_cal();
            s.close();
            rs.close();
            db.myCon().close();
        }catch(Exception e){
            System.out.println(e);
            System.out.println("sai o day a");
        }
    }//GEN-LAST:event_cboProductNameActionPerformed

    private void p_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_p_qtyKeyReleased
        pro_tot_cal();
    }//GEN-LAST:event_p_qtyKeyReleased

    private void myButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton8ActionPerformed
        Double sell_qty = Double.valueOf(p_qty.getText());
        Double stk_qty = Double.valueOf(l_stqty.getText());
        if(sell_qty<stk_qty){
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();

            Vector v = new Vector();

            v.add(inid.getText());
            v.add(cboProductName.getSelectedItem().toString());
            v.add(txtBarcode.getText());
            v.add(p_qty.getText());
            v.add(u_price.getText());
            v.add(tot_price.getText());

            dt.addRow(v);
            cart_total();
            tot();
        }else{
            JOptionPane.showMessageDialog(balance, "Stock Have "+stk_qty+" Qty Only");
        }
    }//GEN-LAST:event_myButton8ActionPerformed

    private void myButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton9ActionPerformed
        try{
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            int rw = jTable1.getSelectedRow();
            dt.removeRow(rw);
        }catch(Exception e){
            System.out.println(e);
        }
        cart_total();
        tot();
    }//GEN-LAST:event_myButton9ActionPerformed

    private void myButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton10ActionPerformed
        DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
        dt.setRowCount(0);
        cart_total();
        tot();
    }//GEN-LAST:event_myButton10ActionPerformed

    private void pay_amtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pay_amtKeyReleased
        tot();
    }//GEN-LAST:event_pay_amtKeyReleased
        
    private void myButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton11ActionPerformed
        boolean check =true;
        if(cboSupplierName.getSelectedItem().toString().equals("Selected")){
            check=false;
            JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng thanh toán");
        }
        
        
        if(check==true){
            try{
            // sales DB
            int inv_id = Integer.parseInt(inid.getText());
            String inv_date =lblDate.getText();
            String inv_time = lblTime.getText();
            String c_name = cboSupplierName.getSelectedItem().toString();
            int totqty = Integer.parseInt(tot_qty.getText());
            Float tot_bill = Float.parseFloat(bill_tot.getText());
            Float bnlc = Float.parseFloat(balance.getText());
            //paid check
            Double tot = Double.valueOf(bill_tot.getText());
            Double pid = Double.valueOf(pay_amt.getText());
            String note = textFieldSuggestion24.getText();
            String payment_Type = cboPaymentType.getSelectedItem().toString();
            String status = null;
            if(pid.equals(0.0)){
                status = "Unpaid";
            }else if(tot>pid){
                status = "Partial";
            }else if(tot<=pid){
                status = "Paid";
            }
                Statement ss = db.myCon().createStatement();
                ss.executeUpdate("INSERT INTO invoices (invoice_id,invoice_date,invoice_time,customer_name,invoice_quantity,invoice_total_amount,invoice_status,invoice_paid_amount,balance,invoice_payment_type,invoice_note,users_id) VALUES ('"+inv_id+"','"+inv_date+"','"+inv_time+"',N'"+c_name+"','"+totqty+"','"+tot_bill+"','"+status+"','"+pid+"','"+bnlc+"','"+payment_Type+"','"+note+"','"+SalesUserID.getText()+"')"); 
                ss.close();
                db.myCon().close();
        }catch(Exception e){


            System.out.println(e);
        }
        // data send to database
            try{
            DefaultTableModel dt = (DefaultTableModel) jTable1.getModel();
            int rc = dt.getRowCount();
            
            for(int i=0;i<rc;i++){
                String inid = dt.getValueAt(i, 0).toString();
                String p_name = dt.getValueAt(i, 1).toString();
                String bar_code = dt.getValueAt(i, 2).toString();
                String qty = dt.getValueAt(i, 3).toString();
                String un_price = dt.getValueAt(i, 4).toString();
                String tot_price = dt.getValueAt(i, 5).toString();
                
                Statement s = db.myCon().createStatement();
                s.executeUpdate("INSERT INTO sales (invoice_id,product_name,barcode,quantity,sales_unit_price,sales_sub_total,users_id) VALUES ('"+inid+"',N'"+p_name+"','"+bar_code+"','"+qty+"','"+un_price+"','"+tot_price+"','"+SalesUserID.getText()+"')");
                s.close();
                db.myCon().close();
            }
            JOptionPane.showMessageDialog(null, "Data Saved");
            
            
        }catch(HeadlessException | SQLException e){
            System.out.println(e);
        }
            
        
        
        try{
            int id = Integer.parseInt(inid.getText());
            id++;
            Statement s = db.myCon().createStatement();
            s.executeUpdate("UPDATE extra SET val='"+id+"' WHERE exid =1");
            s.close();
            db.myCon().close();
        }catch(SQLException e){
            System.out.println(e);
        }
        
        // print or view ireport bill
        try{
            HashMap para = new HashMap();
        
            para.put("inv_id", inid.getText());
            Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    InputStream file = getClass().getResourceAsStream("/Reports/print.jrxml");
                    JasperDesign jasperDesign = JRXmlLoader.load(file);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, para, db.myCon());
                    
                    String outputFileName = "Invoices"+inid.getText()+".pdf";

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
 
        }catch(Exception e){
            System.out.println(e);
        }
        stckup();
        try {
            db.myCon().close();
        } catch (SQLException ex) {
            
        }
        
    }
        
    
        
        
    }//GEN-LAST:event_myButton11ActionPerformed
    
    private void txtVoucherKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVoucherKeyReleased
        
    }//GEN-LAST:event_txtVoucherKeyReleased

    private void myButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton12ActionPerformed
        String voucher_name = txtVoucher.getText();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        try {
            String sql = "Select * from discounts where discount_id = N'"+voucher_name+"'";
            Statement s = db.myCon().createStatement();
            ResultSet rs = s.executeQuery(sql);
            if(rs.next()){
                Vector v = new Vector();
                v.add(rs.getString("discount_id"));
                v.add(rs.getString("voucher_name"));
                v.add(rs.getString("users_id"));
                txtGetVoucher.setText(rs.getString("voucher_name"));
                model.addRow(v);
                
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_myButton12ActionPerformed

    private void myButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton13ActionPerformed
        txtDiscountAmount.setText(txtGetVoucher.getText());
        jTextField6.setText(txtGetVoucher.getText());
        tot();
    }//GEN-LAST:event_myButton13ActionPerformed
    public Boolean isNumber(String number){
             String regexNumber = "[0-9]+";
             return number.matches(regexNumber);
    }
    private void txtShipCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShipCostKeyReleased
        String cost = txtShipCost.getText();
        if(isNumber(cost)){
            if(cost.isEmpty()){
                txtShippingCost.setText(String.valueOf(0));
            }else{
                txtShippingCost.setText(String.valueOf(cost));
            }
             tot();
        }else{
            JOptionPane.showMessageDialog(this, "Chỉ nhập số");
        }
    }//GEN-LAST:event_txtShipCostKeyReleased

    private void cboTaxAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTaxAmountActionPerformed
        String taxAmount = cboTaxAmount.getSelectedItem().toString();
        txtTaxAmount.setText("");
        float taxTotal = 0;
        float subTotal = Float.parseFloat(txtSubTotal.getText());
  
        if(taxAmount.equals("0%")){
            taxTotal = 0;
        }else if(taxAmount.equals("8%")){
            taxTotal = 0.08f*subTotal;
        }else if(taxAmount.equals("10%")){
            taxTotal = 0.1f*subTotal;
        }
        txtTaxAmount.setText(String.valueOf(taxTotal));
        tot();
    }//GEN-LAST:event_cboTaxAmountActionPerformed

    private void cboDiscountRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDiscountRateActionPerformed
        String taxAmount = cboDiscountRate.getSelectedItem().toString();
        float taxTotal = 0;
        float subTotal = Float.parseFloat(txtSubTotal.getText());
        txtDiscountRate.setText(String.valueOf(""));
        if(taxAmount.equals("0%")){
            taxTotal = 0;
        }else if(taxAmount.equals("5%")){
            taxTotal = 0.05f*subTotal;
        }else if(taxAmount.equals("8%")){
            taxTotal = 0.08f*subTotal;
        }else if(taxAmount.equals("10%")){
            taxTotal = 0.1f*subTotal;
        }else if(taxAmount.equals("15%")){
            taxTotal = 0.15f*subTotal;
        }else if(taxAmount.equals("20%")){
            taxTotal = 0.2f*subTotal;
        }
        txtDiscountRate.setText(String.valueOf(taxTotal));
        tot();
    }//GEN-LAST:event_cboDiscountRateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       generateQRCode() ;
       pay_amt.setEditable(false);
       pay_amt.setText(txtAmount.getText());
       tot();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       pay_amt.setEditable(true);
       pay_amt.setText("0");
       txtAmount.setText("0");
       lblQRCode.setIcon(null);
       tot();
    }//GEN-LAST:event_jButton2ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SalesUserID;
    private javax.swing.JTextField balance;
    private javax.swing.JTextField bill_tot;
    private combo_suggestion.ComboBoxSuggestion cboDiscountRate;
    private javax.swing.JComboBox<String> cboPaymentType;
    private combo_suggestion.ComboBoxSuggestion cboProductName;
    private combo_suggestion.ComboBoxSuggestion cboSupplierName;
    private combo_suggestion.ComboBoxSuggestion cboTaxAmount;
    private combo_suggestion.ComboSuggestionUI comboSuggestionUI1;
    private com.raven.datechooser.DateChooser dateChooser;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel inid;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel l_stqty;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblQRCode;
    private javax.swing.JLabel lblTime;
    private button.MyButton myButton10;
    private button.MyButton myButton11;
    private button.MyButton myButton12;
    private button.MyButton myButton13;
    private button.MyButton myButton8;
    private button.MyButton myButton9;
    private textfield_suggestion.TextFieldSuggestion p_qty;
    private javax.swing.JTextField pay_amt;
    private javax.swing.JLabel sadsadasdga;
    private raven.tabbed.TabbedPaneCustom tabbedPaneCustom1;
    private textfield_suggestion.TextFieldSuggestion textFieldSuggestion24;
    private javax.swing.JLabel tot_price;
    private textfield_suggestion.TextFieldSuggestion tot_qty;
    private javax.swing.JTextField txtAmount;
    private textfield_suggestion.TextFieldSuggestion txtBarcode;
    private javax.swing.JLabel txtDiscountAmount;
    private javax.swing.JLabel txtDiscountRate;
    private textfield_suggestion.TextFieldSuggestion txtGetVoucher;
    private textfield_suggestion.TextFieldSuggestion txtItem;
    private textfield_suggestion.TextFieldSuggestion txtShipCost;
    private javax.swing.JLabel txtShippingCost;
    private javax.swing.JLabel txtSubTotal;
    private textfield_suggestion.TextFieldSuggestion txtSupplierCity;
    private textfield_suggestion.TextFieldSuggestion txtSupplierID;
    private javax.swing.JLabel txtTaxAmount;
    private javax.swing.JTextField txtVoucher;
    private javax.swing.JLabel u_price;
    // End of variables declaration//GEN-END:variables
}
