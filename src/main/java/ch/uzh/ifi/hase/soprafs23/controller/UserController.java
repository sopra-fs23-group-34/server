package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
    @PostMapping("/users/logout/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void logoutUser(@RequestHeader("token") String token,
                           @PathVariable Long userId) {
        //Check, if user is authorised to logout
        //userService.verifyUser(token, userId);

        //logout user
        User loggedOutUser = userService.logoutUser(token, userId);

        //return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loggedOutUser);
    }

    @PutMapping("/users/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO updateUserInformation(@RequestBody UserPutDTO userPutDTO,
                                            @RequestHeader("token") String token,
                                            @RequestHeader(required = false, value = "password") String password,
                                            @PathVariable Long id){
        // convert API user to internal user representation
        User userWithUpdateInformation = DTOMapper.INSTANCE.convertUserPutUpdateDTOtoEntity(userPutDTO);
        //update user
        User updatedUser = userService.updateUser(userWithUpdateInformation, token, id);

        // return updated user
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
    //get all Users rank
    @GetMapping("/users/ranking")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetRankDTO> getGlobalRanking() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetRankDTO> userGetRankDTOs = new ArrayList<UserGetRankDTO>();

        // convert each user to the API representation
        for (User user : users) {
            userGetRankDTOs.add(DTOMapper.INSTANCE.convertUserToUserGetRankDTO(user));
        }
        //sort descending
        Collections.sort(userGetRankDTOs, new Comparator<UserGetRankDTO>() {
            @Override
            public int compare(UserGetRankDTO o1, UserGetRankDTO o2) {
                return o1.getTotalScore().compareTo(o2.getTotalScore());
            }
        });
        return userGetRankDTOs;
    }
    @GetMapping("users/food/{requestedFood}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Food getBanana(@PathVariable String requestedFood) throws IOException {
        FoodService foodService = new FoodService();
        Food food = foodService.getFood(requestedFood);
        System.out.println("getBanana");
        //Food myBanana = new Food();
      /*
      myBanana.setName("Banana");
      myBanana.setFat("0.3");
      myBanana.setProtein("1.1");
      myBanana.setCarbs("23");
      myBanana.setPicture("Beautiful Banana");
       */
        //return DTOMapper.INSTANCE.convertEntityToFoodGetDTO(food);
        return food;

    }


}
