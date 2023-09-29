import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (id, first_name, last_name) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());

            preparedStatement.executeUpdate();

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

                    return new Customer(id, firstName, lastName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        }
        return null;
    }
}
