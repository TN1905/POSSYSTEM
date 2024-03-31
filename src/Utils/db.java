/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class db {
    public static final String connectURL = "jdbc:sqlserver://localhost:1433;databaseName=possystem;user=sa;password=123456;encrypt=true;trustServerCertificate=true";
        public static Connection myCon(){
            Connection con = null;
            try{
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                
                con = DriverManager.getConnection(connectURL);
                return con;
            }catch(ClassNotFoundException | SQLException e){
                System.out.println(e);
                return null;
            }
        }
        
        public static PreparedStatement getStmt(String sql,Object... args) throws SQLException{
            Connection connection =myCon();
            
            
       
            PreparedStatement pstmt = null;
            if(sql.trim().startsWith("{")){
                pstmt = connection.prepareCall(sql);
            }else{
                pstmt = connection.prepareStatement(sql);
            }
            for(int i =0;i<args.length;i++){
                pstmt.setObject(i+1, args[i]);
            }
         

            return pstmt;
           
        }
        
        public static int update(String sql,Object... args){
            try {
                PreparedStatement stmt = getStmt(sql,args);
                try {
                    return stmt.executeUpdate();
                } finally  {
                    stmt.getConnection().close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        public static ResultSet query(String sql,Object...args){
            try {
                PreparedStatement stmt = getStmt(sql,args);
                return stmt.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        public static Object value(String sql,Object... args){
            try {
                ResultSet rs = query(sql,args);
                if(rs.next()){
                    return rs.getObject(0);
                }
                rs.getStatement().getConnection().close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }
}
