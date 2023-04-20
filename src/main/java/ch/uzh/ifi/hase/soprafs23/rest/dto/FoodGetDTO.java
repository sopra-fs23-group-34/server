package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodGetDTO {
    String name;
    Double carbs;
    Double protein;
    Double fat;
    Double sugar;
    String image;

}
