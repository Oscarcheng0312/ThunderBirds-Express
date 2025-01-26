package persistence;

import model.Order;
import model.OrderList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads OrderList from JSON data stored in file
//Cited from JsonSerializationDemo 
public class JsonReader {
    private String source;


    //Effects: constructs reader to read from source file
    public JsonReader(String soucre) {
        this.source = soucre;
    }

    //EFFETCS: reads OrderList from file and returns it
    //throws IOException if an error occurs reading data from file
    public OrderList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrderList(jsonObject);
    }


    //EFFECTS: reads source file as string returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses orderlist from JSON object and returns it
    private OrderList parseOrderList(JSONObject jsonObject) {
        OrderList orderList = new OrderList();
        addOrders(orderList, jsonObject);
        return orderList;
    }


    // MODIFIES: orderlist
    //EFFECTS: parses thingies from JSON object and adds them to orderlist
    private void addOrders(OrderList orderList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("orders");
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            addOrder(orderList, nextOrder);
        }
    }

    // MODIFIES: orderlist
    // EFFECTS: parses thingy from JSON object and adds it to orderlist 
    private void addOrder(OrderList orderList, JSONObject jsonObject) {
        int id = jsonObject.getInt("orderId");
        String studentName = jsonObject.getString("studentName");
        String restaurant = jsonObject.getString("restaurant");
        String items = jsonObject.getString("items");
        int ratingMarks = jsonObject.getInt("ratingMarks");
        
        Order order = new Order(id, studentName, restaurant, items, ratingMarks);
        orderList.addOrder(order);
    }


}
