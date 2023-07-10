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

    public void createMeter(String name, String place, String city, int pincode) throws SQLException {
        createUser(name, place, city, pincode, false);
    }

    public void createAdmin(String name, String place, String city, int pincode) throws SQLException {
        createUser(name, place, city, pincode, true);
    }

    private void createUser(String name, String place, String city, int pincode, boolean isAdmin) throws SQLException {
        int id;
        if (isAdmin)
            id = new Random().nextInt(10000, 20000); // users 10000 -> 19999
        else
            id = new Random().nextInt(70000, 100000); // users 70000 -> 99999

        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        mStatement.executeUpdate("INSERT INTO meter VALUES (" + id + ",'" + name + "','" + dateString + "','" + place + "','"
                + city + "'," + pincode + "," + isAdmin + ")");
    }

    public void createBill(int meterId, float units, float amount, boolean paid) throws SQLException {
        int id = new Random().nextInt(10000, 20000);
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        mStatement.executeUpdate("INSERT INTO bill VALUES (" + id + "," + meterId + ",'" + dateString + "',"
                + units + "," + amount + "," + paid + ")");
    }

    public void updateBillAmount(int billId, float newAmount) throws SQLException {
        mStatement.executeUpdate("UPDATE bill SET amount = " + newAmount + " WHERE id = " + billId);
    }

    public void updateBillStatus(int billId, boolean paid) throws SQLException {
        mStatement.executeUpdate("UPDATE bill SET paid = " + paid + " WHERE id = " + billId);
    }

    public void deleteBill(int billId) throws SQLException {
        mStatement.executeUpdate("DELETE FROM bill WHERE id = " + billId);
    }

    public void deleteMeter(int meterId) throws SQLException {
        mStatement.executeUpdate("DELETE FROM meter WHERE id = " + meterId);
    }

    public void getMeter(int meterId) throws SQLException {
        ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter WHERE id = " + meterId);
        System.out.println(resultSet);
    }

    public void getBill(int billId) throws SQLException {
        ResultSet resultSet = mStatement.executeQuery("SELECT * FROM bill WHERE id = " + billId);
        System.out.println(resultSet);
    }

    public void getAllBillsForMeter(int meterId) throws SQLException {
        ResultSet resultSet = mStatement.executeQuery("SELECT * FROM bill WHERE meter_id = " + meterId);
        System.out.println(resultSet);
    }

    public void getAllMeters() throws SQLException {
        ResultSet resultSet = mStatement.executeQuery("SELECT * FROM meter");
        System.out.println(resultSet);
    }
}
