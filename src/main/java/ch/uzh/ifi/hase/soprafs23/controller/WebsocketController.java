package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.messages.PlayerListMessage;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import ch.uzh.ifi.hase.soprafs23.model.PlayerGuesses;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebsocketController {

    private final LobbyService lobbyService;

    WebsocketController( LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }


    @MessageMapping("/update/{gameCode}")
    @SendTo(WebsocketConfig.LOBBYSDESTINATION)
    public PlayerListMessage updateListedPlayers(@DestinationVariable String gameCode)  {
        List <Player> players =  lobbyService.updatePlayerList(gameCode);
        List<PlayerGetDTO> playersGetDTOs = new ArrayList<>();
        for (Player player : players) {
            playersGetDTOs.add(DTOMapper.INSTANCE.convertPlayerToPlayerGetDTO(player));
        }
        return new PlayerListMessage("players", playersGetDTOs );
    }

    @MessageMapping("/guess/{gameCode}/{player_id}")
    public void playerMakeGuesses(PlayerGuesses plm, @DestinationVariable String gameCode, @DestinationVariable Long player_id)  {
        lobbyService.setPlayerGuesses(gameCode,player_id,plm.getContent());
    }

    @MessageMapping("/leave/{gameCode}/{player_id}")
    @SendTo(WebsocketConfig.LOBBYSDESTINATION)
    public PlayerListMessage leaveLobby(@DestinationVariable String gameCode,
                           @DestinationVariable Long player_id) {
        List<Player> playerList = lobbyService.leaveLobby(gameCode, player_id);
        List<PlayerGetDTO> playersGetDTOs = new ArrayList<>();
        for (Player player : playerList) {
            playersGetDTOs.add(DTOMapper.INSTANCE.convertPlayerToPlayerGetDTO(player));
        }
        return new PlayerListMessage("players", playersGetDTOs );
    }
}
