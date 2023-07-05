package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketManagementApp1 {
    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/pdborcl";
    private static final String DB_USER = "hr";
    private static final String DB_PASSWORD = "hr";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }

    static class LoginForm extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;
        private Connection connection;

        public LoginForm() {
            initComponents();
            initializeDatabaseConnection();

            loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    if (authenticateUser(username, password)) {
                        JOptionPane.showMessageDialog(LoginForm.this, "Login Successful");
                        dispose();
                        bf.BusListForm busListForm = new bf.BusListForm();
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

        private void initializeDatabaseConnection() {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(LoginForm.this, "Failed to connect to the database");
                System.exit(1);
            }
        }

        private boolean authenticateUser(String username, String password) {
            try {
                String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    // Rest of the code for BusListForm, SeatSelectionForm, and TicketDetailsForm remains the same
    // ...
}
