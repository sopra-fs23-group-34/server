package ch.uzh.ifi.hase.soprafs23.messages;

import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class PlayerListMessage implements Message{
    @Getter
    private String topic;
    private List<PlayerGetDTO> lobbyPlayers;
    public List<PlayerGetDTO> getContent(){
        return lobbyPlayers;
    }
    public List<PlayerGetDTO> getPlayers() {
        return null;
    }
    @Override
    public String getType() {
        return null;
    }
}
