package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class bg {
    private static Map<String, String> users = new HashMap<>(); // Database for storing user credentials

    public static void main(String[] args) {
        // Add some sample user credentials to the database
        users.put("admin", "admin123");
        users.put("user", "user123");

        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

    static class LoginForm extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;

        public LoginForm() {
            initComponents();

            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    if (authenticateUser(username, password)) {
                        JOptionPane.showMessageDialog(LoginForm.this, "Login Successful");
                        // Open the main application window or perform any other desired actions
                        dispose(); // Close the login window
                        BusListForm busListForm = new BusListForm();
                        busListForm.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password");
                    }
                }
            });
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Login");

            usernameField = new JTextField(20);
            passwordField = new JPasswordField(20);
            loginButton = new JButton("Login");

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Username: "));
            panel.add(usernameField);
            panel.add(new JLabel("Password: "));
            panel.add(passwordField);
            panel.add(new JLabel(""));
            panel.add(loginButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }

        private boolean authenticateUser(String username, String password) {
            String storedPassword = users.get(username);
            return storedPassword != null && storedPassword.equals(password);
        }
    }

    static class BusListForm extends JFrame {
        private JComboBox<String> busComboBox;
        private JButton bookButton;
        private JButton cartButton;
        private int selectedSeatCount = 0;
        private StringBuilder selectedSeats = new StringBuilder();

        public BusListForm() {
            initComponents();

            bookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedBus = (String) busComboBox.getSelectedItem();
                    if (selectedBus != null) {
                        dispose(); // Close the bus list window
                        SeatSelectionForm seatSelectionForm = new SeatSelectionForm(selectedBus, BusListForm.this);
                        seatSelectionForm.setVisible(true);
                    }
                }
            });

            cartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedSeatsString = selectedSeats.toString();
                    if (!selectedSeatsString.isEmpty()) {
                        dispose(); // Close the bus list window
                        PaymentForm paymentForm = new PaymentForm(selectedSeatsString, selectedSeatCount);
                        paymentForm.setVisible(true);
                    }
                }
            });

            updateCartButtonStatus();
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Bus List");

            String[] buses = {"Bus 1", "Bus 2", "Bus 3"}; // Add more buses as needed
            busComboBox = new JComboBox<>(buses);
            bookButton = new JButton("Book");
            cartButton = new JButton("Add to Cart");
            cartButton.setEnabled(false);

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(new JLabel("Select a bus: "));
            panel.add(busComboBox);
            panel.add(bookButton);
            panel.add(cartButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }

        public void updateCartButtonStatus() {
            cartButton.setEnabled(selectedSeatCount > 0);
        }

        public void updateSelectedSeats(String seatNumber, boolean selected) {
            if (selected) {
                selectedSeatCount++;
                selectedSeats.append(seatNumber).append(", ");
            } else {
                selectedSeatCount--;
                String seatToRemove = seatNumber + ", ";
                int index = selectedSeats.indexOf(seatToRemove);
                if (index != -1) {
                    selectedSeats.replace(index, index + seatToRemove.length(), "");
                }
            }
        }
    }

    static class SeatSelectionForm extends JFrame {
        private String selectedBus;
        private JButton[] seatButtons;
        private BusListForm busListForm;

        public SeatSelectionForm(String selectedBus, BusListForm busListForm) {
            this.selectedBus = selectedBus;
            this.busListForm = busListForm;
            initComponents();
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Seat Selection");

            int totalSeats = 50;
            seatButtons = new JButton[totalSeats];

            JPanel panel = new JPanel(new GridLayout(5, 10)); // Assuming 5 rows and 10 columns
            for (int i = 0; i < totalSeats; i++) {
                final String seatNumber = "Seat " + (i + 1);
                seatButtons[i] = new JButton(seatNumber);
                seatButtons[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        boolean selected = !button.isEnabled();
                        button.setEnabled(selected);

                        busListForm.updateSelectedSeats(seatNumber, selected);
                        busListForm.updateCartButtonStatus();
                    }
                });
                panel.add(seatButtons[i]);
            }

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }
    }

    static class PaymentForm extends JFrame {
        private JLabel seatsLabel;
        private JLabel totalSeatsLabel;
        private JButton payButton;

        public PaymentForm(String selectedSeats, int selectedSeatCount) {
            initComponents(selectedSeats, selectedSeatCount);

            payButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Simulate the payment process
                    JOptionPane.showMessageDialog(PaymentForm.this, "Payment successful");
                    dispose(); // Close the payment window
                }
            });
        }

        private void initComponents(String selectedSeats, int selectedSeatCount) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Payment");

            seatsLabel = new JLabel("Selected Seats: " + selectedSeats);
            totalSeatsLabel = new JLabel("Total Seats: " + selectedSeatCount);
            payButton = new JButton("Pay");

            JPanel panel = new JPanel(new GridLayout(3, 1));
            panel.add(seatsLabel);
            panel.add(totalSeatsLabel);
            panel.add(payButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }
    }
}
