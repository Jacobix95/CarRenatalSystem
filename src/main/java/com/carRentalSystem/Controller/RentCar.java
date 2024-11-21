package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RentCar implements Operation {

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter the car ID to rent (or -1 to view available cars): ");
        int carID = scanner.nextInt();

        while (carID == -1) {
            new ViewCars().operation(database, scanner, user);
            System.out.println("Enter the car ID to rent: ");
            carID = scanner.nextInt();
        }

        System.out.println("Enter the number of hours you want to rent the car: ");
        int hours = scanner.nextInt();

        String checkAvailabilitySQL = "SELECT Available, Price FROM cars WHERE ID = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement checkPS = conn.prepareStatement(checkAvailabilitySQL)) {

            checkPS.setInt(1, carID);
            ResultSet rs = checkPS.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("Available");
                double pricePerHour = rs.getDouble("Price");

                if (available != 0) {
                    System.out.println("Sorry, this car is not available for rent.");
                    return;
                }

                double totalPrice = pricePerHour * hours;
                System.out.println("The total price for renting this car is: $" + totalPrice);

                System.out.println("Do you want to proceed with the rental? (yes/no): ");
                String confirmation = scanner.next();

                if (confirmation.equalsIgnoreCase("yes")) {
                    String rentSQL = "INSERT INTO rents (UserID, CarID, DateTime, Hours, Total, Status) VALUES (?, ?, ?, ?, ?, ?)";
                    String updateCarSQL = "UPDATE cars SET Available = 1 WHERE ID = ?";

                    try (PreparedStatement rentPS = conn.prepareStatement(rentSQL);
                         PreparedStatement updateCarPS = conn.prepareStatement(updateCarSQL)) {

                        rentPS.setInt(1, user.getId());
                        rentPS.setInt(2, carID);
                        rentPS.setString(3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
                        rentPS.setInt(4, hours);
                        rentPS.setDouble(5, totalPrice);
                        rentPS.setInt(6, 0);

                        int rentalRows = rentPS.executeUpdate();

                        if (rentalRows > 0) {
                            updateCarPS.setInt(1, carID);
                            int updateRows = updateCarPS.executeUpdate();

                            if (updateRows > 0) {
                                System.out.println("Car rented successfully. Enjoy your ride!");
                            } else {
                                System.out.println("Car rental record created, but failed to update car availability.");
                            }
                        } else {
                            System.out.println("Failed to record the rental. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Rental cancelled.");
                }
            } else {
                System.out.println("No car found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
