package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
public class RoundScoreMessage implements Message{
    @Getter
    private String topic;

    private Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScores;

    public Map<String,Map<String,ArrayList<Map<String,Double>>>> getContent(){

        return roundScores;
    }

    @Override
    public String getType() {
        return null;
    }
}