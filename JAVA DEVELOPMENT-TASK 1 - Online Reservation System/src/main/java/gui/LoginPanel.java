package gui;

import service.DatabaseService;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPanel extends JPanel {
    private boolean isRegisterMode = false;

    public LoginPanel(MainFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login or Create account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; JTextField userField = new JTextField(15); add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; JPasswordField passField = new JPasswordField(15); add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton actionBtn = new JButton("Login");
        actionBtn.setForeground(Color.BLUE);
        add(actionBtn, gbc);

        gbc.gridy = 4;
        JButton toggleBtn = new JButton("Create New Account? Register Here");
        toggleBtn.setBorderPainted(false);
        toggleBtn.setContentAreaFilled(false);
        toggleBtn.setForeground(Color.BLUE);
        add(toggleBtn, gbc);

        toggleBtn.addActionListener(e -> {
            isRegisterMode = !isRegisterMode;
            if (isRegisterMode) {
                title.setText("Create New Account");
                actionBtn.setText("Confirm Registration");
                toggleBtn.setText("Already have an account? Log In");
            } else {
                title.setText("Login or Create account");
                actionBtn.setText("Login");
                toggleBtn.setText("Create New Account? Register Here");
            }
            userField.setText("");
            passField.setText("");
        });

        actionBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be left blank!", "Input Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (isRegisterMode) {
                try {
                    DatabaseService dbService = frame.getDbService();

                    PreparedStatement checkStmt = dbService.getConnection().prepareStatement("SELECT 1 FROM users WHERE username = ?");
                    checkStmt.setString(1, user);
                    if (checkStmt.executeQuery().next()) {
                        JOptionPane.showMessageDialog(this, "Username is already taken! Try another one.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PreparedStatement insertStmt = dbService.getConnection().prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                    insertStmt.setString(1, user);
                    insertStmt.setString(2, pass);
                    insertStmt.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Account successfully registered!.");

                    toggleBtn.doClick();
                    frame.switchView("Dashboard");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database write failure: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (frame.getDbService().authenticate(user, pass)) {
                    JOptionPane.showMessageDialog(this, "Login successfully.");
                    userField.setText("");
                    passField.setText("");
                    frame.switchView("Dashboard");
                } else {
                    JOptionPane.showMessageDialog(this, "Access Denied: Invalid Username or Password Configuration", "Auth Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
