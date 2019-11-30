package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBase {
    private static Connection connection;
    private static String driver = System.getenv("SYM_DATABASE_DRIVER");
    private static String url = System.getenv("SYM_DATABASE_URL");
    private static String user = System.getenv("SYM_DATABASE_USER");
    private static String password = System.getenv("SYM_DATABASE_PASSWORD");

    private DataBase() {
    }

    public static void startConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url + "?user=" + user + "&password=" + password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}