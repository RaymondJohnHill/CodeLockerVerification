package com.codelocker.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class SQLConnection {

	private Connection connection;
	
	/**
	 * Starts a new connection and builds a database.
	 * @param host the host url to connect to
	 * @param user the username to log in with
	 * @param pass the password to log in with
	 */
	public SQLConnection(Connection connection) {
		Statement statement;
		
		try{
			this.connection = connection;
			statement = connection.createStatement();
			//codelocker database creation.
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS codelocker");
			//Use the codelocker database.
			statement.execute("USE codelocker");
			//users table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
									"user_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"email VARCHAR(128) NOT NULL," +
									"PRIMARY KEY (user_id)," +
									"UNIQUE(email))");
			//credentials table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS credentials (" +
									"user_id INT UNSIGNED NOT NULL," +
									"password_digest BINARY(128) NOT NULL," +
									"password_salt BINARY(32) NOT NULL," +
									"INDEX(user_id)," +
									"FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE)");
			//unverified_users table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unverified_users (" +
									"user_id INT UNSIGNED NOT NULL," +
									"verification_code VARCHAR(128) NOT NULL," +
									"INDEX(verification_code)," +
									"FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE)");
			//universities table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS universities (" +
									"university_id INT UNSIGNED AUTO_INCREMENT NOT NULL," +
									"university_name VARCHAR(128) NOT NULL," +
									"domain_suffix VARCHAR(32) NOT NULL," +
									"state CHAR(2) NOT NULL," +
									"PRIMARY KEY (university_id))");
			//university_votes table creation.
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS university_votes (" +
									"university_id INT UNSIGNED NOT NULL," +
									"votes INT NOT NULL," +
									"FOREIGN KEY (university_id) REFERENCES universities(university_id))");
			
		} catch (SQLException e) {
			Logger.getLogger(SQLConnection.class).error(e.getMessage() + " Could not create tables.");
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
