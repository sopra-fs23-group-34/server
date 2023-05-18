package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void givenUsersWhenGetUsersThenReturnJsonArray() throws Exception {
    // given
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setStatus(UserStatus.OFFLINE);
    user.setPassword("testuser1");

    List<User> allUsers = Collections.singletonList(user);

    // this mocks the UserService -> we define above what the userService should
    // return when getUsers() is called
    given(userService.getUsers()).willReturn(allUsers);

    // when
    MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

    // then
    mockMvc.perform(getRequest).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].username", is(user.getUsername())))
        .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())));
  }

  @Test
  void createUserValidInputUserCreated() throws Exception {
    // given
    User user = new User();
    user.setId(1L);
    user.setPassword("Test User");
    user.setUsername("testUsername");
    user.setEmail("test@mail.ch");
    user.setToken("1");
    user.setStatus(UserStatus.ONLINE);
    user.setCreation_date(new Date());

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setPassword("Test User");
    userPostDTO.setUsername("testUsername");
    userPostDTO.setEmail("test@mail.ch");

    given(userService.createUser(Mockito.any())).willReturn(user);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

        // then
    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.token", is(user.getToken())))
        .andExpect(jsonPath("$.username", is(user.getUsername())))
        .andExpect(jsonPath("$.email", is(user.getEmail())))
        .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

  @Test
  void createUserUnsuccessfulEmailAlreadyUsed_error() throws Exception {

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setPassword("Test User");
    userPostDTO.setUsername("testUsername");
    userPostDTO.setEmail("test@mail.ch");

    given(userService.createUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.CONFLICT, "add User failed because email is already used"));

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(userPostDTO));

    // then
    mockMvc.perform(postRequest)
       .andExpect(status().isConflict())
       .andExpect(status().reason("add User failed because email is already used"));
    }

  @Test
  void createUserUnsuccessfulUsernameAlreadyUsed_error() throws Exception {

    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setPassword("Test User");
    userPostDTO.setUsername("testUsername");
    userPostDTO.setEmail("test@mail.ch");

    given(userService.createUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.CONFLICT, "add User failed because username is already used"));

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/users/create")
       .contentType(MediaType.APPLICATION_JSON)
       .content(asJsonString(userPostDTO));

    // then
    mockMvc.perform(postRequest)
       .andExpect(status().isConflict())
       .andExpect(status().reason("add User failed because username is already used"));
    }


  @Test
  void loginUserSuccessful() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");
        user.setEmail("test@mail.ch");
        user.setToken("1");
        user.setStatus(UserStatus.ONLINE);
        user.setCreation_date(new Date());

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setEmail("test@mail.ch");

        given(userService.loginUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.token", is(user.getToken())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())));
    }

    @Test
    void loginGuestUserSuccessful() throws Exception {
        // given
        User guestUser = new User();
        guestUser.setId(1L);
        guestUser.setPassword("TestGuestUser");
        guestUser.setUsername("testGuestUsername");
        guestUser.setEmail("test@guest.ch");
        guestUser.setToken("1");
        guestUser.setStatus(UserStatus.ONLINE);
        guestUser.setCreation_date(new Date());

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("TestGuestUser");
        userPostDTO.setUsername("testGuestUsername");
        userPostDTO.setEmail("test@guest.ch");

        given(userService.loginGuestUser()).willReturn(guestUser);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/login/guestUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(guestUser.getId().intValue())))
                .andExpect(jsonPath("$.token", is(guestUser.getToken())))
                .andExpect(jsonPath("$.username", is(guestUser.getUsername())))
                .andExpect(jsonPath("$.email", is(guestUser.getEmail())))
                .andExpect(jsonPath("$.status", is(guestUser.getStatus().toString())));
    }

  @Test
  void loginUserUnsuccessfulInvalidUsername_error() throws Exception {
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsernameInvalid");

        given(userService.loginUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username is not registered"));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Username is not registered"));
    }

  @Test
  void loginUserUnsuccessfulInvalidPassword_error() throws Exception {
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsernameInvalid");

        given(userService.loginUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password"));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Wrong Password"));
    }

  @Test
  void logoutSuccessful() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");
        user.setEmail("test@mail.ch");
        user.setToken("11");
        user.setStatus(UserStatus.ONLINE);
        user.setCreation_date(new Date());

        given(userService.logoutUser(Mockito.any(), Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/logout/1")
                .header("token", "11")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void getGlobalRanking() throws Exception {
      List<LeaderBoard> globalRanking = new ArrayList<>();
      globalRanking.add(new LeaderBoard(1L, 10L));
      globalRanking.add(new LeaderBoard(2L, 20L));

      given(userService.getTotalScores(Mockito.any(), Mockito.any())).willReturn(globalRanking);

      MockHttpServletRequestBuilder getRequest = get("/users/ranking")
              .header("token", "11")
              .header("id", "1")
              .contentType(MediaType.APPLICATION_JSON);

      mockMvc.perform(getRequest)
                .andExpect(status().isOk());
    }

    @Test
    void getPlayerStatistics() throws Exception {
      Long id = 1L;
      long gamesPlayed = 2L;
      int highScore = 1000;
      long gamesWon = 2L;
      double winRatio = 1.0;

      PlayerStatistics ps = new PlayerStatistics(id, gamesPlayed, highScore, gamesWon, winRatio);
      given(userService.getStatistics(Mockito.any(), Mockito.any(), Mockito.any())).willReturn(ps);

      MockHttpServletRequestBuilder getRequest = get("/users/statistics/1")
                .header("token", "11")
                .header("id", "1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(ps.getUserId().intValue())))
                .andExpect(jsonPath("$.highScore", is(ps.getHighScore())))
                .andExpect(jsonPath("$.winRatio", is(ps.getWinRatio())));
    }

  @Test
  void logoutUnsuccessfulInvalidToken_error() throws Exception {
        given(userService.logoutUser(Mockito.any(), Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action"));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/logout/1")
                .header("token", "11")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("You are not authorized to perform this action"));
    }
    @Test
    void logoutGuestSuccessful() throws Exception {

       // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/logout/guestUser/1")
                .header("token", "11")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void updateUseSuccessful() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("newPassword");
        user.setUsername("testUsername");
        user.setEmail("test@mail.ch");
        user.setToken("11");
        user.setStatus(UserStatus.ONLINE);
        user.setBio("Hi");

        given(userService.updateUser(Mockito.any(User.class), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action"));
        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setPassword("newPassword");
        userPutDTO.setUsername("testUsername");
        userPutDTO.setEmail("test@mail.ch");
        userPutDTO.setBio("Hi");
        userPutDTO.setStatus(UserStatus.ONLINE);

        MockHttpServletRequestBuilder putRequest = put("/users/update/1")
                .header("token", "22")
                .header("password", "newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("You are not authorized to perform this action"));

    }

    @Test
    void updateUserSuccessful() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("newPassword");
        user.setUsername("testUsername");
        user.setEmail("test@mail.ch");
        user.setToken("11");
        user.setStatus(UserStatus.ONLINE);
        user.setBio("Hi");

        given(userService.updateUser(Mockito.any(User.class), Mockito.anyString(), Mockito.anyLong(), Mockito.anyString())).willReturn(user);
        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setPassword("newPassword");
        userPutDTO.setUsername("testUsername");
        userPutDTO.setEmail("test@mail.ch");
        userPutDTO.setBio("Hi");
        userPutDTO.setStatus(UserStatus.ONLINE);

        MockHttpServletRequestBuilder putRequest = put("/users/update/1")
                .header("token", "11")
                .header("password", "newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(user.getToken())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.bio", is(user.getBio())));
    }


    @Test
    void getUserSuccessful() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");
        user.setEmail("test@mail.ch");
        user.setToken("11");
        user.setStatus(UserStatus.ONLINE);
        user.setBio("Hi I am a testUser:)");
        given(userService.getUserById(Mockito.any())).willReturn(user);


        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/users/getUser/1")
                .header("token", "11")
                .contentType(MediaType.APPLICATION_JSON);


        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(user.getToken())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.bio", is(user.getBio())));
    }

    @Test
    void getUserUnsuccessfulUnauthorized_error() throws Exception {
        given(userService.getUserById(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this action"));



        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/users/getUser/1")
                .header("token", "111")
                .contentType(MediaType.APPLICATION_JSON);


        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("You are not authorized to perform this action"));


    }




  /**
   * Helper Method to convert userPostDTO into a JSON string such that the input
   * can be processed
   * Input will look like this: {"name": "Test User", "username": "testUsername"}
   * 
   * @param object
   * @return string
   */
  private String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format("The request body could not be created.%s", e));
    }
  }
}