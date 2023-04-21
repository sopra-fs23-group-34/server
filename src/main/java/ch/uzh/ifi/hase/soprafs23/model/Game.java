package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<Long, Player> players;
    private final Map<String, Map> playerTotalScores;
    private FoodService foodService;
    private final int roundLimit;
    private final FoodCategory foodCategory;
    private final Notifier notifier;
    private List<String> foods = new ArrayList<>();
    private Map<String, Map> playerRoundScores;

    public Game(Map<Long, Player> players, GameConfig config, Notifier notifier, FoodService foodService){
        this.players = players;
        this.notifier = notifier;
        this.roundLimit = config.roundLimit();
        this.foodCategory = config.foodCategory();
        this.foodService = foodService;
    }

    public void run() throws InterruptedException, IOException {
        Scores scores = new Scores(playerRoundScores);

        // select random foods here, only name
        //foods = foodService.getRandomFoodsByCategory(foodCategory, roundLimit);
        foods.add("Banana");
        foods.add("Apple");
        foods.add("Strawberries");
        foods.add("Avocados");
        foods.add("Onion");
        foods.add("Potatoes");
        foods.add("Spinach");
        foods.add("Tomatoes");
        foods.add("Rice");
        foods.add("Eggs");


        for (int round=0; round < roundLimit; round ++){
            Round gameRound = new Round(notifier);
            Food food = gameRound.getRandomFood(FoodCategory.valueOf(foods.get(round)));
            gameRound.run(food);
            for (Player player: players.values()){
                Map<String, Map> roundScores =
                        scores.computeRoundScores(player.getGuesses(),
                        player.getUsername(),
                        food);
                /*
                Map<String, ArrayList<Map<String, Double>>> roundScores =
                        scores.computeRoundScores(player.getGuesses(),
                        player.getUsername(),
                        food);
                 */

            }
            //notifier.publishRoundScores(players.getPlayer_id(), playerTotalScroes);
            notifier.publishGameScores(scores.getPlacement());
            Thread.sleep(10000);
            // todo player gets guesses
            // scores get players as input and food -> computes scores
            // scores return round scores and final scores
        }
        notifier.publishFinalScoreStart();
        notifier.publishFinalScores(scores.getPlacement());

        //notification, also when the food is selected
    }

}
