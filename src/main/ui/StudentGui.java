package ui;

import model.Order;
import model.OrderList;
import persistence.JsonWriter;
import java.io.IOException;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/** The student interface */
public class StudentGui extends JFrame {
    private static final String JSON_STORE = "./data/orderList.json";
    private JsonWriter jsonWriter;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private OrderList orderList;
    private int orderId = 0;
    private DefaultListModel<String> listModel;
    private JList<String> orderDisplay;
    private JsonReader jsonReader;

    /*
     * Construct a StudentGui, with some buttoms at the right to deal with the order
     * and the order will be displayed at left
     */
    public StudentGui(OrderList sharedOrderList) {
        super("ThunderBirdsExpress -- UBC Campous Food Delivery");
        this.orderList = sharedOrderList;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        initializeFrameSettings();
        setupOrderDisplay();
        setupButtons();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the order display list and adds it to the frame
    private void setupOrderDisplay() {
        listModel = new DefaultListModel<>();
        orderDisplay = new JList<>(listModel);
        orderDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(orderDisplay);
        add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up basic frame properties (size, layout, close operation)
    private void initializeFrameSettings() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons and their actions
    private void setupButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setBackground(Color.YELLOW);
        JButton deleteOrderButton = new JButton("Delete Order");
        JButton rateOrderButton = new JButton("Rate Order");
        JButton loadOrdersButton = new JButton("Load Orders");
        JButton saveOrdersButton = new JButton("Save Orders");
        JButton quitButton = new JButton("Quit");

        buttonPanel.add(placeOrderButton);
        buttonPanel.add(deleteOrderButton);
        buttonPanel.add(rateOrderButton);
        buttonPanel.add(loadOrdersButton);
        buttonPanel.add(saveOrdersButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.EAST);

        placeOrderButton.addActionListener(e -> placeOrder());
        deleteOrderButton.addActionListener(e -> deleteOrder());
        rateOrderButton.addActionListener(e -> rateOrder());
        loadOrdersButton.addActionListener(e -> loadOrders());
        saveOrdersButton.addActionListener(e -> saveOrdersAndShowMessage());
        quitButton.addActionListener(e -> quitApplication());
    }

    // EFFECTS: quit student interface go back to main
    private void quitApplication() {
        new ThunderBirdsExpressMainGui();
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: update after operation
    private void updateOrderDisplay() {
        listModel.clear();
        for (Order order : orderList.getAllOrders()) {
            listModel.addElement(order.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: place a new order by student
    private void placeOrder() {
        String studentName = JOptionPane.showInputDialog(this, "Enter your name:");
        String restaurant = JOptionPane.showInputDialog(this, "Enter restaurant name:");
        String items = JOptionPane.showInputDialog(this, "Enter the items you want to order:");

        if (studentName != null && restaurant != null && items != null) {
            Order order = new Order(orderId, studentName, restaurant, items, 0);
            orderList.addOrder(order);
            orderId++;
            saveOrders();
            ImageIcon customIcon = new ImageIcon("image/background.png");
            JOptionPane.showMessageDialog(null, "Order placed successfully!",
                    "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
            updateOrderDisplay();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current order list to the JSON file
    private void saveOrders() {
        try {
            jsonWriter.open();
            jsonWriter.write(orderList);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save orders to file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete an order by id
    private void deleteOrder() {
        String name = JOptionPane.showInputDialog(this, "Enter your name to delete your orders:");
        if (name != null && !name.isEmpty()) {
            boolean found = false;
            for (Order order : orderList.getAllOrders()) {
                if (order.getStudentName().equalsIgnoreCase(name)) {
                    orderList.deleteOrder(order.getID());
                    found = true;
                }
            }
            if (found) {
                ImageIcon customIcon = new ImageIcon("image/background.png");
                JOptionPane.showMessageDialog(null, "Order delete successfully!",
                        "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
                updateOrderDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "No orders found for the name: " + name);
            }
        }
    }

    // REQUIRE: 1 <= score <= 5
    // MODIFIES: this
    // EFFECTS: give score for an order
    private void rateOrder() {
        String studentName = JOptionPane.showInputDialog(this, "Enter your name to rate your order:");
        if (studentName != null) {
            for (Order order : orderList.getAllOrders()) {
                if (order.getStudentName().equals(studentName)) {
                    String ratingStr = JOptionPane.showInputDialog(this, "Enter your rating (1-5):");
                    if (ratingStr != null) {
                        int rating = Integer.parseInt(ratingStr);
                        order.rateOrder(rating);
                        ImageIcon customIcon = new ImageIcon("image/background.png");
                        JOptionPane.showMessageDialog(null, "Thanks for your rating!",
                                "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
                        updateOrderDisplay();
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No order found for the given name.");
        }
    }

    // EFFECTS: load the orders from JSON and update the display
    public void loadOrders() {
        try {
            orderList = jsonReader.read();
            ImageIcon customIcon = new ImageIcon("image/background.png");
            JOptionPane.showMessageDialog(null, "Order placed successfully!",
                    "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
            updateOrderDisplay();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading orders from file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current order list to the JSON file and shows a
    // confirmation message
    private void saveOrdersAndShowMessage() {
        try {
            jsonWriter.open();
            jsonWriter.write(orderList);
            jsonWriter.close();
            ImageIcon customIcon = new ImageIcon("image/background.png");
            JOptionPane.showMessageDialog(null, "Orders saved successfully!",
                    "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save orders to file.");
        }
    }

}
