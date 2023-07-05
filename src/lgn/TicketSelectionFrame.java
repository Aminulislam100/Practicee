package lgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicketSelectionFrame extends JFrame {
    private JTextField fromField;
    private JTextField toField;
    private JTextField dateField;
    private JButton searchButton;

    public TicketSelectionFrame() {
        setTitle("Ticket Management - Select Ticket");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(100, 100, 80, 25);
        panel.add(fromLabel);

        fromField = new JTextField();
        fromField.setBounds(190, 100, 200, 25);
        panel.add(fromField);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(100, 150, 80, 25);
        panel.add(toLabel);

        toField = new JTextField();
        toField.setBounds(190, 150, 200, 25);
        panel.add(toField);

        JLabel dateLabel = new JLabel("Date of Journey:");
        dateLabel.setBounds(100, 200, 120, 25);
        panel.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(220, 200, 170, 25);
        panel.add(dateField);

        searchButton = new JButton("Search");
        searchButton.setBounds(250, 250, 100, 25);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String from = fromField.getText();
                String to = toField.getText();
                String date = dateField.getText();
                // Add code to search for available tickets based on the inputs
                // Display the search results or show a message if no tickets are found
            }
        });
        panel.add(searchButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TicketSelectionFrame();
    }
}
