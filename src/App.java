import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
//Main class. Controls the entire application
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create database manager and DAOs
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.createTablesIfNotExist(); // Ensure tables exist

        ExpenseDAO expenseDAO = new ExpenseDAO(databaseManager);
        BudgetDAO budgetDAO = new BudgetDAO(databaseManager);

        System.out.println("Welcome to the Expense Tracker App!");

        // Main menu loop
        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Set Budget");
            System.out.println("4. View Budgets");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addExpense(scanner, expenseDAO);
                    break;
                case 2:
                    viewExpenses(expenseDAO);
                    break;
                case 3:
                    setBudget(scanner, budgetDAO);
                    break;
                case 4:
                    viewBudgets(budgetDAO);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        System.out.println("Thank you for using the Expense Tracker App!");
        scanner.close();
        databaseManager.closeConnection();
    }

    private static void addExpense(Scanner scanner, ExpenseDAO expenseDAO) {
        System.out.println("\nAdding Expense:");

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Create and add expense
        Date date = new Date(); // Current date
        expenseDAO.addExpense(date, category, amount, description);
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses(ExpenseDAO expenseDAO) {
        System.out.println("\nExpenses:");
        ArrayList<String> expenseList = expenseDAO.getAllExpenses();
        for (String expense : expenseList) {
            System.out.println(expense);
        }
    }

    private static void setBudget(Scanner scanner, BudgetDAO budgetDAO) {
        System.out.println("\nSetting Budget:");
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.print("Enter limit: ");
        double limit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        // Create and add budget
        budgetDAO.addBudget(category, limit);
        System.out.println("Budget set successfully!");
    }

    private static void viewBudgets(BudgetDAO budgetDAO) {
        System.out.println("\nBudgets:");
        ArrayList<String> budgetList = budgetDAO.getAllBudgets();
        for (String budget : budgetList) {
            System.out.println(budget);
        }
    }
}
