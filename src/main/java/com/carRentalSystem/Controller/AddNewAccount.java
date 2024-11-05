package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewAccount implements Operation {

    private int accType;

    public AddNewAccount(int accType) {
        this.accType = accType;
    }

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter First Name:");
        String firstName = scanner.next();
        System.out.println("Enter Last Name:");
        String lastName = scanner.next();
        System.out.println("Enter Email Address:");
        String email = scanner.next();
        while (emailExists(email, database)) {
            System.out.println("This email is already in use. Please use a different email.");
            System.out.println("Enter Email Address:");
            email = scanner.next();
        }
        System.out.println("Enter Phone Number:");
        String phoneNumber = scanner.next();
        System.out.println("Enter Password:");
        String password = scanner.next();
        System.out.println("Enter Confirm Password:");
        String confirmPassword = scanner.next();
        while (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            System.out.println("Enter Password:");
            password = scanner.next();
            System.out.println("Confirm Password:");
            confirmPassword = scanner.next();
        }
        String sql = "INSERT INTO users (FirstName, LastName, Email, PhoneNumber, Password, Type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phoneNumber);
            ps.setString(5, password);
            ps.setInt(6, accType);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Account created successfully\n");
            } else {
                System.out.println("No account was created. Please check your input and try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private boolean emailExists(String email, Database database) {
        String sql = "SELECT COUNT(*) FROM users WHERE Email = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return false;
    }
}