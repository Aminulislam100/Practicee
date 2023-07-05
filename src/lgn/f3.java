package lgn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class f3 {
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
            setSize(600, 600);
            setResizable(false);

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
            setLocationRelativeTo(null);
        }

        private boolean authenticateUser(String username, String password) {
            String storedPassword = users.get(username);
            return storedPassword != null && storedPassword.equals(password);
        }
    }

    static class BusListForm extends JFrame {
        private JTable busTable;
        private JScrollPane scrollPane;
        private JButton addButton;
        private JButton updateButton;
        private JButton deleteButton;
        private DefaultTableModel tableModel;

        public BusListForm() {
            initComponents();

            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Open a dialog to add a new bus
                    String busName = JOptionPane.showInputDialog(BusListForm.this, "Enter bus name:");
                    if (busName != null && !busName.isEmpty()) {
                        addBus(busName);
                    }
                }
            });

            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = busTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        // Open a dialog to update the selected bus
                        String busName = JOptionPane.showInputDialog(BusListForm.this, "Enter updated bus name:", busTable.getValueAt(selectedRow, 0));
                        if (busName != null && !busName.isEmpty()) {
                            updateBus(selectedRow, busName);
                        }
                    } else {
                        JOptionPane.showMessageDialog(BusListForm.this, "Please select a row to update");
                    }
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = busTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int confirm = JOptionPane.showConfirmDialog(BusListForm.this, "Are you sure you want to delete this bus?");
                        if (confirm == JOptionPane.YES_OPTION) {
                            deleteBus(selectedRow);
                        }
                    } else {
                        JOptionPane.showMessageDialog(BusListForm.this, "Please select a row to delete");
                    }
                }
            });
        }

        private void initComponents() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Bus Ticket Management - Bus List");
            setSize(600, 600);
            setResizable(false);

            String[] columnNames = {"Bus Name"};
            tableModel = new DefaultTableModel(columnNames, 0);
            busTable = new JTable(tableModel);
            scrollPane = new JScrollPane(busTable);

            addButton = new JButton("Add");
            updateButton = new JButton("Update");
            deleteButton = new JButton("Delete");

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            setLocationRelativeTo(null);
        }

        private void addBus(String busName) {
            Vector<String> row = new Vector<>();
            row.add(busName);
            tableModel.addRow(row);
        }

        private void updateBus(int row, String busName) {
            tableModel.setValueAt(busName, row, 0);
        }

        private void deleteBus(int row) {
            tableModel.removeRow(row);
        }
    }
}
