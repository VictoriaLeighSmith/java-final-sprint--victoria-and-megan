package com.gymmembership.menus;

import com.gymmembership.membership.Membership;
import com.gymmembership.membership.MembershipService;
import com.gymmembership.merchandise.Merchandise;
import com.gymmembership.merchandise.MerchandiseService;
import com.gymmembership.user.User;
import com.gymmembership.workout.WorkoutClass;
import com.gymmembership.workout.WorkoutClassService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainerMenu {

    private final MembershipService memberService;
    private final MerchandiseService merchService;
    private final WorkoutClassService workoutService;
    private final Scanner scanner;

    public TrainerMenu(Scanner scanner) {
        this.memberService = new MembershipService();
        this.merchService = new MerchandiseService();
        this.workoutService = new WorkoutClassService();
        this.scanner = scanner;
    }

    public void showMenu(User loggedInUser) {

        int choice = -1;

        while (choice != 0) {

            System.out.println(" ========== TRAINER MENU ========== ");
            System.out.println();
            System.out.println(" 1. Create Workout Class");
            System.out.println(" 2. Update Workout Class");
            System.out.println(" 3. Delete Workout Class");
            System.out.println(" 4. Purchase Membership");
            System.out.println(" 5. View Membership");
            System.out.println(" 6. View My Workout Classes");
            System.out.println(" 7. View Merchandise");
            System.out.println(" 0. Log Out");

            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (choice) {

                    case 1:
                        createWorkoutClass(loggedInUser);
                        break;

                    case 2:
                        updateWorkoutClass();
                        break;

                    case 3:
                        deleteClass();
                        break;

                    case 4:
                        purchaseMembership(loggedInUser);
                        break;

                    case 5:
                        viewMembership(loggedInUser);
                        break;

                    case 6:
                        viewClassesByTrainer(loggedInUser);
                        break;

                    case 7:
                        browseMerchandise();
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

    private void createWorkoutClass(User loggedInUser) {

        int classId = 0;
        int trainerId = loggedInUser.getUserID();

        System.out.print("Enter the class name: ");
        String className = scanner.nextLine();

        System.out.print("Enter the class description: ");
        String classDescription = scanner.nextLine();

        System.out.print("Enter the class date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate classDate;

        try {
            classDate = LocalDate.parse(dateInput, dateFormatter);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Please use the format YYYY-MM-DD.");
            return;
        }

        System.out.print("Enter the class time (ex. 2:30 PM): ");
        String timeInput = scanner.nextLine();

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("h:mm a");

        LocalTime classTime;

        try {
            classTime = LocalTime.parse(timeInput, timeFormatter);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid time. Please use the format 2:30 PM.");
            return;
        }

        WorkoutClass newClass = new WorkoutClass(
                classId,
                trainerId,
                className,
                classDescription,
                classDate,
                classTime
        );

        workoutService.createWorkoutClass(newClass);
    }

    private void updateWorkoutClass() {

        System.out.print("Enter the class ID: ");
        int classId = scanner.nextInt();
        scanner.nextLine();

        WorkoutClass workoutClass =
                workoutService.getClassByID(classId);

        System.out.println("Your selected class is: " + workoutClass);

        int updateChoice = -1;

        while (updateChoice != 0) {

            System.out.println(" 1. Change Class Name");
            System.out.println(" 2. Change Description");
            System.out.println(" 3. Change Date");
            System.out.println(" 4. Change Time");
            System.out.println(" 5. Save Changes");
            System.out.println(" 0. Cancel");

            System.out.print("Choose an update option: ");
            updateChoice = scanner.nextInt();
            scanner.nextLine();

            switch (updateChoice) {

                case 1:
                    System.out.print("Enter new class name: ");
                    String newName = scanner.nextLine();
                    workoutClass.setClassName(newName);
                    break;

                case 2:
                    System.out.print("Enter new class description: ");
                    String newDesc = scanner.nextLine();
                    workoutClass.setDescription(newDesc);
                    break;

                case 3:
                    System.out.print("Enter new class date (YYYY-MM-DD): ");
                    String dateInput = scanner.nextLine();

                    DateTimeFormatter dateFormatter =
                            DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    try {
                        LocalDate newDate =
                                LocalDate.parse(dateInput, dateFormatter);

                        workoutClass.setClassDate(newDate);

                    } catch (DateTimeParseException e) {
                        System.out.println(
                                "Invalid date. Please use the format YYYY-MM-DD."
                        );
                    }

                    break;

                case 4:
                    System.out.print("Enter new class time (ex. 2:30 PM): ");
                    String timeInput = scanner.nextLine();

                    DateTimeFormatter timeFormatter =
                            DateTimeFormatter.ofPattern("h:mm a");

                    try {
                        LocalTime newTime =
                                LocalTime.parse(timeInput, timeFormatter);

                        workoutClass.setClassTime(newTime);

                    } catch (DateTimeParseException e) {
                        System.out.println(
                                "Invalid time. Please use the format 2:30 PM."
                        );
                    }

                    break;

                case 5:
                    workoutService.updateWorkoutClass(workoutClass);
                    System.out.println("Workout class successfully updated!");
                    System.out.println(workoutClass);
                    updateChoice = 0;
                    break;

                case 0:
                    break;
            }
        }
    }

    private void deleteClass() {

        System.out.print("Enter the ID of the class you want to delete: ");
        int deleteClassID = scanner.nextInt();
        scanner.nextLine();

        workoutService.deleteWorkoutClass(deleteClassID);

        System.out.println("Class successfully deleted.");
    }

    private void purchaseMembership(User loggedInUser) {

        int membershipId = 0;
        int userId = loggedInUser.getUserID();
        double price = 0;

        System.out.print(
                "Enter a membership type (Monthly, 3-Month, Annual): "
        );

        String memberType = scanner.nextLine();

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

    private void viewMembership(User loggedInUser) {

        ArrayList<Membership> memberships =
                memberService.getMembershipsByUser(
                        loggedInUser.getUserID()
                );

        for (Membership membership : memberships) {
            System.out.println(membership);
        }
    }

    private void viewClassesByTrainer(User loggedInUser) {

        ArrayList<WorkoutClass> workoutClasses =
                workoutService.getAllClassesByTrainer(
                        loggedInUser.getUserID()
                );

        for (WorkoutClass workoutClassByTrainer : workoutClasses) {
            System.out.println(workoutClassByTrainer);
        }
    }

    private void browseMerchandise() {

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
}