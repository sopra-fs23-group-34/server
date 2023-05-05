package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;

import org.junit.jupiter.api.Test;

public class PlayerGetDTOTest {

    @Test
    public void getUsername() {
        String username = "Test User";
        PlayerGetDTO playerGetDTOTest = new PlayerGetDTO();
        playerGetDTOTest.setUsername("Test User");
        assert playerGetDTOTest.getUsername().equals(username);
    }
}
