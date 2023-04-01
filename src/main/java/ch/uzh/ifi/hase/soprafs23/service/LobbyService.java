package ch.uzh.ifi.hase.soprafs23.service;


import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.model.*;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;

@AllArgsConstructor
@Service
@Transactional
public class LobbyService {
    private final UserService userService;
    private final CodeGenerator codeGenerator;
    private final LobbyStorage lobbyStorage;;
    private final SimpMessagingTemplate simpMessagingTemplate;

    /*
    public LobbyService(UserService userService, SimpMessagingTemplate simpMessagingTemplate){
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.codeGenerator = new CodeGenerator();
        this.lobbyStorage = LobbyStorage.createLobbyStorage();
    }
    */


    private void checkIfLobbyExists(String id){
        Lobby lob = lobbyStorage.getLobby(id);
        if (lob == null){
            throw new NullPointerException("Lobby does Not exist");
        }

    }
    public String createLobby(){
        //String gameCode = codeGenerator.nextCode();
        String gameCode = "test";
        //change to gamecode
        lobbyStorage.addLobby("test", new Lobby(gameCode, simpMessagingTemplate));
        return gameCode;
    }

    public List<Player> joinLobby(String gameCode, Long user_id){
        System.out.println("successfull Joined Lobby");
        Player tp = new Player("TestPlayer",user_id);
        //checkIfLobbyExists(gameCode);
        //LobbyPlayer lobbyPlayer = userService.getUserById(user_id);
        //LobbyPlayer lbp = userRepository.findById(10L);
        Lobby lobby =  lobbyStorage.getLobby(gameCode);
        lobby.addPlayer(tp);
        List<Player> lobbyPlayers= new ArrayList(lobby.getPlayers().values());
        return lobbyPlayers;
    };

    private void checkIfHost(Long user_id, String token){
        //has to be authenticated in userservice
    }

    public void startGame(String gameCode, Long user_id, String token, GameConfig config) throws InterruptedException {
        checkIfHost(user_id, token);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.playGame(config);
        lobbyStorage.removeLobby(gameCode);
    }

    public void setPlayerScores(String gameCode, long player_id, String guesses){
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.getPlayers().get(player_id).setGuesses(guesses);
    }

}
