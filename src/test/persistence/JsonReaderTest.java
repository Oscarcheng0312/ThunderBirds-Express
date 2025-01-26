package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import model.Order;
import model.OrderList;
import java.util.List;

import java.io.IOException;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            OrderList orderList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    public void testReaderEmptyOrderList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOrderList.json");
        try {
            OrderList orderList = reader.read();
            assertEquals(0, orderList.getAllOrders().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralOrderList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralOrderList.json");
        try {
            OrderList orderList = reader.read();
            List<Order> orders = orderList.getAllOrders();
            assertEquals(2, orders.size());
            checkOrder(0, "Oscar", "Honor Roll", "Sushi", 5, orders.get(0));
            checkOrder(1, "Kez", "Kinton Ramen", "Chicken Katsu", 4, orders.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    
}
