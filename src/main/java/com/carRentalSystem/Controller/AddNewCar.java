package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewCar implements Operation {
    @Override
    public void operation(Database database, Scanner scanner, User user) {

        System.out.println("Enter Brand: ");
        String brand = scanner.next();
        System.out.println("Enter Model: ");
        String model = scanner.next();
        System.out.println("Enter Color: ");
        String color = scanner.next();
        System.out.println("Enter Year: ");
        int year = scanner.nextInt();
        System.out.println("Enter Price per hour: ");
        double price = scanner.nextDouble();
        int available = 0;

        String sql = "INSERT INTO cars (Brand, Model, Color, Year, Price, Available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, brand);
            ps.setString(2, model);
            ps.setString(3, color);
            ps.setInt(4, year);
            ps.setDouble(5, price);
            ps.setInt(6, available);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Car added successfully\n");
            } else {
                System.out.println("No car was added. Please check your input and try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
