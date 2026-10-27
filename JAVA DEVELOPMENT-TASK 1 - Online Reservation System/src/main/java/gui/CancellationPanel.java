package gui;

import model.Booking;

import javax.swing.*;
import java.awt.*;

public class CancellationPanel extends JPanel {
    public CancellationPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Booking Cancellation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; add(new JLabel("Target PNR Field:"), gbc);
        gbc.gridx = 1; JTextField pnrFld = new JTextField(12); add(pnrFld, gbc);
        gbc.gridx = 2; JButton fetchBtn = new JButton("Search details"); add(fetchBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        JTextArea displayArea = new JTextArea(8, 30); displayArea.setEditable(false);
        add(new JScrollPane(displayArea), gbc);

        gbc.gridy = 3;
        JButton purgeBtn = new JButton("Confirm Booking Cancellation");
        purgeBtn.setEnabled(false);
        add(purgeBtn, gbc);

        gbc.gridy = 4;
        JButton backBtn = new JButton("Return to Dashboard");
        add(backBtn, gbc);

        fetchBtn.addActionListener(e -> {
            Booking b = frame.getDbService().fetchBooking(pnrFld.getText().trim());
            if(b != null) {
                displayArea.setText("PNR: " + b.getPnr() + "\nPassenger: " + b.getPassengerName() +
                        "\nTrain: " + b.getTrainName() + "\nRoute: " + b.getSource() + " -> " + b.getDestination());
                purgeBtn.setEnabled(true);
            } else {
                displayArea.setText(""); purgeBtn.setEnabled(false);
                JOptionPane.showMessageDialog(this, "PNR does not exist.Please enter valid PNR number");
            }
        });

        purgeBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to Cancel model.Booking ?", "Caution Alert", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                try {
                    frame.getDbService().removeBooking(pnrFld.getText().trim());
                    JOptionPane.showMessageDialog(this, "Booking Cancel successfully.");
                    pnrFld.setText(""); displayArea.setText(""); purgeBtn.setEnabled(false);
                    frame.switchView("Dashboard");
                } catch(Exception ex) { ex.printStackTrace(); }
            }
        });
        backBtn.addActionListener(e -> frame.switchView("Dashboard"));
    }
}
