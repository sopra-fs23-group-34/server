package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    // fetch all users in the internal representation
    List<User> users = userService.getUsers();
    List<UserGetDTO> userGetDTOs = new ArrayList<>();

    // convert each user to the API representation
    for (User user : users) {
      userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
    }
    return userGetDTOs;
  }

  @PostMapping("/users/create")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
    // convert API user to internal representation
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);
    System.out.println("hi, I am here");
    // create user
    User createdUser = userService.createUser(userInput);
    // convert internal representation of user back to API
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
  }

    @PostMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // login user
        User loginUser = userService.loginUser(userInput);
        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loginUser);
    }
    @PutMapping("/users/logout/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void logoutUser(@RequestHeader("token") String token,
                           @PathVariable Long userId) {
        System.out.println("here");
        //Check, if user is authorised to logout
        userService.verifyUser(token, userId);

        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostLogoutDTOtoEntity(userId);

        //logout user
        userService.logoutUser(userInput);
    }

    @PutMapping("/users/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO updateUserInformation(@RequestBody UserPutDTO userPutDTO,
                                            @RequestHeader("token") String token,
                                            @PathVariable Long id){
        // convert API user to internal user representation
        User userWithUpdateInformation = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);

        //update user
        User updatedUser = userService.updateUser(userWithUpdateInformation, token, id);

        // return updated user
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(updatedUser);

    }

    @GetMapping("/users/getUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUser(@PathVariable Long id) {
      User user = userService.getUserById(id);
      return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
    }

    @GetMapping("/users/ranking")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void getGlobalRanking() {
        // get global leaderboard from db
    }

    @PostMapping("/lobby/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createLobby(@RequestHeader("id") Long id) {
        // get User from DB with ID
        // create Lobby
        // assign Lobby to User
        // return WebSocket/ Lobby
    }

    @PostMapping("/lobby/join/{gameKey}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createLobby(@PathVariable String lobbyKey) {
        // check if Lobby exists
        // add Player to Lobby
        // return WebSocket
    }

}
