package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyPlayer;

import java.util.ArrayList;

public class Game {
    private ArrayList<LobbyPlayer> player;
    private int roundLimit;
    private FoodCategory foodCategory;
    private Notifier notifier;


    public Game(ArrayList<LobbyPlayer> players, int roundLimit, FoodCategory foodCategory, Notifier notifier){
        this.player = players;
        this.roundLimit = roundLimit;
        this.foodCategory = foodCategory;
    }

    public void run(){
        Scores scores = new Scores();
        for (int round=0; round < roundLimit; round ++){
            Round gameRound = new Round(foodCategory);
            gameRound.run();
            scores.computeRoundScores(gameRound.getPoints());
        }
        scores.getFinalScores();
        //notification, also when the food is selected
    }







}
