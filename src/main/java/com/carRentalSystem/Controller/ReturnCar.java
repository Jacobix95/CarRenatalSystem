package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReturnCar implements Operation {

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter Rent ID to return (or -1 to view your rental history): ");
        int rentID = scanner.nextInt();

        while (rentID == -1) {
            new ShowUserRents(user.getId()).operation(database, scanner, user);
            System.out.println("Enter Rent ID to return: ");
            rentID = scanner.nextInt();
        }

        String checkRentSQL = "SELECT rents.CarID, rents.Status, cars.Available " +
                "FROM rents " +
                "JOIN cars ON rents.CarID = cars.ID " +
                "WHERE rents.RentID = ? AND rents.UserID = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement checkPS = conn.prepareStatement(checkRentSQL)) {

            checkPS.setInt(1, rentID);
            checkPS.setInt(2, user.getId());
            ResultSet rs = checkPS.executeQuery();

            if (rs.next()) {
                int carID = rs.getInt("CarID");
                int rentalStatus = rs.getInt("Status");
                int carAvailability = rs.getInt("Available");

                if (rentalStatus != 0) {
                    System.out.println("This rental is already completed or cancelled.");
                    return;
                }

                if (carAvailability != 1) {
                    System.out.println("The car associated with this rental is already marked as available.");
                    return;
                }

                String updateRentalSQL = "UPDATE rents SET Status = 1 WHERE RentID = ?";
                String updateCarSQL = "UPDATE cars SET Available = 0 WHERE ID = ?";
                try (PreparedStatement updateRentalPS = conn.prepareStatement(updateRentalSQL);
                     PreparedStatement updateCarPS = conn.prepareStatement(updateCarSQL)) {

                    updateRentalPS.setInt(1, rentID);
                    int rentalRows = updateRentalPS.executeUpdate();

                    updateCarPS.setInt(1, carID);
                    int carRows = updateCarPS.executeUpdate();

                    if (rentalRows > 0 && carRows > 0) {
                        System.out.println("Car returned successfully. Thank you!");
                    } else {
                        System.out.println("Failed to process the car return. Please try again.");
                    }
                }
            } else {
                System.out.println("No active rental found with the given Rent ID for your account.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
