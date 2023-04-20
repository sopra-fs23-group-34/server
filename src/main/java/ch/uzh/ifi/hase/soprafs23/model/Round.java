package ch.uzh.ifi.hase.soprafs23.model;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.service.FoodService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Round {

    private final Notifier notifier;
    private FoodService foodService;


    public Round( Notifier notifier){
        this.notifier = notifier;
    }

    public Food getRandomFood(FoodCategory foodCategory) throws IOException {
        FoodService foodService = new FoodService();
        foodService.getFood("pizza");
        //toDo food richtig einfügen
        Map<String, Double> nutritionValues = new HashMap<String, Double>();
        nutritionValues.put("carbs", 100.0);
        Food food = new Food(foodCategory.toString(), nutritionValues,"image");
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


