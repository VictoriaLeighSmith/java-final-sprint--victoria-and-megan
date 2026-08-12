package com.gymmembership.menus;

import com.gymmembership.membership.Membership;
import com.gymmembership.membership.MembershipService;
import com.gymmembership.merchandise.Merchandise;
import com.gymmembership.merchandise.MerchandiseService;
import com.gymmembership.user.User;
import com.gymmembership.workout.WorkoutClass;
import com.gymmembership.workout.WorkoutClassService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberMenu {

    private final MembershipService memberService;
    private final MerchandiseService merchService;
    private final WorkoutClassService workoutService;
    private final Scanner scanner;

    public MemberMenu(Scanner scanner) {
        this.memberService = new MembershipService();
        this.merchService = new MerchandiseService();
        this.workoutService = new WorkoutClassService();
        this.scanner = scanner;
    }

    public void showMenu(User loggedInUser) {

        int choice = -1;

        while (choice != 0) {

            System.out.println(" ========== MEMBER MENU ========== ");
            System.out.println();
            System.out.println(" 1. Purchase Membership");
            System.out.println(" 2. View Merchandise");
            System.out.println(" 3. Browse Workout Classes");
            System.out.println(" 4. View Personal Membership Expenses ");
            System.out.println(" 0. Log Out");

            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (choice) {

                    case 1:
                        purchaseMembership(loggedInUser);
                        break;

                    case 2:
                        viewMerchandise();
                        break;

                    case 3:
                        browseWorkoutClasses();
                        break;

                    case 4:
                        viewPersonalExpenses(loggedInUser);
                        break;

                    case 0:
                        System.out.println("Logging out...");
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void purchaseMembership(User loggedInUser) {
        int membershipId = 0;
        int userId = loggedInUser.getUserID();
        double price = 0;

        System.out.print(
                "Enter a membership type (Monthly, 3-Month, Annual): ");

        String memberType = scanner.next();

        if (!memberType.equalsIgnoreCase("Monthly")
                && !memberType.equalsIgnoreCase("3-Month")
                && !memberType.equalsIgnoreCase("Annual")) {

            throw new IllegalArgumentException(
                    "Membership type must be Monthly, 3-Month, or Annual."
            );
        }

        if (memberType.equalsIgnoreCase("Monthly")) {
            price = 49.99;
        }

        if (memberType.equalsIgnoreCase("3-Month")) {
            price = 129.99;
        }

        if (memberType.equalsIgnoreCase("Annual")) {
            price = 449.99;
        }

        LocalDate purchaseDate = LocalDate.now();

        Membership newMembership = new Membership(
                membershipId,
                userId,
                memberType,
                price,
                purchaseDate
        );

        memberService.createMembership(newMembership);
    }

    private void viewMerchandise() {
        try {
            ArrayList<Merchandise> merchandise =
                    merchService.browseMerchandise();

            for (Merchandise item : merchandise) {
                System.out.println(item);
            }

        } catch (SQLException e) {
            System.out.println("Unable to load merchandise.");
        }
    }

    private void browseWorkoutClasses() {
        ArrayList<WorkoutClass> workoutClasses =
                workoutService.getAllWorkoutClasses();

        for (WorkoutClass workoutClass : workoutClasses) {
            System.out.println(workoutClass);
        }
    }

    private void viewPersonalExpenses(User loggedInUser){
        double totalExpenses =
                memberService.getTotalExpensesByUser(
                        loggedInUser.getUserID()
                );

        System.out.println(
                "Total membership expenses: $" + totalExpenses
        );
    }
}