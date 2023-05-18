package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserInputDTOTest {

    @Test
    void getters() {
        String foodCategory = "ALL";
        int roundLimit = 1;
        UserInputDTO userInputDTO = new UserInputDTO("ALL", 1, 23);
        assertEquals(userInputDTO.getFoodCategory(), foodCategory);
        assertEquals(userInputDTO.getRoundLimit(), roundLimit);
    }
}
