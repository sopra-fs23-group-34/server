package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.model.Scores;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerScoreRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs23.storage.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {


  @Mock
  private UserRepository userRepository;

  @Mock
  private PlayerScoreRepository playerScoreRepository;

  @Mock
  private UserStorage guestUserStorage;

  @InjectMocks
  private UserService userService;


  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    testUser = new User();
    testUser.setId(1L);
    testUser.setUsername("testUsername");
    testUser.setPassword("testPassword");
    testUser.setEmail("testMail");

    when(userRepository.save(Mockito.any())).thenReturn(testUser);

  }

  @Test
  void createUser_validInputs_success() {
    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    User createdUser = userService.createUser(testUser);

    // then
    verify(userRepository, times(1)).save(Mockito.any());

    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertEquals(testUser.getEmail(), createdUser.getEmail());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.ONLINE, createdUser.getStatus());
  }

  @Test
  void createUser_duplicateUserName_throwsException() {
    userService.createUser(testUser);

    when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }
    @Test
    void createUser_duplicateMail_throwsException() {
      User user = new User();
      user.setEmail("testMail");
      user.setUsername("anotherUsername");
      user.setPassword("somePassword");
      userService.createUser(testUser);

      when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

      assertThrows(ResponseStatusException.class, () -> userService.createUser(user));
  }

  @Test
    void createUser_unauthorizedName_throwsException() {
      User user = new User();
      user.setUsername("JohnLemon");

      when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

      assertThrows(ResponseStatusException.class, () -> userService.createUser(user));
  }

  @Test
  void createUser_duplicateInputs_throwsException() {
    userService.createUser(testUser);

    when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }
    @Test
    void loginGuestUser() {
        User createdUser = userService.loginGuestUser();
        verify(userRepository, times(1)).save(Mockito.any());

        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getEmail());
        assertNotNull(createdUser.getPassword());
        assertNotNull(createdUser.getUsername());
    }

    @Test
    void loginGuestUserUserAvailable() {
      User guestUser = new User();
      guestUser.setUsername("test");
      guestUser.setPassword("test");
      guestUser.setId(1L);
      guestUser.setToken("11");
      when(guestUserStorage.createNewGuestUser()).thenReturn(guestUser.getUsername());
      when(userRepository.findByUsername(guestUser.getUsername())).thenReturn(guestUser);
      User loggedInUser = userService.loginGuestUser();
      assertEquals(guestUser.getUsername(), loggedInUser.getUsername());
      assertEquals(guestUser.getPassword(), loggedInUser.getPassword());
      assertEquals(guestUser.getId(), loggedInUser.getId());
    }

    @Test
    void authenticateUserError() {
      User user1 = new User();
      user1.setToken("11");
      user1.setId(1L);
      User user2 = new User();
      user2.setToken("22");
      user2.setId(2L);
      when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user2));
      assertThrows(ResponseStatusException.class, () -> userService.authenticateUser("11", 1L));
    }


  @Test
  void testLoginUserSuccess() {
      User loginUser = new User();
      loginUser.setUsername("testUser");
      loginUser.setPassword("testPassword");

      User userDatabase = new User();
      userDatabase.setUsername("testUser");
      userDatabase.setPassword("testPassword");

      when(userRepository.findByUsername(loginUser.getUsername())).thenReturn(userDatabase);
      User loggedinUser = userService.loginUser(loginUser);
      assertEquals(UserStatus.ONLINE, loggedinUser.getStatus());
    }
    @Test
    void testLoginUserInvalidUsername() {
      User loginUser = new User();
      loginUser.setUsername("testUser");
      loginUser.setPassword("testPassword");

      when(userRepository.findByUsername(loginUser.getUsername())).thenReturn(null);
      assertThrows(ResponseStatusException.class, () -> userService.loginUser(loginUser));
    }
    @Test
    void testLoginUserInvalidPassword() {
      User loginUser = new User();
      loginUser.setUsername("testUser");
      loginUser.setPassword("testPassword");

      User userDatabase = new User();
      userDatabase.setUsername("testUser");
      userDatabase.setPassword("wrongPassword");

      when(userRepository.findByUsername(loginUser.getUsername())).thenReturn(userDatabase);
      assertThrows(ResponseStatusException.class, () -> userService.loginUser(loginUser));
    }

    @Test
    void verifyUserTest() {
      User user1 = new User();
      user1.setToken("11");
      user1.setId(1L);
      User user2 = new User();
      user2.setToken("22");
      user2.setId(2L);
      when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user2));
      assertThrows(ResponseStatusException.class, () -> userService.verifyUser("11", 1L));
    }

    @Test
    void testLogoutUserSuccess() {
        User loginUser = new User();
        loginUser.setUsername("testLoginUser");
        loginUser.setPassword("testPassword");
        loginUser.setId(1L);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setPassword("testPassword");
        userDatabase.setToken("testToken");
        userDatabase.setId(1L);

        when(userRepository.findByUsername(loginUser.getUsername())).thenReturn(userDatabase);
        when(userRepository.findById(loginUser.getId())).thenReturn(Optional.of(userDatabase));

        User loggedInUser = userService.loginUser(loginUser);
        assertEquals(UserStatus.ONLINE, loggedInUser.getStatus());

        User loggedOutUser = userService.logoutUser("testToken",1L);
        assertEquals(UserStatus.OFFLINE, loggedOutUser.getStatus());
    }


    @Test
    void testGetUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User result = userService.getUserById(id);

        assertEquals(user, result);
    }
    @Test
    void testGetUserByIdNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                        () -> userService.getUserById(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void logoutGuestUser() {
      User user = new User();
      user.setUsername("testUser");
      user.setPassword("testPassword");
      user.setToken("11");
      user.setId(1L);

      User userDatabase = new User();
      userDatabase.setUsername("testUser");
      userDatabase.setPassword("testPassword");
      userDatabase.setToken("11");
      userDatabase.setId(1L);
      when(userRepository.findById(user.getId())).thenReturn(Optional.of(userDatabase));
      userService.logoutGuestUser("11", 1L);
    }


    @Test
    void updateUserErrorGuestUser() {
      User userWithUpdateInfo = new User();
      userWithUpdateInfo.setUsername("testUser");
      userWithUpdateInfo.setPassword("newPW");
      userWithUpdateInfo.setId(1L);
      userWithUpdateInfo.setToken("11");
      userWithUpdateInfo.set_guest_user(true);

      User userDatabase = new User();
      userDatabase.setUsername("testUser");
      userDatabase.setPassword("testPassword");
      userDatabase.setToken("11");
      userDatabase.setId(1L);
      userDatabase.set_guest_user(true);
      when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
      assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Profiles of guest users can't be changed!");
  }



    @Test
    void updateUserUsernameNotFree() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("testUser");
        userWithUpdateInfo.setEmail("email");
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);

        User userDatabase2 = new User();
        userDatabase2.setId(2L);

        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        when(userRepository.findByUsername(userWithUpdateInfo.getUsername())).thenReturn(userDatabase2);

        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("You can't pick the same username as somebody else!");    }

    @Test
    void updateUserEmailNotFree() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("testUser");
        userWithUpdateInfo.setEmail("email");
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);

        User userDatabase2 = new User();
        userDatabase2.setId(2L);

        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        when(userRepository.findByEmail(userWithUpdateInfo.getEmail())).thenReturn(userDatabase2);
        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("You can't pick the same mail as somebody else!");
    }

    @Test
    void updateUserUsernameEmptyString() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername(null);
        userWithUpdateInfo.setEmail("email");
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);

        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username and Email can't be empty Strings!");
    }

    @Test
    void updateUserEmailEmptyString() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("testUser");
        userWithUpdateInfo.setEmail(null);
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);

        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username and Email can't be empty Strings!");
    }
    @Test
    void updateUserUsernameAndEmailEmptyString() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername(null);
        userWithUpdateInfo.setEmail(null);
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);

        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username and Email can't be empty Strings!");
    }
    @Test
    void updateUserUsernameAndEmailAndBioSuccessful() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("newUsername");
        userWithUpdateInfo.setEmail("newMail");
        userWithUpdateInfo.setBio("newBio");
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setBio("bio");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);

        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null);
    }

    @Test
    void updateUserOnlyPasswordSuccessful() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("testUser");
        userWithUpdateInfo.setPassword("newPW");
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setPassword("testPassword");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);
        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), userDatabase.getPassword());
    }

    @Test
    void updateUserOnlyPasswordEmptyString() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("testUser");
        userWithUpdateInfo.setEmail("email");
        userWithUpdateInfo.setPassword(null);
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setEmail("email");
        userDatabase.setPassword("testPassword");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);
        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), userDatabase.getPassword()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Password can't be empty Strings!");
    }

    @Test
    void updateUserOnlyPasswordErrorWrongOldPassword() {
        User userWithUpdateInfo = new User();
        userWithUpdateInfo.setUsername("testUser");
        userWithUpdateInfo.setPassword("newPW");
        userWithUpdateInfo.setId(1L);
        userWithUpdateInfo.setToken("11");
        userWithUpdateInfo.set_guest_user(false);

        User userDatabase = new User();
        userDatabase.setUsername("testUser");
        userDatabase.setPassword("testPassword");
        userDatabase.setToken("11");
        userDatabase.setId(1L);
        userDatabase.set_guest_user(false);
        when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
        assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), userWithUpdateInfo.getPassword()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Wrong old Password");
    }

    @Test
    void updateScores() {
        Scores scores = new Scores();
        Map<String, Double> nutritionalValues = new HashMap<>();
        nutritionalValues.put("carbs", 15.0);
        Food food = new Food("test", nutritionalValues, "imageLink");
        User user = new User();
        user.set_guest_user(false);
        user.setUsername("testUser");
        user.setToken("11");
        user.setId(1L);
        User user1 = new User();
        user1.set_guest_user(false);
        user1.setUsername("testUser1");
        user1.setToken("11");
        user1.setId(1L);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(user1);
        Map<String, Double> roundScoreUser = new HashMap<>();
        roundScoreUser.put("carbs", 10.0);
        Map<String, Double> roundScoreUser1 = new HashMap<>();
        roundScoreUser1.put("carbs", 18.0);
        scores.updateRoundScore(roundScoreUser, user.getUsername(), food);
        scores.updateRoundScore(roundScoreUser1, user1.getUsername(), food);
        userService.updateScores(scores);
        assertEquals(75.0, scores.getRoundScore().get("testUser").get("points").get(0).get("points"));
        assertEquals(15.0, scores.getRoundScore().get("testUser").get("carbs").get(0).get("actualValues"));
        assertEquals(10.0, scores.getRoundScore().get("testUser").get("carbs").get(1).get("guessedValues"));
        assertEquals(5.0, scores.getRoundScore().get("testUser").get("carbs").get(2).get("deviations"));
        assertEquals(91.0, scores.getRoundScore().get("testUser1").get("points").get(0).get("points"));
        assertEquals(15.0, scores.getRoundScore().get("testUser1").get("carbs").get(0).get("actualValues"));
        assertEquals(18.0, scores.getRoundScore().get("testUser1").get("carbs").get(1).get("guessedValues"));
        assertEquals(3.0, scores.getRoundScore().get("testUser1").get("carbs").get(2).get("deviations"));
    }

    @Test
    void getTotalScores() {
        Long id = 1L;
        User user = new User();
        user.setId(1L);
        user.setToken("11");
        user.setUsername("testUser");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        List<LeaderBoard> leaderBoard = new ArrayList<>();
        leaderBoard.add(new LeaderBoard(1L, 100L));
        when(playerScoreRepository.getGlobalLeaderboard()).thenReturn(leaderBoard);
        assertEquals(100L, userService.getTotalScores(1L, "11").get(0).getTotalScore());
        assertEquals("testUser", userService.getTotalScores(1L, "11").get(0).getUsername());
        assertEquals(1L, userService.getTotalScores(1L, "11").get(0).getUserId());
    }

    @Test
    void getStatistics() {
        User user = new User();
        user.setId(1L);
        user.setToken("11");
        user.setUsername("testUser");
        PlayerStatistics playerStatistics = new PlayerStatistics(1L, 10L, 500, 5L, 0.5);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(playerScoreRepository.getPlayerStatistics(user.getId())).thenReturn(playerStatistics);
        assertEquals(1, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getUserId());
        assertEquals(10, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getGamesPlayed());
        assertEquals(5, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getGamesWon());
        assertEquals(0.5, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getWinRatio());
        assertEquals(500, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getHighScore());
    }

}
