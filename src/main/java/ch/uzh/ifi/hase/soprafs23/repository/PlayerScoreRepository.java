package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.Foods;
import ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard;
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
    @Query("SELECT NEW ch.uzh.ifi.hase.soprafs23.entity.LeaderBoard(ps.player_id, SUM(ps.score)) FROM PlayerScore ps GROUP BY ps.player_id ORDER BY SUM(ps.score) DESC")
    List<LeaderBoard> getGlobalLeaderboard();
}


