package ch.uzh.ifi.hase.soprafs23.entity;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderBoardTest {

    @Test
    public void testConstructorAndGetters() {
        Long userId = 1234L;
        Long totalScore = 5678L;
        String username = "username";
        LeaderBoard leaderBoard = new LeaderBoard(userId, totalScore);
        assertEquals(userId, leaderBoard.getUser_id());
        assertEquals(totalScore, leaderBoard.getTotalScore());
    }
}

