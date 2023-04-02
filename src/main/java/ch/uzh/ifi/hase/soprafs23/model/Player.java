package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Player {

    @Setter
    private Map<String, Integer> guesses;
    @Setter
    private Integer points;
    private String username;
    private Long player_id;

    public Player(String username, Long player_id) {
        this.username = username;
        this.player_id = player_id;
    }

}
