package student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private DatabaseConnection databaseConnection;

    public Login() {
        setTitle("Login Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setDatabaseConnection(); // Initialize the database connection

        JPanel panel = new JPanel();
        placeComponents(panel);

        add(panel);
        centerFrameOnScreen();
        setVisible(true);
    }

    private void setDatabaseConnection() {
        try {
            databaseConnection = new DatabaseConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database. Check your connection.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Load and resize the image
        try {
            BufferedImage originalImage = ImageIO
                    .read(new File("src//student//Login.png"));
            int scaledWidth = 200; // Specify the desired width
            int scaledHeight = 200 * originalImage.getHeight() / originalImage.getWidth(); // Maintain aspect ratio

            Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBounds(300, 20, scaledWidth, scaledHeight);
            panel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("Login Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(300, 240, 200, 30);
        panel.add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(250, 290, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(350, 290, 165, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(250, 330, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(350, 330, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(250, 370, 80, 25);
        panel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, 370, 80, 25);
        panel.add(cancelButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLogin();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void centerFrameOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void onLogin() {
        if (databaseConnection == null) {
            JOptionPane.showMessageDialog(this, "Error: Database connection not initialized.");
            return;
        }

        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection connection = databaseConnection.getConnection();
            String sql = "SELECT * FROM adminuser WHERE mail = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    openHomePage(); // Open the Home page upon successful login
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid email or password. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error accessing the database.");
        }
    }

    private void openHomePage() {
        // Close the login window
        this.dispose();

        // Open the home page
        new Home();
    }
 
    private void onCancel() {
        System.exit(0); // Close the application on cancel
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}
