package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    public DashboardPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Home Page", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; add(title, gbc);

        JButton reserveBtn = new JButton("Ticket Booking ");
        JButton cancelBtn = new JButton("Cancellation & Review Manager");
        JButton logoutBtn = new JButton("Logout");

        logoutBtn.addActionListener(e -> {
            frame.getReservationPanel().clearFields();
            JOptionPane.showMessageDialog(this, "LogOut successfully.");
            frame.switchView("Login");
        });

        gbc.gridy = 1; add(reserveBtn, gbc);
        gbc.gridy = 2; add(cancelBtn, gbc);
        gbc.gridy = 3; add(logoutBtn, gbc);

        reserveBtn.addActionListener(e -> frame.switchView("Reservation"));
        cancelBtn.addActionListener(e -> frame.switchView("Cancellation"));
        logoutBtn.addActionListener(e -> frame.switchView("Login"));
    }
}
