package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.model.Food;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import nonapi.io.github.classgraph.json.JSONSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
public class FoodService {

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
        Double calories = (Double) food.get("nf_calories");
        Double fat = (Double) food.get("nf_total_fat");
        Double protein = (Double) food.get("nf_protein");
        Double carbs = (Double) food.get("nf_total_carbohydrate");
        String name = (String) food.get("food_name");
        String image_link = (String) photo.get("highres");

        System.out.println("name: " + name);
        System.out.println("calories: " + calories);
        System.out.println("fat: " + fat);
        System.out.println("protein: " + protein);
        System.out.println("carbs: " + carbs);
        System.out.println("image link: " + image_link);

        Map<String, Double> nutritional_values = new HashMap<>();
        nutritional_values.put("calories", calories);
        nutritional_values.put("fat", fat);
        nutritional_values.put("protein", protein);
        nutritional_values.put("carbs", carbs);

        Food apiFood = new Food(name, nutritional_values, image_link);
        return apiFood;


        }
}
