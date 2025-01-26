package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TestOrderList {
    private OrderList list;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void runBefore() {
        list = new OrderList();
        order1 = new Order(0, "Oscar", "Happy Blue Whale", "Roast Duck", 0);
        order2 = new Order(1, "Kez", "Honor Roll", "Chicken Kastu", 0);
        list.addOrder(order1);
        list.addOrder(order2);
    }

    @Test
    public void testConstructor() {
        assertEquals(2, list.getAllOrders().size());
    }

    @Test
    public void testAddOneOrder() {
        Order order3 = new Order(3, "Lee", "Ryuu", "Beef Bento", 0);
        list.addOrder(order3);
        assertEquals(3, list.getAllOrders().size());
    }

    @Test
    public void testAddMultipleOrder() {
        Order order3 = new Order(3, "Lee", "Ryuu", "Beef Bento", 0);
        list.addOrder(order3);
        assertEquals(3, list.getAllOrders().size());
        Order order4 = new Order(4, "Max", "Beyond BBQ", "Fried Udon", 0);
        list.addOrder(order4);
        assertEquals(4, list.getAllOrders().size());
    }

    @Test
    public void testDeleteOneOrder() {
        assertEquals(2, list.getAllOrders().size());
        list.deleteOrder(0);
        assertEquals(1, list.getAllOrders().size());
    }

    @Test
    public void testDeleteMultipleOrder() {
        assertEquals(2, list.getAllOrders().size());
        list.deleteOrder(0);
        assertEquals(1, list.getAllOrders().size());
        list.deleteOrder(1);
        assertEquals(0, list.getAllOrders().size());
    }

    @Test
    public void testFailToDeleteOrder() {
        assertEquals(2, list.getAllOrders().size());
        list.deleteOrder(10);
        assertEquals(2, list.getAllOrders().size());
    }

    @Test
    public void testGetOrderExist() {
        Order result = list.getOrder(1);
        assertEquals("Kez", result.getStudentName());
    }

    @Test
    public void testGetOrderNotExist() {
        Order result = list.getOrder(10);
        assertNull(result);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> result = list.getAllOrders();
        assertEquals(2, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
    }

    @Test
    public void testToJson() {
        OrderList orderList = new OrderList();
        Order order1 = new Order(0, "Oscar", "Honor Roll", "Sushi", 5);
        Order order2 = new Order(1, "Kez", "Kinton Ramen", "Chicken Katsu", 4);
        orderList.addOrder(order1);
        orderList.addOrder(order2);

        JSONObject json = orderList.toJson();
        JSONObject expectedJson = new JSONObject();
        JSONArray ordersArray = new JSONArray();

        ordersArray.put(createOrderJson(0, "Oscar", "Honor Roll", "Sushi", "received", 5));
        ordersArray.put(createOrderJson(1, "Kez", "Kinton Ramen", "Chicken Katsu", "received", 4));

        expectedJson.put("orders", ordersArray);
        assertEquals(expectedJson.toString(), json.toString());
    }

    

    @Test
    public void testOrdersToJson() {
        OrderList orderList = new OrderList();
        Order order1 = new Order(0, "Oscar", "Honor Roll", "Sushi", 5);
        Order order2 = new Order(1, "Kez", "Kinton Ramen", "Chicken Katsu", 4);
        orderList.addOrder(order1);
        orderList.addOrder(order2);

        // Get the actual JSON output from the method
        JSONObject json = orderList.toJson();

        // Create expected JSON
        JSONObject expectedJson = new JSONObject();
        JSONArray expectedArray = new JSONArray();

        // Call the helper method to create expected JSON for each order
        expectedArray.put(createOrderJson(0, "Oscar", "Honor Roll", "Sushi", "received", 5));
        expectedArray.put(createOrderJson(1, "Kez", "Kinton Ramen", "Chicken Katsu", "received", 4));

        expectedJson.put("orders", expectedArray);

        // Assert equality between actual and expected JSON
        assertEquals(expectedJson.toString(), json.toString());
    }


    private JSONObject createOrderJson(int orderId, String studentName,
            String restaurant, String items, String status, int rating) {
        JSONObject jsonOrder = new JSONObject();
        jsonOrder.put("orderId", orderId);
        jsonOrder.put("studentName", studentName);
        jsonOrder.put("restaurant", restaurant);
        jsonOrder.put("orderStatus", status);
        jsonOrder.put("items", items);
        jsonOrder.put("ratingMarks", rating);
        return jsonOrder;
    }



}
