/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class XDate {
    static SimpleDateFormat formater = new SimpleDateFormat();
    /*
        Chuyển đổi String sang Date
        @param date là string cần chuyền
        @param pattern là định dạng thời gian
        @return Date kết quả
    */
    public static Date toDate(String date, String pattern){
        try {
            formater.applyPattern(pattern);
            return formater.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    /*
        Chuyển đổi Date sang String
        @param date là date cần chuyền
        @param pattern là định dạng thời gian
        @return String kết quả
    */
 
    public static String toString(Date date,String pattern){
        formater.applyPattern(pattern);
        return formater.format(date);
    }
    
    public static Date now(){
        return new Date();
    }
    
    /*
        Bổ sung số ngày vào thời gian
        @param date là thời gian hiện có
        @param days là số ngày cần bổ sung vào date
        @return Date kết quả
    */
    public static Date addDays(Date date,long days){
        date.setTime(date.getTime() + days*24*60*60*1000);
        return date;
    }
    
    

}
