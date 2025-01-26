package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Order;

public class JsonTest {
    protected void checkOrder(int id, String studentName,
            String restaurant, String items, int ratingMarks, Order order) {
        assertEquals(id, order.getID());
        assertEquals(studentName, order.getStudentName());
        assertEquals(restaurant, order.getRestaurantName());
        assertEquals(items, order.getItems());
        assertEquals(ratingMarks, order.getRatingMarks());
    }

}
