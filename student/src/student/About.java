package student;
import javax.print.DocFlavor.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame {

	  public About() {
	        setTitle("About");
	        setSize(800, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        getContentPane().setLayout(new BorderLayout());

	        // Add background image
	        ImageIcon backgroundImage = createImageIcon("About.png");
	        Image scaledImage = backgroundImage.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
	        backgroundImage = new ImageIcon(scaledImage);
	        JLabel backgroundLabel = new JLabel(backgroundImage);
	        add(backgroundLabel);

	        // Create navigation bar
	        createNavbar();

	        setVisible(true);
	    }

	  private ImageIcon createImageIcon(String path) {
	        // Get the image URL
	        java.net.URL imageURL = getClass().getResource(path);

	        // If the image URL is not null, create and return an ImageIcon
	        if (imageURL != null) {
	            return new ImageIcon(imageURL);
	        } else {
	            System.err.println("Image not found: " + path);
	            return null;
	        }
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

        fileMenu.add(homeMenuItem);
        fileMenu.add(logoutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);

        setJMenuBar(menuBar);

        // Add action listeners
        homeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new Home frame
                new Home();
                // Close the current About frame
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new About();
            }
        });
    }
}
