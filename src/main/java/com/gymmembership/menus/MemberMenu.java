package com.gymmembership.menus;
import com.gymmembership.membership.MembershipService;
import com.gymmembership.merchandise.Merchandise;
import com.gymmembership.merchandise.MerchandiseService;
import com.gymmembership.user.User;
import com.gymmembership.workout.WorkoutClass;
import com.gymmembership.workout.WorkoutClassService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberMenu {

    private final MembershipService membershipService = new MembershipService();
    private final MerchandiseService merchandiseService = new MerchandiseService();
    private final WorkoutClassService workoutService = new WorkoutClassService();
    private final Scanner scanner;

    public MemberMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu(User loggedInUser) throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("---------- MEMBER MENU ----------");
            System.out.println("1. Purchase Membership");
            System.out.println("2. View Merchandise");
            System.out.println("3. Browse Workout Classes");
            System.out.println("4. View Personal Membership Expenses");
            System.out.println("0. Log Out");

            System.out.println();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        purchaseMembership(loggedInUser);
                        break;
                    case "2":
                        viewMerchandise();
                        break;
                    case "3":
                        browseWorkoutClasses();
                        break;
                    case "4":
                        viewPersonalExpenses(loggedInUser);
                        break;
                    case "0":
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (IllegalArgumentException error) {
                System.out.println();
                System.out.println(error.getMessage());
            }
        }
    }

    private void purchaseMembership(User loggedInUser) throws SQLException {
        System.out.print("Enter a membership type (Monthly, 3-Month, Annual): ");
        String membershipType = scanner.nextLine();

        membershipService.purchaseMembership(loggedInUser.getUserID(), membershipType);

        System.out.println();
        System.out.println("Membership successfully purchased!");
    }

    private void viewMerchandise() throws SQLException {
        ArrayList<Merchandise> merchandise = merchandiseService.browseMerchandise();

        if (merchandise.isEmpty()) {
            System.out.println("No merchandise found.");
            return;
        }

        System.out.println();
        System.out.println("Available Merchandise:");
        System.out.println();

        for (Merchandise item : merchandise) {
            System.out.println("Merchandise ID: " + item.getMerchandiseID());
            System.out.println("Product Name: " + item.getProductName());
            System.out.println("Type: " + item.getType());
            System.out.printf("Price: $%.2f%n", item.getPrice());
            System.out.println("Stock Level: " + item.getStockLevel());
            System.out.println("-".repeat(30));
        }
    }

    private void browseWorkoutClasses() throws SQLException {
        ArrayList<WorkoutClass> workoutClasses = workoutService.getAllWorkoutClasses();

        if (workoutClasses.isEmpty()) {
            System.out.println("No workout classes found.");
            return;
        }

        System.out.println();
        System.out.println("Available Workout Classes:");
        System.out.println();

        for (WorkoutClass workoutClass : workoutClasses) {
            System.out.println("Class ID: " + workoutClass.getClassId());
            System.out.println("Trainer ID: " + workoutClass.getTrainerId());
            System.out.println("Class Name: " + workoutClass.getClassName());
            System.out.println("Description: " + workoutClass.getDescription());
            System.out.println("Date: " + workoutClass.getClassDate());
            System.out.println("Time: " + workoutClass.getClassTime());
            System.out.println("-".repeat(30));
        }
    }

    private void viewPersonalExpenses(User loggedInUser) throws SQLException {
        double totalExpenses = membershipService.getTotalExpensesByUser(loggedInUser.getUserID());

        System.out.println();
        System.out.printf("Total membership expenses: $%.2f%n", totalExpenses);
    }
}