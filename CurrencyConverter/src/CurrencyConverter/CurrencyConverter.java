package CurrencyConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter extends JFrame {

    private JComboBox<String> baseCurrencyComboBox;
    private JComboBox<String> targetCurrencyComboBox;
    private JTextField amountTextField;
    private JLabel resultLabel;

    private static final String API_KEY = "497ee23cbd9a9ec9ef1418d2";
    private static final String API_URL = "https://open.er-api.com/v6/latest/";

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createFormPanel();

        setVisible(true);
    }

    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        // Base Currency
        JLabel baseCurrencyLabel = new JLabel("Base Currency:");
        baseCurrencyLabel.setFont(labelFont);
        baseCurrencyComboBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP", "JPY"}); // You can add more currencies
        baseCurrencyComboBox.setFont(textFieldFont);
        formPanel.add(baseCurrencyLabel);
        formPanel.add(baseCurrencyComboBox);

        // Target Currency
        JLabel targetCurrencyLabel = new JLabel("Target Currency:");
        targetCurrencyLabel.setFont(labelFont);
        targetCurrencyComboBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP", "JPY"}); // You can add more currencies
        targetCurrencyComboBox.setFont(textFieldFont);
        formPanel.add(targetCurrencyLabel);
        formPanel.add(targetCurrencyComboBox);

        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(labelFont);
        amountTextField = new JTextField();
        amountTextField.setFont(textFieldFont);
        formPanel.add(amountLabel);
        formPanel.add(amountTextField);

        // Calculate Button
        JButton calculateButton = new JButton("Convert");
        calculateButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        calculateButton.setBackground(Color.GREEN);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Result Label
        resultLabel = new JLabel("Converted Amount: ");
        resultLabel.setFont(labelFont);

        formPanel.add(calculateButton);
        formPanel.add(new JLabel()); // Empty label for spacing
        formPanel.add(resultLabel);

        getContentPane().add(formPanel, BorderLayout.CENTER);
    }

    private void convertCurrency() {
        try {
            String baseCurrency = (String) baseCurrencyComboBox.getSelectedItem();
            String targetCurrency = (String) targetCurrencyComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountTextField.getText());

            URL url = new URL(API_URL + baseCurrency + "?apikey=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject exchangeRateData = JsonParser.parseReader(reader).getAsJsonObject();
                double exchangeRate = exchangeRateData.getAsJsonObject("rates").get(targetCurrency).getAsDouble();
                double convertedAmount = amount * exchangeRate;

                resultLabel.setText("Converted Amount: " + String.format("%.2f", convertedAmount) + " " + targetCurrency);
            } else {
                resultLabel.setText("Error fetching exchange rates. Please try again later.");
            }
        } catch (IOException | NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter valid amount.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverter();
            }
        });
    }
}
