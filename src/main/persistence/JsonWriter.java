package persistence;

import model.OrderList;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of orderlist to file
//Cited from JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;


    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    //MODIFIES: this
    //EFFECTS: writes JSON representation of workroom to file
    public void write(OrderList orderList) {
        JSONObject json = orderList.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: close writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this 
    //EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }

}
