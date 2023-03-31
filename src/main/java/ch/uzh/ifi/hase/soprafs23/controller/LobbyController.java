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

@Controller
public class LobbyController {

    private final UserService userService;

    private final LobbyService lobbyService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    LobbyController(UserService userService, LobbyService lobbyService) {
        this.userService = userService;
        this.lobbyService = lobbyService;
    }

    @MessageMapping("/testMessage")
    @SendTo("/lobby/messages")
    public PlayerMessage playerMessage(PlayerMessage plm) throws Exception {
        System.out.println(HtmlUtils.htmlEscape(plm.getContent()));
        return plm;
    }

    @MessageMapping("/startGame")
    @SendTo("/lobby/messages")
    public PlayerMessage hostStartsGame(PlayerMessage plm) throws Exception {
        for(int counter = 5; counter  > 0 ; counter --){
            messagingTemplate.convertAndSend("/topic/messages", counter);
            Thread.sleep(500);
        }
        lobbyService.startGame("asdf",1L,"asdf");
        return plm;
    }


    @PostMapping("/lobby/create")
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

    @PostMapping("/lobby/join/{gameKey}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void joinLobby(@PathVariable String gameCode) {
        lobbyService.joinLobby(gameCode, 1L);
        // check if Lobby exists
        // add Player to Lobby
        // return WebSocket
    }

}