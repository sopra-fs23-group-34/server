package ch.uzh.ifi.hase.soprafs23.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LobbyMessageTest {

    @Test
    public void testGetContent() {
        LobbyMessage message = new LobbyMessage();
        message.setContent("Hello World!");
        Assertions.assertEquals("Hello World!", message.getContent());
    }

    @Test
    public void testSetContent() {
        LobbyMessage message = new LobbyMessage();
        message.setContent("Goodbye World!");
        Assertions.assertEquals("Goodbye World!", message.getContent());
    }
}
