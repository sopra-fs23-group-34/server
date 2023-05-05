package ch.uzh.ifi.hase.soprafs23.rest.mapper;
import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetDTOTest {

    @Test
    public void getters() {
        Long id = 1L;
        String username = "TestUsername";
        UserStatus userStatus = UserStatus.ONLINE;
        String email = "email";
        String bio = "bio";
        Date creationDate = new Date();
        String token = "1";
        int totalScore = 1;

        UserGetDTO userGetDTO = new UserGetDTO();
        userGetDTO.setId(1L);
        userGetDTO.setUsername("TestUsername");
        userGetDTO.setStatus(UserStatus.ONLINE);
        userGetDTO.setEmail("email");
        userGetDTO.setBio("bio");
        userGetDTO.setCreationDate(new Date());
        userGetDTO.setToken("1");
        userGetDTO.setTotalScore(1);

        assertEquals(userGetDTO.getId(), id);
        assertEquals(userGetDTO.getUsername(), username);
        assertEquals(userGetDTO.getStatus(), userStatus);
        assertEquals(userGetDTO.getEmail(), email);
        assertEquals(userGetDTO.getBio(), bio);
        assertEquals(userGetDTO.getCreationDate(), creationDate);
        assertEquals(userGetDTO.getToken(), token);
        assertEquals(userGetDTO.getTotalScore(), totalScore);

    }

}
