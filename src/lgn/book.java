package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class book {
    private static Map<String, String> users = new HashMap<>(); // Database for storing user credentials

    public static void main(String[] args) {
        // Add some sample user credentials to the database
        users.put("admin", "1234");
        users.put("user", "1234");

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
                        dispose(); // Close the login form
                        showBusList(); // Show the bus list form
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

        private void showBusList() {
            BusListForm busListForm = new BusListForm();
            busListForm.setVisible(true);
        }
    }

    static class BusListForm extends JFrame {
        private JButton bookButton;

        public BusListForm() {
            initComponents();
            populateBusList();
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Bus List");

            bookButton = new JButton("Book");

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(bookButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }

        private void populateBusList() {
            // TODO: Fetch bus list from the database or any other source
            // and display the list of available buses in the form
            // For now, let's assume we have two sample buses

            String[] busList = {"Bus 1", "Bus 2"};

            JComboBox<String> busComboBox = new JComboBox<>(busList);

            getContentPane().add(busComboBox, BorderLayout.CENTER);

            bookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedBus = (String) busComboBox.getSelectedItem();
                    showSeatSelection(selectedBus);
                }
            });
        }

        private void showSeatSelection(String selectedBus) {
            SeatSelectionForm seatSelectionForm = new SeatSelectionForm(selectedBus);
            seatSelectionForm.setVisible(true);
            dispose(); // Close the bus list form
        }
    }

    static class SeatSelectionForm extends JFrame {
        private String selectedBus;
        private JButton backButton;

        public SeatSelectionForm(String selectedBus) {
            this.selectedBus = selectedBus;
            initComponents();
            populateSeatGrid();
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Seat Selection");

            backButton = new JButton("Back");

            JPanel panel = new JPanel(new FlowLayout());
            panel.add(backButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);

            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    goBackToBusList();
                }
            });
        }

        private void populateSeatGrid() {
            // TODO: Fetch seat information for the selected bus
            // from the database or any other source
            // For now, let's assume we have a 5x5 seat grid

            JPanel seatPanel = new JPanel(new GridLayout(5, 5));

            for (int i = 1; i <= 25; i++) {
                JButton seatButton = new JButton(String.valueOf(i));
                seatPanel.add(seatButton);
            }

            getContentPane().add(seatPanel, BorderLayout.CENTER);
        }

        private void goBackToBusList() {
            BusListForm busListForm = new BusListForm();
            busListForm.setVisible(true);
            dispose(); // Close the seat selection form
        }
    }
}
