package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.repository.FoodsRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodServiceTest {
    @Mock
    private FoodsRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getRandomFoods() throws IOException {
        List<String> f = new ArrayList<>();
        f.add("Apple");
        f.add("Banana");
        f.add("Lemon");
        f.add("Grapes");
        f.add("Strawberry");
        when(foodService.getRandomFoods(5, FoodCategory.FRUITS)).thenReturn(f);
        List<String> foods = foodService.getRandomFoods(5, FoodCategory.FRUITS);
        assertEquals(foods.size(), 5);
    }

    @Test
    public void APIcall() throws IOException {
        Map<String, Double> nutritionalValues = new HashMap<>();
        nutritionalValues.put("carbs", 13.807692307692307);
        nutritionalValues.put("protein", 0.2582417582417582);
        nutritionalValues.put("fat", 0.1703296703296703);
        nutritionalValues.put("calories", 52.0);
        Food apple = new Food("apple", nutritionalValues, "https://nix-tag-images.s3.amazonaws.com/384_highres.jpg");

        Food food = foodService.getFood("apple");
        assertEquals(food.getName(), apple.getName());
        assertEquals(food.getNutritionValues(), apple.getNutritionValues());
        assertEquals(food.getImage(), apple.getImage());
    }

    @Test
    public void RoundTest() throws IOException {
        List<String> randomFood = new ArrayList<>();
        randomFood.add("apple");
        when(foodService.getRandomFoods(1, FoodCategory.FRUITS)).thenReturn(randomFood);
        List<String> foods = foodService.getRandomFoods(1, FoodCategory.FRUITS);
        Map<String, Double> nutritionalValues = new HashMap<>();
        nutritionalValues.put("carbs", 13.81);
        nutritionalValues.put("protein", 0.26);
        nutritionalValues.put("fat", 0.17);
        nutritionalValues.put("calories", 52.0);

        Food apple = new Food("apple", nutritionalValues, "https://nix-tag-images.s3.amazonaws.com/384_highres.jpg");

        Food food = foodService.getFood(foods.get(0));
        System.out.println(food);
        assertEquals(food.getName(), apple.getName());
        assertEquals(food.getNutritionValues(), apple.getNutritionValues());
        assertEquals(food.getImage(), apple.getImage());


    }

}
