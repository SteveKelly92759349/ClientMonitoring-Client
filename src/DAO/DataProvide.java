package DAO;

import java.sql.*;
import java.util.*;

public class DataProvide {
	private static Connection conn;
	private static final String DATABASE_NAME = "chatapplication";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123456";
	private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;    
    
	public static Connection getConnection() {
		try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);

            Properties info = new Properties();
            info.setProperty("characterEncoding", "utf8");
            info.setProperty("user", USERNAME);
            info.setProperty("password", PASSWORD);            
            conn = DriverManager.getConnection(URL_MYSQL, info);
        } catch (Exception e){
            System.out.println("Lỗi kết nối CSDL!");
        	e.printStackTrace();
        }
		return conn; 
	}
}
