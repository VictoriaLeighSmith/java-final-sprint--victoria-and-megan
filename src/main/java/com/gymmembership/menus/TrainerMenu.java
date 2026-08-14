package com.gymmembership.menus;
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
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TrainerMenu {

    private final MembershipService membershipService = new MembershipService();
    private final MerchandiseService merchandiseService = new MerchandiseService();
    private final WorkoutClassService workoutService = new WorkoutClassService();
    private final Scanner scanner;

    public TrainerMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu(User loggedInUser) throws SQLException {
        while (true) {
            System.out.println();
            System.out.println(" ========== TRAINER MENU ========== ");
            System.out.println(" 1. Create Workout Class");
            System.out.println(" 2. Update Workout Class");
            System.out.println(" 3. Delete Workout Class");
            System.out.println(" 4. Purchase Membership");
            System.out.println(" 5. View My Workout Classes");
            System.out.println(" 6. View Merchandise");
            System.out.println(" 7. Log Out");

            System.out.println();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        createWorkoutClass(loggedInUser);
                        break;
                    case "2":
                        updateWorkoutClass(loggedInUser);
                        break;
                    case "3":
                        deleteClass(loggedInUser);
                        break;
                    case "4":
                        purchaseMembership(loggedInUser);
                        break;
                    case "5":
                        viewClassesByTrainer(loggedInUser);
                        break;
                    case "6":
                        browseMerchandise();
                        break;
                    case "7":
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

    private void createWorkoutClass(User loggedInUser) throws SQLException {
        int trainerId = loggedInUser.getUserID();

        System.out.print("Enter the class name: ");
        String className = scanner.nextLine();

        System.out.print("Enter the class description: ");
        String classDescription = scanner.nextLine();

        LocalDate convertedDate = getValidDate("Enter the workout class date (YYYY-MM-DD): ");
        LocalTime convertedTime = getValidTime("Enter the workout class time (e.g. 6:30PM): ");

        WorkoutClass newClass = new WorkoutClass(trainerId, className, classDescription, convertedDate, convertedTime);

        workoutService.createWorkoutClass(newClass);
        System.out.println("Successfully created workout class!");
    }

    private void updateWorkoutClass(User loggedInUser) throws SQLException {
        System.out.print("Enter the class ID: ");
        int classId = scanner.nextInt();
        scanner.nextLine();

        WorkoutClass workoutClass = workoutService.getClassByID(classId);

        System.out.println("Your selected class is: " + workoutClass);

        while (true) {
            System.out.println(" 1. Change Class Name");
            System.out.println(" 2. Change Description");
            System.out.println(" 3. Change Date");
            System.out.println(" 4. Change Time");
            System.out.println(" 5. Save Changes");
            System.out.println(" 0. Cancel");

            System.out.println();
            System.out.print("Choose an update option: ");
            String updateChoice = scanner.nextLine();

            switch (updateChoice) {
                case "1":
                    System.out.print("Enter new class name: ");
                    String newName = scanner.nextLine();
                    workoutClass.setClassName(newName);
                    break;
                case "2":
                    System.out.print("Enter new class description: ");
                    String newDesc = scanner.nextLine();
                    workoutClass.setDescription(newDesc);
                    break;
                case "3":
                    LocalDate newDate = getValidDate("Enter new class date (YYYY-MM-DD): ");
                    workoutClass.setClassDate(newDate);
                    break;
                case "4":
                    LocalTime newTime = getValidTime("Enter new class time (e.g. 2:30PM): ");
                    workoutClass.setClassTime(newTime);
                    break;
                case "5":
                    workoutService.updateWorkoutClass(workoutClass, loggedInUser.getUserID());
                    System.out.println("Workout class successfully updated!");
                    return;
                case "0":
                    System.out.println("Update cancelled.");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    private void deleteClass(User loggedInUser) throws SQLException {
        System.out.print("Enter the ID of the class you want to delete: ");
        int deleteClassID = scanner.nextInt();
        scanner.nextLine();

        workoutService.deleteWorkoutClass(deleteClassID, loggedInUser.getUserID());

        System.out.println("Class successfully deleted.");
    }

    private void purchaseMembership(User loggedInUser) throws SQLException {
        System.out.print("Enter a membership type (Monthly, 3-Month, Annual): ");
        String membershipType = scanner.nextLine();

        membershipService.purchaseMembership(loggedInUser.getUserID(), membershipType);

        System.out.println("Membership successfully purchased!");
    }

    private void viewClassesByTrainer(User loggedInUser) throws SQLException {
        ArrayList<WorkoutClass> workoutClasses = workoutService.getAllClassesByTrainer(loggedInUser.getUserID());

        if (workoutClasses.isEmpty()) {
            System.out.println("You do not have any workout classes assigned.");
            return;
        }

        System.out.println("Your Workout Classes:");
        System.out.println();

        for (WorkoutClass workoutClassByTrainer : workoutClasses) {
            System.out.println(workoutClassByTrainer);
        }
    }

    private void browseMerchandise() throws SQLException {
        ArrayList<Merchandise> merchandise = merchandiseService.browseMerchandise();

        if (merchandise.isEmpty()) {
            System.out.println("No merchandise available");
            return;
        }

        System.out.println("Available Merchandise:");
        System.out.println();
        for (Merchandise item : merchandise) {
            System.out.println(item);
        }
    }

    // Helper method to validate input date
    private LocalDate getValidDate(String prompt) {
        LocalDate convertedDate = null;
        boolean validDate = false;

        while (!validDate) {
            System.out.print(prompt);
            String classDate = scanner.nextLine();

            try {
                convertedDate = LocalDate.parse(classDate);
                validDate = true;
            } catch (DateTimeParseException error) {
                System.out.println("Invalid date format!");
            }
        }
        return convertedDate;
    }

    // Helper method to validate input time
    private LocalTime getValidTime(String prompt) {
        DateTimeFormatter parser = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("h:mma").toFormatter(Locale.ENGLISH);
        LocalTime convertedTime = null;
        boolean validTime = false;

        while (!validTime) {
            System.out.print(prompt);
            String classTime = scanner.nextLine();

            try {
                convertedTime = LocalTime.parse(classTime, parser);
                validTime = true;
            } catch (DateTimeParseException error) {
                System.out.println("Invalid time format!");
            }
        }
        return convertedTime;
    }
}