package ch.uzh.ifi.hase.soprafs23.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class Food {
    private String image;
    private String name;
    @Getter
    private Map<String, Double> nutritionValues;

    public Food(String name, Map<String, Double> nutritionValues, String image) {
        this.name = name;
        this.nutritionValues = nutritionValues;
        this.image = image;
    }
}
