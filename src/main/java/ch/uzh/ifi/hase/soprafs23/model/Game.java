package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<Long, Player> players;
    private FoodService foodService;
    private final int roundLimit;
    private final FoodCategory foodCategory;
    private final Notifier notifier;
    private List<String> foods = new ArrayList<>();

    public Game(Map<Long, Player> players, GameConfig config, Notifier notifier, FoodService foodService){
        this.players = players;
        this.notifier = notifier;
        this.roundLimit = config.getRoundLimit();
        this.foodCategory = config.getFoodCategory();
        this.foodService = foodService;
    }

    public Scores run() throws InterruptedException, IOException {
        Scores scores = new Scores();
        foods = foodService.getRandomFoods(roundLimit,foodCategory);
        for (int round=0; round < roundLimit; round ++){
            Round gameRound = new Round(notifier, foodService);
            Food food = gameRound.getFood(foods.get(round));
            gameRound.run(food);
            for (Player player: players.values()){
                        scores.updateRoundScore(player.getGuesses(),
                        player.getUsername(),
                        food);
            }
            notifier.publishRoundScores(scores.getRoundScore());
            notifier.publishGameScores(scores.getPlacement());
            Thread.sleep(10000);
        }
        notifier.publishFinalScoreStart();
        notifier.publishFinalScores(scores.getPlacement());
        return scores;
    }



}
