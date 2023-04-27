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
import org.junit.jupiter.api.Test;

public class UserInputDTOTest {

    @Test
    public void getters() {
        String foodCategory = "ALL";
        int roundLimit = 1;
        UserInputDTO userInputDTO = new UserInputDTO("ALL", 1);
        assertEquals(userInputDTO.getFoodCategory(), foodCategory);
        assertEquals(userInputDTO.getRoundLimit(), roundLimit);


    }
}
