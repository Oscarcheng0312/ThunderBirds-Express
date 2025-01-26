package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

//Represent a list of orders
public class OrderList implements Writable {
    private List<Order> orders;

    //DEFFECTS: create an empty order list
    public OrderList() {
        this.orders = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: add a new order to the order list
    public void addOrder(Order order) {                       
        orders.add(order);
        EventLog.getInstance().logEvent(new Event("Order added: " + order.toString()));
    }


    // MODIFIES: this
    // EFFECTS: deletes an order from the order list by the given ID and returns true if successful, otherwise false
    public boolean deleteOrder(int id) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getID() == id) {
                EventLog.getInstance().logEvent(new Event("Order deleted: " + orders.get(i).toString())); 
                orders.remove(i);      
                return true; 
            }
        }
        EventLog.getInstance().logEvent(new Event("Order with ID " + id + " not found for deletion."));
        return false;  
    }
 

    
    //REQUIRES: id >= 0
    //EFFECTS: get a specific order form the list of order
    public Order getOrder(int id) {
        for (Order order: orders) {
            if (order.getID() == id) {
                return order;
            } 
        }
        return null;
    }


    //EFFECTS: get the list of order
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orders", ordersToJson());
        return json;
    }


    //EFFECTS: return orders in this order list as a JSON array
    private JSONArray ordersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Order order: orders) {
            jsonArray.put(order.toJson());
        }

        return jsonArray;
    }
}
