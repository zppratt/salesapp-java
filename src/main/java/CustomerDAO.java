import java.sql.*;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        customer.setId(generatedId); // Set the generated ID in the customer object
                    }
                }
            }

            System.out.println("Customer saved to the database.");
        } catch (SQLException e) {
            System.err.println("Error saving customer: " + e.getMessage());
        }
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    // Set the retrieved ID in the Customer object
                    Customer customer = new Customer(firstName, lastName);
                    customer.setId(id);

                    return customer;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        }
        return null;
    }
}
