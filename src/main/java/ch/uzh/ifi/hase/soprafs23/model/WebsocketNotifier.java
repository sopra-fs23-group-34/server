package ch.uzh.ifi.hase.soprafs23.model;

import ch.uzh.ifi.hase.soprafs23.config.WebsocketConfig;
import ch.uzh.ifi.hase.soprafs23.messages.*;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
public class WebsocketNotifier implements Notifier {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final String gameCode;

    public void publishRoundScores( Map<String,Map<String,ArrayList<Map<String,Double>>>> roundScores){
        RoundScoreMessage roundScoreMessage =  new RoundScoreMessage("RoundScore", roundScores );
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES + gameCode, roundScoreMessage);
    }
    public void publishGameScores(Map<String, Integer> placement){
        MapMessage gameScore = new MapMessage("GameScore", placement);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES + gameCode, gameScore);
    }
    public void publishFinalScores(Map<String, Integer> placement){
        MapMessage finalScore = new MapMessage("FinalScore", placement);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES  + gameCode, finalScore);
    }
    public void publishFood(Food food){
        FoodMessage foodItem = new FoodMessage("Food", food.getName(),food.getImage());
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES  + gameCode, foodItem);
    }
    public void publishTimer(int timer){
        IntMessage timerCounter = new IntMessage("Timer",timer);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES  + gameCode, timerCounter);
    }
    public void publishRoundStart(){
        BooleanMessage msg = new BooleanMessage("RoundStart", true);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES  + gameCode, msg);
    }

    public void publishRoundScoreStart(){
        BooleanMessage msg = new BooleanMessage("RoundScoreStart", true);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES  + gameCode, msg);
    }

    public void publishFinalScoreStart(){
        BooleanMessage msg = new BooleanMessage("FinalScoreStart", true);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES  + gameCode, msg);
    }

    @Override
    public void publishRoundCounter(Map<String, Integer> round) {
        MapMessage msg = new MapMessage("RoundCounter", round);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.LOBBIES + gameCode, msg);
    }
}
