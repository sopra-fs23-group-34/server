package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
public class RoundScoreMessage implements Message{
    @Getter
    private String topic;

    private Map<String, Map> roundScores;

    public Map<String, Map> getContent(){
        return roundScores;
    }
}