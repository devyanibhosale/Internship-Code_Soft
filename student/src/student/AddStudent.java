package student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudent extends JFrame {
    private JTextField nameField, fatherNameField, cityField, phoneField;
    private JComboBox<String> bloodGroupComboBox;
    private JButton submitButton, backButton;

    public AddStudent() {
        setTitle("Add Student");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setLayout(new BorderLayout());

        createNavbar();
        createFormPanel();

        setVisible(true);
    }

    private void createNavbar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        JMenuItem homeMenuItem = new JMenuItem("Home");
        homeMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setFont(new Font("Segoe UI", Font.PLAIN, 19));
        JMenuItem aboutMenuItem = new JMenuItem("About");

        fileMenu.add(homeMenuItem);
        fileMenu.add(logoutMenuItem);
        aboutMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        // Add action listeners
        homeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 new Home();
            	 }
        });

        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AddStudent.this, "Logout clicked");
                // Perform logout actions
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 new About();
            	 }
        });
    }

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        Font labelFont = new Font("Arial", Font.BOLD, 30);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 24);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        nameField = new JTextField();
        nameField.setFont(textFieldFont);
        nameField.setPreferredSize(new Dimension(150, 50));

        JLabel fatherNameLabel = new JLabel("Father's Name:");
        fatherNameLabel.setFont(labelFont);
        fatherNameField = new JTextField();
        fatherNameField.setFont(textFieldFont);
        fatherNameField.setPreferredSize(new Dimension(150, 50));

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(labelFont);
        cityField = new JTextField();
        cityField.setFont(textFieldFont);
        cityField.setPreferredSize(new Dimension(150, 50));

        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setFont(labelFont);
        String[] bloodGroups = {"A+", "B+", "A-", "B-", "O+", "O-", "AB+", "AB-"};
        bloodGroupComboBox = new JComboBox<>(bloodGroups);
        bloodGroupComboBox.setFont(textFieldFont);
        bloodGroupComboBox.setPreferredSize(new Dimension(150, 50));

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        phoneField = new JTextField();
        phoneField.setColumns(20);
        phoneField.setFont(textFieldFont);
        phoneField.setPreferredSize(new Dimension(150, 50));

       
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(fatherNameLabel);
        formPanel.add(fatherNameField);
        formPanel.add(cityLabel);
        formPanel.add(cityField);
        formPanel.add(bloodGroupLabel);
        formPanel.add(bloodGroupComboBox);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Tahoma", Font.PLAIN, 30));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);

        JPanel panel = new JPanel();
        buttonPanel.add(panel);
        backButton = new JButton("Back");
        panel.add(backButton);
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 30));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back action
                // You can add your back functionality here
                // For now, let's just close the window
                dispose();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle submit action
                submitStudentDetails();
            }
        });

        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    private void submitStudentDetails() {
        // Collect the entered information
        String name = nameField.getText();
        String fatherName = fatherNameField.getText();
        String city = cityField.getText();
        String bloodGroup = (String) bloodGroupComboBox.getSelectedItem();
        String phone = phoneField.getText();

        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "pass";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // SQL query to insert data into the student table
            String insertQuery = "INSERT INTO student (stdName, stdFatherName, stdCity, stdBlood, stdPhone, class) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set values for the prepared statement
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, fatherName);
                preparedStatement.setString(3, city);
                preparedStatement.setString(4, bloodGroup);
                preparedStatement.setString(5, phone);
                // Assuming 'class' is an integer field
                preparedStatement.setInt(6, 10); // Change the value accordingly

                // Execute the SQL query
                preparedStatement.executeUpdate();

                // Show a success message
                JOptionPane.showMessageDialog(this, "Student details submitted!");

                // Clear the form fields after successful submission
                clearFormFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
            JOptionPane.showMessageDialog(this, "Error submitting student details. Please try again.");
        }
    }

    private void clearFormFields() {
        nameField.setText("");
        fatherNameField.setText("");
        cityField.setText("");
        bloodGroupComboBox.setSelectedIndex(0);
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddStudent();
            }
        });
    }
}
