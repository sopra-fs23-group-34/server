package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class GameConfig {
    private Integer roundLimit;
    private FoodCategory foodCategory;

    public int getRoundLimit() {
        return roundLimit;
    }
    public FoodCategory getFoodCategory() {
        return foodCategory;
    }
}
