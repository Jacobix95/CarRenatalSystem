package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Admin;
import com.carRentalSystem.Model.Client;
import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to CarRentalSystem");
        System.out.println("Enter your email:\n(-1) to create new account");
        String email = scanner.nextLine();
        if (email.equals("-1")) {
            new AddNewAccount(0).operation(database, scanner, null);
            return;
        }
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {
            String select = "SELECT * FROM `users`;";
            ResultSet rs = stmt.executeQuery(select);
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
                        System.out.println("Account does not exist");
                        return;
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

        boolean loggedIn = false;
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
                loggedIn = true;
                user.showList(database, scanner);
            }
        }
        if (!loggedIn) {
            System.out.println("You are not logged in");
            scanner.close();
        }
    }
}
