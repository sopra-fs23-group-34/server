package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerStatisticsTest {
    @Test
    void testConstructorAndGetters() {
        Long userId = 1234L;
        Long SPgamesPlayed = 1291L;
        Long MPgamesPlayed = 5678L;
        int highScore = 1;
        Long gamesWon = 1L;
        double winRatio = 0.1;

        PlayerStatistics playerStatistics = new PlayerStatistics(userId,SPgamesPlayed,MPgamesPlayed,highScore,gamesWon,winRatio);


        assertEquals(userId,playerStatistics.getUserId());
        assertEquals(SPgamesPlayed,playerStatistics.getSingleplayerGamesPlayed());
        assertEquals(MPgamesPlayed,playerStatistics.getMultiplayerGamesPlayed());
        assertEquals(highScore, playerStatistics.getHighScore());
        assertEquals(gamesWon, playerStatistics.getGamesWon());
        assertEquals(winRatio, playerStatistics.getWinRatio());
    }
}
