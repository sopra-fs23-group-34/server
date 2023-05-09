package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserGetDTO> getAllUsers() {
    List<User> users = userService.getUsers();
    List<UserGetDTO> userGetDTOs = new ArrayList<>();
    for (User user : users) {
      userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
    }
    return userGetDTOs;
  }

  @PostMapping("/users/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
    User createdUser = userService.createUser(userInput);
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
  }

    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
        User loginUser = userService.loginUser(userInput);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }
    @PostMapping("/users/logout/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void logoutUser(@RequestHeader("token") String token,
                           @PathVariable Long userId) {
        User loggedOutUser = userService.logoutUser(token, userId);
   }

    @PutMapping("/users/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO updateUserInformation(@RequestBody UserPutDTO userPutDTO,
                                            @RequestHeader("token") String token,
                                            @RequestHeader(required = false, value = "password") String password,
                                            @PathVariable Long id){
        User userWithUpdateInformation = DTOMapper.INSTANCE.convertUserPutUpdateDTOtoEntity(userPutDTO);
        User updatedUser = userService.updateUser(userWithUpdateInformation, token, id, password);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(updatedUser);

    }

    @GetMapping("/users/getUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUser(@RequestHeader("token") String token, @PathVariable Long userId) {
        userService.verifyUser(token, userId);
        User user = userService.getUserById(userId);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
    }

    @GetMapping("/users/ranking")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<LeaderBoard> getGlobalRanking(@RequestHeader("token") String token, @RequestHeader("id") Long id) {
        List<LeaderBoard> scores = userService.getTotalScores(id, token);
       return scores;
    }

    @GetMapping("/users/statistics/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PlayerStatistics getStatistics( @PathVariable Long userId, @RequestHeader("token") String token, @RequestHeader("id") Long id) {
       // you can only check your own scores, otherwise the authentication has to be handled differently
        PlayerStatistics playerStatistics = userService.getStatistics(userId, token, id);
        return playerStatistics;
    }

    @PostMapping("/users/login/guestUser")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginGuestUser() {
        User guestUser = userService.loginGuestUser();
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(guestUser);

    }

    @PostMapping("/users/logout/guestUser/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void logoutGuestUser(@RequestHeader("token") String token,
                                 @PathVariable Long userId) {
      userService.logoutGuestUser(token, userId);
    }

    @PostMapping("/users/test")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String[] test() {
      String a = (System.getenv("API_KEY1"));
      String b = (System.getenv("API_KEY2"));
      String c = (System.getenv("API_KEY3"));
      String d = (System.getenv("API_KEYS"));
        return new String[]{a, b, c, d};
    }
}
