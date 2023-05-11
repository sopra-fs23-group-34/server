package ch.uzh.ifi.hase.soprafs23.service;


import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.messages.StringMessage;
import ch.uzh.ifi.hase.soprafs23.model.CodeGenerator;
import ch.uzh.ifi.hase.soprafs23.model.GameConfig;
import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import ch.uzh.ifi.hase.soprafs23.storage.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.server.ResponseStatusException;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LobbyServiceTest {


    @Mock
    private UserService userService;



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

    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private GameConfig gameConfig;


    private Lobby testLobby;


    SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    UserService userService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        testLobby = new Lobby("1",simpMessagingTemplate,foodService);
    }


    @Test
    public void createLobbyTest(){
        when(codeGenerator.nextCode()).thenReturn("1");
        String code = lobbyService.createLobby();
        assertEquals(code, "1");
    }

    @Test
    public void updatePlayerListTest(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        Lobby testLobby = lobbyStorage.getLobby("a");
        testLobby.addPlayer(new Player("a",1L, true));
        List<Player> playerList = lobbyService.updatePlayerList("a");
        assertEquals(playerList.size() , 1);
        testLobby.addPlayer(new Player("c",2L, true));
        playerList = lobbyService.updatePlayerList("a");
        assertEquals(playerList.size() , 2);
    }


    @Test
    public void joinLobbyTestSuccess(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("a");
        when(userService.getUserById(anyLong())).thenReturn(testUser);
        boolean host1 = lobbyService.joinLobby("a", 1L);
        assertEquals(host1 , true);
        testUser.setId(2L);
        when(userService.getUserById(anyLong())).thenReturn(testUser);
        boolean host2 = lobbyService.joinLobby("a", 2L);
        assertEquals(host2,false);



        //lobbyService.joinLobby(new Player("a",1L, true));
        //assertEquals(testLobby.getPlayers().get(1L).getPlayer_id(),1L );
        //assertEquals(testLobby.getPlayers().get(1L).getUsername(),"a" );
        //assertEquals(testLobby.getPlayers().get(1L).isHost(),true );
        //testLobby.addPlayer(new Player("b",2L, false));
        //assertEquals(testLobby.getPlayers().get(2L).isHost(),false );
        //assertEquals(testLobby.getPlayers().keySet().size(),2 );
    }

    @Test
    public void joinLobbyTestFail(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(null);
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("a");
        when(userService.getUserById(anyLong())).thenReturn(testUser);
        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                        () -> lobbyService.joinLobby("a", 1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }


    @Test
    public void leaveLobbyHostTest(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        testLobby.addPlayer(new Player("a",1L,true));
        assertTrue(testLobby.getPlayers().containsKey(1L));
        lobbyService.leaveLobby("a",1L);
        assertFalse(testLobby.getPlayers().containsKey(1L));
        Mockito.verify(simpMessagingTemplate).convertAndSend(eq(WebsocketConfig.LOBBIES + "a"), any(StringMessage.class));
    }
    @Test
    public void leaveLobbyPlayerTest(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        testLobby.addPlayer(new Player("a",1L,true));
        testLobby.addPlayer(new Player("b",2L,false));
        assertTrue(testLobby.getPlayers().containsKey(2L));
        lobbyService.leaveLobby("a",2L);
        assertFalse(testLobby.getPlayers().containsKey(2L));
        Mockito.verifyNoInteractions(simpMessagingTemplate);
    }


    @Test
    public void startGameTestSuccess(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        testLobby.addPlayer(new Player("a",1L,true));
        assertDoesNotThrow(() -> lobbyService.startGame("a", 1L, "a", gameConfig));
    }
    @Test
    public void startGameNotHostTest(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        testLobby.addPlayer(new Player("a",1L,true));
        testLobby.addPlayer(new Player("a",2L,false));
        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                        () -> lobbyService.startGame("a", 2L, "a", gameConfig));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    public void startGameNotLobbyTest(){
        when(lobbyStorage.getLobby(anyString())).thenReturn(null);
        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                        () -> lobbyService.startGame("a", 2L, "a", gameConfig));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    public void startGameThreadErrorTest() throws ResponseStatusException, InterruptedException, IOException {
        Lobby testLobby = Mockito.mock(Lobby.class);
        when(lobbyStorage.getLobby(anyString())).thenReturn(testLobby);
        when(testLobby.playGame(gameConfig)).thenThrow(ResponseStatusException.class);
        Mockito.doNothing().when(testLobby).checkIfGameStarted();
        doNothing().when(testLobby).checkIfGameStarted();
        testLobby.addPlayer(new Player("a",1L,true));
        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                        () -> lobbyService.startGame("a", 1L, "a", gameConfig));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }


    @Test
    public void setPlayerGuesses(){
        String gameCode = "a";
        long playerId = 1L;
        Map<String, Double> guesses = new HashMap<>();
        guesses.put("apfel", 1.0);
        Lobby mockLobby = Mockito.mock(Lobby.class);
        when(lobbyStorage.getLobby(gameCode)).thenReturn(mockLobby);
        Player mockPlayer = Mockito.mock(Player.class);
        Map<Long, Player> playersMap = new HashMap<>();
        playersMap.put(playerId, mockPlayer);
        when(mockLobby.getPlayers()).thenReturn(playersMap);
        lobbyService.setPlayerGuesses(gameCode, playerId, guesses);
        verify(mockPlayer).setGuesses(guesses);
    }


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

