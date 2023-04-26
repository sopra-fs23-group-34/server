package ch.uzh.ifi.hase.soprafs23.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanMessageTest {
    @Test
    public void testGetContent() {
        BooleanMessage message = new BooleanMessage("test_topic", true);
        assertTrue(message.getContent());

        message = new BooleanMessage("test_topic", false);
        assertFalse(message.getContent());
    }

    @Test
    public void testGetTopic() {
        BooleanMessage message = new BooleanMessage("test_topic", true);
        assertEquals("test_topic", message.getTopic());
    }
}





