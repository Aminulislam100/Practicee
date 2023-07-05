package lgn;

import com.sun.deploy.panel.RuleSetViewerDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketManagementProject extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField fromField;
    private JTextField toField;
    private JTextField dateField;

    public TicketManagementProject() {
        setTitle("Ticket Management");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Login Page Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (login(username, password)) {
                    // Successful login, show the Select Ticket Panel
                    loginPanel.setVisible(false);
                    RuleSetViewerDialog selectTicketPanel = null;
                    selectTicketPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(TicketManagementProject.this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginPanel.add(loginButton, gbc);

        // Select Ticket Panel
        JPanel selectTicketPanel = new JPanel();
        selectTicketPanel.setLayout(new GridBagLayout());

        JLabel fromLabel = new JLabel("From:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectTicketPanel.add(fromLabel, gbc);

        fromField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        selectTicketPanel.add(fromField, gbc);

        JLabel toLabel = new JLabel("To:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectTicketPanel.add(toLabel, gbc);

        toField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        selectTicketPanel.add(toField, gbc);

        JLabel dateLabel = new JLabel("Date of Journey:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        selectTicketPanel.add(dateLabel, gbc);

        dateField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        selectTicketPanel.add(dateField, gbc);

        JButton searchButton = new JButton("Search");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String from = TicketManagementProject.this.fromField.getText();
                String to = TicketManagementProject.this.toField.getText();
                String date = TicketManagementProject.this.dateField.getText();
                // Add your search logic here
                // For example, query the database with the provided parameters
            }
        });
        selectTicketPanel.add(searchButton, gbc);

        selectTicketPanel.setVisible(false);

        getContentPane().setLayout(new CardLayout());
        getContentPane().add(loginPanel);
        getContentPane().add(selectTicketPanel);
    }

    private boolean login(String username, String password) {
        // Add your login logic here, e.g., check the credentials against the database
        return username.equals("hr") && password.equals("hr");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TicketManagementProject project = new TicketManagementProject();
                project.setVisible(true);
            }
        });
    }
}
