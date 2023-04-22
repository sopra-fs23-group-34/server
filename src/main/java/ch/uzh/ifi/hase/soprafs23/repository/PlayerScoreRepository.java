package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.Foods;
import ch.uzh.ifi.hase.soprafs23.entity.PlayerScore;
import ch.uzh.ifi.hase.soprafs23.model.Player;
import com.mysql.cj.xdevapi.SqlResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

@Repository("playerScores")
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {
    @Query(value = "SELECT player_id as user_id, SUM(score) AS total_score FROM player_scores GROUP BY player_id ORDER BY total_score DESC", nativeQuery = true)
    List<Map<String, Integer>> getGlobalLeaderboard();
}
