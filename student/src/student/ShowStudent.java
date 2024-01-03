package student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

public class ShowStudent extends JFrame {
    private JButton backButton;
    private JTable table;

    public ShowStudent() {
        setTitle("Show Students");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setLayout(new BorderLayout());

        createNavbar();
        createContentPanel();

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
                JOptionPane.showMessageDialog(ShowStudent.this, "Logout clicked");
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
 
    private void createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        JLabel studentsLabel = new JLabel("Students");
        studentsLabel.setFont(new Font("Arial", Font.BOLD, 30));
        studentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(studentsLabel, BorderLayout.NORTH);

        // Retrieve student data from the database
        String[] columnNames = {"ID", "Name", "Father's Name", "City", "Blood Group", "Phone", "Class"};
        Object[][] data = retrieveStudentData();

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle back action
                // You can add your back functionality here
                // For now, let's just close the window
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    // Retrieve student data from the database
    private Object[][] retrieveStudentData() {
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "pass";

        List<Object[]> data = new ArrayList<>();

        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT * FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Object[] row = new Object[7];
                        row[0] = resultSet.getInt("id");
                        row[1] = resultSet.getString("stdName");
                        row[2] = resultSet.getString("stdFatherName");
                        row[3] = resultSet.getString("stdCity");
                        row[4] = resultSet.getString("stdBlood");
                        row[5] = resultSet.getString("stdPhone");
                        row[6] = resultSet.getInt("class");
                        data.add(row);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
            JOptionPane.showMessageDialog(this, "Error retrieving student data. Please try again.");
        }

        return data.toArray(new Object[0][]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShowStudent();
            }
        });
    }
}
