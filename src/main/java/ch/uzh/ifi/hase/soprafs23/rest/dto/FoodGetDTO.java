package ch.uzh.ifi.hase.soprafs23.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodGetDTO {
    String food_name;
    String nf_total_carbohydrate;
    String nf_protein;
    String nf_total_fat;
    String highres;
}
