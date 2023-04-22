package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("foodsRepository")
public interface FoodsRepository extends JpaRepository<Foods, Long> {
    Foods findByName(String name);

    Foods findByCategory(FoodCategory category);


    @Query(value = "SELECT name FROM foods WHERE category = :foodCategory AND highres = 1 ORDER BY RAND() LIMIT :num", nativeQuery = true)
    List<String> findsRandomFoods(@Param("foodCategory") String foodCategory, @Param("num") int num);

}
