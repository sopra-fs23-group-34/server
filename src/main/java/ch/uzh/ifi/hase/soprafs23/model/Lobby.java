package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Lobby {

    @Getter
    private boolean gameStarted;

    private final Notifier notifier;

    private final FoodService foodService;
    @Getter
    @Setter
    private int roundTimer;
    @Getter
    @Setter
    private int scoreTimer;

    @Getter
    private final Map<Long, Player> players;

    public Lobby(String gameCode, SimpMessagingTemplate simpMessagingTemplate, FoodService foodService) {
        this.notifier = new WebsocketNotifier(simpMessagingTemplate, gameCode);
        this.players = new HashMap<>();
        this.foodService = foodService;
    }

    public void checkIfGameStarted() {
        if (gameStarted) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        }
    }

    public void addPlayer(Player player) {
        if (gameStarted) {
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        } else {
            players.put(player.getPlayer_id(), player);
        }
    }

    public void removePlayer(long userId) {
        players.remove(userId);
    }

    public Scores playGame(GameConfig config) throws InterruptedException, IOException {
        Game game = new Game(players, config, notifier, foodService);
        this.gameStarted = true;
        return game.run();
    }
}
