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
    testUser.setEmail("test@Mail.ch");

    when(userRepository.save(Mockito.any())).thenReturn(testUser);

  }

  @Test
  void createUser_validInputs_success() {
    User createdUser = userService.createUser(testUser);

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
    user.setEmail("test@Mail.ch");
    user.setUsername("anotherUsername");
    user.setPassword("somePassword");
    userService.createUser(testUser);

    User userDatabase = new User();
    userDatabase.setEmail("test@Mail.ch");
    userDatabase.setUsername("Username");
    userDatabase.setPassword("Password");

    when(userRepository.findByEmail(Mockito.any())).thenReturn(userDatabase);

    assertThrows(ResponseStatusException.class, () -> userService.createUser(user));
  }

  @Test
  void createUser_UsernameStartsWithEmptySpace_throwsException() {
    User user = new User();
    user.setPassword("somePassword");
    user.setEmail("test@Mail.ch");
    user.setUsername(" someUsername");

    assertThatThrownBy(() -> userService.createUser(user))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Username can't start with space");
  }

  @Test
  void createUser_UsernameToShort_throwsException() {
      User user = new User();
      user.setEmail("test@Mail.ch");
      user.setUsername("t");
      user.setPassword("testPassword");

      assertThatThrownBy(() -> userService.createUser(user))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Username must be at least 2 characters");
  }

  @Test
  void createUser_UsernameIs2Characters_successful() {
      User user = new User();
      user.setEmail("test@Mail.ch");
      user.setUsername("testUsername");
      user.setPassword("testPassword");
      User createdUser = userService.createUser(user);

      assertEquals(user.getUsername(), createdUser.getUsername());
      assertEquals(user.getEmail(), createdUser.getEmail());
      assertEquals(user.getPassword(), createdUser.getPassword());
  }

  @Test
  void createUser_PasswordStartsWithEmptySpace_throwsException() {
      User user = new User();
      user.setPassword(" somePassword");
      user.setEmail("test@Mail.ch");
      user.setUsername("anotherUsername");

      assertThatThrownBy(() -> userService.createUser(user))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Password can't contain space");
  }

  @Test
  void createUser_PasswordContainsSpace_throwsException() {
      User user = new User();
      user.setPassword("some Password");
      user.setEmail("test@Mail.ch");
      user.setUsername("anotherUsername");

      assertThatThrownBy(() -> userService.createUser(user))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Password can't contain space");
  }

  @Test
  void createUser_PasswordToShort_throwsException() {
      User user = new User();
      user.setEmail("test@Mail.ch");
      user.setUsername("anotherUsername");
      user.setPassword("t");

      assertThatThrownBy(() -> userService.createUser(user))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Password must be at least 2 characters");
  }

  @Test
  void createUser_PasswordIs2Characters_successful() {
      User user = new User();
      user.setEmail("test@Mail.ch");
      user.setUsername("testUsername");
      user.setPassword("testPassword");

      User createdUser = userService.createUser(user);
      assertEquals(user.getUsername(), createdUser.getUsername());
      assertEquals(user.getEmail(), createdUser.getEmail());
      assertEquals(user.getPassword(), createdUser.getPassword());
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
    assertEquals(userDatabase.getId(), user.getId());
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
      userWithUpdateInfo.setEmail("test@Mail.ch");
      userWithUpdateInfo.setId(1L);
      userWithUpdateInfo.setToken("11");
      userWithUpdateInfo.set_guest_user(false);

      User userDatabase = new User();
      userDatabase.setUsername("testUser");
      userDatabase.setEmail("test@Mail.ch");
      userDatabase.setToken("11");
      userDatabase.setId(1L);
      userDatabase.set_guest_user(false);

      User userDatabase2 = new User();
      userDatabase2.setId(2L);

      when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
      when(userRepository.findByUsername(userWithUpdateInfo.getUsername())).thenReturn(userDatabase2);

      assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("You can't pick the same username as somebody else!");
  }

  @Test
  void updateUserEmailNotFree() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("testUser");
    userWithUpdateInfo.setEmail("test@Mail.ch");
    userWithUpdateInfo.setId(1L);
    userWithUpdateInfo.setToken("11");
    userWithUpdateInfo.set_guest_user(false);

    User userDatabase = new User();
    userDatabase.setUsername("testUser");
    userDatabase.setEmail("test@Mail.ch");
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
  void updateUserUsernameEmptyString_noError() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("");
    userWithUpdateInfo.setEmail("test@Mail.ch");
    userWithUpdateInfo.setBio("bio");
    userWithUpdateInfo.setId(1L);
    userWithUpdateInfo.setToken("11");
    userWithUpdateInfo.set_guest_user(false);

    User userDatabase = new User();
    userDatabase.setUsername("testUser");
    userDatabase.setEmail("test@Mail.ch");
    userDatabase.setBio("bio");
    userDatabase.setToken("11");
    userDatabase.setId(1L);
    userDatabase.set_guest_user(false);

    when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
    assertEquals(userWithUpdateInfo.getId(), userDatabase.getId());
  }

  @Test
  void updateUserEmailEmptyString_noError() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("testUser");
    userWithUpdateInfo.setEmail(null);
    userWithUpdateInfo.setBio("bio");
    userWithUpdateInfo.setId(1L);
    userWithUpdateInfo.setToken("11");
    userWithUpdateInfo.set_guest_user(false);

    User userDatabase = new User();
    userDatabase.setUsername("testUser");
    userDatabase.setEmail("test@Mail.ch");
    userDatabase.setBio("bio");
    userDatabase.setToken("11");
    userDatabase.setId(1L);
    userDatabase.set_guest_user(false);

    when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
    assertEquals(userWithUpdateInfo.getId(), userDatabase.getId());
  }
  @Test
  void updateUserUsernameAndEmailEmptyString_noError() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername(null);
    userWithUpdateInfo.setEmail(null);
    userWithUpdateInfo.setBio("bio");
    userWithUpdateInfo.setId(1L);
    userWithUpdateInfo.setToken("11");
    userWithUpdateInfo.set_guest_user(false);

    User userDatabase = new User();
    userDatabase.setUsername("testUser");
    userDatabase.setEmail("test@Mail.ch");
    userDatabase.setBio("bio");
    userDatabase.setToken("11");
    userDatabase.setId(1L);
    userDatabase.set_guest_user(false);

    when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
    assertEquals(userWithUpdateInfo.getId(), userDatabase.getId());
  }
  @Test
  void updateUserUsernameAndEmailAndBio_Successful() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("newUsername");
    userWithUpdateInfo.setEmail("test@Mail.ch");
    userWithUpdateInfo.setBio("newBio");
    userWithUpdateInfo.setId(1L);
    userWithUpdateInfo.setToken("11");
    userWithUpdateInfo.set_guest_user(false);

    User userDatabase = new User();
    userDatabase.setUsername("testUser");
    userDatabase.setEmail("test@Mail.ch");
    userDatabase.setBio("bio");
    userDatabase.setToken("11");
    userDatabase.setId(1L);
    userDatabase.set_guest_user(false);

    when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
    User updatedUser = userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), null);
    assertEquals(updatedUser.getUsername(), userWithUpdateInfo.getUsername());
    assertEquals(updatedUser.getEmail(), userWithUpdateInfo.getEmail());
    assertNotNull(updatedUser.getToken());
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
    User updatedUser = userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "testPassword");
    assertEquals(updatedUser.getUsername(), userWithUpdateInfo.getUsername());
    assertEquals(updatedUser.getEmail(), userWithUpdateInfo.getEmail());
    assertNotNull(updatedUser.getToken());
  }

  @Test
  void updateUserOnlyPasswordEmptyString_error() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("testUser");
    userWithUpdateInfo.setEmail("test@Mail.ch");
    userWithUpdateInfo.setPassword("");
    userWithUpdateInfo.setId(1L);
    userWithUpdateInfo.setToken("11");
    userWithUpdateInfo.set_guest_user(false);

    User userDatabase = new User();
    userDatabase.setUsername("testUser");
    userDatabase.setEmail("test@Mail.ch");
    userDatabase.setPassword("testPassword");
    userDatabase.setToken("11");
    userDatabase.setId(1L);
    userDatabase.set_guest_user(false);
    when(userRepository.findById(userWithUpdateInfo.getId())).thenReturn(Optional.of(userDatabase));
    assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "testPassword"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Password can't be empty Strings!");
  }

  @Test
  void updateUserOnlyPasswordWrongOldPassword_error() {
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
    assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "newPW"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Wrong old Password");
  }
  @Test
  void updateUserOnlyPasswordSamePassword_error() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("testUser");
    userWithUpdateInfo.setPassword("testPassword");
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
    assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "testPassword"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("New Password can't be same as old");
  }
  @Test
  void updateUserOnlyPasswordNewPasswordStartsWithSpace_error() {
    User userWithUpdateInfo = new User();
    userWithUpdateInfo.setUsername("testUser");
    userWithUpdateInfo.setPassword(" newPW");
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
    assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "testPassword"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("Password can't contain space");
  }

  @Test
  void updateUserOnlyPasswordNewPasswordContainsSpace_error() {
      User userWithUpdateInfo = new User();
      userWithUpdateInfo.setUsername("testUser");
      userWithUpdateInfo.setPassword("new PW");
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
      assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "testPassword"))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Password can't contain space");
  }

  @Test
  void updateUserOnlyPasswordNewPasswordTooShort_error() {
      User userWithUpdateInfo = new User();
      userWithUpdateInfo.setUsername("testUser");
      userWithUpdateInfo.setPassword("t");
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
      assertThatThrownBy(() -> userService.updateUser(userWithUpdateInfo, userWithUpdateInfo.getToken(), userWithUpdateInfo.getId(), "testPassword"))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Password must be at least 2 characters");
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
      assertEquals(170.0, scores.getRoundScore().get("testUser").get("points").get(0).get("points"));
      assertEquals(15.0, scores.getRoundScore().get("testUser").get("carbs").get(0).get("actualValues"));
      assertEquals(10.0, scores.getRoundScore().get("testUser").get("carbs").get(1).get("guessedValues"));
      assertEquals(5.0, scores.getRoundScore().get("testUser").get("carbs").get(2).get("deviations"));
      assertEquals(188.0, scores.getRoundScore().get("testUser1").get("points").get(0).get("points"));
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
      PlayerStatistics playerStatistics = new PlayerStatistics(1L, 10L,10L, 500, 5L, 0.5);
      when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
      when(playerScoreRepository.getPlayerStatistics(user.getId())).thenReturn(playerStatistics);
      assertEquals(1, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getUserId());
      assertEquals(10, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getMultiplayerGamesPlayed());
      assertEquals(10, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getSingleplayerGamesPlayed());
      assertEquals(5, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getGamesWon());
      assertEquals(0.5, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getWinRatio());
      assertEquals(500, userService.getStatistics(user.getId(), user.getToken(), user.getId()).getHighScore());
  }

  @Test
  void getStatisticsErrorPlayerNotFound() {
      User user = new User();
      user.setId(1L);
      user.setToken("11");
      user.setUsername("testUser");
      when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
      when(playerScoreRepository.getPlayerStatistics(user.getId())).thenReturn(null);
      assertThatThrownBy(() -> userService.getStatistics(user.getId(), user.getToken(), user.getId()))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("Player not found");
  }

  @Test
  void createUserErrorUsernameForGuestUserReserved() {
    User newUser = new User();
    newUser.setUsername("FreddieMer-curry123456");
    newUser.setPassword("testPassword");
    List<String> reservedUsernamesStrings = new ArrayList<>();
    reservedUsernamesStrings.add("FreddieMer-curry");
    when(guestUserStorage.getUsernamesString()).thenReturn(reservedUsernamesStrings);
    assertThatThrownBy(() -> userService.createUser(newUser))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("add User failed because username is reserved for guests");
  }
  @Test
  void createUserErrorUsernameForGuestUserReserved2() {
      User newUser = new User();
      newUser.setPassword("testPassword");
      newUser.setUsername("Guest123456");
      assertThatThrownBy(() -> userService.createUser(newUser))
              .isInstanceOf(ResponseStatusException.class)
              .hasMessageContaining("add User failed because username is reserved for guests");
  }
}
