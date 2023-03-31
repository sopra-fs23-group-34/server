package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.PlayerMessage;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebsocketController {

    private final UserService userService;

    private final LobbyService lobbyService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    WebsocketController(UserService userService, LobbyService lobbyService) {
        this.userService = userService;
        this.lobbyService = lobbyService;
    }

    @MessageMapping("/join/{gameCode}")
    @SendTo("/lobbys/messages")
    public List<PlayerGetDTO> joinLobby(PlayerMessage msg, @DestinationVariable String gameCode) {
        List <LobbyPlayer> lobbyPlayers =  lobbyService.joinLobby(gameCode, 10L);
        List<PlayerGetDTO> playersGetDTOs = new ArrayList<>();
        // convert each user to the API representation
        for (LobbyPlayer lobbyPlayer : lobbyPlayers) {
            playersGetDTOs.add(DTOMapper.INSTANCE.convertPlayerToPlayerGetDTO(lobbyPlayer));
        }
        return playersGetDTOs;
        // get User from DB with ID
        //LobbyPlayer hostUser = userService.getUserWithId(id);
        // create Lobby and assign Lobby to User
        //Lobby lobby = new Lobby(hostUser);
        // save Lobby to DB?
        // return WebSocket/ Lobby
    }


    @MessageMapping("/players/response/{player_id}")
    public void playerResponse(PlayerMessage plm) throws Exception {
        System.out.println(HtmlUtils.htmlEscape(plm.getContent()));
    }


    @MessageMapping("/startGame/{gameCode}")
    @SendTo("/lobbys/messages")
    public PlayerMessage hostStartsGame(PlayerMessage plm,@DestinationVariable String gameCode) throws Exception {
        System.out.println(gameCode);
        for(int counter = 5; counter  > 0 ; counter --){
            System.out.println(counter);
            messagingTemplate.convertAndSend("/lobbys/messages", counter);
            Thread.sleep(500);
        }
        messagingTemplate.convertAndSend("/lobbys/messages", "0");
        lobbyService.startGame(gameCode,1L,"asdf");
        return plm;
    }

}
