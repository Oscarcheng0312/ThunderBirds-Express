package ui;

import model.Order;
import model.OrderList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/** The Restaurant owner interface */
public class RestaurantOwnerGui extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/orderList.json";
    private OrderList orderList;
    private DefaultListModel<String> listModel;
    private JList<String> orderDisplay;
    private JsonReader jsonReader;

    /*
     * Construct a RestaurantOwnerGui, with some buttoms at the right to deal with
     * the order
     * and the order will be displayed at left
     */
    public RestaurantOwnerGui(OrderList sharedOrderList) {
        super("ThunderBirdsExpress - Restaurant Owner");
        this.orderList = sharedOrderList;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        jsonReader = new JsonReader(JSON_STORE);
        setupOrderDisplay();
        setupButtons();
        setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: sets up buttons and their actions
    private void setupButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));

        JButton updateOrderButton = new JButton("Update Order");
        JButton deleteOrderButton = new JButton("Delete Order");
        JButton loadOrdersButton = new JButton("Load Orders");
        JButton saveOrdersButton = new JButton("Save Orders");
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(updateOrderButton);
        buttonPanel.add(deleteOrderButton);
        buttonPanel.add(loadOrdersButton);
        buttonPanel.add(saveOrdersButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.EAST);
        updateOrderButton.addActionListener(e -> updateOrder());
        deleteOrderButton.addActionListener(e -> deleteOrder());
        loadOrdersButton.addActionListener(e -> loadOrders());
        saveOrdersButton.addActionListener(e -> saveOrders());
        quitButton.addActionListener(e -> quitApplication());
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

    // EFFECTS: quit the owner go back to main
    private void quitApplication() {
        new ThunderBirdsExpressMainGui();
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: update the order after some operation
    private void updateOrderDisplay() {
        listModel.clear();
        for (Order order : orderList.getAllOrders()) {
            listModel.addElement(order.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: update the status of the order
    public void updateOrder() {
        String orderIdString = JOptionPane.showInputDialog(this, "Enter the order ID to update:");
        int id = Integer.parseInt(orderIdString);
        Order order = orderList.getOrder(id);
        if (order != null) {
            String newStatus = JOptionPane.showInputDialog(this, "Enter the new status for order ID" + id + " :");
            if (!newStatus.isBlank()) {
                order.updateOrderStatus(newStatus);
                ImageIcon customIcon = new ImageIcon("image/background.png");
                JOptionPane.showMessageDialog(null, "Order updated successfully!",
                        "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
                updateOrderDisplay();
            } else {
                JOptionPane.showInputDialog(this, "invalid order status");
            }
        } else {
            JOptionPane.showInputDialog(this, "Order not founded");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete the order by the given ID
    public void deleteOrder() {
        String orderIdString = JOptionPane.showInputDialog(this, "Enter the ID of the order you want to delete");
        int id = Integer.parseInt(orderIdString);
        boolean isDeleted = orderList.deleteOrder(id);
        if (isDeleted) {
            ImageIcon customIcon = new ImageIcon("image/background.png");
            JOptionPane.showMessageDialog(null, "Order delete successfully!",
                    "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found.");
        }
    }

    // EFFECTS: load the orders from JSON
    public void loadOrders() {
        try {
            orderList = jsonReader.read();
            ImageIcon customIcon = new ImageIcon("image/background.png");
            JOptionPane.showMessageDialog(null, "Order loaded successfully!",
                    "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
            updateOrderDisplay();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading orders from file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the current order list to the JSON file
    private void saveOrders() {
        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(orderList);
            jsonWriter.close();
            ImageIcon customIcon = new ImageIcon("image/background.png");
            JOptionPane.showMessageDialog(null, "Orders saved successfully!",
                    "Message", JOptionPane.INFORMATION_MESSAGE, customIcon);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to save orders to file.");
        }
    }   

    

}
