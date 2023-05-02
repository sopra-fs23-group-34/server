package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatistics {
    private Long user_id;
    private long gamesPlayed;
    private int highScore;
    private double winRatio;
    public PlayerStatistics(Long user_id, Long gamesPlayed, int highScore, double winRatio){
        this.user_id = user_id;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
        this.winRatio = winRatio;
    }



}