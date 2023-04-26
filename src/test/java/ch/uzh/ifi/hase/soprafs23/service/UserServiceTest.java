package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Scores;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
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

public class UserServiceTest {


  @Mock
  private UserRepository userRepository;

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
    testUser.setEmail("test@email.com");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    when(userRepository.save(Mockito.any())).thenReturn(testUser);

  }

  @Test
  public void createUser_validInputs_success() {
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
  public void createUser_duplicateUserName_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

  @Test
  public void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
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


}
