package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;
import java.util.List;

import model.Order;
import model.OrderList;
import java.io.IOException;


public class JsonWriterTest extends JsonTest {


    @Test
    public void testWriterInvalidFile() {
        try {
            OrderList orderList = new OrderList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    public void testWriterEmptyOrderList() {
        try {
            OrderList orderList = new OrderList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(orderList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            orderList = reader.read();
            assertEquals(0, orderList.getAllOrders().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    public void testWriterGeneralOrderList() {
        try {
            OrderList orderList = new OrderList();
            orderList.addOrder(new Order(1, "Oscar", "Happy Blue Whale", "Roast Duck", 5));
            orderList.addOrder(new Order(2, "Kez", "Honor Roll", "Chicken Katsu", 4));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralOrderList.json");
            writer.open();
            writer.write(orderList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralOrderList.json");
            orderList = reader.read();
            assertEquals(2, orderList.getAllOrders().size());
            
            List<Order> orders = orderList.getAllOrders();
            checkOrder(1, "Oscar", "Happy Blue Whale", "Roast Duck", 5, orders.get(0));
            checkOrder(2, "Kez", "Honor Roll", "Chicken Katsu", 4, orders.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
