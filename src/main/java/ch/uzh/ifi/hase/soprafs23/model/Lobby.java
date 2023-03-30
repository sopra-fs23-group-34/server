package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;



@AllArgsConstructor
@Data
public class Lobby {
    private String user_name;
    private final String game_code;
    private final User host;

    public Lobby(User hostUser) {
        CodeGenerator codeGenerator = new CodeGenerator();
        game_code = codeGenerator.nextCode();
        host = hostUser;


    }
}
