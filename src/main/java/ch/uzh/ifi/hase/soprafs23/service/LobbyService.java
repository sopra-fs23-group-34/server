package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.messages.StringMessage;
import ch.uzh.ifi.hase.soprafs23.model.*;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import lombok.AllArgsConstructor;
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
    private final FoodService foodService;
    private final CodeGenerator codeGenerator;
    private final LobbyStorage lobbyStorage;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private void checkIfLobbyExists(String id) {
        Lobby lob = lobbyStorage.getLobby(id);
        if (lob == null) {
            throw new ResponseStatusException(HttpStatus.valueOf(404), "Lobby does not exist");
        }
    }

    public String createLobby() {
        String gameCode = codeGenerator.nextCode();
        lobbyStorage.addLobby(gameCode, new Lobby(gameCode, simpMessagingTemplate, foodService));
        return gameCode;
    }

    public List<Player> updatePlayerList(String gameCode) {
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        return new ArrayList<>(lobby.getPlayers().values());
    }

    public boolean joinLobby(String gameCode, Long user_id) {
        checkIfLobbyExists(gameCode);
        LobbyPlayer lobbyPlayer = userService.getUserById(user_id);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        boolean isHost = lobby.getPlayers().isEmpty();
        Player player = new Player(lobbyPlayer.getUsername(), lobbyPlayer.getId(), isHost);
        lobby.addPlayer(player);
        return isHost;
    }

    public List<Player> leaveLobby(String gameCode, Long userId) {
        checkIfLobbyExists(gameCode);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        if (lobby.getPlayers().get(userId).isHost()){
            StringMessage stringMessage = new StringMessage("error", "host_left");
            simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES + gameCode, stringMessage);
        }
        lobby.removePlayer(userId);
        return new ArrayList<>(lobby.getPlayers().values());
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
    public void startGame(String gameCode, Long user_id, String token, GameConfig config) throws RuntimeException {
        userService.authenticateUser(token, user_id);
        checkIfHost(gameCode, user_id);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.checkIfGameStarted();
        lobby.setRoundTimer(config.getTimerLength());
        new Thread(() -> {
            try {
                Scores scores = lobby.playGame(config);
                userService.updateScores(scores);
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            lobbyStorage.removeLobby(gameCode);
        }).start();
    }


    public void setPlayerGuesses(String gameCode, long playerId, Map<String, Double> guesses) {
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.getPlayers().get(playerId).setGuesses(guesses);
    }
}
