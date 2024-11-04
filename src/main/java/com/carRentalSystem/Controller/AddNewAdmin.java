package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.Operation;
import com.carRentalSystem.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewAdmin implements Operation {

    @Override
    public void operation(Database database, Scanner scanner, User user) {
        System.out.println("Enter First Name:");
        String firstName = scanner.next();
        System.out.println("Enter Last Name:");
        String lastName = scanner.next();
        System.out.println("Enter Email Address:");
        String email = scanner.next();
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
        int accType = 1;

        try {
            ResultSet rs = database.getStatement().executeQuery("SELECT COUNT(*);");
            rs.next();
            int ID = rs.getInt("COUNT(*)") -1;

            String instert = "INSERT INTO 'users' ('ID', 'First Name', 'Last Name'," +
                    " 'Email Address', 'Phone Number', 'Password', 'Type') VALUES" +
                    " ('"+ID+"','"+firstName+"','"+lastName+"','"+email+"','"+phoneNumber+"'," +
                    "'"+password+"','"+accType+"');";
            database.getStatement().executeUpdate(instert);
            System.out.println("Successfully added Admin\n");

        }catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
