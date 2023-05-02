package ch.uzh.ifi.hase.soprafs23.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "player_scores")
@Getter
@Setter
public class PlayerScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long player_id;
    @Column (nullable = false)
    private int score;
    @Column
    private Boolean winner;
}