package ch.uzh.ifi.hase.soprafs23.constant;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodMap {
    public String[] getRandomFoodList(int NumberOfItems){
        String Foods[];
        String fruits[] ={"apple", "banana"};
        HashMap<FoodCategory, String[]> foodMap = new HashMap<>();
        foodMap.put(FoodCategory.FRUITS,fruits);
        Foods = foodMap.get(FoodCategory.FRUITS);
        return Foods;
    }

}
