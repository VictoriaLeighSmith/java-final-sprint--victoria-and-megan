package com.gymmembership.membership;

import com.gymmembership.database.DatabaseConnection;
import com.gymmembership.workout.WorkoutClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MembershipDAO {

    // Inserts a new membership into the memberships table
    public void createMembership(Membership membership) {

        String query = "INSERT INTO memberships "
                + "(user_id, membership_type, price, purchase_date) "
                + "VALUES (?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            // Set the values for the new membership
            statement.setInt(
                    1,
                    membership.getUserId());

            statement.setString(
                    2,
                    membership.getMembershipType());

            statement.setDouble(
                    3,
                    membership.getPrice());

            statement.setDate(
                    4,
                    java.sql.Date.valueOf(membership.getPurchaseDate()));


            statement.execute();

            statement.close();
            con.close();

            System.out.println(
                    "Membership saved to database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves all memberships from the database
    public ArrayList<Membership> getAllMemberships() {

        ArrayList<Membership> allMemberships = new ArrayList<>();

        String query = "SELECT * FROM memberships";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            // Convert each database row into a Membership object
            while (rs.next()) {
                int membershipId = rs.getInt("membership_id");
                int userId = rs.getInt("user_id");
                String membershipType = rs.getString("membership_type");
                double price = rs.getDouble("price");
                java.sql.Date purchaseDate = rs.getDate("purchase_date");

                Membership newMembership = new Membership(
                        membershipId,
                        userId,
                        membershipType,
                        price,
                        purchaseDate.toLocalDate()
                );

                // Add the object to the list that will be returned
                allMemberships.add(newMembership);
            }

            rs.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allMemberships;
    }

    // Retrieves all memberships purchased by a specific user
    public ArrayList<Membership> getMembershipsByUser(int userId) {

        ArrayList<Membership> membershipsByUser = new ArrayList<>();

        String query = "SELECT * FROM memberships WHERE user_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);

            // Use the provided user ID to filter the query
            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            // Convert each matching database row into a Membership object
            while (rs.next()) {
                int membershipId = rs.getInt("membership_id");
                String membershipType = rs.getString("membership_type");
                double price = rs.getDouble("price");
                java.sql.Date purchaseDate = rs.getDate("purchase_date");

                Membership singleMembership = new Membership(
                        membershipId,
                        userId,
                        membershipType,
                        price,
                        purchaseDate.toLocalDate()
                );

                membershipsByUser.add(singleMembership);
            }

            rs.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return membershipsByUser;
    }
}
