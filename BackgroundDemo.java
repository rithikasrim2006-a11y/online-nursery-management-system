package onlinenursery;



import javax.swing.*;
import java.awt.*;

public class BackgroundDemo {
    public static void main(String[] args) {
        JFrame f = new JFrame("🌱 Nursery Background Demo 🌱");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load your image
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\rithi\\OneDrive\\Pictures\\plant.jpeg");

        // Set image as background
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout()); // allow adding components on top
        f.setContentPane(background);

        // Example: add a title label on top of background
        JLabel title = new JLabel("Welcome to Rithi Online Nursery", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE); // text color visible on background
        background.add(title, BorderLayout.NORTH);

        f.setVisible(true);
    }
}

