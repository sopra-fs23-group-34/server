package ch.uzh.ifi.hase.soprafs23.storage;

import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class LobbyStorage implements Serializable {
    private transient Map<String, Lobby> lobbies;
    LobbyStorage() {
        lobbies = new HashMap<>();
    }
    public Lobby getLobby(String lobbyCode) {
        return lobbies.get(lobbyCode);
    }
    public void addLobby(String gameCode, Lobby lobby) {
        lobbies.put(gameCode, lobby);
    }
    public void removeLobby(String gameCode) {
        lobbies.remove(gameCode);
    }
}