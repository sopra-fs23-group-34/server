package ch.uzh.ifi.hase.soprafs23.messages;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BooleanMessageTest {

    @Test
    void testGetContent() {
        BooleanMessage message = new BooleanMessage("test_topic", true);
        assertTrue(message.getContent());

        message = new BooleanMessage("test_topic", false);
        assertFalse(message.getContent());
    }

    @Test
    void testGetTopic() {
        BooleanMessage message = new BooleanMessage("test_topic", true);
        assertEquals("test_topic", message.getTopic());
    }
}





