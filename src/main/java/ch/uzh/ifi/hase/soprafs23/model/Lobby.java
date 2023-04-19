package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Lobby {

    @Getter
    private boolean gameStarted;

    private Notifier notifier;

    @Getter
    private Map<Long, Player> players;

    public Lobby(String gameCode, SimpMessagingTemplate simpMessagingTemplate) {
        this.notifier = new WebsocketNotifier(simpMessagingTemplate, gameCode);
        this.players = new HashMap<>();
    }

    public void checkIfGameStarted() {
        if (gameStarted == true) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        }
    }

    public void addPlayer(Player player) {
        if (gameStarted == true) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        } else {
            players.put(player.getPlayer_id(), player);
        }
    }

    public void removePlayer(long user_id) {
        players.remove(user_id);
    }

    public void playGame(GameConfig config) throws InterruptedException {
        Game game = new Game(players, config, notifier);
        this.gameStarted = true;
        game.run();

    }

}
