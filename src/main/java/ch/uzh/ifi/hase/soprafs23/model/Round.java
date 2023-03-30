package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;

import java.util.HashMap;
import java.util.Map;

public class Round {

    private final FoodCategory foodCategory;
    public Round(FoodCategory foodCategory){
        this.foodCategory = foodCategory;
    }

    public Map<Long, Map<String,Integer>> getPoints(){
        Map<Long, Map<String,Integer>> score = new HashMap<>();
        return score;
    }

    private Food getRandomFood(){
        // api request here
        Food food = new Food();
        return food;
    }

    public void run(){
        Food food = getRandomFood();



    }
}


