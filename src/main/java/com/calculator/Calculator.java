package com.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Calculator GUI class with modern UI design.
 */
public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private JLabel expressionDisplay;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean isNewNumber = true;
    private StringBuilder expression = new StringBuilder();
    private StringBuilder currentNumber = new StringBuilder();
    private CalculatorLogic logic = new CalculatorLogic();
    private boolean isErrorState = false;

    // Color scheme
    private static final Color BACKGROUND_COLOR = new Color(45, 45, 48);
    private static final Color DISPLAY_COLOR = new Color(30, 30, 32);
    private static final Color BUTTON_COLOR = new Color(70, 70, 73);
    private static final Color OPERATOR_COLOR = new Color(255, 149, 0);
    private static final Color EQUALS_COLOR = new Color(255, 149, 0);
    private static final Color CLEAR_COLOR = new Color(200, 70, 70);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color DISPLAY_TEXT_COLOR = Color.WHITE;

    public Calculator() {
        setTitle("Modern Calculator");
        setSize(350, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.setBackground(DISPLAY_COLOR);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        expressionDisplay = new JLabel("");
        expressionDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        expressionDisplay.setHorizontalAlignment(JLabel.RIGHT);
        expressionDisplay.setForeground(new Color(180, 180, 180));
        displayPanel.add(expressionDisplay, BorderLayout.NORTH);

        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(DISPLAY_COLOR);
        display.setForeground(DISPLAY_TEXT_COLOR);
        display.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        display.setOpaque(true);
        displayPanel.add(display, BorderLayout.CENTER);

        add(displayPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttonLabels = {
            "C", "⌫", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", ""
        };

        for (String label : buttonLabels) {
            if (label.isEmpty()) {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setBackground(BACKGROUND_COLOR);
                buttonPanel.add(emptyPanel);
                continue;
            }
            JButton button = createButton(label);
            if (label.equals("C")) {
                button.setBackground(CLEAR_COLOR);
            }
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Set button colors based on type
        if (label.equals("=")) {
            button.setBackground(EQUALS_COLOR);
        } else if (label.matches("[+\\-*/]")) {
            button.setBackground(OPERATOR_COLOR);
        } else if (label.equals("⌫")) {
            button.setBackground(BUTTON_COLOR);
        } else if (label.equals("C")) {
            button.setBackground(CLEAR_COLOR);
        } else {
            button.setBackground(BUTTON_COLOR);
        }

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (label.equals("=")) {
                    button.setBackground(EQUALS_COLOR);
                } else if (label.matches("[+\\-*/]")) {
                    button.setBackground(OPERATOR_COLOR);
                } else if (label.equals("C")) {
                    button.setBackground(CLEAR_COLOR);
                } else {
                    button.setBackground(BUTTON_COLOR);
                }
            }
        });

        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // If in error state, only allow C button
        if (isErrorState && !command.equals("C")) {
            return;
        }

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            System.out.println("DEBUG: Digit pressed: " + command + ", isNewNumber=" + isNewNumber + ", currentNumber before=" + currentNumber.toString());
            if (isNewNumber) {
                currentNumber.setLength(0);
                isNewNumber = false;
            }
            currentNumber.append(command);
            display.setText(currentNumber.toString());
            System.out.println("DEBUG: After processing, currentNumber=" + currentNumber.toString() + ", display=" + display.getText());
            updateExpression();
        } else if (command.equals("C")) {
            display.setText("");
            expression.setLength(0);
            currentNumber.setLength(0);
            expressionDisplay.setText("");
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
            isNewNumber = true;
            // Reset error state and restore display color
            if (isErrorState) {
                isErrorState = false;
                display.setForeground(DISPLAY_TEXT_COLOR);
            }
        } else if (command.equals("⌫")) {
            handleBackspace();
        } else if (command.equals("=")) {
            if (!operator.isEmpty() && currentNumber.length() > 0) {
                secondNumber = Double.parseDouble(currentNumber.toString());
                double result = calculate();
                // Only update display if not in error state (error is handled in calculate())
                if (!isErrorState) {
                    expression.setLength(0);
                    expression.append(formatResult(firstNumber)).append(" ").append(operator).append(" ").append(formatResult(secondNumber)).append(" = ");
                    expressionDisplay.setText(expression.toString());
                    display.setText(formatResult(result));
                }
                operator = "";
                isNewNumber = true;
                currentNumber.setLength(0);
                expression.setLength(0);
            }
        } else {
            // Operator buttons (+, -, *, /)
            if (currentNumber.length() > 0 || !isNewNumber) {
                if (!operator.isEmpty() && !isNewNumber) {
                    // Chain calculations
                    secondNumber = Double.parseDouble(currentNumber.toString());
                    double result = calculate();
                    firstNumber = result;
                    display.setText(formatResult(result));
                } else {
                    firstNumber = Double.parseDouble(currentNumber.toString());
                }
                operator = command;
                isNewNumber = true;
                currentNumber.setLength(0);
                expression.setLength(0);
                expression.append(formatResult(firstNumber)).append(" ").append(operator).append(" ");
                expressionDisplay.setText(expression.toString());
            }
        }
    }

    private void updateExpression() {
        System.out.println("DEBUG updateExpression: operator='" + operator + "', firstNumber=" + firstNumber + ", currentNumber=" + currentNumber);
        if (!operator.isEmpty()) {
            expression.setLength(0);
            expression.append(formatResult(firstNumber)).append(" ").append(operator).append(" ").append(currentNumber);
            System.out.println("DEBUG: Setting expression display to: " + expression.toString());
            expressionDisplay.setText(expression.toString());
        }
    }

    private void handleBackspace() {
        if (currentNumber.length() > 0) {
            // Remove last character from current number
            currentNumber.setLength(currentNumber.length() - 1);
            display.setText(currentNumber.toString());
            // Update expression if operator is set
            updateExpression();
        }
        // If current number is empty and we have an operator, clear the operator
        if (currentNumber.length() == 0 && !operator.isEmpty()) {
            operator = "";
            expression.setLength(0);
            expressionDisplay.setText("");
            isNewNumber = false;
            currentNumber.append(firstNumber);
            display.setText(currentNumber.toString());
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.format("%.8f", result).replaceAll("0+$", "").replaceAll("\\.$", "");
        }
    }

    private double calculate() {
        try {
            switch (operator) {
                case "+":
                    return logic.add(firstNumber, secondNumber);
                case "-":
                    return logic.subtract(firstNumber, secondNumber);
                case "*":
                    return logic.multiply(firstNumber, secondNumber);
                case "/":
                    return logic.divide(firstNumber, secondNumber);
                default:
                    return 0;
            }
        } catch (ArithmeticException e) {
            // Set error state and display error message in red
            isErrorState = true;
            display.setForeground(Color.RED);
            display.setText("Can't divide by 0");
            expressionDisplay.setText("");
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}

