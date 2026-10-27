package service;

import model.Booking;

import java.sql.*;
import java.util.Random;

public class DatabaseService {
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "transport_system";
    private static final String DB_USER = "root";                  // Default MySQL root username
    private static final String DB_PASS = "1234";    // <-- Replace with your real MySQL password

    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private Connection connection;

    public DatabaseService() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            initTables();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver missing! Add the connector JAR to your project classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("--- DB CONNECTION ERROR LOG ---");
            System.err.println("Connection to MySQL Instance failed. Confirm your server is running, the database exists, and your credentials match.");
            System.err.println(e.getMessage());
            System.err.println("--------------------------------");
            e.printStackTrace();
        }
    }

    private void initTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (username VARCHAR(50) PRIMARY KEY, password VARCHAR(255))");
            stmt.execute("CREATE TABLE IF NOT EXISTS trains (train_no VARCHAR(20) PRIMARY KEY, train_name VARCHAR(100))");
            stmt.execute("CREATE TABLE IF NOT EXISTS bookings (pnr VARCHAR(20) PRIMARY KEY, passenger_name VARCHAR(100), train_no VARCHAR(20), train_name VARCHAR(100), class_type VARCHAR(50), journey_date VARCHAR(20), source VARCHAR(100), destination VARCHAR(100))");

            stmt.execute("INSERT IGNORE INTO users (username, password) VALUES ('admin', 'password123')");

            stmt.execute("INSERT IGNORE INTO trains (train_no, train_name) VALUES " +
                    "('12626', 'Kerala Express'), " +
                    "('12952', 'Mumbai Rajdhani'), " +
                    "('12002', 'Bhopal Shatabdi')");
        }
    }

    public boolean authenticate(String username, String password) {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public String fetchTrainName(String trainNo) {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT train_name FROM trains WHERE train_no = ?")) {
            pstmt.setString(1, trainNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("train_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String issueBooking(Booking booking) throws SQLException {
        String generatedPnr = "PNR" + (100000 + new Random().nextInt(899999));
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO bookings (pnr, passenger_name, train_no, train_name, class_type, journey_date, source, destination) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, generatedPnr);
            pstmt.setString(2, booking.getPassengerName());
            pstmt.setString(3, booking.getTrainNo());
            pstmt.setString(4, booking.getTrainName());
            pstmt.setString(5, booking.getClassType());
            pstmt.setString(6, booking.getJourneyDate());
            pstmt.setString(7, booking.getSource());
            pstmt.setString(8, booking.getDestination());
            pstmt.executeUpdate();
        }
        return generatedPnr;
    }

    public Booking fetchBooking(String pnr) {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM bookings WHERE pnr = ?")) {
            pstmt.setString(1, pnr);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                            rs.getString("pnr"), rs.getString("passenger_name"),
                            rs.getString("train_no"), rs.getString("train_name"),
                            rs.getString("class_type"), rs.getString("journey_date"),
                            rs.getString("source"), rs.getString("destination")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeBooking(String pnr) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM bookings WHERE pnr = ?")) {
            pstmt.setString(1, pnr);
            pstmt.executeUpdate();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
