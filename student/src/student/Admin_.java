package student;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin_ extends JFrame {
    private JTextField idField, nameField, emailField, passwordField;
    private JButton updateButton, deleteButton, backButton, searchButton;

    // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pass";

    public Admin_() {
        setTitle("Admin Information");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        createFormPanel();

        setVisible(true);
    }

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("Arial", Font.BOLD, 30);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 24);

        JLabel adminLabel = new JLabel("Admin");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 40));

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(labelFont);
        idField = new JTextField();
        idField.setFont(textFieldFont);
        idField.setPreferredSize(new Dimension(150, 50));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        nameField = new JTextField();
        nameField.setFont(textFieldFont);
        nameField.setPreferredSize(new Dimension(150, 50));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailField = new JTextField();
        emailField.setFont(textFieldFont);
        emailField.setPreferredSize(new Dimension(150, 50));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordField = new JTextField();
        passwordField.setFont(textFieldFont);
        passwordField.setPreferredSize(new Dimension(150, 50));

        formPanel.add(adminLabel);
        formPanel.add(new JLabel()); // Empty label for spacing
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 30));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel panel = new JPanel();
        buttonPanel.add(panel);
        searchButton = new JButton("Search");
        panel.add(searchButton);
        searchButton.setFont(new Font("Tahoma", Font.PLAIN, 30));

        backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 30));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the current Admin window
                // Open the Home window
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new Home();
                    }
                });
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAdminDetails();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAdminDetails();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAdminDetails();
            }
        });

        formPanel.add(buttonPanel);
        formPanel.add(backButton);

        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    private void updateAdminDetails() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String updateQuery = "UPDATE adminuser SET password=?, name=? WHERE mail=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, email);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Admin details updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "No admin found with the specified email.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating admin details. Please try again.");
        }
    }


    private void deleteAdminDetails() {
        int id = Integer.parseInt(idField.getText());

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM adminuser WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Admin details deleted!");
                    clearFormFields();
                } else {
                    JOptionPane.showMessageDialog(this, "No admin found with the specified ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting admin details. Please try again.");
        }
    }

    private void searchAdminDetails() {
        int id = Integer.parseInt(idField.getText());

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String selectQuery = "SELECT * FROM adminuser WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Admin found, populate fields with retrieved data
           
                    emailField.setText(resultSet.getString("mail"));
                    passwordField.setText(resultSet.getString("password"));
                    nameField.setText(resultSet.getString("name"));
                } else {
                    // No admin found with the specified ID
                    JOptionPane.showMessageDialog(this, "No admin found with the specified ID.");
                    clearFormFields();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for admin details. Please try again.");
        }
    }

    private void clearFormFields() {
        // Clear form fields
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Admin_();
            }
        });
    }
}
