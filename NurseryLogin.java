package onlinenursery;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class NurseryLogin implements ActionListener {
    JFrame f;
    JTextField t3;
    JPasswordField t4;
    JButton b;

    public void lg() {
        f = new JFrame("🌱 Login Form 🌱");
        f.setSize(600, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\rithi\\OneDrive\\Pictures\\plant2.jpg");
        Image scaledImg = bgIcon.getImage().getScaledInstance(1500, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

       
        JLabel background = new JLabel(scaledIcon);
        background.setLayout(null); 
        f.setContentPane(background);

        JLabel l = new JLabel("Login Form");
        l.setBounds(200, 80, 400, 50);
        l.setFont(new Font("Arial", Font.BOLD, 40));
        l.setForeground(Color.BLACK);
        background.add(l);

        JLabel l3 = new JLabel("Email ID :");
        l3.setBounds(100, 200, 200, 25);
        background.add(l3);

        t3 = new JTextField();
        t3.setBounds(250, 200, 250, 25);
        background.add(t3);

        JLabel l4 = new JLabel("Password :");
        l4.setBounds(100, 250, 200, 25);
        background.add(l4);

        t4 = new JPasswordField();
        t4.setBounds(250, 250, 250, 25);
        background.add(t4);

        b = new JButton("Login");
        b.setBounds(200, 350, 200, 40);
        background.add(b);
        b.addActionListener(this);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = t3.getText();
        String pass = new String(t4.getPassword());

        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(b, "Fill all Fields");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nursery_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "root", "12345");

                PreparedStatement ps = c.prepareStatement(
                    "SELECT * FROM users WHERE email=? AND password=?");
                ps.setString(1, email);
                ps.setString(2, pass);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(b, "Login Success 🌱");
                    f.setVisible(false);
                    NurseryProduct np = new NurseryProduct(email);
                    np.dg();
                } else {
                    JOptionPane.showMessageDialog(b, "Invalid User ❌");
                }

                rs.close();
                ps.close();
                c.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
