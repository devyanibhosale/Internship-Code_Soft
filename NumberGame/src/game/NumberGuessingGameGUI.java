package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private final int MIN_RANGE = 1;
    private final int MAX_RANGE = 100;
    private final int MAX_ATTEMPTS = 10;

    private int targetNumber;
    private int attempts;
    private int roundsWon;
    private int totalAttempts;

    private JLabel instructionLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JLabel scoreLabel;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize variables
        targetNumber = generateRandomNumber();
        attempts = 0;
        roundsWon = 0;
        totalAttempts = 0;

        // Create components
        instructionLabel = new JLabel("Guess the number between " + MIN_RANGE + " and " + MAX_RANGE);
        instructionLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        guessTextField = new JTextField(20);
        guessTextField.setFont(new Font("Tahoma", Font.PLAIN, 28));
        guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
        scoreLabel = new JLabel("Score: Rounds won - " + roundsWon + ", Total attempts - " + totalAttempts);
        scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));

        // Set layout
        getContentPane().setLayout(new GridLayout(5, 1));

        // Add components to the frame
        getContentPane().add(instructionLabel);
        getContentPane().add(guessTextField);
        getContentPane().add(guessButton);
        getContentPane().add(resultLabel);
        getContentPane().add(scoreLabel);

        // Add action listener to the Guess button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    private int generateRandomNumber() {
        return new Random().nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
    }

    private void processGuess() {
        try {
            int userGuess = Integer.parseInt(guessTextField.getText());
            if (userGuess == targetNumber) {
                resultLabel.setText("Congratulations! You guessed the correct number.");
                roundsWon++;
                totalAttempts += attempts;
                scoreLabel.setText("Score: Rounds won - " + roundsWon + ", Total attempts - " + totalAttempts);
                resetGame();
            } else if (userGuess < targetNumber) {
                resultLabel.setText("Too low. Try again.");
            } else {
                resultLabel.setText("Too high. Try again.");
            }

            attempts++;
            if (attempts == MAX_ATTEMPTS) {
                resultLabel.setText("Sorry, you've reached the maximum number of attempts. The correct number was " + targetNumber);
                resetGame();
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    private void resetGame() {
        targetNumber = generateRandomNumber();
        attempts = 0;
        guessTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGameGUI();
            }
        });
    }
}
