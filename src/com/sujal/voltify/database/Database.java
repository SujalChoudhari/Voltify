package com.sujal.voltify.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Database {

	private Connection mConnection;
	private Statement mStatement;

	public Database(String url, String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		mConnection = DriverManager.getConnection(url, username, password);
		mStatement = mConnection.createStatement();
	}

	public void createMeter(int meterNo, String name, String password, String place, String city, int pincode)
			throws SQLException {
		createUser(meterNo, name, password, place, city, pincode, false);
	}

	public void createAdmin(String name, String password, String place, String city, int pincode) throws SQLException {
		createUser(new Random().nextInt(10000, 20000), name, password, place, city, pincode, true);
	}

	public void updateMeter(int meterNo, String name, String password, String place, String city, int pincode)
			throws SQLException {
		updateUser(meterNo, name, password, place, city, pincode, false);
	}

	public void updateAdmin(String name, String password, String place, String city, int pincode) throws SQLException {
		updateUser(new Random().nextInt(10000, 20000), name, password, place, city, pincode, true);
	}

	private void createUser(int meterNo, String name, String password, String place, String city, int pincode,
			boolean isAdmin) throws SQLException {

		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		mStatement.executeUpdate("INSERT INTO meter VALUES (" + meterNo + ",'" + name + "','" + password + "','"
				+ dateString + "','" + place + "','" + city + "'," + pincode + "," + isAdmin + ")");
	}

	private void updateUser(int meterNo, String name, String password, String place, String city, int pincode,
			boolean isAdmin) throws SQLException {

		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String sql = "UPDATE meter SET username = '" + name + "', user_password = '" + password + "', created_at = '"
				+ dateString + "', place = '" + place + "', city = '" + city + "', pincode = " + pincode
				+ ", administrator = " + isAdmin + " WHERE id = " + meterNo;

		mStatement.executeUpdate(sql);
	}

	public boolean verifyUser(String username, String password) throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter WHERE username = '" + username
				+ "' AND user_password = '" + password + "' AND administrator = false");
		return resultSet.next();
	}

	public boolean verifyAdmin(String username, String password) throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter WHERE username = '" + username
				+ "' AND user_password = '" + password + "' AND administrator = true");
		return resultSet.next();
	}

	public void createBill(int meterId, float units, float amount, boolean paid) throws SQLException {
		int id = new Random().nextInt(10000, 20000);
		String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		mStatement.executeUpdate("INSERT INTO bill VALUES (" + id + "," + meterId + ",'" + dateString + "'," + units
				+ "," + amount + "," + paid + ")");
	}

	public void updateBillStatus(int billId, boolean paid) throws SQLException {
		mStatement.executeUpdate("UPDATE bill SET paid = " + paid + " WHERE id = " + billId);
	}


	public void deleteMeter(int meterId) throws SQLException {
		mStatement.executeUpdate("DELETE FROM meter WHERE id = " + meterId);
	}

	public ResultSet getMeter(int meterId) throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter WHERE id = " + meterId);
		resultSet.next();
		return resultSet;
	}

	public ResultSet getMeter(String username) throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter WHERE username = '" + username + "'");
		resultSet.next();
		return resultSet;
	}

	public ResultSet getBill(int billId) throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM bill WHERE id = " + billId);
		resultSet.next();
		return resultSet;
	}

	public ResultSet getAllBillsForMeter(int meterId) throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM bill WHERE meter_id = " + meterId);
		return resultSet;
	}

	public ResultSet getAllMeters() throws SQLException {
		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter");
		return resultSet;
	}
	
	public ResultSet getAllBills() throws SQLException {
	    ResultSet resultSet = mStatement.executeQuery("SELECT * FROM bill");
	    return resultSet;
	}

	public ResultSet getFilteredBills(String filterColumn, String filterValue) throws SQLException {
	    String query = "SELECT * FROM bill WHERE " + filterColumn + " = '" + filterValue + "'";
	    ResultSet resultSet = mStatement.executeQuery(query);
	    return resultSet;
	}

}
