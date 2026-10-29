package gui;

import service.DatabaseService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DatabaseService dbService;
    private ReservationPanel reservationPanel;

    public MainFrame() {
        setTitle("Train/Transport reservation system");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dbService = new DatabaseService();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        reservationPanel = new ReservationPanel(this);

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new DashboardPanel(this), "Dashboard");
        mainPanel.add(reservationPanel, "Reservation");
        mainPanel.add(new CancellationPanel(this), "Cancellation");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    public DatabaseService getDbService() { return dbService; }

    public ReservationPanel getReservationPanel() { return reservationPanel; }

    public void switchView(String viewName) { cardLayout.show(mainPanel, viewName); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
