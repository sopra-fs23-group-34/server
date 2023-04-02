package ch.uzh.ifi.hase.soprafs23.model;


import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public class Food {
    private String image;
    private String name;
    @Getter
    private Map<String, Integer> nutritionValues;

    public Food(String name, Map<String, Integer> nutritionValues, String image) {
        this.name = name;
        this.nutritionValues = nutritionValues;
        this.image = image;
    }
}
