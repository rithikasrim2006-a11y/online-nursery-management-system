package onlinenursery;
import java.sql.*;

public class DBsetup {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nursery_db";
        String user = "root";
        String pass = "12345";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            // Create users table
            String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "phone VARCHAR(20)," +
                    "email VARCHAR(100) UNIQUE," +
                    "password VARCHAR(100))";
            stmt.executeUpdate(createUsers);

            // Create orders table
            String createOrders = "CREATE TABLE IF NOT EXISTS orders (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "email VARCHAR(100)," +
                    "plant VARCHAR(50)," +
                    "quantity INT," +
                    "total INT," +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(createOrders);

            System.out.println("Tables created successfully 🌱");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

