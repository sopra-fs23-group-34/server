package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.Foods;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.repository.FoodsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class FoodService {

    private final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final FoodsRepository foodsRepository;

    @Autowired
    public FoodService(@Qualifier("foodsRepository") FoodsRepository foodsRepository) {
        this.foodsRepository = foodsRepository;
    }

    public List<Foods> getFoods() {
        return this.foodsRepository.findAll();
    }

    public List<String> getRandomFoodsByCategory(FoodCategory category, Integer rounds) {
        List<Foods> allFoods = getFoods();
        List<Foods> specificFoods = new ArrayList<>();
        for (Foods food : allFoods) {
            if (food.getCategory() == category) {
                specificFoods.add(food);
            }
        }
        List<String> randomFoods = new ArrayList<>();
        Random rand = new Random();
        while (randomFoods.size() < rounds) {
            int random = rand.nextInt(specificFoods.size());
            if (!randomFoods.contains(specificFoods.get(random).getName())) {
                randomFoods.add(specificFoods.get(random).getName());
            }
        }
        return randomFoods;
    }



    public Food getFood(String food_name) throws IOException {
        String apiUrl = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        String appId = "9dd751e9";
        String appKey = "7470f45a98ccc467dc3c043b1f997cf4";
        String remoteUser = "0";
        String query = food_name;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);
        headers.set("x-remote-user-id", remoteUser);
        Map<String, String> body = new HashMap<>();
        body.put("query", query);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);
        List<Map<String, Object>> foods = (List<Map<String, Object>>) map.get("foods");
        Map<String, Object> food = foods.get(0);
        Map<String, String> photo = (Map<String, String>) food.get("photo");
        System.out.println(food.get("nf_calories").getClass());
        System.out.println(food.get("nf_total_fat").getClass());
        System.out.println(food.get("nf_protein").getClass());
        System.out.println(food.get("nf_total_carbohydrate").getClass());
        System.out.println(food.get("----------"));
        Number calories = (Number) food.get("nf_calories");
        Number fat = (Number) food.get("nf_total_fat");
        Number protein = (Number) food.get("nf_protein");
        Number carbs = (Number) food.get("nf_total_carbohydrate");
        String name = (String) food.get("food_name");
        String image_link = photo.get("highres");

        System.out.println("name: " + name);
        System.out.println("calories: " + calories);
        System.out.println("fat: " + fat);
        System.out.println("protein: " + protein);
        System.out.println("carbs: " + carbs);
        System.out.println("image link: " + image_link);

        Map<String, Double> nutritional_values = new HashMap<>();
        nutritional_values.put("calories", calories.doubleValue());
        nutritional_values.put("fat", fat.doubleValue());
        nutritional_values.put("protein", protein.doubleValue());
        nutritional_values.put("carbs", carbs.doubleValue());

        Food apiFood = new Food(name, nutritional_values, image_link);
        return apiFood;
        }

    public static void main(String[] args) throws IOException {

        /*
        String[] foods = new String[] {
                "bananas",
                "strawberries",
                "grapes",
                "apples",
                "watermelon",
                "oranges",
                "blueberries",
                "lemons",
                "peaches",
                "avocados",
                "pineapple",
                "cherries",
                "cantaloupe",
                "raspberries",
                "pears",
                "limes",
                "blackberries",
                "clementine",
                "mangoes",
                "plums"};
        for (int i = 0; i < foods.length; i ++) {
            FoodService.getFood(foods[i]);
        }

         */
    }

}
