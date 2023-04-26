package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Data;

@Data
public class PlayerScoreGetDTO {
    private int id;
    private Long user_id;
    private String score;
}
