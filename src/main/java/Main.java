import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Customer customer1 = new Customer(1, "Alice", "Onion");
        System.out.println(customer1);

        // JDBC URL, username, and password of PostgreSQL server
        String url = "jdbc:postgresql://localhost:5432/salesapp";
        String user = "postgres";
        String password = "secret";

        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the PostgreSQL server successfully.");
            // Create a Statement
            try (Statement statement = connection.createStatement()) {
                // Execute a SELECT query
                String sql = "SELECT * FROM customer";
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    // Process the result set
                    while (resultSet.next()) {
                        // Retrieve data from the result set
                        int id = resultSet.getInt("id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");

                        // Print the data
                        System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
