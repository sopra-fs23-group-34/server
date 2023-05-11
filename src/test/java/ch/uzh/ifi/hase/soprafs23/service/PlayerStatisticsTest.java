package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PlayerStatisticsTest {
    @Test
    public void testConstructorAndGetters() {
        Long userId = 1234L;
        Long gamesPlayed = 5678L;
        int highScore = 23;
        long gamesWon = 1;
        double winRatio = 0.1;

        PlayerStatistics playerStatistics = new PlayerStatistics(userId,gamesPlayed,highScore,gamesWon,winRatio);
        assertEquals(userId, playerStatistics.getUserId());
        assertEquals(gamesWon, playerStatistics.getGamesWon());

    }


}
