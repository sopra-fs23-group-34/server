package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Lobby {
    private String user_name;
    private String game_code;

    public Lobby() {
        CodeGenerator codeGenerator = new CodeGenerator();
        game_code = codeGenerator.nextCode();

    }
}
