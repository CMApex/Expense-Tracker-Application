import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//More extensive code for Expenses Database
public class ExpenseDAO {
    private DatabaseManager databaseManager;

    public ExpenseDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void addExpense(Date date, String category, double amount, String description) {
        String sql = "INSERT INTO Expenses (Date, Category, Amount, Description) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date)); 
            statement.setString(2, category);
            statement.setDouble(3, amount);
            statement.setString(4, description);
            statement.executeUpdate();
            System.out.println("Expense added to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllExpenses() {
        ArrayList<String> expenseList = new ArrayList<>();
        String sql = "SELECT * FROM Expenses";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Date date = resultSet.getDate("Date");
                String category = resultSet.getString("Category");
                double amount = resultSet.getDouble("Amount");
                String description = resultSet.getString("Description");
                String expenseStr = "Date: " + date + ", Category: " + category + ", Amount: " + amount + ", Description: " + description;
                expenseList.add(expenseStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenseList;
    }
}
