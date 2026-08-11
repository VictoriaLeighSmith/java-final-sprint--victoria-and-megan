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

        // Create scanner and LoginMenu objects
        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu(scanner);

        try {
            while (true) {
                User loggedInUser = loginMenu.displayLoginMenu();

                if (loggedInUser == null) {
                    System.out.println("See you next time!");
                    break;
                }

                String loggedInUserRole = loggedInUser.getRole().toUpperCase();

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
            error.printStackTrace();
        }

        scanner.close();
    }
}
