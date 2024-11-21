package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteCar implements Operation {
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter the Car ID to delete: ");
        int carID = scanner.nextInt();

        String sql = "DELETE FROM cars WHERE ID = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, carID);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Car with ID " + carID + " deleted successfully.");
            } else {
                System.out.println("No car found with the ID: " + carID + ". Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}