package ch.uzh.ifi.hase.soprafs23.service;


import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.model.TestPlayer;
import ch.uzh.ifi.hase.soprafs23.model.CodeGenerator;
import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    public List<LobbyPlayer> joinLobby(String gameCode, Long user_id){
        System.out.println("successfull Joined Lobby");
        TestPlayer tp = new TestPlayer();
        //checkIfLobbyExists(gameCode);
        //LobbyPlayer lobbyPlayer = userService.getUserById(user_id);
        //LobbyPlayer lbp = userRepository.findById(10L);
        Lobby lobby =  lobbyStorage.getLobby(gameCode);
        lobby.addPlayer(tp);
        List<LobbyPlayer> lobbyPlayers= lobby.getPlayers();
        return lobbyPlayers;
    };

    private void checkIfHost(Long user_id, String token){
        //has to be authenticated in userservice
    }

    public void startGame(String gameCode, Long user_id, String token) throws InterruptedException {
        checkIfHost(user_id, token);
        Lobby lobby = lobbyStorage.getLobby(gameCode);
        lobby.playGame();
        lobbyStorage.removeLobby(gameCode);
    }

}
