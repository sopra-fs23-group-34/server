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
        Food food = foodService.getFood(name);
        return food;
    }

    public void run(Food food, int roundTime) throws InterruptedException {
        notifier.publishRoundStart();
        Thread.sleep(10);
        notifier.publishFood(food);
        for (int tick = roundTime; tick >= 0; tick --){
            notifier.publishTimer(tick);
            Thread.sleep(1000);
        }
        notifier.publishRoundScoreStart();
    }
}


