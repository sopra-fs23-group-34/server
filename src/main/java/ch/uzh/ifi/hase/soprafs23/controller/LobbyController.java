package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.model.PlayerMessage;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.web.util.HtmlUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.concurrent.TimeUnit;

@RestController
public class LobbyController {

    private final UserService userService;

    private final LobbyService lobbyService;



    LobbyController(UserService userService, LobbyService lobbyService) {
        this.userService = userService;
        this.lobbyService = lobbyService;
    }



    @PostMapping("/lobbys/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createLobby(@RequestHeader("id") Long id) {
        String lobby_id = lobbyService.createLobby();
        return lobby_id;
        // get User from DB with ID
        //LobbyPlayer hostUser = userService.getUserWithId(id);
        // create Lobby and assign Lobby to User
        //Lobby lobby = new Lobby(hostUser);
        // save Lobby to DB?
        // return WebSocket/ Lobby
    }

    @PostMapping("/lobbys/join")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void joinLobby(@RequestHeader("id") Long id, @RequestParam("arg") String arg) {
        lobbyService.joinLobby(arg, 1L);
        // get User from DB with ID
        //LobbyPlayer hostUser = userService.getUserWithId(id);
        // create Lobby and assign Lobby to User
        //Lobby lobby = new Lobby(hostUser);
        // save Lobby to DB?
        // return WebSocket/ Lobby
    }


}