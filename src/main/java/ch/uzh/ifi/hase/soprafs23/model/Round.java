package ch.uzh.ifi.hase.soprafs23.model;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import java.util.HashMap;
import java.util.Map;

public class Round {

    private final Notifier notifier;


    public Round( Notifier notifier){
        this.notifier = notifier;
    }

    public Food getRandomFood(FoodCategory foodCategory){
        // api request here
        Map<String, Integer> nutritionValues = new HashMap<String, Integer>();
        nutritionValues.put("carbs", 100);
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


