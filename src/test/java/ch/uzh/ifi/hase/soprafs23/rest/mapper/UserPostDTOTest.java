package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
