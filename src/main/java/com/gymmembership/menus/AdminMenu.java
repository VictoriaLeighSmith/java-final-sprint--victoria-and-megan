package com.gymmembership.menus;

import com.gymmembership.logging.AppLogger;
import com.gymmembership.membership.MembershipService;
import com.gymmembership.merchandise.Merchandise;
import com.gymmembership.merchandise.MerchandiseService;
import com.gymmembership.user.User;
import com.gymmembership.user.UserService;
import com.gymmembership.workout.WorkoutClass;
import com.gymmembership.workout.WorkoutClassService;
import com.gymmembership.reports.MembershipReportExporter;
import com.gymmembership.reports.MerchandiseReportExporter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class AdminMenu {

    private final UserService userService = new UserService();
    private final MembershipService membershipService = new MembershipService();
    private final MerchandiseService merchandiseService = new MerchandiseService();
    private final WorkoutClassService workoutClassService = new WorkoutClassService();
    private final Scanner scanner;

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu(User loggedInUser) throws SQLException {
        while (true) {
            System.out.println();
            System.out.println("---------- ADMIN MENU ----------");
            System.out.println("1. Display All Users");
            System.out.println("2. Add Trainer");
            System.out.println("3. Delete User");
            System.out.println("4. Get Total Annual Revenue");
            System.out.println("5. Add New Merchandise");
            System.out.println("6. Update Merchandise Price");
            System.out.println("7. Restock Merchandise");
            System.out.println("8. View All Merchandise and Total Value");
            System.out.println("9. Create New Workout Class");
            System.out.println("10. Update Workout Class");
            System.out.println("11. Delete Workout Class");
            System.out.println("12. Export Reports to File");
            System.out.println("0. Logout");

            System.out.println();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        displayAllUsers();
                        break;
                    case "2":
                        addTrainer();
                        break;
                    case "3":
                        deleteUser(loggedInUser);
                        break;
                    case "4":
                        displayTotalAnnualRevenue();
                        break;
                    case "5":
                        addNewMerchandiseItem();
                        break;
                    case "6":
                        updateMerchandisePrice();
                        break;
                    case "7":
                        restockMerchandise();
                        break;
                    case "8":
                        viewMerchandiseAndTotalValue();
                        break;
                    case "9":
                        createWorkoutClass();
                        break;
                    case "10":
                        updateWorkoutClass();
                        break;
                    case "11":
                        deleteWorkoutClass();
                        break;
                    case "12":
                        exportReports();
                        break;
                    case "0":
                        System.out.println("Logging out ...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (IllegalArgumentException | IllegalStateException error) {
                System.out.println();
                System.out.println(error.getMessage());
            } catch (IOException error) {
                AppLogger.warning("File export error: " + error.getMessage());
                System.out.println();
                System.out.println("Unable to export report. Please try again.");
            }
        }
    }

    // Method to view all users and contact info
    private void displayAllUsers() throws SQLException {
        ArrayList<User> allUsers = userService.getAllUsers();

        if (allUsers.isEmpty()) {
            System.out.println();
            System.out.println("No users found.");
            return;
        }

        System.out.println();
        System.out.println("All Current Users:");
        System.out.println();

        for (User user : allUsers) {
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone Number: " + user.getPhoneNumber());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Role: " + user.getRole());
            System.out.println("-".repeat(30));
        }
    }

    // Method to add new trainer to the system
    private void addTrainer() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        User trainer = new User(username, password, email, phoneNumber, address);

        userService.saveNewTrainer(trainer);

        System.out.println();
        System.out.println("Trainer successfully created!");

        AppLogger.info("Admin created trainer account: " + username);
    }

    // Method to delete users from the system
    private void deleteUser(User loggedInUser) throws SQLException {
        System.out.print("Enter the ID of the user you want to delete: ");
        String userIDInput = scanner.nextLine();
        int userID = Integer.parseInt(userIDInput);

        // Check to make sure the user isn't trying to nuke their own admin account. Could cause issues if they're the only admin in the system.
        if (userID == loggedInUser.getUserID()) {
            throw new IllegalArgumentException("You can't delete your own admin account.");
        }

        userService.deleteUser(userID);

        System.out.println();
        System.out.println("User successfully deleted!");

        AppLogger.warning("Admin deleted user: " + userID);
    }

    // Method to track total annual membership revenue
    private void displayTotalAnnualRevenue() throws SQLException {
        System.out.print("Enter the year for total annual revenue: ");
        String yearInput = scanner.nextLine();
        int year = Integer.parseInt(yearInput);

        double totalAnnualRevenue = membershipService.getTotalAnnualRevenue(year);

        System.out.println();
        System.out.printf("Total Annual Revenue for %d: $%.2f%n", year, totalAnnualRevenue);
    }

    private void addNewMerchandiseItem() throws SQLException {
        System.out.print("Enter the name of the merchandise item: ");
        String productName = scanner.nextLine();

        System.out.print("Enter the type of merchandise item: ");
        String type = scanner.nextLine();

        System.out.print("Enter the price of the merchandise item: ");
        String priceInput = scanner.nextLine();
        double price = Double.parseDouble(priceInput);

        System.out.print("Enter the quantity of the merchandise item: ");
        String stockLevelInput = scanner.nextLine();
        int stockLevel = Integer.parseInt(stockLevelInput);

        Merchandise merchandise = new Merchandise(productName, type, price, stockLevel);

        merchandiseService.addMerchandise(merchandise);

        System.out.println();
        System.out.println("Merchandise successfully added!");

        AppLogger.info("Admin added new merchandise item: " + productName);
    }

    // Method to change merchandise item's price
    private void updateMerchandisePrice() throws SQLException {
        System.out.print("Enter the ID of the merchandise item: ");
        String merchandiseIDInput = scanner.nextLine();
        int merchandiseID = Integer.parseInt(merchandiseIDInput);

        System.out.print("Enter the new price of the merchandise item: ");
        String merchandisePriceInput = scanner.nextLine();
        double merchandisePrice = Double.parseDouble(merchandisePriceInput);

        merchandiseService.changeProductPrice(merchandiseID, merchandisePrice);

        System.out.println();
        System.out.println("Merchandise price updated successfully!");

        AppLogger.info("Admin changed price for item: " + merchandiseID);
    }

    // Method to restock merchandise
    private void restockMerchandise() throws SQLException {
        System.out.print("Enter the ID of the merchandise item: ");
        String merchandiseIDInput = scanner.nextLine();
        int merchandiseID = Integer.parseInt(merchandiseIDInput);

        System.out.print("Enter the quantity to add to merchandise stock: ");
        String quantityInput = scanner.nextLine();
        int quantity = Integer.parseInt(quantityInput);

        merchandiseService.addStock(merchandiseID, quantity);

        System.out.println();
        System.out.println("Merchandise stock added successfully!");

        AppLogger.info("Admin added merchandise stock for item: " + merchandiseID);
    }

    // Method to view merchandise stock and total value
    private void viewMerchandiseAndTotalValue() throws SQLException {
        ArrayList<Merchandise> allMerchandise = merchandiseService.browseMerchandise();

        if (allMerchandise.isEmpty()) {
            System.out.println();
            System.out.println("No merchandise found");
            return;
        }

        System.out.println();
        System.out.println("All Merchandise Items:");
        System.out.println();

        for (Merchandise merchandise : allMerchandise) {
            System.out.println("Merchandise ID: " + merchandise.getMerchandiseID());
            System.out.println("Merchandise Name: " + merchandise.getProductName());
            System.out.println("Merchandise Type: " + merchandise.getType());
            System.out.printf("Merchandise Price: $%.2f%n", merchandise.getPrice());
            System.out.println("Merchandise Stock Level: " + merchandise.getStockLevel());
            System.out.println("-".repeat(30));
        }

        double totalValue = merchandiseService.calculateInventoryValue();

        System.out.println();
        System.out.printf("Total Inventory Value: $%.2f%n", totalValue);
    }

    // Method to create a workout class
    private void createWorkoutClass() throws SQLException {
        System.out.print("Enter the trainer ID for the workout class: ");
        String trainerIDInput = scanner.nextLine();
        int trainerID = Integer.parseInt(trainerIDInput);

        System.out.print("Enter the workout class name: ");
        String className = scanner.nextLine();

        System.out.print("Enter the workout class description: ");
        String classDescription = scanner.nextLine();

        LocalDate convertedDate = getValidDate("Enter the workout class date (YYYY-MM-DD): ");
        LocalTime convertedTime = getValidTime("Enter the workout class time (e.g. 6:30PM): ");

        // Create a new workout class with the user input
        WorkoutClass workoutClass = new WorkoutClass(trainerID, className, classDescription, convertedDate, convertedTime);

        workoutClassService.createWorkoutClass(workoutClass);

        System.out.println();
        System.out.println("Workout class successfully created!");

        AppLogger.info("Admin created workout class: " + className);
    }

    // Method to update workout class
    private void updateWorkoutClass() throws SQLException {
        System.out.print("Enter the workout class ID you wish to update: ");
        String workoutClassIDInput = scanner.nextLine();
        int workoutClassID = Integer.parseInt(workoutClassIDInput);

        System.out.print("Enter the new trainer ID: ");
        String trainerIDInput = scanner.nextLine();
        int trainerID = Integer.parseInt(trainerIDInput);

        System.out.print("Enter the new workout class name: ");
        String className = scanner.nextLine();

        System.out.print("Enter the new workout class description: ");
        String classDescription = scanner.nextLine();

        LocalDate convertedDate = getValidDate("Enter the new workout class date (YYYY-MM-DD): ");
        LocalTime convertedTime = getValidTime("Enter the new workout class time (e.g. 6:30PM): ");

        // Create new workout class with updated values
        WorkoutClass workoutClass = new WorkoutClass(workoutClassID, trainerID, className, classDescription, convertedDate, convertedTime);

        workoutClassService.updateWorkoutClass(workoutClass);

        System.out.println();
        System.out.println("Workout class successfully updated!");

        AppLogger.info("Admin updated workout class with ID: " + workoutClassID);
    }

    // Method to delete workout class
    private void deleteWorkoutClass() throws SQLException {
        System.out.print("Enter the workout class ID to delete: ");
        String workoutClassIDInput = scanner.nextLine();
        int workoutClassID = Integer.parseInt(workoutClassIDInput);

        workoutClassService.deleteWorkoutClass(workoutClassID);

        System.out.println();
        System.out.println("Workout class successfully deleted!");

        AppLogger.warning("Admin deleted workout class with ID: " + workoutClassID);
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
                System.out.println();
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
                System.out.println();
                System.out.println("Invalid time format!");
            }
        }
        return convertedTime;
    }

    // Method to export reports to a text file
    private void exportReports() throws SQLException, IOException {

        System.out.println();
        System.out.println("---------- EXPORT REPORT ----------");
        System.out.println("1. Merchandise Inventory Report");
        System.out.println("2. Membership Revenue Report");
        System.out.println("0. Back");

        System.out.println();
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                MerchandiseReportExporter merchandiseExporter = new MerchandiseReportExporter();
                merchandiseExporter.exportReport();

                System.out.println();
                System.out.println("Merchandise report successfully exported!");
                break;
            case "2":
                MembershipReportExporter membershipExporter = new MembershipReportExporter();
                membershipExporter.exportReport();

                System.out.println();
                System.out.println("Membership report successfully exported!");
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
}
