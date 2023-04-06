package ch.uzh.ifi.hase.soprafs23.service;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class FoodService {
    public void getFood() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://trackapi.nutritionix.com/v2/natural/nutrients"))
                .header("x-app-id","9dd751e9")
                .header("x-app-key","7470f45a98ccc467dc3c043b1f997cf4")
                .header("x-remote-user-id","0")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"query\":\"burger\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JSONObject = response.body();
        int count = responseString.length();
        System.out.println(count);
        System.out.println(responseString.indexOf("food_name"));
    }
}
