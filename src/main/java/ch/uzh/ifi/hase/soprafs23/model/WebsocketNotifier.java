package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@AllArgsConstructor
public class WebsocketNotifier implements Notifier {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final String gameCode;

    public void publishTurnScores(Long user_id){
        int intUser_id = user_id.intValue();
        //send turn scores
        simpMessagingTemplate.convertAndSend("/players/messages/turnScore" + intUser_id, "lauch");
    };
    public void publishGameScores(){
        simpMessagingTemplate.convertAndSend("/lobbys/messages/gameScore/" + gameCode, "winner");
    };
    public void publishFood(Food food){
        simpMessagingTemplate.convertAndSend("/lobbys/messages/food/" + gameCode, food.getName());
    };
    public void publishTimer(int timer){
        simpMessagingTemplate.convertAndSend("/lobbys/messages/timer/" + gameCode, timer);
    };

}
