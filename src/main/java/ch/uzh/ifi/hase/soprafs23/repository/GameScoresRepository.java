package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.GameScores;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("gameScoresRepository")

public interface GameScoresRepository extends JpaRepository<GameScores, Long> {
    GameScores findByIdAndDate(Long id, Date date);
}
