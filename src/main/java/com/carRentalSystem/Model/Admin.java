package com.carRentalSystem.Model;

import com.carRentalSystem.Controller.AddNewAdmin;

import java.util.Scanner;

public class Admin extends User {

    private Operation[] operations = new Operation[]{new AddNewAdmin()};

    public Admin() {
        super();
    }


    @Override
    public void showList(Database database, Scanner scanner) {
        System.out.println("\n1. Add New Car");
        System.out.println("2. View Car");
        System.out.println("3. Update Car");
        System.out.println("4. Delete Car");
        System.out.println("5. Add New Admin");
        System.out.println("6. Show Rents");
        System.out.println("7. Exit\n");

        int i = scanner.nextInt();
        operations[i].operation(database, scanner, this);
    }
}
