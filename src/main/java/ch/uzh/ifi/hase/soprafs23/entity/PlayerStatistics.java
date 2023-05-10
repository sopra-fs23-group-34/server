package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatistics {
    private Long userId;
    private long gamesPlayed;
    private int highScore;
    private long gamesWon;
    private double winRatio;
    public PlayerStatistics(Long userId, Long gamesPlayed, int highScore, long gamesWon, double winRatio){
        this.userId = userId;
        this.gamesPlayed = gamesPlayed;
        this.highScore = highScore;
        this.winRatio = winRatio;
        this.gamesWon = gamesWon;
    }



}