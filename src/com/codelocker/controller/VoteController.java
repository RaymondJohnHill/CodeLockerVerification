package com.codelocker.controller;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.codelocker.model.SQLConnection;
import com.codelocker.model.UniversityProfile;
import com.codelocker.model.UserProfile;

public class VoteController {

	private final UniversityProfile currentUni;
	private SQLConnection connection;
	private DataSource dataSource;
	
	/**
	 * Instantiates the class along with the SQLConnection and the UniversityProfile. 
	 * @param email the recipients email
	 */
	public VoteController(String email) {
		PropertyConfigurator.configure("./configs/base.configuration");
		
		try {
            //get the datasource
            Context initContext  = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            dataSource = (DataSource)envContext.lookup("codelocker_connection");
            connection = new SQLConnection(dataSource.getConnection());             
        } catch (NamingException e) {
        	Logger.getLogger(SQLConnection.class).error(e.getMessage());
        	System.exit(1);
        } catch (SQLException e) {
        	Logger.getLogger(SQLConnection.class).error(e.getMessage());
        	System.exit(1);
		}
		
		currentUni = new UniversityProfile(getDomainSuffix(email), connection.getConnection());
	}
	
	/**
	 * Gets the domain suffix from the users email address.
	 * @param email the users email address
	 * @return the domain suffix
	 */
	private String getDomainSuffix(String email) {
		return email.split("@")[1];
	}
	
	public void addVote() {
		currentUni.incrementUniversityVotes();
	}
}
