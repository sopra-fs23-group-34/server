package ch.uzh.ifi.hase.soprafs23.messages;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class FoodMessageTest {

    @Test
    void testGetContent() {
        // Create a new FoodMessage object with sample data
        FoodMessage message = new FoodMessage("food", "Pizza", "https://example.com/pizza.jpg");

        // Get the content of the message
        HashMap<String, String> content = message.getContent();

        // Check that the content contains the correct data
        assertEquals("Pizza", content.get("name"));
        assertEquals("https://example.com/pizza.jpg", content.get("imageLink"));
    }
    @Test
    void testGetTopic() {
        FoodMessage message = new FoodMessage("food", "Pizza", "https://example.com/pizza.jpg");
        assertEquals("food", message.getTopic());
    }
}

