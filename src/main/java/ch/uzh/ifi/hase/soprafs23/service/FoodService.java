package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import ch.uzh.ifi.hase.soprafs23.repository.FoodsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FoodService {

    private final String apiKey = "9dd751e9,7470f45a98ccc467dc3c043b1f997cf4,376a71b1,46ef2c8c088e63f038d5b2e0d43cf066";

    private final FoodsRepository foodsRepository;

    @Autowired
    public FoodService(@Qualifier("foodsRepository") FoodsRepository foodsRepository) {
        this.foodsRepository = foodsRepository;
    }

    public List<String> getRandomFoods(int num, FoodCategory foodCategory) {
        if (foodCategory == FoodCategory.ALL) {
            return foodsRepository.findsRandomFoods(num);
        }
        return foodsRepository.findsSpecificFoods(foodCategory.getValue(), num);
    }

    public Food getFood(String foodName) throws IOException {
        if (apiKey == null) {
            throw new IOException("API Keys are null");
        }
        List<String[]> apiKeys = extractApiKeys(apiKey);
        int maxTries = apiKeys.size();
        int tries = 0;
        String responseBody = "";
        while (true) {
            try {
                String appId = apiKeys.get(tries)[0];
                String appKey = apiKeys.get(tries)[1];
                String remoteUser = apiKeys.get(tries)[2];
                responseBody = apiCall(foodName, appId, appKey, remoteUser);
                break;
            } catch (Exception e) {
                tries++;
                if (tries >= maxTries) {
                    throw new IOException("no more API calls available for today");
                }
            }
        }
        Food food = extractNutritionalValues(responseBody);
        if (food.getName() == null) {
            food.setName(foodName);
        }
        return food;
        }

    public List<String[]> extractApiKeys(String apiKeys) {
        List<String[]> extractedApiKeys = new ArrayList<>();
        String[] APIKeysArray = apiKeys.split(",");
        for (int i = 0; i < APIKeysArray.length/2; i++) {
            extractedApiKeys.add(new String[] {APIKeysArray[i*2], APIKeysArray[i*2+1], "0"});
            extractedApiKeys.add(new String[] {APIKeysArray[i*2], APIKeysArray[i*2+1], "1"});
        }
        return extractedApiKeys;
    }

    public String apiCall(String foodName, String appId, String appKey, String remoteUser) {
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

    public Food extractNutritionalValues(String apiResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(apiResponse, Map.class);
        List<Map<String, Object>> foods = (List<Map<String, Object>>) map.get("foods");
        Map<String, Object> food = foods.get(0);
        Map<String, String> photo = (Map<String, String>) food.get("photo");
        Number servingWeight = (Number) food.get("serving_weight_grams");
        Number calories = (Number) food.get("nf_calories");
        Number fat = (Number) food.get("nf_total_fat");
        Number protein = (Number) food.get("nf_protein");
        Number carbs = (Number) food.get("nf_total_carbohydrate");
        double servingWeightDouble = servingWeight.doubleValue();
        double caloriesDouble = calories.doubleValue();
        double fatDouble = fat.doubleValue();
        double proteinDouble = protein.doubleValue();
        double carbsDouble = carbs.doubleValue();
        String name = (String) food.get("food_name");
        if (name == null) {
            name = (String) food.get("foodName");
        }
        String imageLink = photo.get("highres");
        Map<String, Double> nutritional_values = new HashMap<>();
        nutritional_values.put("calories", (double) Math.round((caloriesDouble/servingWeightDouble * 100)));
        nutritional_values.put("fat", (double) Math.round((fatDouble / servingWeightDouble * 100)));
        nutritional_values.put("protein", (double) Math.round((proteinDouble / servingWeightDouble * 100)));
        nutritional_values.put("carbs", (double) Math.round((carbsDouble / servingWeightDouble * 100)));
        return new Food(name, nutritional_values, imageLink);
    }
}