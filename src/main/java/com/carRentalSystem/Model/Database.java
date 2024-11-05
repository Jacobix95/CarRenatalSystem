package com.carRentalSystem.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String user = "user";
    private String password = "testpassword";
    private String url = "jdbc:mysql://localhost/carrentalsystem";
    private Connection connection;

    public Database() {
        connect();
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            System.out.println("Failed to validate connection: " + e.getMessage());
        }
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
}
