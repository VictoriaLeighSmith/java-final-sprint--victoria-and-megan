package com.gymmembership.membership;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MembershipService {

    // DAO used to communicate with the memberships table
    private MembershipDAO membershipDao;

    // Creates the DAO when the service is created
    public MembershipService() {
        this.membershipDao = new MembershipDAO();
    }

    // Validates membership information before saving it to the database
    public void createMembership(Membership membership) {

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

        if (membership.getPrice() == null) {
            throw new IllegalArgumentException("Price must be entered.");
        }

        if (membership.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        if (membership.getPurchaseDate() == null) {
            throw new IllegalArgumentException("Purchase date must be entered.");
        }

        membershipDao.createMembership(membership);
    }

    // Retrieves all memberships from the database
    public ArrayList<Membership> getAllMemberships() {
        return membershipDao.getAllMemberships();
    }

    // Retrieves all memberships purchased by a specific user
    public ArrayList<Membership> getMembershipsByUser(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        return membershipDao.getMembershipsByUser(userId);
    }

    // Calculates the total amount spent on memberships by one user
    public BigDecimal getTotalExpensesByUser(int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0.");
        }

        ArrayList<Membership> memberships =
                membershipDao.getMembershipsByUser(userId);

        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Membership membership : memberships) {
            totalExpenses = totalExpenses.add(membership.getPrice());
        }

        return totalExpenses;
    }

    // Calculates total membership revenue for a specific year
    public BigDecimal getTotalAnnualRevenue(int year) {

        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than 0.");
        }

        ArrayList<Membership> memberships =
                membershipDao.getAllMemberships();

        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Membership membership : memberships) {
            if (membership.getPurchaseDate().getYear() == year) {
                totalRevenue = totalRevenue.add(membership.getPrice());
            }
        }

        return totalRevenue;
    }
}