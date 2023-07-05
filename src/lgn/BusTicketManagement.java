package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BusTicketManagement {
    private static Map<String, String> users = new HashMap<>(); // Database for storing user credentials

    public static void main(String[] args) {
        // Add some sample user credentials to the database
        users.put("admin", "admin123");
        users.put("user", "user123");

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                    } else {
                        JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password");
                    }
                }
            });
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Login");

            ImageIcon icon = new ImageIcon("path/to/your/icon.png");
            setIconImage(icon.getImage());

            usernameField = new JTextField(10);
            passwordField = new JPasswordField(10);
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
            setPreferredSize(new Dimension(400, 200));
            setResizable(false);
            setLocationRelativeTo(null);
        }

        private boolean authenticateUser(String username, String password) {
            String storedPassword = users.get(username);
            return storedPassword != null && storedPassword.equals(password);
        }
    }
}
