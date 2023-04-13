package ch.uzh.ifi.hase.soprafs23.model;

import java.util.ArrayList;
import java.util.Map;

public interface Notifier {

    void publishRoundScores(Long user_id,Map<String, ArrayList<Map<String, Double>>> roundScores);

    void publishGameScores(Map<String, Integer> placement);
    void publishFinalScores(Map<String, Integer> placement);

    void publishFood(Food food);

    void publishTimer(int timer);
    void publishRoundStart();
    void publishFinalScoreStart();
    void publishRoundScoreStart();
    void error(String msg, String topic, Long user_id);


}
