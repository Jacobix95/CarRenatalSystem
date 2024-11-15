package com.carRentalSystem.Model;

import com.carRentalSystem.Controller.AddNewAccount;
import com.carRentalSystem.Controller.AddNewCar;
import com.carRentalSystem.Controller.UpdateCar;
import com.carRentalSystem.Controller.ViewCars;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin extends User {

    private Operation[] operations;

    public Admin() {
        super();
        // Initialize all operations matching the menu items
        this.operations = new Operation[]{
                new AddNewCar(),
                new ViewCars(),
                new UpdateCar(),
                //new DeleteCar(),
                new AddNewAccount(1),
                //new ShowRents(),
                //new ExitOperation()
        };
    }


    @Override
    public void showList(Database database, Scanner scanner) {
        System.out.println("\n1. Add New Car");
        System.out.println("2. View Cars");
        System.out.println("3. Update Car");
        System.out.println("4. Delete Car");
        System.out.println("5. Add New Admin");
        System.out.println("6. Show Rents");
        System.out.println("7. Exit\n");

        boolean validInput = false;
        while (!validInput) {
            try {
                int i = scanner.nextInt() - 1;
                if (i < 0 || i >= operations.length) {
                    System.out.println("Invalid option. Please try again.");
                } else {
                    operations[i].operation(database, scanner, this);
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
