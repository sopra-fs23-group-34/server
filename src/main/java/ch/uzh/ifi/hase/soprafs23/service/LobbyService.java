package ch.uzh.ifi.hase.soprafs23.service;


import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;
import ch.uzh.ifi.hase.soprafs23.model.CodeGenerator;
import ch.uzh.ifi.hase.soprafs23.model.Lobby;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class LobbyService {

    private final UserService userService;

    private final CodeGenerator codeGenerator = new CodeGenerator();;

    private final LobbyStorage lobbyStorage = LobbyStorage.createLobbyStorage();;

    public LobbyService(UserService userService){
        this.userService = userService;

    }

    private void checkIfLobbyExists(String id){
        Lobby lob = lobbyStorage.getLobby(id);
        if (lob == null){
            throw new NullPointerException("Lobby does Not exist");
        }

    }
    public String createLobby(){
        String gameCode = codeGenerator.nextCode();
        lobbyStorage.addLobby(gameCode, new Lobby(gameCode));
        System.out.println(gameCode);
        return gameCode;
    }

    public void joinLobby(String gameCode, Long user_id){
        checkIfLobbyExists(gameCode);
        LobbyPlayer lobbyPlayer = userService.getUserById(user_id);
        lobbyStorage.getLobby(gameCode).addPlayer(lobbyPlayer);
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
