package student;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    public Home() {
        setTitle("Student Register System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.GRAY);

        createNavbar();
        createHeader();
        createButtons();
       
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
        aboutMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 20));

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
                JOptionPane.showMessageDialog(Home.this, "Logout clicked");
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

    private void createHeader() {
        JPanel headerPanel = new JPanel(null);

        JLabel homePageLabel = new JLabel("Student Register System");
        homePageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homePageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        homePageLabel.setBounds(0, 0, getWidth(), 20);

        ImageIcon headerImage = new ImageIcon("REGISTER.jpeg");
        Image scaledImage = headerImage.getImage().getScaledInstance(getWidth(), getHeight() / 3, Image.SCALE_SMOOTH);
        headerImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(headerImage);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(0, 20, getWidth(), getHeight() / 3);

        JLabel titleLabel = new JLabel("Home Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, getHeight() / 3 + 20, getWidth(), 40);

        headerPanel.add(homePageLabel);
        headerPanel.add(titleLabel);
        headerPanel.add(imageLabel);

        getContentPane().add(headerPanel, BorderLayout.CENTER);
    }


    private void createButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3));

        // Load images from the same package
        addButtonWithLabel("src//student//ADD.jpeg", "Add Student", "Add Student Button", buttonPanel);
        addButtonWithLabel("src//student//SEARCH.png", "Search Operation", "Search Operation Button", buttonPanel);
        addButtonWithLabel("src//student//LIST.jpeg", "Show List Of Student", "Show Student Button", buttonPanel);

        addButtonWithLabel("src//student//admin.png", "Add Admin", "Add Admin Button", buttonPanel);
        addButtonWithLabel("src//student//adminoperation.jpeg", "Admin Operation", "Admin Operation Button", buttonPanel);
        addButtonWithLabel("src//student//admins.png", "Show Admin", "Show Admin Button", buttonPanel);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private ImageIcon createImageIcon(String imageName) {
        // Get the image URL from the same package
        java.net.URL imageURL = getClass().getResource(imageName);

        // If the image URL is not null, create and return an ImageIcon
        if (imageURL != null) {
            return new ImageIcon(imageURL);
        } else {
            System.err.println("Image not found: " + imageName);
            return null;
        }
    }

    private void addButtonWithLabel(String imagePath, String buttonText, String actionCommand, JPanel panel) {
        JButton button = new JButton();
        ImageIcon buttonImage = new ImageIcon(imagePath);
        Image scaledImage = buttonImage.getImage().getScaledInstance(getWidth() / 3, getHeight() / 3, Image.SCALE_SMOOTH);
        buttonImage = new ImageIcon(scaledImage);
        button.setIcon(buttonImage);
        button.setActionCommand(actionCommand);
        button.setBackground(Color.WHITE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(actionCommand);
            }
        });

        JLabel label = new JLabel(buttonText);
        label.setFont(new Font("Arial", Font.BOLD,30));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.Y_AXIS));
        buttonWrapper.add(label);
        buttonWrapper.add(button);

        panel.add(buttonWrapper);
    }

    private void handleButtonClick(String actionCommand) {
        switch (actionCommand) {
            case "Add Student Button":
                // Open AddStudent page
                new AddStudent();
                break;
            case "Search Operation Button":
                // Implement Search Operation
            	  new Student();
                  break;
            
            case "Show Student Button":
                // Open ShowStudent page
                new ShowStudent();
                break;
            case "Add Admin Button":
                // Implement Add Admin functionality
            	 new Admin();
            	break;
            case "Admin Operation Button":
                // Implement Admin Operation functionality
            	  new Admin_();
            	break;
            case "Show Admin Button":
                // Implement Show Admin functionality
            	 new ShowAdmins();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Home();
            }
        });
    }
}