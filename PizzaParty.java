//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PizzaParty extends JFrame {

    // GUI components
    private JTextField guestField;
    private JComboBox<String> pizzaSizeComboBox;
    private JButton orderButton;
    private JLabel totalCostLabel;
    private JLabel messageLabel;

    // pizza prices
    private final double SMALL_PRICE = 10.00;
    private final double MEDIUM_PRICE = 15.00;
    private final double LARGE_PRICE = 20.00;

    public PizzaParty() {
        super("🍕 Pizza Party Planner: ");

        //set up GUI components
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        //make components
        createComponents();

        setupActions(); //confg. actions

        setupFrame(); //config. frame
    }

    private void createComponents() {

        // title label
        JLabel titleLabel = new JLabel("🎊 Welcome to Pizza Party Planner! 🎊");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        // guest number input
        JLabel guestLabel = new JLabel("👥 Number of guests:");
        guestLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        guestField = new JTextField(10);
        guestField.setFont(new Font("Arial", Font.PLAIN, 14));

        // select pizza size
        JLabel sizeLabel = new JLabel("🍕 Pizza size (per guest):");
        sizeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        String[] pizzaSizes = { "Select size...", "Small - $10.00", "Medium - $15.00", "Large - $20.00" };
        pizzaSizeComboBox = new JComboBox<>(pizzaSizes);
        pizzaSizeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        //order button
        orderButton = new JButton("📦 Place Order 🛒");
        orderButton.setFont(new Font("Arial", Font.BOLD, 14));
        orderButton.setBackground(new Color(50, 205, 50)); // Verde
        orderButton.setForeground(Color.WHITE);

        // Labels para mostrar resultados
        totalCostLabel = new JLabel("💰 Total cost: $0.00");
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalCostLabel.setForeground(new Color(255, 69, 0)); // Laranja

        messageLabel = new JLabel("✨ Please enter number of guests and select pizza size ✨");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        messageLabel.setForeground(Color.GRAY);

        // Adicionar todos os componentes ao frame
        add(guestLabel);
        add(guestField);
        add(sizeLabel);
        add(pizzaSizeComboBox);
        add(orderButton);
        add(totalCostLabel);
        add(messageLabel);
    }

    private void setupActions() {

        // order button action
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });
    }

    private void placeOrder() {

        // verify input and calculate total cost
        String guestText = guestField.getText().trim();
        
        // empty input
        if (guestText.isEmpty()) {
            messageLabel.setText("❌ Error: Please enter the number of guests!");
            messageLabel.setForeground(Color.RED);
            guestField.requestFocus();
            return;
        }
        
        //integer number validation
        int numberOfGuests;
        try {
            numberOfGuests = Integer.parseInt(guestText);
        } catch (NumberFormatException e) {
            messageLabel.setText("❌ Error: Please enter a valid number (e.g., 10)!");
            messageLabel.setForeground(Color.RED);
            guestField.setText("");
            guestField.requestFocus();
            return;
        }
        
        // negative number validation
        if (numberOfGuests <= 0) {
            messageLabel.setText("❌ Error: Number of guests must be greater than zero!");
            messageLabel.setForeground(Color.RED);
            guestField.setText("");
            guestField.requestFocus();
            return;
        }
        
        // choice pizza size validation
        int selectedIndex = pizzaSizeComboBox.getSelectedIndex();
        if (selectedIndex == 0) {
            messageLabel.setText("❌ Error: Please select a pizza size!");
            messageLabel.setForeground(Color.RED);
            return;
        }
        
        // max. guest number
        if (numberOfGuests > 500) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "⚠️ " + numberOfGuests + " guests is a lot! Do you have enough pizza?",
                "Large Party Warning",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.NO_OPTION) {
                return;
            }
        }
        
        // total cost calculation
        double pricePerPizza = getPizzaPrice(selectedIndex);
        double totalCost = numberOfGuests * pricePerPizza;
        
        //print result
        String pizzaSize = (String) pizzaSizeComboBox.getSelectedItem();
        totalCostLabel.setText(String.format("💰 Total cost: $%.2f", totalCost));
        totalCostLabel.setForeground(new Color(34, 139, 34)); // Verde escuro
        
        messageLabel.setText(String.format("✅ Your order has been placed! %d guests ordering %s each. Total: $%.2f 🎉", 
                              numberOfGuests, pizzaSize, totalCost));
        messageLabel.setForeground(new Color(34, 139, 34)); // Verde escuro
        
        //only for show a order confirmation dialog
        JOptionPane.showMessageDialog(this, 
            String.format("🎊 ORDER CONFIRMED! 🎊\n\n👥 Guests: %d\n🍕 Pizza: %s\n💰 Total: $%.2f\n\nThank you for using Pizza Party Planner!", 
                          numberOfGuests, pizzaSize, totalCost),
            "Order Confirmed",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void calculateAndShowTotal() {

        // Mostrar prévia do custo sem "place order"
        String guestText = guestField.getText().trim();
        if (guestText.isEmpty()) return;
        
        try {
            int numberOfGuests = Integer.parseInt(guestText);
            int selectedIndex = pizzaSizeComboBox.getSelectedIndex();
            
            if (selectedIndex > 0 && numberOfGuests > 0) {
                double pricePerPizza = getPizzaPrice(selectedIndex);
                double totalCost = numberOfGuests * pricePerPizza;
                totalCostLabel.setText(String.format("💰 Estimated total: $%.2f", totalCost));
                totalCostLabel.setForeground(Color.BLUE);
            }
        } catch (NumberFormatException e) {
            // Ignorar, o botão fará a validação completa
        }
    }
    
    private double getPizzaPrice(int selectedIndex) {
        switch (selectedIndex) {
            case 1: return SMALL_PRICE;  // Small
            case 2: return MEDIUM_PRICE; // Medium
            case 3: return LARGE_PRICE;  // Large
            default: return 0;
        }
    }
    
    private void setupFrame() {
        setSize(500, 350);
        setLocationRelativeTo(null);  // Centralizar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(255, 248, 225)); 
        setVisible(true);
    }
    
    public static void main(String[] args) {
        // Executar na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PizzaParty();
            }
        });
    }
}
