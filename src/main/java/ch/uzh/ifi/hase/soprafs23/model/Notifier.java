package ch.uzh.ifi.hase.soprafs23.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Notifier {

    private final String lobbyId;

    public void publishTurnScores(){
        //send turn scores
    }

    public void publishGameScores(){
        //send final scores
    }



}
