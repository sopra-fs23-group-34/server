package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LobbyController {

    private final UserService userService;

    LobbyController(UserService userService) {
        this.userService = userService;
    }


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String asdf(String hallo) throws Exception {
        System.out.println("hallo");
        return hallo;
    }

    @PostMapping("/lobby/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createLobby(@RequestHeader("id") Long id) {
        // get User from DB with ID
        User hostUser = userService.getUserWithId(id);
        // create Lobby and assign Lobby to User
        Lobby lobby = new Lobby(hostUser);
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