package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("foodsRepository")
public interface FoodsRepository extends JpaRepository<Foods, Long> {
    @Query(value = "SELECT DISTINCT name FROM foods WHERE category = :foodCategory AND highres = 1 ORDER BY RAND() LIMIT :num", nativeQuery = true)
    List<String> findsSpecificFoods(@Param("foodCategory") String foodCategory, @Param("num") int num);

    @Query(value = "SELECT DISTINCT name FROM foods WHERE highres = 1 ORDER BY RAND() LIMIT :num", nativeQuery = true)
    List<String> findsRandomFoods(@Param("num") int num);

}
