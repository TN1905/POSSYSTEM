/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import com.raven.entity.users;

/**
 *
 * @author ADMIN
 */
public class Auth {
    /*
        Đối tượng này chứa thông tin người dùng sau khi đăng nhập
    */
    public static users user = null;
    /*
        Xóa thông tin người sử dụng khi có yêu cầu đăng xuất
    */
    public static void clear(){
        Auth.user = null;
    }
    /*
        Kiểm tra xem đăng nhập hay chưa
        @return đăng nhập hay chưa
    */
    public static boolean isLogin(){
        return Auth.user != null;
    }
    
//    public static boolean isManager(){
//        return Auth.isLogin() && user.isVaiTro();
//    }
}
