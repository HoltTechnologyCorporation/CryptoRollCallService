package com.crc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Single service responsible for all DB interaction
 * 
 * @author shankarganesh1234
 *
 */
public class DatabaseServiceImpl {

	static final String DB_URL = "";
	static final String DB_USERNAME = "";
	static final String DB_PASSWORD = "";

	static final String HASH_EXISTS = "SELECT COUNT(*) FROM HASH_TABLE WHERE HASH=?";
	static final String HASH_CREATE = "INSERT INTO HASH_TABLE (HASH, NAME) VALUES (?, ?)";
	static final String HASH_GET = "SELECT * FROM HASH_TABLE WHERE NAME=? AND HASH=?";
	static final String PASSPHRASE_GET = "SELECT word FROM words ORDER BY RANDOM() LIMIT ?";

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the corresponding hash entry for the name/hash combination.
	 * Returns null if not match found
	 * 
	 * @param name
	 * @param hash
	 * @return
	 */
	public String getHashEntry(String name, String hash) {
		// if conn is null, short circuit.
		Connection conn = getConnection();
		if (conn == null)
			return null;

		ResultSet rs = null;
		String hashEntry = null;

		// check count of existing hash. If it exists, then return false
		try (PreparedStatement ps = conn.prepareStatement(HASH_GET)) {
			ps.setString(1, name);
			ps.setString(2, hash);
			rs = ps.executeQuery();
			while (rs.next()) {
				hashEntry = rs.getString(1);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return hashEntry;
	}

	/**
	 * Returns a list of random strings based on the difficulty setting
	 * Difficulty denotes the number of strings returned as list
	 * 
	 * @param difficulty
	 * @return
	 */
	public String getRandomPassPhrase(int difficulty) {

		// if conn is null, short circuit.
		Connection conn = getConnection();
		if (conn == null)
			return null;

		ResultSet rs = null;
		String passPhrase = "";

		// check count of existing hash. If it exists, then return false
		try (PreparedStatement ps = conn.prepareStatement(PASSPHRASE_GET)) {
			ps.setInt(1, difficulty);
			rs = ps.executeQuery();
			while (rs.next()) {
				passPhrase = passPhrase + rs.getString(1) + " ";
			}
			passPhrase = passPhrase.trim();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return passPhrase;
	}

	/**
	 * Checks for any hash collision. Name + passphrase should not generate the
	 * same hash. If this condition occurs, then logic should discard the
	 * current hash and create a new hash and check again
	 * 
	 * @param hash
	 * @return
	 */
	public boolean isHashExists(String hash) {

		// if conn is null, short circuit.
		Connection conn = getConnection();
		if (conn == null)
			return false;

		boolean result = true;
		ResultSet rs = null;

		// check count of existing hash. If it exists, then return false
		try (PreparedStatement ps = conn.prepareStatement(HASH_EXISTS)) {
			ps.setString(1, hash);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) == 0) {
					result = false;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/**
	 * Creates an entry in the hash_table hash is the primary key, so in case of
	 * collisions, the insert would fail It is the responsibility of the service
	 * to implement a retry in case of hash collisions
	 * 
	 * @param hash
	 * @return
	 */
	public boolean createHashEntry(String name, String hash) {
		// if conn is null, short circuit.
		Connection conn = getConnection();
		if (conn == null)
			return false;

		boolean result = true;
		// insert new hash into the table
		try (PreparedStatement ps = conn.prepareStatement(HASH_CREATE)) {
			ps.setString(1, hash);
			ps.setString(2, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Returns a connection instance
	 * 
	 * @return
	 */
	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
