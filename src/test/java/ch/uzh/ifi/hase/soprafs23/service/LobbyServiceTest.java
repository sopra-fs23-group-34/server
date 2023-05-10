package ch.uzh.ifi.hase.soprafs23.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class LobbyServiceTest {
    @Mock
    private LobbyService lobbyService;

    @Test
    void test_nullLobby() {
        //assertThrows(ResponseStatusException.class, () -> lobbyService.);
    }
}
