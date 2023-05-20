package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;

public class PlayerStatisticsTest {
    @Test
    public void testConstructorAndGetters() {
        Long userId = 1234L;
        Long SPgamesPlayed = 1291L;
        Long MPgamesPlayed = 5678L;
        int highScore = 23;
        long gamesWon = 1;
        double winRatio = 0.1;

        PlayerStatistics playerStatistics = new PlayerStatistics(userId,SPgamesPlayed,MPgamesPlayed,highScore,gamesWon,winRatio);
        assertEquals(userId, playerStatistics.getUserId());
        Assertions.assertEquals(SPgamesPlayed,playerStatistics.getSingleplayerGamesPlayed());
        Assertions.assertEquals(MPgamesPlayed,playerStatistics.getMultiplayerGamesPlayed());
        assertEquals(highScore, playerStatistics.getHighScore());
        assertEquals(gamesWon, playerStatistics.getGamesWon());
        assertEquals(winRatio, playerStatistics.getWinRatio());

    }


}
