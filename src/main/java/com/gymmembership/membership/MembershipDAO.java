package com.gymmembership.membership;
import com.gymmembership.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MembershipDAO {

    // Inserts a new membership into the memberships table
    public void createMembership(Membership membership) throws SQLException {
        String query = "INSERT INTO memberships (user_id, membership_type, price, purchase_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Set the values for the new membership
            statement.setInt(1, membership.getUserId());
            statement.setString(2, membership.getMembershipType());
            statement.setDouble(3, membership.getPrice());
            statement.setDate(4, java.sql.Date.valueOf(membership.getPurchaseDate()));

            statement.execute();
        }
    }

    // Retrieves all memberships from the database
    public ArrayList<Membership> getAllMemberships() throws SQLException {
        String query = "SELECT * FROM memberships";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Membership> allMemberships = new ArrayList<>();

                while (resultSet.next()) {
                    Membership membership = buildMembershipObject(resultSet);
                    allMemberships.add(membership);
                }
                return allMemberships;
            }
        }
    }

    // Retrieves all memberships purchased by a specific user
    public ArrayList<Membership> getMembershipsByUser(int userId) throws SQLException {
        String query = "SELECT * FROM memberships WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Use the provided user ID to filter the query
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Membership> membershipsByUser = new ArrayList<>();

                while (resultSet.next()) {
                    Membership membership = buildMembershipObject(resultSet);
                    membershipsByUser.add(membership);
                }

                return membershipsByUser;
            }
        }
    }

    // Helper method to build a membership object
    private Membership buildMembershipObject (ResultSet resultSet)  throws SQLException {
        Membership membership = new Membership();

        membership.setMembershipId(resultSet.getInt("membership_id"));
        membership.setUserId(resultSet.getInt("user_id"));
        membership.setMembershipType(resultSet.getString("membership_type"));
        membership.setPrice(resultSet.getDouble("price"));
        membership.setPurchaseDate(resultSet.getDate("purchase_date").toLocalDate());

        return membership;
    }
}

