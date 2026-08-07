package com.gymmembership;

import com.gymmembership.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // Database connection test
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Database connection successful!");
            connection.close();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
