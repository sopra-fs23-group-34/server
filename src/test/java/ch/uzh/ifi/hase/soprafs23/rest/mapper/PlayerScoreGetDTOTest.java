package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerScoreGetDTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerScoreGetDTOTest {

    @Test
    void getters() {
        PlayerScoreGetDTO playerScoreGetDTO = new PlayerScoreGetDTO();
        String score = "1";
        int id = 1;
        long userID = 1L;
        playerScoreGetDTO.setScore("1");
        playerScoreGetDTO.setId(1);
        playerScoreGetDTO.setUser_id(1L);
        assertEquals(playerScoreGetDTO.getScore(), score);
        assertEquals(playerScoreGetDTO.getId(), id);
        assertEquals(playerScoreGetDTO.getUser_id(), userID);
    }
}
