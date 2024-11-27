package com.carRentalSystem.Controller;

import com.carRentalSystem.Model.Admin;
import com.carRentalSystem.Model.Client;
import com.carRentalSystem.Model.Database;
import com.carRentalSystem.Model.User;
import com.carRentalSystem.View.JLabel;
import com.carRentalSystem.View.JTextField;
import com.carRentalSystem.View.JButton;
import com.carRentalSystem.View.JPasswordField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();

        JFrame frame = new JFrame("Login");
        frame.setSize(600, 330);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(144, 238, 144));
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to CarRentalSystem", 35);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        frame.add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setBackground(null);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email", 25));
        JTextField email = new JTextField(25);
        panel.add(email);

        panel.add(new JLabel("Password", 25));
        JPasswordField password = new JPasswordField(25);
        panel.add(password);

        JButton createAcc = new JButton("Create New Account", 25);
        panel.add(createAcc);

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

        JButton login = new JButton("Login", 22);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (email.getText().equals("") ) {
                    JOptionPane.showMessageDialog(frame, "Email cannot be empty");
                    return;
                }

                if (password.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "Password cannot be empty");
                    return;
                }

                boolean loggedIn = false;
                for (User user : users) {
                    if (user.getEmail().equals(email.getText()) && user.getPassword().equals(password.getText())) {
                        System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
                        loggedIn = true;
                        System.out.println("Logged in successfully");
//                user.showList(database, scanner);
                    }
                }
                if (!loggedIn) {
                    JOptionPane.showMessageDialog(frame, "Email or Password does not match");
                    System.out.println("You are not logged in");

                }
            }
        });
        panel.add(login);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);


    }
}
