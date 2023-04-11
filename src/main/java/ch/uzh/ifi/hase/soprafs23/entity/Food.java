package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Food {
    String name;
    int carbs;
    int protein;
    int fat;
    String image;
    int calories;

    public void Food(String name, int carbs, int protein, int fat, String image, int calories) {
        this.name = name;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.image = image;
        this.calories = calories;

    }
}
