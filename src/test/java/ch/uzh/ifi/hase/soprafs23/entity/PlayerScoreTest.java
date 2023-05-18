package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerScoreTest {
    @Test
    void testConstructorAndGetters() {
        Long playerId = 1234L;
        int score = 5678;
        Boolean winner = false;
        PlayerScore playerScore = new PlayerScore();
        playerScore.setPlayer_id(playerId);
        playerScore.setScore(score);
        playerScore.setWinner(winner);
        playerScore.setId(1L);

        assertEquals(1L, playerScore.getId());
        assertEquals(playerId, playerScore.getPlayer_id());
        assertEquals(score, playerScore.getScore());
        assertEquals(winner, playerScore.getWinner());
    }

}
