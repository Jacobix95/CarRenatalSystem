package com.carRentalSystem.Model;

import com.carRentalSystem.Controller.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin extends User {

    private Operation[] operations;

    public Admin() {
        super();
        this.operations = new Operation[]{
                new AddNewCar(),
                new ViewCars(),
                new UpdateCar(),
                new DeleteCar(),
                new AddNewAccount(1),
                new ShowAllRents(),
                new ShowSpecUserRent(),
                new EditUserData()
        };
    }


    @Override
    public void showList(Database database, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add New Car");
            System.out.println("2. View Cars");
            System.out.println("3. Update Car");
            System.out.println("4. Delete Car");
            System.out.println("5. Add New Admin");
            System.out.println("6. Show All Rents");
            System.out.println("7. Show Spec User Rents");
            System.out.println("8. Edit my Data");
            System.out.println("9. Exit\n");

            try {
                System.out.print("Select an option: ");
                int choice = scanner.nextInt() - 1;

                if (choice == 8) {
                    exit = true;
                    System.out.println("Exiting admin menu...");
                } else if (choice >= 0 && choice < operations.length) {
                    operations[choice].operation(database, scanner, this);
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
