package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
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
    private final User host;
    private Integer roundLimit;
    private FoodCategory foodCategory;
    private ArrayList<User> players;

    public Lobby(User hostUser) {
        CodeGenerator codeGenerator = new CodeGenerator();
        gameCode = codeGenerator.nextCode();
        host = hostUser;
    }

    public void addPlayer(User user){
        players.add(user);
    }

    public void removePlayer(User user) {
        players.remove(user);
    }

    public void playGame(){

    }


}
