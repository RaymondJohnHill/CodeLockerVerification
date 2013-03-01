package com.codelocker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class SQLConnection {

	private Connection connection;
	private static Logger logger;
	
	/**
	 * Starts a new connection and builds a database.
	 * @param host the host url to connect to
	 * @param user the username to log in with
	 * @param pass the password to log in with
	 */
	public SQLConnection(String host, String user, String pass) {
		//creates a new logger
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("./configs/SQLConnection.configuration");
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
									"UNIQUE(email))");
			//Credentials table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Credentials (" +
									"user_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"password_digest VARBINARY(128)," +
									"password_salt VARBINARY(32)," +
//									"PRIMARY KEY (user_id))");
									"INDEX(user_id)," +
									"FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE)");
			//UnverifiedUsers table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS UnverifiedUsers (" +
									"user_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"verification_code VARCHAR(128)," +
//									"PRIMARY KEY (user_id))");
									"INDEX(user_id)," +
									"FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE)");
			
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
		}
	}
	
	/**
	 * Returns the connection that was made when this was SQLConnection was instantiated.
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
}
