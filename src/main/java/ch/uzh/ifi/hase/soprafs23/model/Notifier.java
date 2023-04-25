package ch.uzh.ifi.hase.soprafs23.model;

import java.util.ArrayList;
import java.util.Map;

public interface Notifier {

    void publishRoundScores(Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScore);

    void publishGameScores(Map<String, Integer> placement);
    void publishFinalScores(Map<String, Integer> placement);

    void publishFood(Food food);

    void publishTimer(int timer);
    void publishRoundStart();
    void publishFinalScoreStart();
    void publishRoundScoreStart();
    void error(String msg, String topic, Long user_id);


}
