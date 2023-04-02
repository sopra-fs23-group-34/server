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

    private final String gameCode;
    @Getter
    private boolean gameStarted = false;

    private Notifier notifier;

    @Getter
    private Map<Long, Player> players;

    public Lobby(String gameCode, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameCode = gameCode;
        this.notifier = new WebsocketNotifier(simpMessagingTemplate, gameCode);
        this.players = new HashMap<Long, Player>();
    }

    public void addPlayer(Player player){
        if (gameStarted == true){
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        }
        else{
            players.put(player.getPlayer_id(), player);
        }
    }

    public void removePlayer(LobbyPlayer lobbyPlayer) {
        players.remove(lobbyPlayer);
    }

    public boolean playGame(GameConfig config) throws InterruptedException {
        if (this.gameStarted == true){
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        }
        Game game = new Game(players, config, notifier);
        this.gameStarted = true;
        game.run();
        return true;
    }


}
