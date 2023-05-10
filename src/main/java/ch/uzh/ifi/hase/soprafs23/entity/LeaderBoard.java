package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderBoard {
        private Long userId;
        private Long totalScore;

        private String username;

        public LeaderBoard(Long userId, Long totalScore){
            this.userId = userId;
            this.totalScore = totalScore;
        }

}
