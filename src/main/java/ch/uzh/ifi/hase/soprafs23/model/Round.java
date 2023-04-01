package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;

import java.util.HashMap;
import java.util.Map;

public class Round {

    private final FoodCategory foodCategory;
    private final Notifier notifier;
    public Round(FoodCategory foodCategory, Notifier notifier){
        this.foodCategory = foodCategory;
        this.notifier = notifier;
    }

    public Map<Long, Map<String,Integer>> getPoints(){
        Map<Long, Map<String,Integer>> score = new HashMap<>();
        return score;
    }

    private Food getRandomFood(FoodCategory foodCategory){
        // api request here
        Map<String, Integer> nutritionValues = new HashMap<String, Integer>();
        nutritionValues.put("carbs", 100);
        Food food = new Food("Apfel", nutritionValues,"image");
        return food;
    }

    public void run() throws InterruptedException {
        //ArrayList<Result>
        Food food = getRandomFood(foodCategory);
        notifier.publishFood(food);
        for (int tick = 10; tick > 0; tick --){
            notifier.publishTimer(tick);
            Thread.sleep(1000);

        }

    }
}


