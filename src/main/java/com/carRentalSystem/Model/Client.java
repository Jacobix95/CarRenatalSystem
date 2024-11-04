package com.carRentalSystem.Model;

import java.util.Scanner;

public class Client extends User {

    public Client() {
        super();
    }

    @Override
    public void showList(Database database, Scanner scanner) {
        System.out.println("\n1. View Cars");
        System.out.println("2. Rental Car");
        System.out.println("3. Return Car");
        System.out.println("4. Show My Rents");
        System.out.println("5. Edit My Data");
        System.out.println("6. Exit\n");

    }
}
