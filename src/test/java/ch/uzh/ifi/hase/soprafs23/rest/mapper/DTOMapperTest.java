package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerScoreGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class DTOMapperTest {
  @Test
  public void testCreateUser_fromUserPostDTO_toUser_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("username");

    // MAP -> Create user
    User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertEquals(userPostDTO.getUsername(), user.getUsername());
  }

  @Test
  public void testGetUser_fromUser_toUserGetDTO_success() {
    // create User
    User user = new User();
    user.setUsername("firstname@lastname");
    //user.setStatus(UserStatus.OFFLINE);
    user.setToken("1");

    // MAP -> Create UserGetDTO
    UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

    // check content
    assertEquals(user.getId(), userGetDTO.getId());
    assertEquals(user.getUsername(), userGetDTO.getUsername());
    assertEquals(user.getStatus(), userGetDTO.getStatus());
  }
    @Test
    public void testConvertUserPostLogoutDTOtoEntity() {
        User user = new User();

        Long id = 1L;
        user = DTOMapper.INSTANCE.convertUserPostLogoutDTOtoEntity(id);

        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    public void testConvertUserPostLogoutDTOtoEntity_NullId() {
        User user = new User();

        Long id = null;
        user = DTOMapper.INSTANCE.convertUserPostLogoutDTOtoEntity(id);

        assertNull(user);
    }

    @Test
    public void testConvertUserPutUpdateDTOtoEntity() {

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setBio("new bio");
        userPutDTO.setEmail("new email");
        userPutDTO.setPassword("new password");
        userPutDTO.setUsername("new username");
        userPutDTO.setId(1L);

        User user = DTOMapper.INSTANCE.convertUserPutUpdateDTOtoEntity(userPutDTO);

        assertNotNull(user);
        assertEquals(userPutDTO.getBio(), user.getBio());
        assertEquals(userPutDTO.getEmail(), user.getEmail());
        assertEquals(userPutDTO.getPassword(), user.getPassword());
        assertEquals(userPutDTO.getUsername(), user.getUsername());
        assertEquals(userPutDTO.getStatus(), user.getStatus());
        assertEquals(userPutDTO.getId(), user.getId());
    }

    @Test
    public void testConvertUserPutUpdateDTOtoEntity_NullInput() {


        UserPutDTO userPutDTO = null;

        User user = DTOMapper.INSTANCE.convertUserPutUpdateDTOtoEntity(userPutDTO);

        assertNull(user);
    }
    @Test
    public void testConvertPlayerScoreToPlayerScoreGetDTO() {

        PlayerScore playerScore = new PlayerScore();
        playerScore.setScore(100);
        playerScore.setPlayer_id(1L);
        playerScore.setId(1L);

        PlayerScoreGetDTO playerScoreGetDTO = DTOMapper.INSTANCE.convertPlayerScoreToPlayerScoreGetDTO(playerScore);

        assertNotNull(playerScoreGetDTO);
        assertEquals(String.valueOf(playerScore.getScore()), playerScoreGetDTO.getScore());
        assertEquals(playerScore.getPlayer_id(), playerScoreGetDTO.getUser_id());
        assertEquals(playerScore.getId().intValue(), playerScoreGetDTO.getId());
    }

    @Test
    public void testConvertPlayerScoreToPlayerScoreGetDTO_NullInput() {
        PlayerScore playerScore = new PlayerScore();

        playerScore = null;

        PlayerScoreGetDTO playerScoreGetDTO = DTOMapper.INSTANCE.convertPlayerScoreToPlayerScoreGetDTO(playerScore);

        assertNull(playerScoreGetDTO);
    }

}
