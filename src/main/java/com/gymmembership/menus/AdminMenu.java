package com.gymmembership.menus;

import com.gymmembership.membership.MembershipService;
import com.gymmembership.user.User;
import com.gymmembership.user.UserService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminMenu {

    private final UserService userService = new UserService();
    private final MembershipService membershipService = new MembershipService();
    private final Scanner scanner;

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    // -------- TO DO --------
    // Track total annual membership revenue
    // Add new merchandise items and set prices
    // View merchandise stock and total value
    // Create, update and delete workout classes

    // Method to view all users and contact info
    private void displayAllUsers() throws SQLException {
        ArrayList<User> allUsers = userService.getAllUsers();

        // We'll need to fix this output once we start testing. Not bothering with it until I can see it.
        System.out.println("All Users");

        for (User user : allUsers) {
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone Number: " + user.getPhoneNumber());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Role: " + user.getRole());
            System.out.println("-".repeat(20));
        }
    }

    // Method to delete users from the system
    private void deleteAUser() throws SQLException {

        System.out.println("Enter the ID of the user you want to delete: ");
        int userID  = scanner.nextInt();
        scanner.nextLine();

        userService.deleteUser(userID);
        System.out.println("User successfully deleted!");
    }

    // Method to track total annual membership revenue
    private double getTotalAnnualRevenue() throws SQLException {

        System.out.println("Enter the year for total annual revenue: ");
        int year = scanner.nextInt();
        scanner.nextLine();


    }
}
