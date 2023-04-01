package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;

import java.util.ArrayList;

public class Game {
    private ArrayList<LobbyPlayer> players;
    private int roundLimit;
    private FoodCategory foodCategory;
    private Notifier notifier;

    public Game(ArrayList<LobbyPlayer> players, GameConfig config, Notifier notifier){
        this.players = players;
        this.notifier = notifier;
        this.roundLimit = config.roundLimit();
        this.foodCategory = config.foodCategory();
    }

    public void run() throws InterruptedException {
        Scores scores = new Scores();
        for (int round=0; round < roundLimit; round ++){
            System.out.println("round nr: " +  (1 + round));
            Round gameRound = new Round(foodCategory, notifier);
            gameRound.run();
            scores.computeRoundScores(gameRound.getPoints());
            // todo player gets guesses
            // scores get players as input and food -> computes scores
            // scores return round scores and final scores
        }
        scores.getFinalScores();
        //notification, also when the food is selected
    }

}
