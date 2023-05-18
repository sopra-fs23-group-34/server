package ch.uzh.ifi.hase.soprafs23.messages;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class IntMessageTest {

    @Test
    void testGetContent() {
        // Create a new IntMessage object with sample data
        IntMessage message = new IntMessage("number", 42);

        // Get the content of the message
        int content = message.getContent();

        // Check that the content is equal to the expected value
        assertEquals(42, content);
    }
    @Test
    void testGetTopic() {
        IntMessage message = new IntMessage("number", 42);
        assertEquals("number", message.getTopic());
    }
    @Test
    void GetTypeTest(){
        IntMessage message = new IntMessage("number", 42);
        String s = message.getType();
        assertEquals(null,s);
    }
}

