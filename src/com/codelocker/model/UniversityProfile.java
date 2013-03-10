package com.codelocker.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class UniversityProfile {

	private final Connection connection;
	private final String domain_suffix;
	private final int university_id;
	
	private ResultSet results;
	
	/**
	 * UniversityProfile creates a new university profile based on the domain suffix.
	 * @param domain_suffix the universities domain suffix
	 * @param connection the current SQL connection
	 */
	public UniversityProfile(String domain_suffix, Connection connection) {
		//sets local university_id, domain_suffix, and connection variables for later use
		this.connection = connection;
		this.domain_suffix = domain_suffix;
		university_id = getUniversityID();
	}
	
	/**
	 * Gets the university_id field value from the database using the domain_suffix.
	 * @return the unique int that represents the university_id
	 */
	private int getUniversityID() {
		PreparedStatement prepared;
		//sets the into to -1 so we know we messed
		//up somewhere if we get this as a result.
		int university_id = -1;
		
		try {
			//builds the prepared statement.
			prepared = connection.prepareStatement("SELECT university_id FROM codelocker.universities WHERE domain_suffix=?");
			prepared.setString(1, domain_suffix);
			results = prepared.executeQuery();
			
			//grabs the first result, that's all
			//we need since it should be unique.
			while(results.next()) {
				university_id = results.getInt(1);
			}
			
		} catch (SQLException e){
			//uh oh! this just logs the errors we get.
			Logger.getLogger(this.getClass()).error(e.getMessage());
		}
		
		return university_id;
	}
	
	/**
	 * Increments the vote count by 1.
	 */
	public void incrementUniversityVotes() {
		PreparedStatement prepared;
		
		try {
			prepared = connection.prepareStatement("UPDATE codelocker.university_votes SET votes=(votes+1) WHERE university_id=?");
			prepared.setInt(1, university_id);
			prepared.executeUpdate();
			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
		}
	}
	
	/**
	 * Gets the number of votes that the university currently has.
	 * @return the current number of votes
	 */
	public int getUniversityVotes() {
		PreparedStatement prepared;
		int votes = 0;
		
		try {
			prepared = connection.prepareStatement("UPDATE codelocker.university_votes SET votes=(votes+1) WHERE university_id=?");
			prepared.setInt(1, university_id);
			results = prepared.executeQuery();
			
			while(results.next()) {
				votes = results.getInt(1);
			}			
		} catch (SQLException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
		}
		
		return votes;
	}
	
}
