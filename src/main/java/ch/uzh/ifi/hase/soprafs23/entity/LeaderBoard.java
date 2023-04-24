package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaderBoard {
        private Long user_id;
        private Long totalScore;

        private String username;

        public LeaderBoard(Long user_id, Long totalScore){
            this.user_id = user_id;
            this.totalScore = totalScore;
        }



}
