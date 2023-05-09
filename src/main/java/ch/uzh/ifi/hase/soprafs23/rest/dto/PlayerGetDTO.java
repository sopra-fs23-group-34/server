package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerGetDTO {
    private String username;
    private Long id;
    private boolean host;

}
