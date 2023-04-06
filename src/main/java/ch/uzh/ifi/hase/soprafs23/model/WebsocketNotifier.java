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

    public void publishRoundScores(Long user_id, Map<String, ArrayList<Map<String, Integer>>> roundScores){
        int intUser_id = user_id.intValue();
        RoundScoreMessage roundScoreMessage =  new RoundScoreMessage("RoundScore", roundScores );
        simpMessagingTemplate.convertAndSend(WebsocketConfig.simpleBrokerDestination + "/players/" + intUser_id, roundScoreMessage);
    };
    public void publishGameScores(Map<String, Integer> placement){
        MapMessage gameScore = new MapMessage("GameScore", placement);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies + gameCode, gameScore);
    };
    public void publishFinalScores(Map<String, Integer> placement){
        MapMessage finalScore = new MapMessage("FinalScore", placement);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies  + gameCode, finalScore);
    }
    public void publishFood(Food food){
        StringMessage foodItem = new StringMessage("Food", food.getName());
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies  + gameCode, foodItem);
    };
    public void publishTimer(int timer){
        IntMessage timerCounter = new IntMessage("Timer",timer);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies  + gameCode, timerCounter);
    };

    public void publishRoundStart(){
        BooleanMessage roundEnd = new BooleanMessage("RoundStart", true);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies  + gameCode, roundEnd);
    }

    public void publishRoundScoreStart(){
        BooleanMessage roundEnd = new BooleanMessage("RoundScoreStart", true);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies  + gameCode, roundEnd);
    }

    public void publishFinalScoreStart(){
        BooleanMessage roundEnd = new BooleanMessage("FinalScoreStart", true);
        simpMessagingTemplate.convertAndSend(WebsocketConfig.lobbies  + gameCode, roundEnd);
    }


}
