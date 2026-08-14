package com.gymmembership.membership;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MembershipService {

    // DAO used to communicate with the memberships table
    private final MembershipDAO membershipDAO = new MembershipDAO();

    // Validates membership information before saving it to the database
    public void createMembership(Membership membership) throws SQLException {
        if (membership == null) {
            throw new IllegalArgumentException("Membership must be provided.");
        }

        if (membership.getUserId() <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        if (membership.getMembershipType() == null ||
                membership.getMembershipType().isBlank()) {
            throw new IllegalArgumentException("Membership type must be entered.");
        }

        if (membership.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        if (membership.getPurchaseDate() == null) {
            throw new IllegalArgumentException("Purchase date must be entered.");
        }

        membershipDAO.createMembership(membership);
    }

    // Method to purchase membership
    public void purchaseMembership(int userId, String membershipType) throws SQLException {
        double price;

        if (membershipType == null || membershipType.isBlank()) {
            throw new IllegalArgumentException("Membership type must be entered.");
        }

        if (membershipType.equalsIgnoreCase("Monthly")) {
            price = 49.99;

        } else if (membershipType.equalsIgnoreCase("3-Month")) {
            price = 129.99;

        } else if (membershipType.equalsIgnoreCase("Annual")) {
            price = 449.99;

        } else {
            throw new IllegalArgumentException("Membership type must be Monthly, 3-Month, or Annual.");
        }

        Membership membership = new Membership(0, userId, membershipType, price, LocalDate.now());

        createMembership(membership);
    }

    // Retrieves all memberships from the database
    public ArrayList<Membership> getAllMemberships() throws SQLException {
        return membershipDAO.getAllMemberships();
    }

    // Retrieves all memberships purchased by a specific user
    public ArrayList<Membership> getMembershipsByUser(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        return membershipDAO.getMembershipsByUser(userId);
    }

    // Calculates the total amount spent on memberships by one user
    public double getTotalExpensesByUser(int userId) throws SQLException {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        ArrayList<Membership> memberships = membershipDAO.getMembershipsByUser(userId);

        double totalExpenses = 0;

        for (Membership membership : memberships) {
            totalExpenses += membership.getPrice();
        }

        return totalExpenses;
    }

    // Calculates total membership revenue for a specific year
    public double getTotalAnnualRevenue(int year) throws SQLException {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0.");
        }

        ArrayList<Membership> memberships = membershipDAO.getAllMemberships();

        double totalRevenue = 0;

        for (Membership membership : memberships) {
            if (membership.getPurchaseDate().getYear() == year) {
                totalRevenue += membership.getPrice();
            }
        }

        return totalRevenue;
    }
}