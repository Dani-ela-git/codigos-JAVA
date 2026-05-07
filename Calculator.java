//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
    // inicializing the components
    private JTextField number1Field;
    private JTextField number2Field;
    private JComboBox<String> operationComboBox;
    private JButton calculateButton;
    private JLabel resultLabel;

    public Calculator() {
        super("SIMPLE CALCULATOR 🧮");

        // set up GUI components
        setLayout(new GridLayout(6, 2, 10, 10));

        // make components
        createComponents();

        setupActions(); // confg. actions

        setupFrame(); // config. frame
    }

    // method to create components
    private void createComponents() {
        // Linha 1: Título (ocupa 2 colunas)
        JLabel titleLabel = new JLabel("🔢 Welcome to Simple Calculator! 🔢", SwingConstants.CENTER);
        add(titleLabel);
        add(new JLabel()); // espaço vazio para completar as 2 colunas

        // Linha 2: Número 1
        add(new JLabel("Enter first number:"));
        number1Field = new JTextField();
        add(number1Field);

        // Linha 3: Número 2
        add(new JLabel("Enter second number:"));
        number2Field = new JTextField();
        add(number2Field);

        // Linha 4: Operação
        add(new JLabel("Select operation:"));
        String[] operations = { "Select operation...", "Addition (+)", "Subtraction (-)", "Multiplication (×)",
                "Division (÷)" };
        operationComboBox = new JComboBox<>(operations);
        add(operationComboBox);

        // Linha 5: Botão (centralizado, ocupando 2 colunas)
        calculateButton = new JButton("Calculate");
        add(calculateButton);
        add(new JLabel()); // espaço vazio

        // Linha 6: Resultado (ocupando 2 colunas)
        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(resultLabel);
        add(new JLabel()); // espaço vazio
    }

    private void setupActions() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCalculation();
            }
        });
    }

    private void performCalculation() {
        String num1Text = number1Field.getText();
        String num2Text = number2Field.getText();

        // input validation
        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            resultLabel.setText("Please enter both numbers!");
            return;
        }

        // verify if are a number
        try {
            Double.parseDouble(num1Text);
            Double.parseDouble(num2Text);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input! Please enter valid numbers.");
            return;
        }

        int selectedIndex = operationComboBox.getSelectedIndex();
        if (selectedIndex == 0) {
            resultLabel.setText("Please select an operation!");
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Text);
            double num2 = Double.parseDouble(num2Text);
            double result = 0;

            switch (selectedIndex) {
                case 1: // Addition
                    result = num1 + num2;
                    break;
                case 2: // Subtraction
                    result = num1 - num2;
                    break;
                case 3: // Multiplication
                    result = num1 * num2;
                    break;
                case 4: // Division
                    if (num2 == 0) {
                        resultLabel.setText("Cannot divide by zero!");
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            resultLabel.setText(String.format("Result: %.2f", result));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input! Please enter valid numbers.");
        }

    }

    // method to set up frame
    private void setupFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculator();
            }
        });
    }
}