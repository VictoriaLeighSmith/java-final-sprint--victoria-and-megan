package com.gymmembership.merchandise;
import com.gymmembership.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MerchandiseDAO {

    // Method to save new merchandise item to the database
    public void saveNewMerchandiseToDatabase(Merchandise merchandise) throws SQLException {
        // SQL query to save new item to the DB
        String query = "INSERT INTO merchandise (product_name, type, price, stock_level) VALUES (?, ?, ?, ?)";

        // Try with resources to set up connection and set prepared statement parameters
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, merchandise.getProductName());
            statement.setString(2, merchandise.getType());
            statement.setDouble(3, merchandise.getPrice());
            statement.setInt(4, merchandise.getStockLevel());

            statement.executeUpdate();
        }
    }

    // Method to get merchandise by ID
    public Merchandise getMerchandiseByID(int merchandiseID) throws SQLException {
        // SQL query to select merchandise from merchandise table with matching ID
        String query = "SELECT * FROM merchandise WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, merchandiseID);

            // If we receive a result with the query, build the merchandise object with the helper function
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildNewMerchandiseObject(resultSet);
                }
            }
        }

        return null;
    }

    // Method to get all merchandise from database
    public ArrayList<Merchandise> getAllMerchandise() throws SQLException {
        // SQL query to select all items from the merchandise table
        String query = "SELECT * FROM merchandise";

        // Create an ArrayList to store the results from the DB
        ArrayList<Merchandise> merchandiseList = new ArrayList<>();

        // Try with resources to connect to the DB and display the results
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {

            // Loop through the results and add each item to the ArrayList
            while (resultSet.next()) {
                Merchandise merchandise = buildNewMerchandiseObject(resultSet);
                merchandiseList.add(merchandise);
            }
        }

        return merchandiseList;
    }

    // Method to update price of item in the DB
    public boolean updateProductPrice(int merchandiseID, double newPrice) throws SQLException {

        // SQL query to update item's price to new price based on ID
        String query = "UPDATE merchandise SET price = ? WHERE id = ?";

        // Try with resources to connect to DB and set the new price
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, newPrice);
            statement.setInt(2, merchandiseID);

            // Store the result of the update in a variable
            int rowsUpdated = statement.executeUpdate();

            // Use the result to return a boolean value. The service class will handle the result.
            return rowsUpdated > 0;
        }
    }

    // Method to update stock of item in the DB
    public boolean updateProductStockLevel(int merchandiseID, int newStockLevel) throws SQLException {

        // SQL query to update item's stock level based on ID
        String query = "UPDATE merchandise SET stock_level = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, newStockLevel);
            statement.setInt(2, merchandiseID);

            // Store the result of the update in a variable
            int rowsUpdated = statement.executeUpdate();

            // Use the result to return a boolean value. The service class will handle the result.
            return rowsUpdated > 0;
        }
    }

    // Helper method to build merchandise object - that way we don't have to rewrite it if we need it again elsewhere
    private Merchandise buildNewMerchandiseObject(ResultSet resultSet) throws SQLException {

        // Create new merchandise object
        Merchandise merchandise = new Merchandise();

        // Set merchandise object's values based off of the info received from the DB
        merchandise.setMerchandiseID(resultSet.getInt("id"));
        merchandise.setProductName(resultSet.getString("product_name"));
        merchandise.setType(resultSet.getString("type"));
        merchandise.setPrice(resultSet.getDouble("price"));
        merchandise.setStockLevel(resultSet.getInt("stock_level"));

        // Return the object
        return merchandise;
    }
}
