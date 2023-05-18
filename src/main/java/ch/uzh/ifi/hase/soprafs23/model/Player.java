package ch.uzh.ifi.hase.soprafs23.model;

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

    public Player(String username, Long playerId, boolean host) {
        this.username = username;
        this.player_id = playerId;
        this.host = host;
    }

}
