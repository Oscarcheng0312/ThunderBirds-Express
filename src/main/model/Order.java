package model;

import org.json.JSONObject;

import persistence.Writable;

/*Represents an order having an id, student name. restaurant, 
order status, total price, order items, timeplaced, rating marks
*/
public class Order implements Writable {
    private int orderId;
    private String studentName;
    private String restaurant;
    private String orderStatus; // Like "received", "preparing", "delivering"
    private String items; // Like "Burger", "Remen", "Pizza", "chicken katsu"
    private int ratingMarks; // from 0 to 5, 0 means unrated, 5 means the best

    // REQUIRE: the rating mark should among 1 to 5
    /*
     * EFFECTS: the order id is set to some integer, and studnetName is string,
     * name of restaureant also string, iteam is the string
     * the food that the student orderd, and rating marks are integers from 1 to 5.
     */
    public Order(int id, String studentName, String restaurant, String items, int ratingMarks) {
        this.orderId = id;
        this.studentName = studentName;
        this.restaurant = restaurant;
        this.orderStatus = "received";
        this.items = items;
        this.ratingMarks = ratingMarks;

    }

    // EFFECTS: return id of the order
    public int getID() {
        return this.orderId;
    }

    // EFFECTS: return the name of the student who place the order
    public String getStudentName() {
        return this.studentName;
    }

    // EFFECTS: retrun the name of the restaurant who received the order
    public String getRestaurantName() {
        return this.restaurant;
    }

    // EFFECTS: return the order's status
    public String getStatus() {
        return this.orderStatus;
    }

    // EFFECTS: return the iteams in the order
    public String getItems() {
        return this.items;
    }

    // EFFECTS: gives the rating mark of the order
    public int getRatingMarks() {
        return this.ratingMarks;
    }

    // REQUIRES: 5 >= ratingMarks >= 1
    // MODIFIES: this
    // EFFECTS: rate the order among 1-5, 0 means unrated
    public void rateOrder(int ratingMarks) {
        this.ratingMarks = ratingMarks;
        EventLog.getInstance().logEvent(new Event("Order rated: "
                + "Order ID: " + this.orderId
                + ", New Rating: " + ratingMarks));
    }

    // MODIFIES: this
    /*
     * EFFECTS: update the status of a order the initial status includes received,
     * preparing, delivering, finished
     */
    public void updateOrderStatus(String newStatus) {
        if (!this.orderStatus.equals(newStatus)) {
            this.orderStatus = newStatus;
            EventLog.getInstance().logEvent(new Event("Order status updated to: " + newStatus));
        }
    }

    // EFFECTS: returns string representation of this order
    @Override
    public String toString() {
        return "Order ID: " + orderId
                + ", Student Name: " + studentName
                + ", Restaurant: " + restaurant
                + ", Status: " + orderStatus
                + ", Items: " + items
                + ", Rating: " + ratingMarks;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderId", orderId);
        json.put("studentName", studentName);
        json.put("restaurant", restaurant);
        json.put("orderStatus", orderStatus);
        json.put("items", items);
        json.put("ratingMarks", ratingMarks);
        return json;
    }

}
