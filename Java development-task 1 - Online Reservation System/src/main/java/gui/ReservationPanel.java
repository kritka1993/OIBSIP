package gui;

import model.Booking;

import javax.swing.*;
import java.awt.*;

public class ReservationPanel extends JPanel {
    public ReservationPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Passenger Reservation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Passenger Name:"), gbc);
        gbc.gridx = 1; JTextField nameFld = new JTextField(15); add(nameFld, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Train Number (Numeric):"), gbc);
        gbc.gridx = 1; JTextField noFld = new JTextField(15); add(noFld, gbc);
        gbc.gridx = 2; JButton verifyBtn = new JButton("Verify"); add(verifyBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Train Name:"), gbc);
        gbc.gridx = 1; JTextField nameFldAuto = new JTextField(15); nameFldAuto.setEditable(false); add(nameFldAuto, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("Class Level:"), gbc);
        gbc.gridx = 1; JComboBox<String> classBox = new JComboBox<>(new String[]{"Sleeper (SL)", "AC 3 Tier (3A)", "AC 1 Tier (1A)"}); add(classBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5; add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; JTextField dateFld = new JTextField(15); add(dateFld, gbc);

        gbc.gridx = 0; gbc.gridy = 6; add(new JLabel("Source Station:"), gbc);
        gbc.gridx = 1; JTextField srcFld = new JTextField(15); add(srcFld, gbc);

        gbc.gridx = 0; gbc.gridy = 7; add(new JLabel("Destination Station:"), gbc);
        gbc.gridx = 1; JTextField destFld = new JTextField(15); add(destFld, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        JButton submitBtn = new JButton("Commit Booking & Issue PNR"); add(submitBtn, gbc);
        gbc.gridy = 9; JButton backBtn = new JButton("Back to Dashboard"); add(backBtn, gbc);

        verifyBtn.addActionListener(e -> {
            String tNo = noFld.getText().trim();
            if(!tNo.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Train must be numeric!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String lookup = frame.getDbService().fetchTrainName(tNo);
            nameFldAuto.setText(lookup != null ? lookup : "Custom Mapping Registered");
            if(lookup == null) nameFldAuto.setEditable(true);
        });

        submitBtn.addActionListener(e -> {
            if(nameFld.getText().isEmpty() || noFld.getText().isEmpty() || dateFld.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Required fields are empty!", "Validation Failure", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(!dateFld.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Date syntax must follow YYYY-MM-DD format.", "Validation Failure", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Booking b = new Booking(null, nameFld.getText(), noFld.getText(), nameFldAuto.getText(),
                        (String)classBox.getSelectedItem(), dateFld.getText(), srcFld.getText(), destFld.getText());
                String pnr = frame.getDbService().issueBooking(b);
                JOptionPane.showMessageDialog(this, "Successfully Booked!\n Your System PNR Sequence Code: " + pnr);
                frame.switchView("Dashboard");
            } catch (Exception ex) { ex.printStackTrace(); }
        });
        backBtn.addActionListener(e -> frame.switchView("Dashboard"));
    }

    public void clearFields() {
        Component[] components = this.getComponents();
        for (Component comp : components) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field.isEditable() || field.getName() != null || field.getText().equals("Custom Mapping Registered")) {
                    field.setText("");
                } else {
                    field.setText("");
                    field.setEditable(false);
                    field.setBackground(new Color(235, 235, 235));
                }
            }
        }
    }

}
