package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EditUserData implements Operation {

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Edit Your Details:");
        System.out.println("Leave fields blank to keep current values.");

        System.out.print("New First Name: ");
        scanner.nextLine();
        String firstName = scanner.nextLine();

        System.out.print("New Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("New Email: ");
        String email = scanner.nextLine();

        System.out.print("New Phone Number: ");
        String phoneNumber = scanner.nextLine();

        String password = null;
        while (true) {
            System.out.print("New Password: ");
            password = scanner.nextLine();

            if (password.isBlank()) {
                break;
            }

            System.out.print("Repeat Password: ");
            String repeatPassword = scanner.nextLine();

            if (password.equals(repeatPassword)) {
                break;
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        }

        String sql = "UPDATE users SET " +
                "FirstName = COALESCE(NULLIF(?, ''), FirstName), " +
                "LastName = COALESCE(NULLIF(?, ''), LastName), " +
                "Email = COALESCE(NULLIF(?, ''), Email), " +
                "PhoneNumber = COALESCE(NULLIF(?, ''), PhoneNumber), " +
                "Password = COALESCE(NULLIF(?, ''), Password) " +
                "WHERE ID = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phoneNumber);
            ps.setString(5, password);
            ps.setInt(6, user.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Your details have been updated successfully.");
            } else {
                System.out.println("No changes were made. Please check your input and try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
