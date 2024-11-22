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
        boolean exit = false;
        while (!exit) {
            System.out.println("\nClient Menu:");
            System.out.println("1. View Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. Show My Rents");
            System.out.println("5. Edit My Data");
            System.out.println("6. Exit\n");

            try {
                System.out.print("Select an option: ");
                int choice = scanner.nextInt() - 1;

                if (choice == 5) {
                    exit = true;
                    System.out.println("Thank you for visiting us! Good bye!");
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