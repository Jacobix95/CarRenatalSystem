package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Admin;
import com.carRentalSystem.Model.Client;
import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Login {

    public Login(Database database, Scanner scanner) {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        ArrayList<User> users = new ArrayList<>();

        try {
            String select = "SELECT * FROM 'users';";
            ResultSet rs = database.getStatement().executeQuery(select);
            while (rs.next()) {
                User user;
                int ID = rs.getInt("ID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String em = rs.getString("Email");
                String phoneNumber = rs.getString("PhoneNumber");
                String pass = rs.getString("Password");
                int Type = rs.getInt("Type");
                switch (Type) {
                    case 0:
                        user = new Client();
                        break;

                    case 1:
                        user = new Admin();
                        break;
                    default:
                        user = new Client();
                        break;
                }
                user.setId(ID);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(em);
                user.setPhoneNumber(phoneNumber);
                user.setPassword(pass);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
                user.showList(database, scanner);
            }
        }
    }
}
