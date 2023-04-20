package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import ch.uzh.ifi.hase.soprafs23.entity.Foods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("foodRepository")
public interface FoodRepository extends JpaRepository<Foods, Long> {
    Foods findByName(String name);

    Foods findByCategory(FoodCategory category);


}
