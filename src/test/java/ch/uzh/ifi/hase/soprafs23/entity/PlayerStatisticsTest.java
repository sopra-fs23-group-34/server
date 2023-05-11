package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerStatisticsTest {
    @Test
    void testConstructorAndGetters() {
        Long userId = 1234L;
        Long gamesPlayed = 5678L;
        int highScore = 1;
        Long gamesWon = 1L;
        double winRatio = 0.1;

        PlayerStatistics playerStatistics = new PlayerStatistics(userId,gamesPlayed,highScore,gamesWon,winRatio);


        assertEquals(userId,playerStatistics.getUserId());
        assertEquals(gamesPlayed,playerStatistics.getGamesPlayed());
        assertEquals(highScore, playerStatistics.getHighScore());
        assertEquals(gamesWon, playerStatistics.getGamesWon());
        assertEquals(winRatio, playerStatistics.getWinRatio());
    }
}
