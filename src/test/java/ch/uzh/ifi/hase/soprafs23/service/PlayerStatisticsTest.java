package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerStatisticsTest {
    @Test
    public void testConstructorAndGetters() {
        Long userId = 1234L;
        long gamesPlayed = 5678L;
        int highScore = 23;
        long gamesWon = 1;
        double winRatio = 0.1;

        PlayerStatistics playerStatistics = new PlayerStatistics(userId,gamesPlayed,highScore,gamesWon,winRatio);
        assertEquals(userId, playerStatistics.getUserId());
        assertEquals(gamesPlayed, playerStatistics.getGamesPlayed());
        assertEquals(highScore, playerStatistics.getHighScore());
        assertEquals(gamesWon, playerStatistics.getGamesWon());
        assertEquals(winRatio, playerStatistics.getWinRatio());

    }


}
