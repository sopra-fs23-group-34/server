package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserPutDTOTest {
    @Test
    void getters() {
        Long id = 1L;
        String username = "TestUsername";
        UserStatus userStatus = UserStatus.ONLINE;
        String email = "email";
        String bio = "bio";
        String password = "TestPassword";

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setId(1L);
        userPutDTO.setUsername("TestUsername");
        userPutDTO.setStatus(UserStatus.ONLINE);
        userPutDTO.setEmail("email");
        userPutDTO.setBio("bio");
        userPutDTO.setPassword("TestPassword");


        assertEquals(userPutDTO.getId(), id);
        assertEquals(userPutDTO.getUsername(), username);
        assertEquals(userPutDTO.getStatus(), userStatus);
        assertEquals(userPutDTO.getEmail(), email);
        assertEquals(userPutDTO.getBio(), bio);
        assertEquals(userPutDTO.getPassword(), password);
    }
}
