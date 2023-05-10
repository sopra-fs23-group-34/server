package ch.uzh.ifi.hase.soprafs23.messages;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapMessageTest {
    @Test
    void testGetContent() {
        Map<String, Integer> contentMap = new HashMap<>();
        contentMap.put("player1", 34);

        // Create a new MapMessage object with sample data
        MapMessage message = new MapMessage("map", contentMap);

        // Get the content of the message
        Map<String, Integer> content = message.getContent();

        // Check that the content is equal to the expected value
        assertEquals(contentMap, content);
    }
    @Test
    void testGetTopic() {
        Map<String, Integer> contentMap = new HashMap<>();
        contentMap.put("player1", 34);

        MapMessage message = new MapMessage("map", contentMap);
        assertEquals("map", message.getTopic());
    }
}
