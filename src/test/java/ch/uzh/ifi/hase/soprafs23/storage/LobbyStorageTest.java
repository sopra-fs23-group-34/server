package ch.uzh.ifi.hase.soprafs23.storage;

import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.junit.jupiter.api.Assertions.*;

public class LobbyStorageTest {
    private LobbyStorage lobbyStorage;

    private Lobby lobby;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private FoodService foodService;

    @BeforeEach
    void setUp() {
        lobbyStorage = new LobbyStorage();
        String gameCode  = "a";
        lobby = new Lobby(gameCode,simpMessagingTemplate,foodService);
    }

    @Test
    void testAddLobbyAndGetLobby() {
        String gameCode = "a";
        lobbyStorage.addLobby(gameCode, lobby);
        Lobby retrievedLobby = lobbyStorage.getLobby(gameCode);
        assertEquals(lobby, retrievedLobby);

    }

    @Test
    void testLobbyCodeNotExist() {
        String gameCode = "c";
        Lobby retrievedLobby = lobbyStorage.getLobby(gameCode);
        assertNull(retrievedLobby);
    }

    @Test
    void testRemoveLobby() {
        String gameCode = "a";
        lobbyStorage.addLobby(gameCode, lobby);
        lobbyStorage.removeLobby(gameCode);
        Lobby retrievedLobby = lobbyStorage.getLobby(gameCode);
        assertNull(retrievedLobby);
    }

    @Test
    void testRemoveLobbyNotExist() {
        String gameCode = "PQR";
        lobbyStorage.removeLobby(gameCode);
        assertNotNull(lobbyStorage);
    }
}
