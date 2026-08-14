package com.gymmembership.user;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public void saveNewUser(User user) throws SQLException {
        // Check to ensure that a user was provided
        if (user == null) {
            throw new IllegalArgumentException("User must be provided");
        }

        // Validate that user provided credentials
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        if (user.getAddress() == null || user.getAddress().isBlank()) {
            throw new IllegalArgumentException("Address is required");
        }

        // Check to make sure that the username doesn't already exist before saving
        User existingUsername = userDAO.getByUsername(user.getUsername());

        if (existingUsername != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Check to make sure email isn't already in use
        User existingEmail = userDAO.getByEmail(user.getEmail());

        if (existingEmail != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Check to see if we have any users in the DB. If not, first user created is admin.
        if (!userDAO.hasAnyUsers()) {
            user.setRole("ADMIN");
        } else {
            user.setRole("MEMBER");
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

        if (user == null || !verifyPassword(password, user.getPassword())) {
            // ADD LOGGER HERE TO LOG FAILED LOGIN ATTEMPT
            System.out.println("Invalid username or password");
            return null;
        }

        System.out.println();
        System.out.println("Login successful!");
        return user;
    }

    // Method to delete user
    public void deleteUser(int userID) throws SQLException {
       boolean deletedUser = userDAO.deleteUserFromDatabase(userID);

       // If the deleteUserFromDatabase method returns a falsy value, throw an error
       if (!deletedUser) {
           throw new IllegalArgumentException("User with ID " + userID + " not found");
       }
    }

    // Method to call get all users
    public ArrayList<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsersFromDB();
    }
}
