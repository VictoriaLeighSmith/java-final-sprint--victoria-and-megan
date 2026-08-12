package com.gymmembership.user;
import com.gymmembership.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    public void saveNewUserToDatabase(User user) throws SQLException {
        // SQL query to add new user to the database
        String query = "INSERT INTO users (username, password, email, phone_number, address, role) VALUES (?, ?, ?, ?, ?, ?)";

        // Try with resources to set up connection and set prepared statement parameters
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement userStatement = connection.prepareStatement(query)) {

            userStatement.setString(1, user.getUsername());
            userStatement.setString(2, user.getPassword());
            userStatement.setString(3, user.getEmail());
            userStatement.setString(4, user.getPhoneNumber());
            userStatement.setString(5, user.getAddress());
            userStatement.setString(6, user.getRole());

            userStatement.executeUpdate();
        }
    }

    public User getByUsername(String username) throws SQLException {
        // SQL query to select user by username from table
        String query = "SELECT * FROM users WHERE username = ?";

        // Try with resources to set up connection and search for user by username with above query
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement userStatement = connection.prepareStatement(query)) {

            userStatement.setString(1, username);

            try (ResultSet resultSet = userStatement.executeQuery()) {
                // If we get a result back, create a new user object with the results
                if (resultSet.next()) {
                    return buildNewUserObject(resultSet);
                }
            }
        }
        return null;
    }

    // Method to get all users from the DB
    public ArrayList<User> getAllUsersFromDB() throws SQLException {

        // SQL query to get all users from the DB
        String query = "SELECT * FROM users";

        // Try with resources to run the above query
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement userStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = userStatement.executeQuery()) {
                ArrayList<User> allUsers = new ArrayList<>();

                // If we get results, add the user(s) found to the allUsers array
                while (resultSet.next()) {
                    User user = buildNewUserObject(resultSet);
                    allUsers.add(user);
                }

                return allUsers;
            }
        }
    }

    // Method to delete users from the DB
    public boolean deleteUserFromDatabase(int userID) throws SQLException {
        // SQL query to delete a user from the DB based on ID
        String  query = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement deleteStatement = connection.prepareStatement(query)) {

            deleteStatement.setInt(1, userID);

            // Store the result of the update in a variable
            int rowsUpdated = deleteStatement.executeUpdate();

            // Use the result to return a boolean value. The service class will handle the result.
            return rowsUpdated > 0;
        }
    }

    // Separated this method to build a user object from the result set so we can also use it with get all users later
    private User buildNewUserObject(ResultSet resultSet) throws SQLException {
        // Create new user object
        User user  = new User();

        // Use user objects setter methods to set fields
        user.setUserID(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setAddress(resultSet.getString("address"));
        user.setRole(resultSet.getString("role"));

        // Return user object so we can use it elsewhere
        return user;
    }
}
