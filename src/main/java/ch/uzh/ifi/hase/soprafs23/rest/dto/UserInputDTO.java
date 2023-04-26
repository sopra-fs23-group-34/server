package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UserInputDTO {
    @Getter
    @Setter
    private String foodCategory;
    @Getter
    @Setter
    private int roundLimit;
}
