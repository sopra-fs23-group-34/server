package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;


@AllArgsConstructor
public class Lobby {

    private final String gameCode;
    @Getter
    private boolean gameStarted = false;

    private Notifier notifier;

    @Getter
    private ArrayList<LobbyPlayer> players;

    public Lobby(String gameCode, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameCode = gameCode;
        this.notifier = new WebsocketNotifier(simpMessagingTemplate, gameCode);
        this.players = new ArrayList<>();
    }

    public void addPlayer(LobbyPlayer lobbyPlayer){
        if (gameStarted == true){
            throw new ResponseStatusException(HttpStatus.valueOf(401), "Game already started");
        }
        else{
            players.add(lobbyPlayer);
        }
    }

    public void removePlayer(LobbyPlayer lobbyPlayer) {
        players.remove(lobbyPlayer);
    }

    public boolean playGame(GameConfig config) throws InterruptedException {
        Game game = new Game(players, config, notifier);
        this.gameStarted = true;
        game.run();
        return true;
    }


}
