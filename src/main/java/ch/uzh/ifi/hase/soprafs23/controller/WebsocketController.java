package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.model.PlayerMessage;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
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

    @MessageMapping("/testMessage")
    @SendTo("/lobbys/messages")
    public PlayerMessage playerMessage(PlayerMessage plm) throws Exception {
        System.out.println(HtmlUtils.htmlEscape(plm.getContent()));
        return plm;
    }

    @MessageMapping("/players/response/{player_id}")
    public void playerResponse(PlayerMessage plm) throws Exception {
        System.out.println(HtmlUtils.htmlEscape(plm.getContent()));
    }



    @MessageMapping("/startGame")
    @SendTo("/lobbys/messages")
    public PlayerMessage hostStartsGame(PlayerMessage plm) throws Exception {
        for(int counter = 5; counter  > 0 ; counter --){
            messagingTemplate.convertAndSend("/topic/messages", counter);
            Thread.sleep(500);
        }
        lobbyService.startGame("asdf",1L,"asdf");
        return plm;
    }

}
