package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


public class Player {
    @Getter
    @Setter
    private String guesses;
    @Getter
    private String username;
    @Getter
    private Long player_id;

    public Player(String username, Long player_id) {
        this.username = username;
        this.player_id = player_id;
    }

}
