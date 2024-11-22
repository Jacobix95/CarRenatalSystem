package com.carRentalSystem.Model;

import com.carRentalSystem.Controller.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Client extends User {

    private Operation[] operations;

    public Client() {
        super();
        this.operations = new Operation[]{
                new ViewCars(),
                new RentCar(),
                new ReturnCar(),
                new ShowUserRents(getId()),
                new EditUserData()

        };
    }

    @Override
    public void showList(Database database, Scanner scanner) {
        System.out.println("\n1. View Cars");
        System.out.println("2. Rental Car");
        System.out.println("3. Return Car");
        System.out.println("4. Show My Rents");
        System.out.println("5. Edit My Data");
        System.out.println("6. Exit\n");

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
