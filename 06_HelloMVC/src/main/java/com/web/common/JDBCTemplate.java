package com.web.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	public static Connection getConnection() {
		Connection conn = null;
		String path = JDBCTemplate.class.getResource("/driver.properties").getPath();
		Properties prop = new Properties();
		try(FileReader fr = new FileReader(path)) {
			prop.load(fr);
			Class.forName(prop.getProperty("driver"));
			conn= DriverManager.getConnection(prop.getProperty("url"),
											  prop.getProperty("user"),
											  prop.getProperty("pass"));
			conn.setAutoCommit(false);
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed()) stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
	}
	public static void close(ResultSet rs) {
		try {
			if(rs!=null && !rs.isClosed()) rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
	}
	public static void commit(Connection conn){
		try {
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn){
		try {
			conn.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
