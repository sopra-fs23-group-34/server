package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@AllArgsConstructor
public class WebsocketNotifier implements Notifier {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final String gameCode;

    public void publishTurnScores(){
        //send turn scores
    };
    public void publishGameScores(){

    };
    public void publishFood(Food food){
    System.out.println("i am food");
    };

    public void publishTimer(int timer){
        System.out.println(timer);
    };

}
