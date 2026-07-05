package util;


import exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/hw16";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "66030323Am";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database connection failed: " + e.getMessage());
        }
    }
}
