package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import ch.uzh.ifi.hase.soprafs23.storage.LobbyStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<Long, Player> players;
    private final FoodService foodService;
    private final int roundLimit;
    private final int roundTime;
    private final FoodCategory foodCategory;
    private final Notifier notifier;
    private int currentRound;
    private Scores scores;
    private UserService userService;


    private List<String> foods = new ArrayList<>();

    public Game(Map<Long, Player> players, GameConfig config, Notifier notifier, FoodService foodService, UserService userService){
        this.players = players;
        this.notifier = notifier;
        this.roundLimit = config.getRoundLimit();
        this.foodCategory = config.getFoodCategory();
        this.foodService = foodService;
        this.roundTime = config.getTimerLength();
        this.currentRound = 0;
        this.scores = new Scores();
        foods = foodService.getRandomFoods(roundLimit,foodCategory);
        this. userService = userService;
    }
    public void publishRound() throws InterruptedException, IOException {

        if (currentRound<roundLimit){
            Round gameRound = new Round(notifier, foodService);
            Food food = gameRound.getFood(foods.get(currentRound));
            gameRound.run(food, roundTime, currentRound ,roundLimit);
            for (Player player: players.values()){
                if (player.getGuesses() != null) {
                    scores.updateRoundScore(player.getGuesses(),
                            player.getUsername(),
                            food);
                }
                player.setGuesses(null);
            }
            notifier.publishRoundScores(scores.getRoundScore());
            notifier.publishGameScores(scores.getPlacement());
        }
        else{

            userService.updateScores(scores);
            notifier.publishFinalScoreStart();
            notifier.publishFinalScores(scores.getPlacement());
        }
        currentRound += 1;
    }
    public Scores getScores(){
        return scores;
    }


}
