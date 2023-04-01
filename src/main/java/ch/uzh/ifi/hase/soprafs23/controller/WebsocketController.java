package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.messages.IntMessage;
import ch.uzh.ifi.hase.soprafs23.messages.PlayerListMessage;
import ch.uzh.ifi.hase.soprafs23.messages.StringMessage;
import ch.uzh.ifi.hase.soprafs23.messages.TurnScoreMessage;
import ch.uzh.ifi.hase.soprafs23.model.GameConfig;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import ch.uzh.ifi.hase.soprafs23.model.PlayerGuesses;
import ch.uzh.ifi.hase.soprafs23.model.PlayerMessage;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
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

    @MessageMapping("/join/{gameCode}/{player_id}")
    @SendTo(WebsocketConfig.lobbysDestination)
    public PlayerListMessage joinLobby(PlayerMessage msg, @DestinationVariable String gameCode,
                                       @DestinationVariable Long player_id) {
        //authentication should be in msg
        List <Player> players =  lobbyService.joinLobby(gameCode, player_id);
        List<PlayerGetDTO> playersGetDTOs = new ArrayList<>();
        // convert each user to the API representation
        for (Player player : players) {
            playersGetDTOs.add(DTOMapper.INSTANCE.convertPlayerToPlayerGetDTO(player));
        }
        PlayerListMessage playerListMessage = new PlayerListMessage("players", playersGetDTOs );
        return playerListMessage;
    }

    @MessageMapping("/startGame/{gameCode}")
    @SendTo(WebsocketConfig.lobbysDestination)
    public StringMessage hostStartsGame(PlayerMessage plm,@DestinationVariable String gameCode) throws Exception {
        for(int counter = 5; counter  > 0 ; counter --){
            messagingTemplate.convertAndSend(WebsocketConfig.lobbysDestination , new IntMessage("timer", counter));
            Thread.sleep(500);
        }
        messagingTemplate.convertAndSend(WebsocketConfig.lobbysDestination , new IntMessage("timer", 0));
        lobbyService.startGame(gameCode,1L,"asdf", new GameConfig(plm.getRoundLimit(), plm.getFoodCategory()));
        return new StringMessage("start", "TheGameIsStarting :) ");
    }

    @MessageMapping("/guess/{gameCode}/{player_id}")
    @SendTo(WebsocketConfig.simpleBrokerDestination + "/players/{player_id}")
    public TurnScoreMessage playerMakeGuesses(PlayerGuesses plm, @DestinationVariable String gameCode, @DestinationVariable Long player_id) throws Exception {
        System.out.println("hallo");
        lobbyService.setPlayerScores(gameCode,player_id,plm.getContent());
        return new TurnScoreMessage("RoundScore","999");
    }


}
