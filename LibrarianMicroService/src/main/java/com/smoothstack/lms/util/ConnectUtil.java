package com.smoothstack.lms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class ConnectUtil {

	private final String driver = "com.mysql.cj.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/library?useSSL=false";
	private final String username = "root";
	private final String password = "root";

	public ConnectUtil() throws ClassNotFoundException {
		Class.forName(driver);
	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;
		synchronized (this) {
			connection = DriverManager.getConnection(url, username, password);
			connection.setAutoCommit(false);
		}
		return connection;
	}

}
