package ui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import model.OrderList;
import model.Event;
import model.EventLog;

import java.awt.*;

/** The main user interface */
public class ThunderBirdsExpressMainGui extends JFrame {
    //private static final String JSON_STORE = "./data/orderList.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private OrderList sharedOrderList;

    /*
     * Construct a ThunderBirdsExpress food deliver system, with a title in the
     * middle
     * and two buttom one is studnet another is restaurant owner
     */
    public ThunderBirdsExpressMainGui() {
        super("ThunderBirdsExpress -- UBC Campous Food Delivery");
        initializeFrameSettings();
        sharedOrderList = new OrderList();

        addImageToTop();
        JLabel welcomeLabel = new JLabel("Welcome to ThunderBirdsExpress", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.CENTER);
        setupButtons();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitApplication();
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: sets up buttons and their actions
    private void setupButtons() {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton studentButton = new JButton("I am a Student");
        JButton restaurantOwnerButton = new JButton("I am a Restaurant Owner");
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(studentButton);
        buttonPanel.add(restaurantOwnerButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        studentButton.addActionListener(e -> studentGui());
        restaurantOwnerButton.addActionListener(e -> restaurantOwnerGui());
        quitButton.addActionListener(e -> quitApplication());
    }

    // MODIFIES: this
    // EFFECTS: sets up basic frame properties (size, layout, close operation)
    private void initializeFrameSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
    }

    // Effects: if we click the student buttom, we will go to studnet interface
    private void studentGui() {
        new StudentGui(sharedOrderList);
        dispose();
    }

    private void addImageToTop() {
        ImageIcon imageIcon = new ImageIcon("image/main.png");
        Image resizedImage = imageIcon.getImage().getScaledInstance(414, 377, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.NORTH);
    }

    // Effects: if we clicke the restaurant owner buttom, we will go to restaurant
    // owner interface
    private void restaurantOwnerGui() {
        new RestaurantOwnerGui(sharedOrderList);
        dispose();
    }

    // EFFECTS: exits the application
    private void quitApplication() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            printEventLog();
            System.exit(0);
        }
    }

    // Helper method to print the event log
    private void printEventLog() {
        System.out.println("Event Log:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    public static void main(String[] args) {
        new ThunderBirdsExpressMainGui();
    }

}
