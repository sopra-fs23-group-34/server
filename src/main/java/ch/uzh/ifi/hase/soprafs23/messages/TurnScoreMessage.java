package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TurnScoreMessage implements Message{
    @Getter
    private String topic;

    private String strMessage;

    public String getContent(){
        return strMessage;
    }
}