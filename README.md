# Customer Database App

This Java application demonstrates basic CRUD operations (Create, Read, Update, Delete) on a `Customer` entity in a PostgreSQL database. The application uses JDBC for database connectivity.

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
    javac -cp .;path/to/your/postgresql-driver.jar MainProgram.java
    java -cp .;path/to/your/postgresql-driver.jar MainProgram
    ```

   If you are using Maven:

    ```bash
    mvn compile
    mvn exec:java
