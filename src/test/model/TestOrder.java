package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestOrder {
    private Order order1;
    private Order order2;

    @BeforeEach
    void runBefore() {
        order1 = new Order(1, "Oscar", "Kindon Ramen", "Beef Ramen", 0);
        order2 = new Order(2, "Kez", "Kindon Ramen", "Chicken Katus", 0);
    }

    @Test
    void testConstructor() {
        assertEquals(1, order1.getID());
        assertEquals("Oscar", order1.getStudentName());
        assertEquals("Kindon Ramen", order1.getRestaurantName());
        assertEquals("received", order1.getStatus());
        assertEquals("Beef Ramen", order1.getItems());
        assertEquals(0, order1.getRatingMarks());

        assertEquals(2, order2.getID());
        assertEquals("Kez", order2.getStudentName());
        assertEquals("Kindon Ramen", order2.getRestaurantName());
        assertEquals("received", order2.getStatus());
        assertEquals("Chicken Katus", order2.getItems());
        assertEquals(0, order2.getRatingMarks());
    }

    @Test
    void testRateOrder() {
        assertEquals(0, order1.getRatingMarks());
        order1.rateOrder(4);
        assertEquals(4, order1.getRatingMarks());
        order2.rateOrder(5);
        assertEquals(5, order2.getRatingMarks());
    }

    @Test
    void testUpdateOrderStatus() {
        assertEquals("received", order1.getStatus());
        order1.updateOrderStatus("preparing");
        assertEquals("preparing", order1.getStatus());
        order1.updateOrderStatus("delivering");
        assertEquals("delivering", order1.getStatus());
        order1.updateOrderStatus("finished");
        assertEquals("finished", order1.getStatus());
    }

    @Test
    void testUpdateOrderStatusSameStatus() {
        order1.updateOrderStatus("received");
        assertEquals("received", order1.getStatus());

        order1.updateOrderStatus("received");
        assertEquals("received", order1.getStatus());
    }

    @Test
    public void testToString() {
        Order order = new Order(0, "Oscar", "Honor Roll", "Sushi", 5);
        String expectedString = "Order ID: 0, Student Name: Oscar, Restaurant: Honor Roll, Status: received, "
                + "Items: Sushi, Rating: 5";
        assertEquals(expectedString, order.toString());
    }

    @Test
    public void testToJson() {
        Order order = new Order(1, "Kez", "Kinton Ramen", "Chicken Kastu", 0);
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("orderId", 1);
        expectedJson.put("studentName", "Kez");
        expectedJson.put("restaurant", "Kinton Ramen");
        expectedJson.put("orderStatus", "received");
        expectedJson.put("items", "Chicken Kastu");
        expectedJson.put("ratingMarks", 0);

        assertEquals(expectedJson.toString(), order.toJson().toString());
    }

}
