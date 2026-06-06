package onlinenursery;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class NurseryRegister implements ActionListener {
    JFrame f;
    JLabel l,l1,l2,l3,l4;
    JTextField t1,t2,t3;
    JPasswordField t4;
    JButton b;

    public void rg() {
        f = new JFrame("🌱 Register Form 🌱");
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\rithi\\OneDrive\\Pictures\\hii.jpg");
        Image scaledImg = bgIcon.getImage().getScaledInstance(1500, 800, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JLabel background = new JLabel(scaledIcon);


        background.setLayout(null);
        f.setContentPane(background);

        f.setSize(600, 500);
        f.setLayout(null);
        f.setVisible(true);

        l = new JLabel("Register Form");
        l.setBounds(180, 50, 400, 50);
        l.setFont(new Font("Arial", Font.BOLD, 40));
        l.setForeground(Color.BLACK);
        f.add(l);

        l1 = new JLabel("Full Name :");
        l1.setBounds(100, 150, 200, 25);
        f.add(l1);
        t1 = new JTextField();
        t1.setBounds(250, 150, 250, 25);
        f.add(t1);

        l2 = new JLabel("Phone No :");
        l2.setBounds(100, 200, 200, 25);
        f.add(l2);
        t2 = new JTextField();
        t2.setBounds(250, 200, 250, 25);
        f.add(t2);

        l3 = new JLabel("Email ID :");
        l3.setBounds(100, 250, 200, 25);
        f.add(l3);
        t3 = new JTextField();
        t3.setBounds(250, 250, 250, 25);
        f.add(t3);

        l4 = new JLabel("Password :");
        l4.setBounds(100, 300, 200, 25);
        f.add(l4);
        t4 = new JPasswordField();
        t4.setBounds(250, 300, 250, 25);
        f.add(t4);

        b = new JButton("Register");
        b.setBounds(200, 380, 200, 40);
        f.add(b);
        b.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = t1.getText();
        String phone = t2.getText();
        String email = t3.getText();
        String pass = new String(t4.getPassword());

        if(name.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(b, "Fill all Fields");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); 
                Connection c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nursery_db?useSSL=false&serverTimezone=UTC",
                    "root", "12345");

                PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO users(name,phone,email,password) VALUES(?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, email);
                ps.setString(4, pass);
                ps.execute();
                ps.close();
                JOptionPane.showMessageDialog(b, "Registered Successfully 🌱");
                f.setVisible(false);
                NurseryLogin l = new NurseryLogin();
                l.lg();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}



