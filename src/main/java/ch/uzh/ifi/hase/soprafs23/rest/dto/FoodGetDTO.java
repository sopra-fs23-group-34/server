package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodGetDTO {
    String name;
    String carbs;
    String protein;
    String fat;
    String picture;
}
