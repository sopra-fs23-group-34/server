package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserGetDTO {
  private Long id;
  private String username;
  private UserStatus status;
  private String email;
  private String bio;
  private Date creationDate;
}
