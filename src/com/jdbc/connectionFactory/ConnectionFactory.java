package com.jdbc.connectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		try {
			String user = "root";
			String password = "K@ka128128";
			
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/meu_db", user, password);
		}  catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
