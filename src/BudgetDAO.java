import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Manages Budget Database
public class BudgetDAO {
    private DatabaseManager databaseManager;

    public BudgetDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void addBudget(String category, double limit) {
        // Implement adding budget to the database
    }

    public ArrayList<String> getAllBudgets() {
        ArrayList<String> budgetList = new ArrayList<>();
        String sql = "SELECT * FROM Budgets"; // Adjust SQL query according to your table structure
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String category = resultSet.getString("Category");
                double limit = resultSet.getDouble("Limit");
                String budgetStr = String.format("Category: %s, Limit: %.2f", category, limit);
                budgetList.add(budgetStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgetList;
    }
}
