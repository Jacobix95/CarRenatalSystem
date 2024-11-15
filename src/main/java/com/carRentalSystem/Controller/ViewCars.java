package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ViewCars implements Operation {
    @Override
    public void operation(Database database, Scanner scanner, User user) {
        String sql = "SELECT Brand, Model, Color, Year, Price, Available FROM cars";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Available Cars:");
            while (rs.next()) {
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                String color = rs.getString("Color");
                int year = rs.getInt("Year");
                double price = rs.getDouble("Price");
                int available = rs.getInt("Available");


                System.out.println("Brand: " + brand + ", Model: " + model + ", Color: " + color +
                        ", Year: " + year + ", Price per hour: $" + price +
                        ", Available: " + (available == 0 ? "Yes" : "No"));
                System.out.println("___________________________________________________________________\n");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
