package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class bslst {
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
                        dispose(); // Close the login form

                        // Open the main application window
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
        private JList<String> busList;
        private JButton bookButton;

        public BusListForm() {
            initComponents();

            bookButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedBus = busList.getSelectedValue();
                    if (selectedBus != null) {
                        // Perform booking logic here
                        JOptionPane.showMessageDialog(BusListForm.this, "Booking successful for " + selectedBus);
                    } else {
                        JOptionPane.showMessageDialog(BusListForm.this, "Please select a bus");
                    }
                }
            });
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Bus List");

            String[] busData = { "Bus 1", "Bus 2", "Bus 3", "Bus 4", "Bus 5" };
            busList = new JList<>(busData);
            bookButton = new JButton("Book Selected Bus");

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(busList), BorderLayout.CENTER);
            panel.add(bookButton, BorderLayout.SOUTH);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(null);
        }
    }
}
