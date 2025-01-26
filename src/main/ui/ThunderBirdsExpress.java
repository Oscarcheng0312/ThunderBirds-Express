package ui;

import java.util.Scanner;
import model.Order;
import model.OrderList;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;

// food delivery manage system
//Cited from JsonSerializationDemo 
public class ThunderBirdsExpress {
    private OrderList studentOrders;
    private Scanner input;
    private int id = 0;
    private static final String JSON_STORE = "./data/orderlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: run the ThunderBirdsExpress application
    public ThunderBirdsExpress() throws FileNotFoundException {
        input = new Scanner(System.in);
        studentOrders = new OrderList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runThunderBirdsExpress();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runThunderBirdsExpress() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();


        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processComand(command);
            }
        }
        System.out.println("\nGoodbye, have a nice day!");
    }

    private void processComand(String command) {
        if (command.equals("a")) {
            addOrder();
        } else if (command.equals("d")) {
            deleteOrder();
        } else if (command.equals("f")) {
            handleFindOrder();
        } else if (command.equals("g")) {
            getAllOrder();
        } else if (command.equals("u")) {
            updateOrderStatus();
        } else if (command.equals("p")) {
            printOrders();
        } else if (command.equals("s")) {
            saveOrderList();
        } else if (command.equals("l")) {
            loadOrderList();
        } else {
            System.out.println("Selection not valid");
        }
    }
    

    //MODIFIE: this
    //EFFECTS: deal with the rate order choice
    private void handleFindOrder() {
        Order foundOrder = findOrder();
        if (foundOrder != null) {
            System.out.println("Would you like to rate your order? (yes/no)");
            String command = input.nextLine().toLowerCase();
            if (command.equals("yes")) {
                rateOrder(foundOrder);
            } else {
                System.out.println("It is fine, hope you enjoy your meal.");
            }
        }
    }
    

    // MODIFIES: this
    // EFFECTS: initializes the order list and 
    private void init() {
        studentOrders = new OrderList();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add order");
        System.out.println("\td -> delete order");
        System.out.println("\tf -> find order");
        System.out.println("\tg -> get all order");
        System.out.println("\tu -> update order status");
        System.out.println("\tp -> print orders");
        System.out.println("\ts -> save order list to file");
        System.out.println("\tl -> load order list from file");
        System.out.println("\tq -> quit");
    }


    //MODIFIES: this
    //EFFECTS: add an order to our system
    private void addOrder() {
        System.out.println("Enter student's name: ");
        String studentName = input.nextLine();
        System.out.println("Enter restaurant name: ");
        String restaurant = input.nextLine();
        System.out.println("Enter iteams you want order: ");
        String iteams = input.nextLine();
        Order newOrder = new Order(id, studentName, restaurant, iteams, 0);
        studentOrders.addOrder(newOrder);;
        System.out.println("Add order successfully!!!");
        id++;
    }


    //MODIFIES: this
    //EFFECTS: delete an order from the order list
    private void deleteOrder() {
        System.out.println("Enter the ID of the order you want to delete:");
        int id = input.nextInt();
        input.nextLine();

        boolean delete = studentOrders.deleteOrder(id);
        if (delete) {
            System.out.println("The order ID: " + id + "has been deleted successfully");
        } else {
            System.out.println("Sorry, the order with ID: " + id + "cannot been found");
        }    
    }



    //MODIFIES: this
    //EFFECTS: find the order by the given id
    private Order findOrder() {
        System.out.println("Enter you order's id");
        id = input.nextInt(); 
        input.nextLine();
        Order foundedOrder = studentOrders.getOrder(id);
        if (foundedOrder != null) {
            System.out.println("Got it");
            System.out.println("ID: " + foundedOrder.getID());
            System.out.println("StudentName: " + foundedOrder.getStudentName());
            System.out.println("Status: " + foundedOrder.getStatus());
            System.out.println("items: " + foundedOrder.getItems());
            System.out.println("rate mark: " + foundedOrder.getRatingMarks());
            return foundedOrder;
        } else {
            System.out.println("Order with ID: " + id + "is not founded");
            return null;
        }      
    }


    //MODIFIES: this
    //EFFECTS: displace all the order
    private void getAllOrder() {
        System.out.println("Okay, boss it is your trun to get all the order");

        if (studentOrders.getAllOrders().isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : studentOrders.getAllOrders()) {
                System.out.println("Order ID: " + order.getID());
                System.out.println("Student Name: " + order.getStudentName());
                System.out.println("Restaurant: " + order.getRestaurantName());
                System.out.println("Items: " + order.getItems());
                System.out.println("Order Status: " + order.getStatus());
                System.out.println("Rating: " + order.getRatingMarks());
            }
        }
    }



    // MODIFIES: this
    // EFFECTS: updates the status of the order with the given ID
    private void updateOrderStatus() {
        System.out.println("Enter the order ID to update:");
        int orderId = input.nextInt();
        input.nextLine(); 
        Order order = studentOrders.getOrder(orderId);

        if (order != null) {
            System.out.println("Current status: " + order.getStatus());
        
            System.out.println("Enter new order status ('received', 'preparing', 'delivered', 'finished'):");
            String newStatus = input.nextLine();
            order.updateOrderStatus(newStatus);
            System.out.println("Order status updated successfully!");
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }



    //REQUIERS: the rating mark should be integer among 0 to 5
    //MODIFIES: this
    //EFFECTS: rate the order, 0 means unrated, 5 means the best 
    private void rateOrder(Order order) {
        System.out.println("Enter you mark for your order from 1 to 5, five means the best:");
        int ratingMarks = input.nextInt();
        input.nextLine();
        order.rateOrder(ratingMarks);
        System.out.println("Thanks for your rating, hope you enjoy your meal");      
    }


    //EFFECTS: prints all the orders in orderlist to the console
    private void printOrders() {
        List<Order> orders = studentOrders.getAllOrders();

        for (Order order: orders) {
            System.out.println(order);
        }
    }


    // EFFECTS: saves the orderlist to file 
    private void saveOrderList() {
        try {
            jsonWriter.open();
            jsonWriter.write(studentOrders);
            jsonWriter.close();
            System.out.println("Orders saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    //MODIFIES: this
    //EFFECTS: loads order list from file
    private void loadOrderList() {
        try {
            studentOrders = jsonReader.read();
            System.out.println("Loaded order list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
