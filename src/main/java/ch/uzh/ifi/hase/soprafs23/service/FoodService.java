package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.repository.FoodsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@Transactional
public class FoodService {
    private final FoodsRepository foodsRepository;
    @Autowired
    public FoodService(@Qualifier("foodsRepository") FoodsRepository foodsRepository) {
        this.foodsRepository = foodsRepository;
    }

    public List<String> getRandomFoods(int num, FoodCategory foodCategory) {
        return foodsRepository.findsRandomFoods(foodCategory.getValue(), num);
    }

    public Food getFood(String food_name) throws IOException {
        List<String[]> apiKeys = new ArrayList<>();
        apiKeys.add(new String[]{"9dd751e9","7470f45a98ccc467dc3c043b1f997cf4", "0"});
        apiKeys.add(new String[]{"9dd751e9","7470f45a98ccc467dc3c043b1f997cf4", "1"});
        apiKeys.add(new String[]{"376a71b1","46ef2c8c088e63f038d5b2e0d43cf066", "0"});
        apiKeys.add(new String[]{"376a71b1","46ef2c8c088e63f038d5b2e0d43cf066", "1"});
        int maxTries = apiKeys.size();
        int tries = 0;
        String responseBody = "";
        while (true) {
            try {
                String appId = apiKeys.get(tries)[0];
                String appKey = apiKeys.get(tries)[1];
                String remoteUser = apiKeys.get(tries)[2];
                responseBody = apiCall(food_name, appId, appKey, remoteUser);
                break;
            } catch (Exception e) {
                tries++;
                if (tries >= maxTries) {
                    throw new RuntimeException("no more API calls available for today");
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);
        List<Map<String, Object>> foods = (List<Map<String, Object>>) map.get("foods");
        Map<String, Object> food = foods.get(0);
        Map<String, String> photo = (Map<String, String>) food.get("photo");
        Number calories = (Number) food.get("nf_calories");
        Number fat = (Number) food.get("nf_total_fat");
        Number protein = (Number) food.get("nf_protein");
        Number carbs = (Number) food.get("nf_total_carbohydrate");
        String name = (String) food.get("food_name");
        String image_link = photo.get("highres");
        Map<String, Double> nutritional_values = new HashMap<>();
        nutritional_values.put("calories", calories.doubleValue());
        nutritional_values.put("fat", fat.doubleValue());
        nutritional_values.put("protein", protein.doubleValue());
        nutritional_values.put("carbs", carbs.doubleValue());
        return new Food(name, nutritional_values, image_link);
        }

        private String apiCall(String foodName, String appId, String appKey, String remoteUser) {
            String apiUrl = "https://trackapi.nutritionix.com/v2/natural/nutrients";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-app-id", appId);
            headers.set("x-app-key", appKey);
            headers.set("x-remote-user-id", remoteUser);
            Map<String, String> body = new HashMap<>();
            body.put("query", foodName);
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
            return response.getBody();
        }
}
