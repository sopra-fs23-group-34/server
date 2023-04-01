package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IntMessage implements Message{
    @Getter
    private String topic;

    private int intMessage;

    public int getContent(){
        return intMessage;
    }
}
