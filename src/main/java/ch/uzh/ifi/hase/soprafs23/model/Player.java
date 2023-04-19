package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Player {

    @Setter
    private Map<String, Double> guesses;
    @Setter
    private Integer points;
    private final String username;
    private final Long player_id;
    private final boolean host;

    public Player(String username, Long player_id, boolean host) {
        this.username = username;
        this.player_id = player_id;
        this.host = host;
    }

}
