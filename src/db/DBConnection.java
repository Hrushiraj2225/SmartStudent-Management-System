package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * db.DBConnection.java
 * Manages MySQL database connection for SmartStudent app.
 */
public class DBConnection {

    // ✅ Changed to student_management — your actual DB name!
    private static final String URL  = "jdbc:mysql://localhost:3306/student_management?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "Hrushi@123";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}