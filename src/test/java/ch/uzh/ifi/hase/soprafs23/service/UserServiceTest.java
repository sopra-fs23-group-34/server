package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.User;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {


  @Mock
  private UserRepository userRepository;

  @Mock
  private UserStorage userStorage = UserStorage.getInstance();

  @InjectMocks
  private UserService userService;


  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setId(1L);
    testUser.setUsername("testUsername");
    testUser.setPassword("testPassword");
    testUser.setEmail("testMail");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
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
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }
    @Test
    void createUser_duplicateMail_throwsException() {
      User user = new User();
      user.setEmail("testMail");
      user.setUsername("anotherUsername");
      user.setPassword("somePassword");
        // given -> a first user has already been created
        userService.createUser(testUser);

        // when -> setup additional mocks for UserRepository
        when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        // then -> attempt to create second user with same user -> check that an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(user));
    }
  /*
  @Test
    void createUser_unauthorizedName_throwsException() {
      User user = new User();
      user.setUsername("JohnLemon");

        // when -> setup additional mocks for UserRepository
        when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        // then -> attempt to create second user with same user -> check that an error
        // is thrown
        assertThrows(ResponseStatusException.class, () -> userService.createUser(user));
    }*/

  @Test
  void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }
    @Test
    void loginGuestUser() {
        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        User createdUser = userService.loginGuestUser();

        // then
        verify(userRepository, times(1)).save(Mockito.any());


        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getEmail());
        assertNotNull(createdUser.getPassword());
        assertNotNull(createdUser.getUsername());
        assertNotNull(createdUser.isHost());
    }/*
    @Test
    public void logoutGuestUser() {
        // when -> any object is being save in the userRepository -> return the dummy
        // testUser
        User createdUser = userService.loginGuestUser();

        // then
        verify(userRepository, times(1)).save(Mockito.any());

        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getEmail());
        assertNotNull(createdUser.getPassword());
        assertNotNull(createdUser.getUsername());
        assertNotNull(createdUser.isHost());
        userService.logoutGuestUser(createdUser.getToken(),createdUser.getId());
    }*/
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
    void testLogoutUserSuccess() {
        User loginUser = new User();
        loginUser.setUsername("testUser");
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
    /*
    @Test
    void testUpdateUserInvalidPassword() {
        User loginUser = new User();
        loginUser.setUsername("testUser");
        loginUser.setPassword("testPassword");
        loginUser.setToken("testToken");
        loginUser.setId(1L);

        User userUpdateInformation = new User();
        loginUser.setUsername("userTest");
        loginUser.setPassword("newPassword");


        when(userRepository.findById(1L)).thenReturn(loginUser);

        userService.updateUser(userUpdateInformation,"testToken",1L,"testPassword");
    }
*/
    /*
    @Test
    void testUpdateScores() {
        User loginUser = new User();
        loginUser.setUsername("testUser");
        loginUser.setPassword("testPassword");
        loginUser.setToken("testToken");
        loginUser.setId(1L);
        loginUser.setId(1L);

        HashMap playerGuesses = new HashMap<>();
        playerGuesses.put("carbs", 100);
        HashMap nutritionValues = new HashMap<>();

        Food food = new Food("banana",nutritionValues,"myImage");

        Scores scores = new Scores();
        scores.updateRoundScore(playerGuesses,"testUser",food);

        //when(userRepository.findByUsername(loginUser.getUsername())).thenReturn(userDatabase);
        //when(userRepository.findById(loginUser.getId())).thenReturn(Optional.of(userDatabase));


        userService.updateScores(scores);
        System.out.println(userService.getTotalScores(testUser.getId(),testUser.getToken()));

    }
*/

}
