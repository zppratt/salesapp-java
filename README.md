# Customer Database App

This Java application demonstrates basic CRUD operations (Create, Read, Update, Delete) on a `Customer` entity in a PostgreSQL database. The application uses JDBC for database connectivity, Spark Java for building RESTful APIs, and GSON for JSON serialization.

## Setup

### Prerequisites

- Java JDK (version X.X.X)
- PostgreSQL database server (version X.X.X)
- Maven (version X.X.X) - optional but recommended for building and managing dependencies

### Database Setup

1. Create a PostgreSQL database and a `customer` table. You can use the following SQL script as an example:

    ```sql
    CREATE TABLE customer (
        id SERIAL PRIMARY KEY,
        first_name VARCHAR(255),
        last_name VARCHAR(255)
    );
    ```

2. Update the database connection details in `secrets.properties`:

    ```
    db.url=jdbc:postgresql://your_postgresql_server:5432/your_database
    db.user=your_username
    db.password=your_password
    ```

## Usage

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/customer-database-app.git
    ```

2. Navigate to the project directory:

    ```bash
    cd customer-database-app
    ```

3. Compile and run the application:

    ```bash
    mvn compile
    mvn exec:java
    ```

4. The Spark API will start at `http://localhost:4567`.

### API Endpoints

- **GET /customers**: Retrieve all customers.
- **GET /customers/:id**: Retrieve a specific customer by ID.
- **POST /customers**: Create a new customer.
- **PUT /customers/:id**: Update an existing customer by ID.
- **DELETE /customers/:id**: Delete a customer by ID.

## Contributing

Contributions are welcome! Please follow the [Contribution Guidelines](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE).
