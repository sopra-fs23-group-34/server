package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "game_scores")
@Getter
@Setter
public class GameScores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    private Long player_id;

    @Column
    private Date gameDate;

    @Column
    private Integer score;

}

