package ch.uzh.ifi.hase.soprafs23.service;
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

import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
public class FoodService {

    public void getFood() throws IOException, InterruptedException {


        String apiUrl = "https://trackapi.nutritionix.com/v2/natural/nutrients";
        String appId = "9dd751e9";
        String appKey = "7470f45a98ccc467dc3c043b1f997cf4";
        String remoteUser = "0";
        String query = "banana";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAccept((List<MediaType>) MediaType.APPLICATION_JSON);
        headers.set("x-app-id", appId);
        headers.set("x-app-key", appKey);
        headers.set("x-remote-user-id", remoteUser);
        Map<String, String> body = new HashMap<>();
        body.put("query", query);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        String responseBody = response.getBody();
        System.out.println("-----------------");
        System.out.println(responseBody);
        System.out.println("-----------------");
        System.out.println(responseBody.getClass());
        System.out.println("-------------------");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);

        List<Map<String, Object>> foods = (List<Map<String, Object>>) map.get("foods");
        Map<String, Object> food = foods.get(0);
        Double calories = (Double) food.get("nf_calories");
        Double fat = (Double) food.get("nf_total_fat");
        Double protein = (Double) food.get("nf_protein");
        Double carbs = (Double) food.get("nf_total_carbohydrate");
        String name = (String) food.get("food_name");

        System.out.println("name: " + name);
        System.out.println("Calories: " + calories);
        System.out.println("fat: " + fat);
        System.out.println("protein: " + protein);
        System.out.println("carbs: " + carbs);

        ObjectMapper objectMapper2 = new ObjectMapper();
        Map<String, Object> map2 = objectMapper.readValue(responseBody, Map.class);

        List<Map<String, Object>> foods1 = (List<Map<String, Object>>) map.get("foods");
        Map<String, Object> altMeasure = foods1.get(0);
        String highres = (String) altMeasure.get("photo");
        System.out.println("picture link: " + highres);
        System.out.println("--------------------");
        System.out.println("-----------------");
        System.out.println(altMeasure);
        System.out.println("-----------------");














        /*
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://trackapi.nutritionix.com/v2/natural/nutrients"))
                .header("x-app-id","9dd751e9")
                .header("x-app-key","7470f45a98ccc467dc3c043b1f997cf4")
                .header("x-remote-user-id","0")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"query\":\"burger\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("-----------------");
        System.out.println(response.body());
        System.out.println("------------------");
        System.out.println(response);
        System.out.println("------------------");
        System.out.println(response.body().getClass());
        System.out.println("-------------------");



        JSONObject = response.body();
        int count = responseString.length();
        System.out.println(count);
        System.out.println(responseString.indexOf("food_name"));

         */

        }



}
