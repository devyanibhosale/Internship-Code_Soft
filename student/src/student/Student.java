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

public class Student extends JFrame {
    private JTextField idField, nameField, fatherNameField, cityField, phoneField, bloodGroupField;
    private JButton updateButton, deleteButton, backButton, searchButton;
     // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/student";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pass";

    public Student() {
        setTitle("Student Information");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        createFormPanel();

        setVisible(true);
    }
    

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("Arial", Font.BOLD, 30);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 24);

        JLabel studentLabel = new JLabel("Student");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 40));

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
        bloodGroupField = new JTextField();
        bloodGroupField.setFont(textFieldFont);
        bloodGroupField.setPreferredSize(new Dimension(150, 50));

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        phoneField = new JTextField();
        phoneField.setColumns(20);
        phoneField.setFont(textFieldFont);
        phoneField.setPreferredSize(new Dimension(150, 50));

        formPanel.add(studentLabel);
        formPanel.add(new JLabel()); // Empty label for spacing
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(fatherNameLabel);
        formPanel.add(fatherNameField);
        formPanel.add(cityLabel);
        formPanel.add(cityField);
        formPanel.add(bloodGroupLabel);
        formPanel.add(bloodGroupField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

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
                dispose();  // Close the current Student window
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
                updateStudentDetails();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudentDetails();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudentDetails();
            }
        });

        formPanel.add(buttonPanel);
        formPanel.add(backButton);

        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    private void updateStudentDetails() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String fatherName = fatherNameField.getText();
        String city = cityField.getText();
        String bloodGroup = bloodGroupField.getText();
        String phone = phoneField.getText();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String updateQuery = "UPDATE student SET stdName=?, stdFatherName=?, stdCity=?, stdBlood=?, stdPhone=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, fatherName);
                preparedStatement.setString(3, city);
                preparedStatement.setString(4, bloodGroup);
                preparedStatement.setString(5, phone);
                preparedStatement.setInt(6, id);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Student details updated!");
                } else {
                    JOptionPane.showMessageDialog(this, "No student found with the specified ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student details. Please try again.");
        }
    }

    private void deleteStudentDetails() {
        int id = Integer.parseInt(idField.getText());

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String deleteQuery = "DELETE FROM student WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Student details deleted!");
                    clearFormFields();
                } else {
                    JOptionPane.showMessageDialog(this, "No student found with the specified ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student details. Please try again.");
        }
    }

    private void searchStudentDetails() {
        int id = Integer.parseInt(idField.getText());

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String selectQuery = "SELECT * FROM student WHERE id=?";
            try            (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Student found, populate fields with retrieved data
                    nameField.setText(resultSet.getString("stdName"));
                    fatherNameField.setText(resultSet.getString("stdFatherName"));
                    cityField.setText(resultSet.getString("stdCity"));
                    bloodGroupField.setText(resultSet.getString("stdBlood"));
                    phoneField.setText(resultSet.getString("stdPhone"));
                } else {
                    // No student found with the specified ID
                    JOptionPane.showMessageDialog(this, "No student found with the specified ID.");
                    clearFormFields();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for student details. Please try again.");
        }
    }

    private void clearFormFields() {
        // Clear form fields
        nameField.setText("");
        fatherNameField.setText("");
        cityField.setText("");
        bloodGroupField.setText("");
        phoneField.setText("");
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Student();
            }
        });
    }
}
