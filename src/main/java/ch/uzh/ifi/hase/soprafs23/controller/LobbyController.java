package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.model.GameConfig;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserInputDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
public class LobbyController {

    private final UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final LobbyService lobbyService;

    LobbyController(UserService userService, LobbyService lobbyService) {
        this.userService = userService;
        this.lobbyService = lobbyService;
    }

    @PostMapping("/lobbys/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createLobby(@RequestHeader("id") Long id) {
        String gameCode = lobbyService.createLobby();
        return gameCode;
    }

    @PostMapping("/lobbys/join/{lobbyCode}/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean joinLobby(@PathVariable("lobbyCode") String gameCode, @PathVariable("user_id") long id) {
        Boolean isHost = lobbyService.joinLobby(gameCode, id);
        return isHost;
    }

    @PostMapping("/lobbys/startGame/{lobbyCode}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void startGame(@PathVariable("lobbyCode") String gameCode, @RequestHeader("id") long id,
                          @RequestHeader("token") String token,@RequestBody UserInputDTO userInputDto) throws InterruptedException {
        GameConfig gc = DTOMapper.INSTANCE.convertUserInputDTOToGameConfig(userInputDto);
        lobbyService.startGame(gameCode, id,token , gc);
    }








}