package ch.uzh.ifi.hase.soprafs23.messages;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringMessageTest {
    @Test
    void testGetContent() {

        // Create a new MapMessage object with sample data
        StringMessage message = new StringMessage("string", "message");

        // Get the content of the message
        String StringContent = message.getContent();

        // Check that the content is equal to the expected value
        assertEquals("message", StringContent);
    }
    @Test
    void testGetTopic() {
        StringMessage message = new StringMessage("string", "message");
        assertEquals("string", message.getTopic());
    }
    @Test
    void GetTypeTest(){
        StringMessage message = new StringMessage("string", "messge");
        String s = message.getType();
        assertEquals(null,s);
    }
}
