package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.service.FoodService;
import java.io.IOException;

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

    public void run(Food food, int roundTime, int roundNumber) throws InterruptedException {
        notifier.publishRoundStart(roundNumber);
        Thread.sleep(10);
        notifier.publishFood(food);
        for (int tick = roundTime; tick >= 0; tick --){
            notifier.publishTimer(tick);
            Thread.sleep(1000);
        }
        notifier.publishRoundScoreStart();
    }
}


