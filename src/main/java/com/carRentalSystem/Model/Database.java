package com.carRentalSystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
public class Database {

    private String user = "user";
    private String password = "testPassword";
    private String url = "jdbc:mysql://localhost/carrentalsystem";
    private Statement statement;

    public Database() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
