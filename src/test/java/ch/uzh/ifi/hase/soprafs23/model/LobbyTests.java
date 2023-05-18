package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

class LobbyTests {

    FoodService foodService = mock(FoodService.class);
    SimpMessagingTemplate simpMessagingTemplate = mock(SimpMessagingTemplate.class);

    @Test
    void testCheckIfGameStarted_false() {
        Lobby lobby = new Lobby("XXXXXX", simpMessagingTemplate, foodService);
        try {
            lobby.checkIfGameStarted();
        } catch (ResponseStatusException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testCheckIfGameStarted_true() {
        Lobby lobby = new Lobby("gameCode", simpMessagingTemplate, foodService);
        ReflectionTestUtils.setField(lobby,"gameStarted",true);
        try {
            lobby.checkIfGameStarted();
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
            assertEquals("Game already started", e.getReason());
        }
    }
    @Test
    void testAddPlayer() {
        Lobby lobby = new Lobby("gameCode", simpMessagingTemplate, foodService);
        Player player = new Player("name", 12L,true);
        lobby.addPlayer(player);
        Map<Long, Player> players = lobby.getPlayers();
        assertEquals(1, players.size());
        assertEquals(player, players.get(12L));
    }
    @Test
    void testAddPlayer_gameStarted() {
        Lobby lobby = new Lobby("gameCode", simpMessagingTemplate, foodService);
        Player player = new Player("name", 12L,false);
        ReflectionTestUtils.setField(lobby,"gameStarted",true);
        try {
            lobby.addPlayer(player);
            fail("Expected ResponseStatusException to be thrown");
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
            assertEquals("Game already started", e.getReason());
        }
    }
    @Test
    void testRemovePlayer() {
        Lobby lobby = new Lobby("gameCode", simpMessagingTemplate, foodService);
        Player player1 = new Player("name", 12L,false);
        Player player2 = new Player("name1", 13L,false);
        lobby.addPlayer(player1);
        lobby.addPlayer(player2);
        lobby.removePlayer(13L);
        Map<Long, Player> players = lobby.getPlayers();
        assertEquals(1, players.size());
        assertEquals(player1, players.get(12L));
    }


}
