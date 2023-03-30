package ch.uzh.ifi.hase.soprafs23.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LobbyController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String asdf(String hallo) throws Exception {
        System.out.println("hallo");
        return hallo;
    }

}