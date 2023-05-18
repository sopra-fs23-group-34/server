package ch.uzh.ifi.hase.soprafs23.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaderBoardTest {

    @Test
     void testConstructorAndGetters() {
        Long userId = 1234L;
        Long totalScore = 5678L;
        String username = "username";
        LeaderBoard leaderBoard = new LeaderBoard(userId, totalScore);
        leaderBoard.setUsername(username);
        assertEquals(userId, leaderBoard.getUserId());
        assertEquals(totalScore, leaderBoard.getTotalScore());
        assertEquals(username, leaderBoard.getUsername());
    }

}

