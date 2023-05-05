package ch.uzh.ifi.hase.soprafs23.rest.mapper;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetRankDTOTest {

    @Test
    public void getters() {
        UserGetRankDTO userGetRankDTO = new UserGetRankDTO();
        String username = "TestUsername";
        String totalScore = "1";
        userGetRankDTO.setUsername("TestUsername");
        userGetRankDTO.setTotalScore("1");
        assertEquals(userGetRankDTO.getUsername(), username);
        assertEquals(userGetRankDTO.getTotalScore(), totalScore);
    }
}
