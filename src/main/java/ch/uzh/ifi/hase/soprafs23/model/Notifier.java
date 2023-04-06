package ch.uzh.ifi.hase.soprafs23.model;

import java.util.ArrayList;
import java.util.Map;

public interface Notifier {

    public void publishRoundScores(Long user_id,Map<String, ArrayList<Map<String, Integer>>> roundScores);

    public void publishGameScores(Map<String, Integer> placement);
    public void publishFinalScores(Map<String, Integer> placement);

    public void publishFood(Food food);

    public void publishTimer(int timer);

    public void publishRoundStart();

    public void publishRoundEnd();

}
