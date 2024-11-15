package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateCar implements Operation {

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.print("Enter the Car ID to update: ");
        int ID = scanner.nextInt();

        String selectSQL = "SELECT * FROM cars WHERE ID = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement selectPS = conn.prepareStatement(selectSQL)) {
            selectPS.setInt(1, ID);
            ResultSet rs = selectPS.executeQuery();

            if (rs.next()) {
                System.out.println("Car found: ");
                System.out.println("Brand: " + rs.getString("Brand") + ", Model: " + rs.getString("Model") +
                        ", Color: " + rs.getString("Color") + ", Year: " + rs.getInt("Year") +
                        ", Price per hour: $" + rs.getDouble("Price") + ", Status: " + rs.getInt("Available"));

                System.out.println("Enter new Brand (leave blank to keep current): ");
                scanner.nextLine();
                String brand = scanner.nextLine();

                System.out.println("Enter new Model (leave blank to keep current): ");
                String model = scanner.nextLine();

                System.out.println("Enter new Color (leave blank to keep current): ");
                String color = scanner.nextLine();

                System.out.println("Enter new Year (leave blank to keep current): ");
                String yearStr = scanner.nextLine();
                Integer year = yearStr.isBlank() ? null : Integer.parseInt(yearStr);

                System.out.println("Enter new Price per hour (leave blank to keep current): ");
                String priceStr = scanner.nextLine();
                Double price = priceStr.isBlank() ? null : Double.parseDouble(priceStr);

                System.out.println("Enter new Status (0=Available, 1=Rented, 2=Deleted, leave blank to keep current): ");
                String availableStr = scanner.nextLine();
                Integer available = availableStr.isBlank() ? null : Integer.parseInt(availableStr);

                String updateSQL = "UPDATE cars SET Brand = COALESCE(?, Brand), Model = COALESCE(?, Model), Color = COALESCE(?, Color), Year = COALESCE(?, Year), Price = COALESCE(?, Price), Available = COALESCE(?, Available) WHERE ID = ?";
                try (PreparedStatement updatePS = conn.prepareStatement(updateSQL)) {
                    updatePS.setString(1, brand.isBlank() ? null : brand);
                    updatePS.setString(2, model.isBlank() ? null : model);
                    updatePS.setString(3, color.isBlank() ? null : color);
                    updatePS.setObject(4, year);
                    updatePS.setObject(5, price);
                    updatePS.setObject(6, available);
                    updatePS.setInt(7, ID);

                    int affectedRows = updatePS.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Car updated successfully.");
                    } else {
                        System.out.println("No updates were made. Please check your inputs.");
                    }
                }
            } else {
                System.out.println("No car found with the ID: " + ID);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}