package ch.uzh.ifi.hase.soprafs23.model;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Round {

    private final Notifier notifier;
    private FoodService foodService;


    public Round( Notifier notifier, FoodService foodService){
        this.notifier = notifier;
        this.foodService = foodService;
    }

    public Food getFood(String name) throws IOException {
        Food food = foodService.getFood(name);
        //Map<String, Double> nutritionValues = new HashMap<String, Double>();
        //nutritionValues.put("carbs", 100.0);
        //Food food = new Food(foodCategory.toString(), nutritionValues,"image");
        return food;
    }

    public void run(Food food) throws InterruptedException {
        notifier.publishRoundStart();
        notifier.publishFood(food);
        for (int tick = 10; tick >= 0; tick --){
            notifier.publishTimer(tick);
            Thread.sleep(1000);
        }
        notifier.publishRoundScoreStart();
    }
}


