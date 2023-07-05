package lgn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bf {
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
        private SeatSelectionForm seatSelectionForm;

        public BusListForm() {
            initComponents();

            bookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedBus = (String) busComboBox.getSelectedItem();
                    if (selectedBus != null) {
                        dispose(); // Close the bus list window
                        seatSelectionForm = new SeatSelectionForm(selectedBus);
                        seatSelectionForm.setVisible(true);
                    }
                }
            });

            cartButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (seatSelectionForm != null) {
                        CartForm cartForm = new CartForm(seatSelectionForm.getSelectedSeats(), seatSelectionForm.getSelectedPrices());
                        cartForm.setVisible(true);
                    }
                }
            });
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
    }

    static class SeatSelectionForm extends JFrame {
        private String selectedBus;
        private JButton[] seatButtons;
        private boolean[] seatAvailability;
        private List<Integer> selectedSeats;
        private List<Integer> selectedPrices;

        public SeatSelectionForm(String selectedBus) {
            this.selectedBus = selectedBus;
            seatAvailability = new boolean[50]; // Assuming 50 seats per bus
            initComponents();
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Seat Selection");

            int totalSeats = 50;
            seatButtons = new JButton[totalSeats];
            selectedSeats = new ArrayList<>();
            selectedPrices = new ArrayList<>();

            JPanel panel = new JPanel(new GridLayout(0, 2)); // Variable number of rows, 2 columns
            JScrollPane scrollPane = new JScrollPane(panel);
            getContentPane().add(scrollPane);

            for (int i = 0; i < totalSeats; i++) {
                seatButtons[i] = new JButton("Seat " + (i + 1));
                seatAvailability[i] = true; // All seats are initially available
                seatButtons[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        int seatNumber = Integer.parseInt(button.getText().replace("Seat ", "")) - 1;
                        if (seatAvailability[seatNumber]) {
                            seatAvailability[seatNumber] = false; // Book the seat
                            button.setBackground(Color.RED); // Update seat color
                            selectedSeats.add(seatNumber + 1); // Add seat number to selected seats
                            selectedPrices.add(500); // Add seat price to selected prices (assuming 500 for each seat)
                        } else {
                            seatAvailability[seatNumber] = true; // Cancel the booking
                            button.setBackground(null); // Reset seat color
                            selectedSeats.remove(Integer.valueOf(seatNumber + 1)); // Remove seat number from selected seats
                            selectedPrices.remove(Integer.valueOf(500)); // Remove seat price from selected prices
                        }
                    }
                });
                panel.add(seatButtons[i]);
            }

            JButton homeButton = new JButton("Home");
            JButton backButton = new JButton("Back");

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(homeButton);
            buttonPanel.add(backButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            homeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    LoginForm loginForm = new LoginForm();
                    loginForm.setVisible(true);
                }
            });

            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    BusListForm busListForm = new BusListForm();
                    busListForm.setVisible(true);
                }
            });

            pack();
            setLocationRelativeTo(null);
        }

        public boolean[] getSeatAvailability() {
            return seatAvailability;
        }

        public String getSelectedBus() {
            return selectedBus;
        }

        public List<Integer> getSelectedSeats() {
            return selectedSeats;
        }

        public List<Integer> getSelectedPrices() {
            return selectedPrices;
        }
    }

    static class CartForm extends JFrame {
        private String selectedBus;
        private List<Integer> selectedSeats;
        private List<Integer> selectedPrices;
        private JButton checkoutButton;

        public CartForm(List<Integer> selectedSeats, List<Integer> selectedPrices) {
            this.selectedSeats = selectedSeats;
            this.selectedPrices = selectedPrices;
            initComponents();
        }

        public CartForm() {
            
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Cart");

            JPanel panel = new JPanel(new GridLayout(0, 2)); // Variable number of rows, 2 columns
            JScrollPane scrollPane = new JScrollPane(panel);
            getContentPane().add(scrollPane);

            for (int i = 0; i < selectedSeats.size(); i++) {
                panel.add(new JLabel("Seat " + selectedSeats.get(i)));
                panel.add(new JLabel("Price: " + selectedPrices.get(i)));
            }

            checkoutButton = new JButton("Checkout");
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(checkoutButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            checkoutButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    int totalPrice = 0;
                    for (Integer price : selectedPrices) {
                        totalPrice += price;
                    }
                    PaymentForm paymentForm = new PaymentForm(totalPrice);
                    paymentForm.setVisible(true);
                }
            });

            pack();
            setLocationRelativeTo(null);
        }
    }

    static class PaymentForm extends JFrame {
        private JButton backButton;
        private JButton confirmButton;
        private int totalPrice;

        public PaymentForm(int totalPrice) {
            this.totalPrice = totalPrice;
            initComponents();
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Payment");

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Payment Details"));
            panel.add(new JTextField());
            panel.add(new JLabel("Total Amount"));
            panel.add(new JTextField(String.valueOf(totalPrice)));

            getContentPane().add(panel);

            backButton = new JButton("Back");
            confirmButton = new JButton("Confirm");

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(backButton);
            buttonPanel.add(confirmButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    CartForm cartForm = new CartForm();
                    cartForm.setVisible(true);
                }
            });

            confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(PaymentForm.this, "Payment Successful");
                    dispose();
                    LoginForm loginForm = new LoginForm();
                    loginForm.setVisible(true);
                }
            });

            pack();
            setLocationRelativeTo(null);
        }



    }
}
