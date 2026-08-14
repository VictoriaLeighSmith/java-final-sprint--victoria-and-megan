package com.gymmembership.merchandise;

import java.sql.SQLException;
import java.util.ArrayList;

public class MerchandiseService {

    private final MerchandiseDAO merchandiseDAO = new MerchandiseDAO();

    // Method to save a new product
    public void addMerchandise(Merchandise merchandise) throws SQLException {
        if (merchandise == null) {
            throw new IllegalArgumentException("Merchandise must be provided.");
        }

        // Validate that user provided all necessary info
        if (merchandise.getProductName() == null || merchandise.getProductName().isBlank()) {
            throw new IllegalArgumentException("Product name must be provided.");
        }

        if (merchandise.getType() == null || merchandise.getType().isBlank()) {
            throw new IllegalArgumentException("Product type must be provided.");
        }

        if (merchandise.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        if (merchandise.getStockLevel() < 0) {
            throw new IllegalArgumentException("Stock level can't be a negative number.");
        }

        merchandiseDAO.saveNewMerchandiseToDatabase(merchandise);
    }

    // Method to change product price
    public void changeProductPrice(int merchandiseID, double newPrice) throws SQLException {
        Merchandise merchandise = merchandiseDAO.getMerchandiseByID(merchandiseID);

        // Validate that user input is valid
        if (merchandise == null) {
            throw new IllegalArgumentException("Merchandise with ID " + merchandiseID + " does not exist.");
        }

        if (newPrice <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        boolean updatedMerchandise = merchandiseDAO.updateProductPrice(merchandiseID, newPrice);

        if (!updatedMerchandise) {
            throw new IllegalStateException("Failed to update merchandise with ID " + merchandiseID + ".");
        }
    }

    // Method to restock merchandise - we aren't required to remove stock as per requirements but we can add that later if we need
    public void addStock(int merchandiseID, int quantity) throws SQLException {
        Merchandise merchandise = merchandiseDAO.getMerchandiseByID(merchandiseID);

        // Validate that user input is valid
        if (merchandise == null) {
            throw new IllegalArgumentException("Merchandise with ID " + merchandiseID + " does not exist.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be greater than 0.");
        }

        // Calculate new stock quantity
        int newQuantity = merchandise.getStockLevel() + quantity;

        boolean updatedMerchandise = merchandiseDAO.updateProductStockLevel(merchandiseID, newQuantity);

        if (!updatedMerchandise) {
            throw new IllegalStateException("Failed to update merchandise with ID " + merchandiseID + ".");
        }
    }

    // Method to calculate the total value of all inventory
    public double calculateInventoryValue() throws SQLException {
        // Get all merchandise
        ArrayList<Merchandise> allMerchandise = merchandiseDAO.getAllMerchandise();

        // Variable to store total value of all merchandise
        double totalValue = 0;

        // Loop through Array List of merchandise, calculate total value for each item, and add that to total price
        for (Merchandise merchandise : allMerchandise) {
            double totalItemPrice = merchandise.getStockLevel() * merchandise.getPrice();
            totalValue += totalItemPrice;
        }

        return totalValue;
    }

    // Method to get all merchandise
    public ArrayList<Merchandise> browseMerchandise() throws SQLException {
        return merchandiseDAO.getAllMerchandise();
    }
}


