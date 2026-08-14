package com.gymmembership;
import com.gymmembership.menus.AdminMenu;
import com.gymmembership.menus.LoginMenu;
import com.gymmembership.menus.MemberMenu;
import com.gymmembership.menus.TrainerMenu;
import com.gymmembership.user.User;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // ADD LOGGING HERE FOR PROGRAM STARTUP

        // Create scanner and LoginMenu objects
        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu(scanner);

        try {
            while (true) {
                // Create logged in user and display the login menu
                User loggedInUser = loginMenu.displayLoginMenu();

                if (loggedInUser == null) {
                    System.out.println("See you next time!");
                    break;
                }

                // Get the logged in users role
                String loggedInUserRole = loggedInUser.getRole().toUpperCase();

                // Use the logged in user's role to display the appropriate menu
                switch (loggedInUserRole) {
                    case "ADMIN":
                        AdminMenu adminMenu = new AdminMenu(scanner);
                        adminMenu.showMenu(loggedInUser);
                        break;
                    case "TRAINER":
                        TrainerMenu trainerMenu = new TrainerMenu(scanner);
                        trainerMenu.showMenu(loggedInUser);
                        break;
                    case "MEMBER":
                        MemberMenu memberMenu = new MemberMenu(scanner);
                        memberMenu.showMenu(loggedInUser);
                        break;
                    default:
                        System.out.println("Invalid user role");
                        break;
                }
            }
        } catch (SQLException error) {
            // ADD LOGGER HERE FOR DATABASE ERROR HERE
            System.out.println("A database error occurred. Please try again.");
        }

        scanner.close();
    }
}
