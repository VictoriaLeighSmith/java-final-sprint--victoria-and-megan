package com.gymmembership.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String user = "YOUR_USERNAME";
    private static final String password = "YOUR_PASSWORD";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException error) {
            error.printStackTrace();
        }

        return connection;
    }
}
