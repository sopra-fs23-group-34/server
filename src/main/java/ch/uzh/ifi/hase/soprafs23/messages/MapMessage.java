package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Map;



@AllArgsConstructor
public class MapMessage implements Message{
    @Getter
    private String topic;

    private Map<String, Integer> content;

    public Map<String,Integer> getContent(){

        return content;
    }
}