package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("playerScores")
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {
    @Query("SELECT NEW ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard(ps.player_id, SUM(ps.score)) FROM PlayerScore ps GROUP BY ps.player_id ORDER BY SUM(ps.score) DESC")
    List<LeaderBoard> getGlobalLeaderboard();
    @Query("SELECT NEW ch.uzh.ifi.hase.soprafs23.entity.PlayerStatistics(ps.player_id,COUNT((CASE when ps.winner = null THEN 1 END)), COUNT((CASE when ps.winner is not null THEN 1 END)), MAX(ps.score), COUNT((CASE when ps.winner = true THEN 1 END)), (CASE WHEN (COUNT((CASE when ps.winner is not null THEN 1 END)) = 0) THEN 0.0 ELSE (SUM((CASE WHEN ps.winner is true THEN 1 ELSE 0 END)) * 1.0 / COUNT((CASE WHEN ps.winner is not null THEN 1 END))) END)) FROM PlayerScore ps WHERE ps.player_id = :playerId GROUP BY ps.player_id")
    PlayerStatistics getPlayerStatistics(@Param("playerId") long playerId);
}


