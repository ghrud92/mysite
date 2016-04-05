package com.estsoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLWebDBConnection implements DBConnection{
	@Override
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
	         String url = "jdbc:mysql://localhost/webdb";
	         conn = DriverManager.getConnection(url, "webdb", "webdb");
		}catch (ClassNotFoundException e) {
	         System.out.println("드라이버를 찾을 수 없습니다." + e);
	    } 
		return conn;
	}
}
