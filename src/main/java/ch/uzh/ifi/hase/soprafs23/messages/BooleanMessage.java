package ch.uzh.ifi.hase.soprafs23.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BooleanMessage {
    @Getter
    private String topic;

    private Boolean content;

    public Boolean getContent(){

        return content;
    }

}
