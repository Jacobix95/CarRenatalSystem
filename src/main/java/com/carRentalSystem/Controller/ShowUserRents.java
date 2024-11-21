package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ShowUserRents implements Operation {

    public ShowUserRents(int userId) {
    }

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        String sql = "SELECT rents.RentID, rents.CarID, rents.DateTime, rents.Hours, rents.Total, rents.Status, " +
                "cars.Brand, cars.Model, cars.Color, cars.Year " +
                "FROM rents " +
                "JOIN cars ON rents.CarID = cars.ID " +
                "WHERE rents.UserID = ? " +
                "ORDER BY rents.DateTime DESC";

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            System.out.println("Your Rental History:");
            System.out.println("--------------------------------------------------------------");

            boolean hasRents = false;
            while (rs.next()) {
                hasRents = true;
                int rentID = rs.getInt("RentID");
                int carID = rs.getInt("CarID");
                String dateTime = rs.getString("DateTime");
                int hours = rs.getInt("Hours");
                double total = rs.getDouble("Total");
                int status = rs.getInt("Status");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                String color = rs.getString("Color");
                int year = rs.getInt("Year");

                String statusText = switch (status) {
                    case 0 -> "Active";
                    case 1 -> "Completed";
                    case 2 -> "Cancelled";
                    default -> "Unknown";
                };

                System.out.println("Rent ID: " + rentID);
                System.out.println("Car: " + brand + " " + model + " (" + color + ", " + year + ")");
                System.out.println("Date & Time: " + dateTime);
                System.out.println("Hours: " + hours);
                System.out.println("Total Cost: $" + total);
                System.out.println("Status: " + statusText);
                System.out.println("--------------------------------------------------------------");
            }

            if (!hasRents) {
                System.out.println("No rentals found for your account.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
