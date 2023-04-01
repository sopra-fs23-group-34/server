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
@Data
public class Lobby {

    private final String gameCode;
    private Integer roundLimit;
    private FoodCategory foodCategory;
    @Setter(AccessLevel.NONE)
    private boolean gameStarted;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Notifier notifier;

    @Setter(AccessLevel.NONE)
    private ArrayList<LobbyPlayer> players;

    public Lobby(String gameCode, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameCode = gameCode;
        this.notifier = new WebsocketNotifier(simpMessagingTemplate, gameCode);
        this.players = new ArrayList<>();
        this.gameStarted = false;
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

    public boolean playGame() throws InterruptedException {
        Game game = new Game(players, roundLimit, foodCategory, notifier);
        this.gameStarted = true;
        game.run();
        return true;
    }


}
