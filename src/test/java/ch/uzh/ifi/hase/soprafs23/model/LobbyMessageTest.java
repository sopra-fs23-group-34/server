package ch.uzh.ifi.hase.soprafs23.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LobbyMessageTest {

    @Test
    void testGetContent() {
        LobbyMessage message = new LobbyMessage();
        message.setContent("Hello World!");
        Assertions.assertEquals("Hello World!", message.getContent());
    }

    @Test
    void testSetContent() {
        LobbyMessage message = new LobbyMessage();
        message.setContent("Goodbye World!");
        Assertions.assertEquals("Goodbye World!", message.getContent());
    }
}
