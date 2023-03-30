package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class LobbyRepository {
    private Map<String, Lobby> lobbies = new HashMap<>();

    public void addLobby(Lobby lob){
        lobbies.put(lob.getGameCode(), lob);
    }

    public void addPlayerToLobby(LobbyPlayer player, String id){
        Lobby lobby = findById(id);
        lobby.addPlayer(player);
    }

    public Lobby findById(String id) {
        return lobbies.get(id);
    }

}
