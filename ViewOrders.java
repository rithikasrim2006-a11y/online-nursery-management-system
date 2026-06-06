package onlinenursery;
import java.sql.*;
import java.io.*;

public class ViewOrders {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nursery_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "12345";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String plant = rs.getString("plant");
                int quantity = rs.getInt("quantity");
                int total = rs.getInt("total");
                Timestamp orderDate = rs.getTimestamp("order_date");

                // File name for each bill
                File f = new File("E:\\rithi\\Documents\\JAVA\\Bill1_" + id + ".txt");
                f.createNewFile();

                FileWriter fw = new FileWriter(f);
                fw.write("        🌱 RITHI NURSERY 🌱\n");
                fw.write("=====================================\n");
                fw.write("Order ID   : " + id + "\n");
                fw.write("Customer   : " + email + "\n");
                fw.write("Plant      : " + plant + "\n");
                fw.write("Quantity   : " + quantity + "\n");
                fw.write("Total Bill : Rs. " + total + "\n");
                fw.write("Order Date : " + orderDate + "\n");
                fw.write("=====================================\n");
                fw.write("   THANK YOU FOR SHOPPING WITH US!   \n");
                fw.flush();
                
                fw.close();

                System.out.println("Bill_" + id + ".txt created successfully.");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
