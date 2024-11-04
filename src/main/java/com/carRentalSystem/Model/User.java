package com.carRentalSystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;
import java.util.UUID;

@Getter
@Setter
public abstract class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    public User() {
    }

    public abstract void showList(Database database, Scanner scanner);
}
