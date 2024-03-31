import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.imageio.ImageIO;
import okhttp3.*;

public class VietQRPaymentApp extends JFrame {
    private JComboBox<BankDatum> comboBoxBanks;
    private JTextField txtAccountNo;
    private JTextField txtAccountName;
    private JTextField txtAmount;
    private JComboBox<String> comboBoxTemplate;
    private JLabel lblQRCode;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new VietQRPaymentApp();
        });
    }

    public VietQRPaymentApp() {
        setTitle("VietQR Payment");
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel lblBank = new JLabel("Bank:");
        comboBoxBanks = new JComboBox<>();
        panel.add(lblBank);
        panel.add(comboBoxBanks);

        JLabel lblAccountNo = new JLabel("Account No:");
        txtAccountNo = new JTextField("7307727"); // Giá trị cố định
        panel.add(lblAccountNo);
        panel.add(txtAccountNo);

        JLabel lblAccountName = new JLabel("Account Name:");
        txtAccountName = new JTextField("CHIEM NGUYEN TRI NGUYEN"); // Giá trị cố định
        panel.add(lblAccountName);
        panel.add(txtAccountName);

        JLabel lblAmount = new JLabel("Amount:");
        txtAmount = new JTextField();
        panel.add(lblAmount);
        panel.add(txtAmount);

        JLabel lblTemplate = new JLabel("Template:");
        String[] templates = {"compact", "detailed"};
        comboBoxTemplate = new JComboBox<>(templates);
        panel.add(lblTemplate);
        panel.add(comboBoxTemplate);

        JButton btnGenerate = new JButton("Generate QR Code");
        lblQRCode = new JLabel();
        lblQRCode.setSize(200, 200);
        txtAccountNo.setSize(100, 100);
        txtAccountName.setSize(100, 100);
        txtAmount.setSize(100, 100);
        panel.add(btnGenerate);
        panel.add(lblQRCode);

        add(panel);
        // Khởi tạo giao diện và các thành phần khác tương tự như trong code bạn cung cấp

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generateQRCode();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadBanks();
        setVisible(true);
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
            comboBoxBanks.setModel(new DefaultComboBoxModel<>(bankData.data.toArray(new BankDatum[0])));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private void generateQRCode() {
        try {
            BankDatum selectedBank = (BankDatum) comboBoxBanks.getSelectedItem();
            String apiUrl = "https://api.vietqr.io/v2/generate";
            selectedBank.id = 970416;
            ApiRequest apiRequest = new ApiRequest();
            apiRequest.accountNo = Long.parseLong(txtAccountNo.getText());
            apiRequest.accountName = txtAccountName.getText();
            apiRequest.acqId = selectedBank.id;
            apiRequest.amount = Integer.parseInt(txtAmount.getText());
            apiRequest.format = "text";
            apiRequest.template = (String) comboBoxTemplate.getSelectedItem();

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
                InputStream inputStream = new ByteArrayInputStream(qrImageData);
                Image qrImage = ImageIO.read(inputStream);
                lblQRCode.setIcon(new ImageIcon(qrImage));
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
}
 
        