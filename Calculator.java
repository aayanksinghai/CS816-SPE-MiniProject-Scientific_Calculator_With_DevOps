import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean isNewNumber = true;
    private CalculatorLogic logic = new CalculatorLogic();

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
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(DISPLAY_COLOR);
        display.setForeground(DISPLAY_TEXT_COLOR);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        display.setOpaque(true);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 8, 8));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = createButton(label);
            buttonPanel.add(button);
        }

        JPanel clearPanel = new JPanel();
        clearPanel.setLayout(new BorderLayout());
        clearPanel.setBackground(BACKGROUND_COLOR);
        clearPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        JButton clearButton = createButton("C");
        clearButton.setBackground(CLEAR_COLOR);
        clearPanel.add(clearButton, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(clearPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(mainPanel, BorderLayout.CENTER);
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

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
            if (isNewNumber) {
                display.setText(command);
                isNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("");
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
            isNewNumber = true;
        } else if (command.equals("=")) {
            if (!operator.isEmpty()) {
                secondNumber = Double.parseDouble(display.getText());
                double result = calculate();
                display.setText(String.valueOf(result));
                operator = "";
                isNewNumber = true;
            }
        } else {
            if (!operator.isEmpty()) {
                secondNumber = Double.parseDouble(display.getText());
                double result = calculate();
                display.setText(String.valueOf(result));
                firstNumber = result;
            } else {
                firstNumber = Double.parseDouble(display.getText());
            }
            operator = command;
            isNewNumber = true;
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
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
