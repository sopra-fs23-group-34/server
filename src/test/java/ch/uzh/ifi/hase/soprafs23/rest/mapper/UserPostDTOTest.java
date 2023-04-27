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
public class UserPostDTOTest {

    @Test
    public void getters() {
        String username = "TestUsername";
        String password = "TestPassword";
        String email = "email";
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setUsername("TestUsername");
        userPostDTO.setPassword("TestPassword");
        userPostDTO.setEmail("email");
        assertEquals(userPostDTO.getUsername(), username);
        assertEquals(userPostDTO.getPassword(), password);
        assertEquals(userPostDTO.getEmail(), email);


    }
}
