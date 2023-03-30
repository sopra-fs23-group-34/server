package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;


@AllArgsConstructor
@Data
public class Lobby {
    private String user_name;
    private final String gameCode;
    private final LobbyPlayer host;
    private Integer roundLimit;
    private FoodCategory foodCategory;
    private ArrayList<LobbyPlayer> players;

    public Lobby(LobbyPlayer hostUser) {
        CodeGenerator codeGenerator = new CodeGenerator();
        gameCode = codeGenerator.nextCode();
        host = hostUser;
        players.add(hostUser);
    }

    public void addPlayer(LobbyPlayer lobbyPlayer){
        players.add(lobbyPlayer);
    }

    public void removePlayer(LobbyPlayer lobbyPlayer) {
        players.remove(lobbyPlayer);
    }

    public void playGame(){

    }


}
