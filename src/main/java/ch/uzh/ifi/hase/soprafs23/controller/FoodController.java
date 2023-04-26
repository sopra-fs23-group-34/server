package ch.uzh.ifi.hase.soprafs23.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class FoodController{
    public String getFood() throws URISyntaxException, IOException, InterruptedException {
        String reqestFood = "burger";

        HttpRequest postFoodRequest = HttpRequest.newBuilder()
                .uri(new URI("https://trackapi.nutritionix.com/v2/natural/nutrients"))
                .headers("x-app-id", "9dd751e9","x-app-key","7470f45a98ccc467dc3c043b1f997cf4","x-remote-user-id","0" )
                .POST(HttpRequest.BodyPublishers.ofString("{\"query\":\"burger\"}"))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postFoodResponse= httpClient.send(postFoodRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(postFoodResponse.body());
        return postFoodResponse.body();
    }
}
