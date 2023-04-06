package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetRankDTO {
    private String username;
    private String totalScore;
}
