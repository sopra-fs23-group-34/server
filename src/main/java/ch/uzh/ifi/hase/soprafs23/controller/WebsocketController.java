package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.messages.IntMessage;
import ch.uzh.ifi.hase.soprafs23.messages.PlayerListMessage;
import ch.uzh.ifi.hase.soprafs23.messages.StringMessage;
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
        // todo somehow needs to be authenticated. Othewise we could also start the game with rest api
        lobbyService.startGame(gameCode,1L,"asdf", new GameConfig(plm.getRoundLimit(), plm.getFoodCategory()));
        return new StringMessage("start", "TheGameIsStarting :) ");
    }



    @MessageMapping("/guess/{gameCode}/{player_id}")
    public void playerMakeGuesses(PlayerGuesses plm, @DestinationVariable String gameCode, @DestinationVariable Long player_id)  {
        lobbyService.setPlayerGuesses(gameCode,player_id,plm.getContent());
    }

    @MessageMapping("/leave/{gameCode}/{player_id}")
    @SendTo(WebsocketConfig.lobbysDestination)
    public PlayerListMessage leaveLobby(@DestinationVariable String gameCode,
                           @DestinationVariable Long player_id) {
        List<Player> playerList = lobbyService.leaveLobby(gameCode, player_id);
        List<PlayerGetDTO> playersGetDTOs = new ArrayList<>();

        for (Player player : playerList) {
            playersGetDTOs.add(DTOMapper.INSTANCE.convertPlayerToPlayerGetDTO(player));
        }
        PlayerListMessage playerListMessage = new PlayerListMessage("players", playersGetDTOs );
        return playerListMessage;
    }
}
