import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Manages Database calls 
public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:expenses.db";
    private Connection connection;

    public DatabaseManager() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Establish connection to the database
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connected to the database");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTablesIfNotExist() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            // Create Expenses table if it doesn't exist
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Expenses (" +
                    "ExpenseID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Date TEXT, " +
                    "Category TEXT, " +
                    "Amount REAL, " +
                    "Description TEXT)");
            // Add more table creation statements if needed
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
