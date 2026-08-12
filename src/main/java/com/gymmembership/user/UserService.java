package com.gymmembership.user;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public void saveNewUser(User user) throws SQLException {
        // Validate that user provided credentials
        if (user.getUsername() == null || user.getUsername().isBlank() || user.getEmail() == null || user.getEmail().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username, email and password must be provided");
        }

        // Check to make sure that the user doesn't already exist before saving
        User existingUser = userDAO.getByUsername(user.getUsername());

        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Hash user password before saving
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userDAO.saveNewUserToDatabase(user);
    }

    // Verify user password with BCrypt
    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public User getUserByUsername(String username) throws SQLException {
        return userDAO.getByUsername(username);
    }

    public User loginToSystem(String username, String password) throws SQLException {
        User user = userDAO.getByUsername(username);

        if (user == null) {
            System.out.println("User not found");
            return null;
        }

        if (verifyPassword(password, user.getPassword())) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Login failed.");
            return null;
        }
    }

    // Method to delete user
    public void deleteUser(int userID) throws SQLException {
       boolean deletedUser = userDAO.deleteUserFromDatabase(userID);

       // If the deleteUserFromDatabase method returns a falsy value, display an error to the user
       if (!deletedUser) {
           throw new IllegalArgumentException("User with ID " + userID + " not found");
       }
    }

    // Method to call get all users
    public ArrayList<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsersFromDB();
    }
}
