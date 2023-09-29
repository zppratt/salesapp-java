import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));

        Properties properties = loadProperties("secrets.properties");

        if (properties != null) {
            // Read database connection properties
            String dbUrl = properties.getProperty("db.url");
            String dbUser = properties.getProperty("db.user");
            String dbPassword = properties.getProperty("db.password");

            // Create a Customer model object
            Customer customer = new Customer("John", "Doe");

            // Establish a database connection
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

                // Create a CustomerDAO and pass the connection
                CustomerDAO customerDAO = new CustomerDAO(connection);

                // Save the customer to the database
                customerDAO.saveCustomer(customer);

                // Retrieve the customer from the database by ID
                Customer retrievedCustomer = customerDAO.getCustomerById(1);

                // Print the retrieved customer details
                System.out.println("Retrieved Customer: " + retrievedCustomer);

            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
            }
        }
    }

    private static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(fileName)) {
            properties.load(input);
            return properties;
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return null;
        }
    }
}
