package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Notifier {

    private final String lobbyId;

    public void publishTurnScores(){
        //send turn scores
    }

    public void publishGameScores(){
        //send final scores
    }

    public void publishFood(Food food){
        System.out.println("aosdfoaijs");
    }

    public void publishTimer(int timer){
        System.out.println(timer);
    }


    public ArrayList<Result> getResult(){
        Map<String, Integer> guesses = new HashMap<>();
        guesses.put("carbs",3);
        Result res = new Result(1L,guesses);
        ArrayList<Result> results = new ArrayList<>();
        results.add(res);
        return results;
    }


}
