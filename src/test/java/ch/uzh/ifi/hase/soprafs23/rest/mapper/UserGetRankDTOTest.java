package ch.uzh.ifi.hase.soprafs23.rest.mapper;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
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
