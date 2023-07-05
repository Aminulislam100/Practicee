package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TicketManagementApp {
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
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Bus List");

            String[] buses = {"Bus 1", "Bus 2", "Bus 3"}; // Add more buses as needed
            busComboBox = new JComboBox<>(buses);
            bookButton = new JButton("Book");

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(new JLabel("Select a bus: "));
            panel.add(busComboBox);
            panel.add(bookButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }
    }

    static class SeatSelectionForm extends JFrame {
        private String selectedBus;
        private JButton[] seatButtons;
        private boolean[] seatAvailability;
        private java.util.List<Integer> selectedSeats;
        private JButton nextButton;

        public SeatSelectionForm(String selectedBus) {
            this.selectedBus = selectedBus;
            seatAvailability = new boolean[50]; // Assuming 50 seats per bus
            initComponents();

            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedSeats.isEmpty()) {
                        JOptionPane.showMessageDialog(SeatSelectionForm.this, "Please select at least one seat");
                    } else {
                        dispose(); // Close the seat selection window
                        TicketDetailsForm ticketDetailsForm = new TicketDetailsForm(selectedBus, selectedSeats);
                        ticketDetailsForm.setVisible(true);
                    }
                }
            });
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Seat Selection");

            int totalSeats = 50;
            seatButtons = new JButton[totalSeats];
            selectedSeats = new java.util.ArrayList<>();

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
                        } else {
                            seatAvailability[seatNumber] = true; // Cancel the booking
                            button.setBackground(null); // Reset seat color
                            selectedSeats.remove(Integer.valueOf(seatNumber + 1)); // Remove seat number from selected seats
                        }
                    }
                });
                panel.add(seatButtons[i]);
            }

            nextButton = new JButton("Next");

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(nextButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
        }
    }

    static class TicketDetailsForm extends JFrame {
        private String selectedBus;
        private java.util.List<Integer> selectedSeats;
        private JButton confirmButton;

        public TicketDetailsForm(String selectedBus, java.util.List<Integer> selectedSeats) {
            this.selectedBus = selectedBus;
            this.selectedSeats = selectedSeats;
            initComponents();

            confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Perform any required actions, such as saving the ticket details to a database or generating a ticket

                    StringBuilder sb = new StringBuilder();
                    sb.append("Selected Bus: ").append(selectedBus).append("\n");
                    sb.append("Selected Seats: ").append(selectedSeats.toString()).append("\n");

                    JOptionPane.showMessageDialog(TicketDetailsForm.this, sb.toString());

                    dispose(); // Close the ticket details window
                    LoginForm loginForm = new LoginForm();
                    loginForm.setVisible(true);
                }
            });
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Ticket Details");

            JTextArea ticketDetailsArea = new JTextArea(5, 20);
            ticketDetailsArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(ticketDetailsArea);
            getContentPane().add(scrollPane);

            StringBuilder sb = new StringBuilder();
            sb.append("Selected Bus: ").append(selectedBus).append("\n");
            sb.append("Selected Seats: ").append(selectedSeats.toString()).append("\n");

            ticketDetailsArea.setText(sb.toString());

            confirmButton = new JButton("Confirm");

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(confirmButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
        }
    }
}
