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
            System.out.println(" ========== MEMBER MENU ========== ");
            System.out.println();
            System.out.println(" 1. Purchase Membership");
            System.out.println(" 2. View Merchandise");
            System.out.println(" 3. Browse Workout Classes");
            System.out.println(" 4. View Personal Membership Expenses ");
            System.out.println(" 0. Log Out");

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
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (IllegalArgumentException error) {
                System.out.println(error.getMessage());
            }
        }
    }

    private void purchaseMembership(User loggedInUser) throws SQLException {
        System.out.print("Enter a membership type (Monthly, 3-Month, Annual): ");
        String membershipType = scanner.nextLine();

        membershipService.purchaseMembership(loggedInUser.getUserID(), membershipType);

        System.out.println("Membership successfully purchased!");
    }

    private void viewMerchandise() throws SQLException {
        ArrayList<Merchandise> merchandise = merchandiseService.browseMerchandise();

        if (merchandise.isEmpty()) {
            System.out.println("No merchandise found");
            return;
        }

        for (Merchandise item : merchandise) {
            System.out.println(item);
        }
    }

    private void browseWorkoutClasses() throws SQLException {
        ArrayList<WorkoutClass> workoutClasses = workoutService.getAllWorkoutClasses();

        if (workoutClasses.isEmpty()) {
            System.out.println("No workout classes found");
            return;
        }

        for (WorkoutClass workoutClass : workoutClasses) {
            System.out.println(workoutClass);
        }
    }

    private void viewPersonalExpenses(User loggedInUser) throws SQLException {
        double totalExpenses = membershipService.getTotalExpensesByUser(loggedInUser.getUserID());

        System.out.printf("Total membership expenses: $%.2f%n", totalExpenses);
    }
}