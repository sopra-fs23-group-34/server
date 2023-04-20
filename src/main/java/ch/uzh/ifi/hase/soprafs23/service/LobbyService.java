package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.model.*;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Transactional
public class LobbyService {
    private final UserService userService;
    private final CodeGenerator codeGenerator;
    private final LobbyStorage lobbyStorage;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private void checkIfLobbyExists(String id) {
        Lobby lob = lobbyStorage.getLobby(id);
        if (lob == null) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Lobby does not exist");
        }
    }

    public String createLobby() {
        String gameCode = codeGenerator.nextCode();
        lobbyStorage.addLobby(gameCode, new Lobby(gameCode, simpMessagingTemplate));
        return gameCode;
    }

    public List<Player> updatePlayerList(String gameCode) {
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        List<Player> lobbyPlayers = new ArrayList(lobby.getPlayers().values());
        return lobbyPlayers;
    }

    public boolean joinLobby(String gameCode, Long user_id) {
        checkIfLobbyExists(gameCode);
        LobbyPlayer lobbyPlayer = userService.getUserById(user_id);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        boolean isHost = false;
        if (lobby.getPlayers().isEmpty()) {
            isHost = true;
        }
        Player player = new Player(lobbyPlayer.getUsername(), lobbyPlayer.getId(), isHost);
        lobby.addPlayer(player);
        return isHost;
    }

    public List<Player> leaveLobby(String gameCode, Long user_id) {
        checkIfLobbyExists(gameCode);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.removePlayer(user_id);
        System.out.println("Successfully left Lobby");
        List<Player> lobbyPlayers = new ArrayList(lobby.getPlayers().values());
        return lobbyPlayers;
    }

    private void checkIfHost(String gameCode, Long user_id) {
        try {
            Player player = lobbyStorage.getLobby(gameCode).getPlayers().get(user_id);
            if (!player.isHost()) {
                throw new ResponseStatusException(HttpStatus.valueOf(401), "You are not authorized to start the game");
            }
        } catch (NullPointerException ex) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "You are not authorized to start the game");
        }
    }

    public void startGame(String gameCode, Long user_id, String token, GameConfig config) throws InterruptedException {
        userService.authenticateUser(token, user_id);
        checkIfHost(gameCode, user_id);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.checkIfGameStarted();
        new Thread(() -> {
            try {
                lobby.playGame(config);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            lobbyStorage.removeLobby(gameCode);
        }).start();
    }

    public void setPlayerGuesses(String gameCode, long player_id, Map<String, Double> guesses) {
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.getPlayers().get(player_id).setGuesses(guesses);

    }

}
