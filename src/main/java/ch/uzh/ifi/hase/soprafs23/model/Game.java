package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    private Map<Long, Player> players;
    private int roundLimit;
    private FoodCategory foodCategory;
    private Notifier notifier;

    public Game(Map<Long, Player> players, GameConfig config, Notifier notifier){
        this.players = players;
        this.notifier = notifier;
        this.roundLimit = config.roundLimit();
        this.foodCategory = config.foodCategory();
    }

    public void run() throws InterruptedException {
        Scores scores = new Scores();
        for (int round=0; round < roundLimit; round ++){
            Round gameRound = new Round(notifier);
            Food food = gameRound.getRandomFood(foodCategory);
            gameRound.run(food);
            for (Player player: players.values()){
                Map<String, ArrayList<Map<String, Integer>>> roundScores =
                        scores.computeRoundScores(player.getGuesses(),
                        player.getUsername(),
                        food);
                notifier.publishRoundScores(player.getPlayer_id(), roundScores);
                notifier.publishGameScores(scores.getPlacement());
            }
            Thread.sleep(10000);
            // todo player gets guesses
            // scores get players as input and food -> computes scores
            // scores return round scores and final scores
        }
        notifier.publishFinalScores(scores.getPlacement());

        //notification, also when the food is selected
    }

}
