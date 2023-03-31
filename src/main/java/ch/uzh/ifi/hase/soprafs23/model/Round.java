package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;

import java.util.ArrayList;
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
        Food food = new Food();
        return food;
    }

    public ArrayList<Result> run() throws InterruptedException {
        Food food = getRandomFood(foodCategory);
        notifier.publishFood(food);
        for (int tick = 10; tick > 0; tick ++){
            notifier.publishTimer(tick);
            Thread.sleep(1000);
        }
        return notifier.getResult();
    }
}


