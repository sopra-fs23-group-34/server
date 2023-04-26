package ch.uzh.ifi.hase.soprafs23.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class FoodTest {

    @Test
    public void testFoodConstructor() {
        String name = "Apple";
        Map<String, Double> nutritionValues = new HashMap<>();
        nutritionValues.put("calories", 52.0);
        nutritionValues.put("protein", 0.3);
        nutritionValues.put("fat", 0.2);
        nutritionValues.put("carbs", 14.0);
        String image = "apple.jpg";
        Food food = new Food(name, nutritionValues, image);
        assertEquals(name, food.getName());
        assertEquals(nutritionValues, food.getNutritionValues());
        assertEquals(image, food.getImage());
    }

    @Test
    public void testFoodName() {
        String name = "Orange";
        Map<String, Double> nutritionValues = new HashMap<>();
        nutritionValues.put("calories", 47.0);
        nutritionValues.put("protein", 0.9);
        nutritionValues.put("fat", 0.1);
        nutritionValues.put("carbs", 12.0);
        String image = "orange.jpg";
        Food food = new Food(name, nutritionValues, image);
        assertEquals(name, food.getName());
    }

    @Test
    public void testFoodEquals(){
        String name = "Orange";
        Map<String, Double> nutritionValues = new HashMap<>();
        nutritionValues.put("calories", 47.0);
        nutritionValues.put("protein", 0.9);
        nutritionValues.put("fat", 0.1);
        nutritionValues.put("carbs", 12.0);
        String image = "orange.jpg";
        Food food = new Food(name, nutritionValues, image);
        Food food1 = new Food(name, nutritionValues,image);
        assertEquals(food,food1);
    }

}

