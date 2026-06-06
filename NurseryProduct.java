package onlinenursery;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class NurseryProduct {
    JFrame f;
    JTextArea area;
    JLabel totalLabel;
    JButton viewBtn, buyBtn, clearBtn, exitBtn, ordersBtn;
    int grandTotal = 0;
    String userEmail;

    public NurseryProduct(String email) {
        this.userEmail = email;
    }

    public void dg() {
        f = new JFrame("🌱 Nursery Product Page 🌱");
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\rithi\\OneDrive\\Pictures\\plant.jpeg");
        Image scaledImg = bgIcon.getImage().getScaledInstance(1500, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(scaledIcon);
        f.setSize(700, 600);
        f.setLayout(null);
        f.setVisible(true);

        JLabel l = new JLabel("Available Plants 🌸");
        l.setBounds(200, 20, 400, 40);
        l.setFont(new Font("Arial", Font.BOLD, 30));
        l.setForeground(Color.ORANGE);
        f.add(l);

        area = new JTextArea();
        area.setBounds(100, 80, 500, 200);
        area.setFont(new Font("Arial", Font.PLAIN, 16));
        area.setEditable(false);
        f.add(area);

        viewBtn = new JButton("View Plants");
        viewBtn.setBounds(50, 300, 150, 40);
        f.add(viewBtn);

        buyBtn = new JButton("Buy Plant");
        buyBtn.setBounds(220, 300, 150, 40);
        f.add(buyBtn);

        clearBtn = new JButton("Clear Cart");
        clearBtn.setBounds(390, 300, 150, 40);
        f.add(clearBtn);

        ordersBtn = new JButton("View My Orders");
        ordersBtn.setBounds(220, 360, 150, 40);
        f.add(ordersBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(220, 420, 150, 40);
        f.add(exitBtn);

        totalLabel = new JLabel("Grand Total: Rs. 0");
        totalLabel.setBounds(200, 480, 300, 30);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        f.add(totalLabel);

        viewBtn.addActionListener(e -> viewPlants());
        buyBtn.addActionListener(e -> buyPlant());
        clearBtn.addActionListener(e -> clearCart());
        ordersBtn.addActionListener(e -> viewOrders());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void viewPlants() {
        area.setText(
            " 1.Rose - Rs.50\n" +
            " 2.Tulsi - Rs.30\n" +
            " 3.Money Plant - Rs.100\n" +
            " 4.Jasmine - Rs.70\n" +
            " 5.Aloe Vera - Rs.60\n" +
            " 6.Hibiscus - Rs.80\n"+
            " 7.Neem - Rs.120\n"+
            " 8.Sunflower - Rs.40\n"+
            " 9.Chrysanthemum - Rs.40\n"+
            " 10.Fern - Rs.250\n"
        );
    }

    private void buyPlant() {
        String[] options = {
            "Rose (Rs.50)",
            "Tulsi (Rs.30)",
            "Money Plant (Rs.100)",
            "Jasmine (Rs.70)",
            "Aloe Vera (Rs.60)",
            "Hibiscus (Rs.80)",
            "Neem (Rs.120)",
            "Sunflower(Rs.40)",
            "Chrysanthemum(Rs.40)",
            "Fern(Rs.250)"
        };
        String choice = (String) JOptionPane.showInputDialog(
                f,
                "Choose a plant:",
                "Buy Plant",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == null) return;

        int price = 0;
        String plantName = "";
        if (choice.startsWith("Rose")) { price = 50; plantName = "Rose"; }
        else if (choice.startsWith("Tulsi")) { price = 30; plantName = "Tulsi"; }
        else if (choice.startsWith("Money Plant")) { price = 100; plantName = "Money Plant"; }
        else if (choice.startsWith("Jasmine")) { price = 70; plantName = "Jasmine"; }
        else if (choice.startsWith("Aloe Vera")) { price = 60; plantName = "Aloe Vera"; }
        else if (choice.startsWith("Hibiscus")) { price = 80; plantName = "Hibiscus"; }
        else if (choice.startsWith("Neem")) { price = 120; plantName = "Neem"; }
        else if (choice.startsWith("Sunflower")) { price = 40; plantName = "Sunflower"; }
        else if (choice.startsWith("Chrysanthemum")) { price = 40; plantName = "Chrysanthemum"; }
        else if (choice.startsWith("Fern")) { price = 250; plantName = "Fern"; }
        

        String qtyStr = JOptionPane.showInputDialog(f, "Enter quantity:");
        if (qtyStr == null) return;

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();

            int total = price * qty;
            grandTotal += total;

            area.append("\nAdded: " + plantName + " x " + qty + " = Rs." + total);
            totalLabel.setText("Grand Total: Rs. " + grandTotal);

            saveOrder(userEmail, plantName, qty, total);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(f, "Invalid quantity");
        }
    }

    private void clearCart() {
        grandTotal = 0;
        totalLabel.setText("Grand Total: Rs. 0");
        area.append("\nCart cleared.");
    }

    private void saveOrder(String email, String plant, int qty, int total) {
        String url = "jdbc:mysql://localhost:3306/nursery_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "12345";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = c.prepareStatement(
                "INSERT INTO orders(email, plant, quantity, total) VALUES(?,?,?,?)");
            ps.setString(1, email);
            ps.setString(2, plant);
            ps.setInt(3, qty);
            ps.setInt(4, total);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewOrders() {
        String url = "jdbc:mysql://localhost:3306/nursery_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "12345";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(url, user, pass);
            PreparedStatement ps = c.prepareStatement(
                "SELECT plant, quantity, total, order_date FROM orders WHERE email=?");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder("🌱 Your Past Orders:\n");
            while(rs.next()) {
                sb.append(rs.getString("plant"))
                  .append(" x ").append(rs.getInt("quantity"))
                  .append(" = Rs.").append(rs.getInt("total"))
                  .append(" (").append(rs.getTimestamp("order_date")).append(")\n");
            }
            area.setText(sb.toString());

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


