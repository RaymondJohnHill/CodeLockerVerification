package com.codelocker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {

	private Connection connection;
	private ExceptionLogger logger;
	
	public SQLConnection(String host, String user, String pass) {
		//creates a new logger with this class.
		logger = new ExceptionLogger(this.getClass());
		Statement statement;
		
		try{
			connection = DriverManager.getConnection(host, user, pass);
			statement = connection.createStatement();
			//CodeLocker database creation.
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS CodeLocker");
			//Use the CodeLocker database.
			statement.execute("USE CodeLocker");
			//Users table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (" +
									"user_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"email VARCHAR(128)," +
									"PRIMARY KEY (user_id)," +
									"UNIQUE(user_id, email))");
			//Credentials table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Credentials (" +
									"user_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"password_digest VARBINARY(128)," +
									"password_salt VARBINARY(32)," +
									"INDEX(user_id)," +
									"FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE");
			//UnverifiedUsers table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS UnverifiedUsers (" +
									"user_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"verification_code VARCHAR(128)," +
									"INDEX(user_id)," +
									"FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE");
			
		} catch (SQLException e) {
			logger.generateFatalReport(e.getMessage());
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
