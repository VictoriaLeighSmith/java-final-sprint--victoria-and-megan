package com.gymmembership.menus;

import com.gymmembership.user.User;
import com.gymmembership.user.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {

    // Create new UserService object and store scanner object received from Main
    private final UserService userService =  new UserService();
    private final Scanner scanner;

    // Login Menu constructor
    public LoginMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    // Method to display Login menu - return a User object to be used in Main
    public User displayLoginMenu() throws SQLException {
        while (true) {
            System.out.println("LOGIN/REGISTER");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    return loginMenu();
                case 2:
                    registerMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return null;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    // Method for user login
    private User loginMenu() throws SQLException {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.loginToSystem(username, password);
        return user;
    }

    // Method for user registration
    private void registerMenu() throws SQLException {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter address: ");
        String address = scanner.nextLine();

        // Think about how we can handle this so that users aren't manually inputting their role
        System.out.println("Enter role: ");
        String role = scanner.nextLine();

        // Maybe want to think about putting this in a try/catch? Not sure if it's necessary
        User user = new  User(username, password, email, phoneNumber, address, role);
        userService.saveNewUser(user);

        System.out.println("Successfully registered!");
    }
}
