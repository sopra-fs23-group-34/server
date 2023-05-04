package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerScoreGetDTO {
    private int id;
    private Long user_id;
    private String score;
    private Boolean winner;
}
