package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ShowAllRents implements Operation {

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        String sql = "SELECT rents.RentID, rents.UserID, rents.CarID, rents.DateTime, rents.Hours, " +
                "rents.Total, rents.Status, users.FirstName, users.LastName, cars.Brand, cars.Model " +
                "FROM rents " +
                "JOIN users ON rents.UserID = users.ID " +
                "JOIN cars ON rents.CarID = cars.ID " +
                "ORDER BY rents.DateTime DESC";

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("All Rental Records:");
            System.out.println("--------------------------------------------------------------");

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                int rentID = rs.getInt("RentID");
                int userID = rs.getInt("UserID");
                int carID = rs.getInt("CarID");
                String dateTime = rs.getString("DateTime");
                int hours = rs.getInt("Hours");
                double total = rs.getDouble("Total");
                int status = rs.getInt("Status");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");

                String statusText = switch (status) {
                    case 0 -> "Active";
                    case 1 -> "Completed";
                    case 2 -> "Cancelled";
                    default -> "Unknown";
                };

                System.out.println("Rent ID: " + rentID);
                System.out.println("User: " + firstName + " " + lastName + " (ID: " + userID + ")");
                System.out.println("Car: " + brand + " " + model + " (ID: " + carID + ")");
                System.out.println("Date & Time: " + dateTime);
                System.out.println("Hours: " + hours);
                System.out.println("Total Cost: $" + total);
                System.out.println("Status: " + statusText);
                System.out.println("--------------------------------------------------------------");
            }

            if (!hasRecords) {
                System.out.println("No rental records found.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
