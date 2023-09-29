import com.google.gson.Gson;
import spark.Spark;

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

            // Establish a database connection
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

                // Create a CustomerDAO and pass the connection
                CustomerDAO customerDAO = new CustomerDAO(connection);

                // Initialize Spark
                Spark.port(4567);

                // Define API endpoints
                setupEndpoints(customerDAO);

                // Wait for Spark to stop before closing the connection
                Spark.awaitStop();

            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
            } finally {
                // Manually close the connection when Spark is stopped
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        System.err.println("Error closing database connection: " + e.getMessage());
                    }
                }
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

    private static void setupEndpoints(CustomerDAO customerDAO) {
        // Enable CORS for all routes
        Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Define API routes
        Spark.get("/customers", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(customerDAO.getAllCustomers());
        });

        Spark.get("/customers/:id", (request, response) -> {
            response.type("application/json");
            int customerId = Integer.parseInt(request.params(":id"));
            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer != null) {
                return new Gson().toJson(customer);
            } else {
                response.status(404);
                return "Customer not found";
            }
        });

        Spark.post("/customers", (request, response) -> {
            response.type("application/json");
            Customer customer = new Gson().fromJson(request.body(), Customer.class);
            customerDAO.saveCustomer(customer);
            return "Customer saved successfully";
        });

        Spark.put("/customers/:id", (request, response) -> {
            response.type("application/json");
            int customerId = Integer.parseInt(request.params(":id"));
            Customer existingCustomer = customerDAO.getCustomerById(customerId);
            if (existingCustomer != null) {
                Customer updatedCustomer = new Gson().fromJson(request.body(), Customer.class);
                updatedCustomer.setId(customerId);
                customerDAO.updateCustomer(updatedCustomer);
                return "Customer updated successfully";
            } else {
                response.status(404);
                return "Customer not found";
            }
        });

        Spark.delete("/customers/:id", (request, response) -> {
            response.type("application/json");
            int customerId = Integer.parseInt(request.params(":id"));
            if (customerDAO.deleteCustomer(customerId)) {
                return "Customer deleted successfully";
            } else {
                response.status(404);
                return "Customer not found";
            }
        });
    }
}
