package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.model.Game;
import ch.uzh.ifi.hase.soprafs23.model.GameConfig;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserInputDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class LobbyController {

    private final LobbyService lobbyService;

    LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @PostMapping("/lobbys/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createLobby(@RequestHeader("id") Long id) {
        return lobbyService.createLobby();
    }

    @PostMapping("/lobbys/join/{lobbyCode}/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public boolean joinLobby(@PathVariable("lobbyCode") String gameCode, @PathVariable("user_id") long id) {
        return lobbyService.joinLobby(gameCode, id);
    }

    @PostMapping("/lobbys/startGame/{lobbyCode}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void startGame(@PathVariable("lobbyCode") String gameCode, @RequestHeader("id") long id,
                          @RequestHeader("token") String token, @RequestBody UserInputDTO userInputDto) throws InterruptedException {
        GameConfig gc = DTOMapper.INSTANCE.convertUserInputDTOToGameConfig(userInputDto);
        lobbyService.startGame(gameCode, id, token, gc);
    }
    @PutMapping("lobbys/continueGame/{lobbyCode}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void roundScoreToGuess(@PathVariable("lobbyCode") String gameCode, @RequestHeader("id") long id, @RequestHeader("token") String token) throws IOException, InterruptedException {
        lobbyService.updateRound(gameCode,id,token);
    }
}