package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.CodeGenerator;
import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class LobbyServiceTest {
    @InjectMocks
    private LobbyService lobbyService;

    @Mock
    private LobbyStorage lobbyStorage;

    @Mock
    private FoodService foodService;

    @Mock
    private CodeGenerator codeGenerator;

    @Mock
    SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_nullLobby() {
        //assertThrows(ResponseStatusException.class, () -> lobbyService.);
    }

    @Test
    void checkIfLobbyExistsSuccessful() {
        //MessageChannel channel = Mockito.mock(MessageChannel.class);
        //lobbyStorage.addLobby("11", new Lobby("11", new SimpMessagingTemplate(channel), foodService));
        when(codeGenerator.nextCode()).thenReturn("123456");
        String code = lobbyService.createLobby();
        assertEquals("123456", code);
    }

    @Test
    void updatePlayer() {
        Lobby lobby = new Lobby("11", simpMessagingTemplate, foodService);
        lobby.addPlayer(new Player("test", 1L, true));
        when(lobbyStorage.getLobby("123456")).thenReturn(lobby);
        List<Player> playerList = lobbyService.updatePlayerList("123456");
        assertEquals("test", playerList.get(0).getUsername());
        assertEquals(1, playerList.size());
    }

    @Test
    void joinLobbyHost() {
        Lobby lobby = new Lobby("11", simpMessagingTemplate, foodService);
        when(lobbyStorage.getLobby("11")).thenReturn(lobby);
        User user = new User();
        user.setUsername("testUser");
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn((user));
        assertTrue(lobbyService.joinLobby("11", 1L));
    }

    @Test
    void leaveLobbyHostLeft() {
        Lobby lobby = new Lobby("11", simpMessagingTemplate, foodService);
        lobby.addPlayer(new Player("testUser1", 1L, false));
        lobby.addPlayer(new Player("testUser2", 2L, true));
        when(lobbyStorage.getLobby("11")).thenReturn(lobby);
        List<Player> playerList = lobbyService.leaveLobby("11", 2L);
        assertEquals(1, playerList.size());
    }

    @Test
    void startGame(){
        Lobby lobby = new Lobby("11", simpMessagingTemplate, foodService);
        lobby.addPlayer(new Player("testUser1", 1L, false));
        lobby.addPlayer(new Player("testUser2", 2L, true));
        when(lobbyStorage.getLobby("11")).thenReturn(lobby);
    }

    @Test
    void setPlayerGuesses() {
        Lobby lobby = new Lobby("11", simpMessagingTemplate, foodService);
        lobby.addPlayer(new Player("testUser1", 1L, false));
        lobby.addPlayer(new Player("testUser2", 2L, true));
        when(lobbyStorage.getLobby("11")).thenReturn(lobby);
        Map<String, Double> guesses = new HashMap<>();
        guesses.put("calories", 10.0);
        guesses.put("carbs", 20.0);
        guesses.put("fat", 30.0);
        lobbyService.setPlayerGuesses("11", 1L, guesses);
        Map<Long, Player> playerList = lobby.getPlayers();
        assertEquals(10.0, playerList.get(1L).getGuesses().get("calories"));
        assertEquals(20.0, playerList.get(1L).getGuesses().get("carbs"));
        assertEquals(30.0, playerList.get(1L).getGuesses().get("fat"));
    }
}
