package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPutDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private UserStatus status;

}
