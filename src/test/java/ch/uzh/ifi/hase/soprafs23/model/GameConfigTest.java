package ch.uzh.ifi.hase.soprafs23.model;
import ch.uzh.ifi.hase.soprafs23.constant.Fruits;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import static org.junit.jupiter.api.Assertions.*;

public class GameConfigTest {
    @Test
    void testGetRoundLimit() {
        GameConfig config = new GameConfig();
        ReflectionTestUtils.setField(config,"roundLimit",10);
        assertEquals(10, config.getRoundLimit());
    }
    @Test
    void testGetFoodCategory() {
        GameConfig config = new GameConfig();
        ReflectionTestUtils.setField(config,"foodCategory", FoodCategory.FRUITS);
        assertEquals(FoodCategory.FRUITS, config.getFoodCategory());
    }
}
