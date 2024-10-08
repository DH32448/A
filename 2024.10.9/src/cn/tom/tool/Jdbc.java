package cn.tom.tool;

import java.sql.*;

public class Jdbc {
    private static String url="jdbc:mysql://localhost:3306/mark?" +
            "useSSL=true&verifyServerCertificate=true";
    private static String username = "root";
    private static String password = "123456";
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            System.out.println("加载驱动失败");
        }
    }
    public static Connection getCn() throws SQLException {
        Connection cn = DriverManager.getConnection(url,username,password);
        System.out.println("连接成功");
        return cn;
    }
    public static void close(Connection cn, PreparedStatement ps , ResultSet rs){
        try{
            if (rs!=null)rs.close();
            if (ps!=null)ps.close();
            if (cn!=null)cn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        Jdbc.getCn();
    }
}
