/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.form;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author ADMIN
 */
public class MaHoa {
    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static void main(String[] args) {
        String plaintextPassword = "MySecurePassword123";
        String hashedPassword = hashPassword(plaintextPassword);

        System.out.println("Hashed Password: " + hashedPassword);

        // Kiểm tra mật khẩu có khớp với mật khẩu đã lưu không
        boolean isPasswordCorrect = checkPassword("MySecurePassword123", hashedPassword);
        System.out.println("Is Password Correct: " + isPasswordCorrect);
    }
}
