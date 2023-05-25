package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Round {

    private final Notifier notifier;
    private final FoodService foodService;

    public Round( Notifier notifier, FoodService foodService){
        this.notifier = notifier;
        this.foodService = foodService;
    }

    public Food getFood(String name) throws IOException {
        return foodService.getFood(name);
    }

    public void run(Food food, int roundTime, int currentRound, int maxRounds) throws InterruptedException {
        Map<String, Integer> round = new HashMap<>();
        // we add + 1 because in the backend we count from 0
        round.put("currentRound", currentRound + 1);
        round.put("maxRounds", maxRounds);
        notifier.publishRoundStart();
        Thread.sleep(10);
        notifier.publishRoundCounter(round);
        Thread.sleep(10);
        notifier.publishFood(food);
        for (int tick = roundTime; tick >= 0; tick --){
            notifier.publishTimer(tick);
            Thread.sleep(1000);
        }
        notifier.publishRoundScoreStart();
        Thread.sleep(10);
        notifier.publishRoundCounter(round);
    }
}


